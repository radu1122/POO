import com.fasterxml.jackson.databind.ObjectMapper;
import dataset.Consumer;
import dataset.Consumers;
import dataset.Distributors;
import dataset.Entity;
import dataset.EntityFactory;
import input.ConsumerInput;
import input.CostChange;
import input.DistributorInput;
import input.InputData;
import input.MonthlyUpdateInput;

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
        String inputFile = args[0];
        String outputFile = args[1];

        ObjectMapper objectMapper = new ObjectMapper();
        InputData inputData = objectMapper.readValue(new File(inputFile), InputData.class);

        Consumers consumers = Consumers.getInstance();
        Distributors distributors = Distributors.getInstance();

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
                    distributor.getInitialProductionCost()));
        }

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

            // update distributors cost
            for (CostChange distributor : currentRound.getCostsChanges()) {
                distributors.updateCosts(distributor.getId(), distributor.getInfrastructureCost(),
                        distributor.getProductionCost());
            }

            distributors.computePrices();

            if (!distributors.getHasDistributors()) {
                break;
            }

            consumers.checkContracts();

            consumers.makeContracts();

            consumers.generateBills();

            consumers.payBills();

            distributors.payBills();

        }

        distributors.prepareExport();

        PrintWriter writer = new PrintWriter(outputFile, StandardCharsets.UTF_8);
        writer.println("{\"consumers\":" + consumers + ","
                + "\"distributors\":" + distributors + "}");
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
    }
}
