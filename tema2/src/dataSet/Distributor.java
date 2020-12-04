package dataSet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Distributor {
    private int id;
    private int contractLength;
    private int budget;
    private int infrastructureCost;
    private int productionCost;
    private boolean isBankrupt = false;
    private int contractCost;
    private int profit;
    private int numberOfClients = 0;
    private int finalProductionCost;
    private int monthlyClients = 0;
    private final LinkedHashMap<Integer, Contract> contracts = new LinkedHashMap<>();
    private final ArrayList<Contract> contractsToExport = new ArrayList<>();


    public Distributor(int id, int contractLength, int budget, int infrastructureCost, int productionCost) {
        this.id = id;
        this.contractLength = contractLength;
        this.budget = budget;
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
        this.computePrices();
    }

    public void updatesCosts(int infrastructureCost, int productionCost) {
        this.infrastructureCost = infrastructureCost;
        this.productionCost = productionCost;
        this.computePrices();
    }

    public void addContract(int customerId) {

        this.numberOfClients++;
        contracts.put(customerId, new Contract(customerId, this.contractCost, this.contractLength));
        Consumer consumer = Consumers.getInstance().getConsumers().get(customerId);
        consumer.setHasContract(true);
        consumer.setContractPrice(this.contractCost);
        consumer.setRemainedContractMonths(this.contractLength);
        consumer.setDistributorId(this.id);
        consumer.setDistributorProfit(this.profit);
    }

    public void clientBankrupt(int clientId) {
        numberOfClients--;
        contracts.remove(clientId);
    }

    public void declareBankruptcy() {
        this.isBankrupt = true;
        for (Map.Entry<Integer, Contract> entry : contracts.entrySet()) {
            int key = entry.getKey();
            Consumers.getInstance().getConsumers().get(key).distributorBankrupt();
        }
        contracts.clear();

    }

    public void payBills() {
        computePricesBills();
        if (id == 1) {
//            System.out.println("buget inainte "+ this.budget);
//            System.out.println("nr clienti care platesc " + this.monthlyClients);
//            System.out.println("costa infra " + this.infrastructureCost);
//            System.out.println("cost prod " + this.finalProductionCost);
        }
        if (this.budget - this.infrastructureCost - this.finalProductionCost < 0) {
            this.declareBankruptcy();
        }
        this.budget = this.budget - this.infrastructureCost - this.finalProductionCost;
        monthlyClients = 0;
        if (id == 1) {
//            System.out.println("buget dupa "+ this.budget);
        }
    }

    public void receivePayment(int invoice) {
//        System.out.println("Receive Payment " + id + " -> " + invoice);
        this.budget = this.budget + invoice;
    }

    public void computePrices() {
        this.finalProductionCost = this.productionCost * this.numberOfClients;
        this.profit = (int) Math.round(Math.floor(0.2 * this.productionCost));
        if (this.numberOfClients == 0) {
            this.contractCost = infrastructureCost + this.productionCost + this.profit;
        } else {
            this.contractCost = (int) Math.round(Math.floor((double)this.infrastructureCost / (double)this.numberOfClients) +
                    this.productionCost + this.profit);
        }
    }

    public void computePricesBills() {
        this.finalProductionCost = this.productionCost * this.monthlyClients;
    }

    public LinkedHashMap<Integer, Contract> getContracts() {
        return contracts;
    }

    public int getContractCost() {
        return contractCost;
    }

    public void setContractCost(int contractCost) {
        this.contractCost = contractCost;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getMonthlyClients() {
        return monthlyClients;
    }

    public void setMonthlyClients() {
        this.monthlyClients = numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public int getNumberOfClients() {
        return this.numberOfClients;
    }

    public int getFinalProductionCost() {
        return finalProductionCost;
    }

    public void setFinalProductionCost(int finalProductionCost) {
        this.finalProductionCost = finalProductionCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(int contractLength) {
        this.contractLength = contractLength;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public void exportContracts(){
        for (Map.Entry<Integer, Contract> entry : contracts.entrySet()) {
            contractsToExport.add(entry.getValue());
        }
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"budget\":" + budget +
                ", \"isBankrupt\":" + isBankrupt +
                ", \"contracts\":" + contractsToExport +
                '}';
    }
}
