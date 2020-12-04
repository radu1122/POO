import com.fasterxml.jackson.databind.ObjectMapper;
import dataSet.Consumer;
import dataSet.Consumers;
import dataSet.Distributor;
import dataSet.Distributors;
import input.*;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws Exception {
        String inputFile = args[0];
        String outputFile = args[1];

        System.out.println(inputFile);
        System.out.println(outputFile);

        ObjectMapper objectMapper = new ObjectMapper();
        InputData inputData = objectMapper.readValue(new File(inputFile), InputData.class);

        Consumers consumers = Consumers.getInstance();
        Distributors distributors = Distributors.getInstance();

        // add initial consumers to the dataSet consumers
        for (ConsumerInput consumer : inputData.getInitialData().getConsumers()) {
            consumers.addConsumer(new Consumer(consumer.getId(), consumer.getInitialBudget(),
                    consumer.getMonthlyIncome()));
        }

        // add initial distributors to the dataSet distributors
        for (DistributorInput distributor : inputData.getInitialData().getDistributors()) {
            distributors.addDistributor(new Distributor(distributor.getId(), distributor.getContractLength(),
                    distributor.getInitialBudget(), distributor.getInitialInfrastructureCost(),
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
            for(ConsumerInput newConsumer : currentRound.getNewConsumers()) {
                consumers.addConsumer(new Consumer(newConsumer.getId(), newConsumer.getInitialBudget(),
                        newConsumer.getMonthlyIncome()));
            }

            // update distributors cost
            for (CostChange distributor : currentRound.getCostsChanges()) {
                distributors.updateCosts(distributor.getId(), distributor.getInfrastructureCost(),
                        distributor.getProductionCost());
            }

            distributors.computePrices();

            consumers.checkContracts();

            consumers.makeContracts();

            consumers.generateBills();

            consumers.payBills();

            distributors.payBills();

            System.out.println("{\"consumers\":" + consumers + "," +
                    "\"distributors\":" + distributors + "}");
        }

        distributors.prepareExport();
        System.out.println("{\"consumers\":" + consumers + "," +
                "\"distributors\":" + distributors + "}");

        PrintWriter writer = new PrintWriter(outputFile, StandardCharsets.UTF_8);
        writer.println("{\"consumers\":" + consumers + "," +
                "\"distributors\":" + distributors + "}");
        writer.close();
        resetSingleton();
    }

    public static void resetSingleton() {
        Distributors.getInstance().getDistributors().clear();
        Consumers.getInstance().getConsumers().clear();
    }
}
