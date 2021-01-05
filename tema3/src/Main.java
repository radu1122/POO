import com.fasterxml.jackson.databind.ObjectMapper;
import dataset.*;
import input.*;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public final class Main {

    private Main() {
    }

    /**
     * MAIN
     *
     */
    public static void main(final String[] args) throws Exception {
        resetSingleton();
        String inputFile = args[0];
        String outputFile = args[1];

        ObjectMapper objectMapper = new ObjectMapper();
        InputData inputData = objectMapper.readValue(new File(inputFile), InputData.class);

        Consumers consumers = Consumers.getInstance();
        Distributors distributors = Distributors.getInstance();
        Producers producers = Producers.getInstance();

        // add initial consumers to the dataSet consumers
        for (ConsumerInput consumer : inputData.getInitialData().getConsumers()) {
            Entity entity = EntityFactory.getEntity("CONSUMER");
            consumers.addConsumer((Consumer) entity.
                    populateEntity(consumer.getId(), consumer.getInitialBudget(),
                    consumer.getMonthlyIncome()));
        }

        // add initial distributors to the dataSet distributors
        for (DistributorInput distributor : inputData.getInitialData().getDistributors()) {
            Entity entity = EntityFactory.getEntity("DISTRIBUTOR");

            distributors.addDistributor(entity.populateEntity(distributor.getId(),
                    distributor.getContractLength(),
                    distributor.getInitialBudget(),
                    distributor.getInitialInfrastructureCost(),
                    distributor.getEnergyNeededKW(),
                    distributor.getProducerStrategy()));
        }

        // add initial producers to the dataSet producers
        for (ProducerInput producer : inputData.getInitialData().getProducers()) {
            producers.addProducer(new Producer(producer.getId(),
                    producer.getEnergyType(), producer.getMaxDistributors(),
                    producer.getPriceKW(), producer.getEnergyPerDistributor()));
        }

        distributors.selectProducers();

        distributors.computePrices();

        consumers.checkContracts();

        consumers.makeContracts();

        consumers.generateBills();

        consumers.payBills();

        distributors.payBills();

        // iterate rounds
        for (MonthlyUpdateInput currentRound : inputData.getMonthlyUpdates()) {
            // add new consumers
            for (ConsumerInput newConsumer : currentRound.getNewConsumers()) {
                Entity entity = EntityFactory.getEntity("CONSUMER");
                consumers.addConsumer((Consumer) entity.populateEntity(newConsumer.getId(),
                        newConsumer.getInitialBudget(),
                        newConsumer.getMonthlyIncome()));
            }

            // update infrastructure cost
            for (DistributorChange distributor : currentRound.getDistributorChanges()) {
                distributors.updateCosts(distributor.getId(), distributor.getInfrastructureCost());
            }

            // update energyPerDistributor on producers
            for (ProducerChange producer : currentRound.getProducerChanges()) {
                producers.updateCosts(producer.getId(), producer.getEnergyPerDistributor());

            }

            distributors.computePrices(); // to be changed

            if (!distributors.getHasDistributors()) {
                break;
            }

            producers.computeMonthlyStats();

            consumers.checkContracts();

            consumers.makeContracts();

            consumers.generateBills();

            consumers.payBills();

            distributors.payBills();

//            System.out.println("{\"consumers\":" + consumers + ","
//                    + "\"distributors\":" + distributors + ","
//                    + "\"energyProducers\":" + producers + "}");
        }

        distributors.prepareExport();

        System.out.println("{\"consumers\":" + consumers + ","
                + "\"distributors\":" + distributors + ","
                + "\"energyProducers\":" + producers + "}");

        PrintWriter writer = new PrintWriter(outputFile, StandardCharsets.UTF_8);
        writer.println("{\"consumers\":" + consumers + ","
                + "\"distributors\":" + distributors + ","
                + "\"energyProducers\":" + producers + "}");
        writer.close();
        resetSingleton();
    }

    /**
     * reset all DataSets
     *
     */
    public static void resetSingleton() {
        Distributors.getInstance().getDistributors().clear();
        Consumers.getInstance().getConsumers().clear();
        Producers.getInstance().getProducers().clear();
    }
}
