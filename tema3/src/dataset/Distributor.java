package dataset;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Distributor extends Entity {
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

    public Distributor() {

    }

    /**
     * populate the class
     *
     */
    public Distributor populateEntity(final int idX, final int contractLengthX, final int budgetX,
                                      final int infrastructureCostX, final int productionCostX) {
        this.id = idX;
        this.contractLength = contractLengthX;
        this.budget = budgetX;
        this.infrastructureCost = infrastructureCostX;
        this.productionCost = productionCostX;
        this.computePrices();
        return this;
    }

    /**
     * update distributor prices
     *
     */
    public void updatesCosts(final int infrastructureCostX, final int productionCostX) {
        this.infrastructureCost = infrastructureCostX;
        this.productionCost = productionCostX;
        this.computePrices();
    }

    /**
     * add new contract to the dataset
     *
     */
    public void addContract(final int customerId) {
        this.numberOfClients++;
        contracts.put(customerId, new Contract(customerId, this.contractCost, this.contractLength));
        Consumer consumer = Consumers.getInstance().getConsumers().get(customerId);
        consumer.setHasContract(true);
        consumer.setContractPrice(this.contractCost);
        consumer.setRemainedContractMonths(this.contractLength);
        consumer.setDistributorId(this.id);
        consumer.setDistributorProfit(this.profit);
    }

    /**
     * trigger for client bankrupt and delete the contract
     *
     */
    public void clientBankrupt(final int clientId) {
        numberOfClients--;
        contracts.remove(clientId);
    }

    /**
     * distributor bankrupt trigger
     *
     */
    public void declareBankruptcy() {
        this.isBankrupt = true;
        for (Map.Entry<Integer, Contract> entry : contracts.entrySet()) {
            int key = entry.getKey();
            Consumers.getInstance().getConsumers().get(key).distributorBankrupt();
        }
        contracts.clear();

    }

    /**
     * pay distributor bills
     *
     */
    public void payBills() {
        computePricesBills();
        if (this.budget - this.infrastructureCost - this.finalProductionCost < 0) {
            this.declareBankruptcy();
        }
        this.budget = this.budget - this.infrastructureCost - this.finalProductionCost;
        monthlyClients = 0;
    }

    /**
     * receive bills payment
     *
     */
    public void receivePayment(final int invoice) {
        this.budget = this.budget + invoice;
    }

    /**
     * compute new contracts price
     *
     */
    public void computePrices() {
        this.finalProductionCost = this.productionCost * this.numberOfClients;
        this.profit = (int) Math.round(Math.floor(0.2 * this.productionCost));
        if (this.numberOfClients == 0) {
            this.contractCost = infrastructureCost + this.productionCost + this.profit;
        } else {
            this.contractCost = (int) Math.round(Math.floor(
                    (double) this.infrastructureCost / (double) this.numberOfClients)
                    + this.productionCost + this.profit);
        }
    }

    /**
     * compute price for distributor bill
     *
     */
    public void computePricesBills() {
        this.finalProductionCost = this.productionCost * this.monthlyClients;
    }

    public LinkedHashMap<Integer, Contract> getContracts() {
        return contracts;
    }

    public int getContractCost() {
        return contractCost;
    }

    public void setContractCost(final int contractCost) {
        this.contractCost = contractCost;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(final int profit) {
        this.profit = profit;
    }

    public int getMonthlyClients() {
        return monthlyClients;
    }

    /**
     * setter for monthly clients, edge case of client bankrupt before paying
     *
     */
    public void setMonthlyClients() {
        this.monthlyClients = numberOfClients;
    }

    public void setNumberOfClients(final int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public int getNumberOfClients() {
        return this.numberOfClients;
    }

    public int getFinalProductionCost() {
        return finalProductionCost;
    }

    public void setFinalProductionCost(final int finalProductionCost) {
        this.finalProductionCost = finalProductionCost;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public int getContractLength() {
        return contractLength;
    }

    public void setContractLength(final int contractLength) {
        this.contractLength = contractLength;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(final int budget) {
        this.budget = budget;
    }

    public int getInfrastructureCost() {
        return infrastructureCost;
    }

    public void setInfrastructureCost(final int infrastructureCost) {
        this.infrastructureCost = infrastructureCost;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(final int productionCost) {
        this.productionCost = productionCost;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(final boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    /**
     * export contracts from hashmap to arraylist
     *
     */
    public void exportContracts() {
        for (Map.Entry<Integer, Contract> entry : contracts.entrySet()) {
            contractsToExport.add(entry.getValue());
        }
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":" + id
                + ", \"budget\":" + budget
                + ", \"isBankrupt\":" + isBankrupt
                + ", \"contracts\":" + contractsToExport
                + '}';
    }
}
