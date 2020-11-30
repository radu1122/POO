package dataSet;

import java.util.ArrayList;

public class Distributors {
    private static Distributors singleInstance = null;

    private ArrayList<Distributor> distributors = new ArrayList<>();
    private int distributorMinId;

    public ArrayList<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(ArrayList<Distributor> distributors) {
        this.distributors = distributors;
    }

    public void addDistributor(Distributor distributor) {
        this.distributors.add(distributor);
    }

    public void updateCosts(int id, int infrastructureCost, int productionCost) {
        distributors.get(id).updatesCosts(infrastructureCost, productionCost);
    }

    public void computePrices() {
        int id = 0;
        int minCost = 0;
        for (Distributor distributor : distributors) {
            distributor.computePrices();
            int cost =  distributor.getContractCost();
            if (cost < minCost) {
                minCost = cost;
                id = distributor.getId();
            }
        }
        this.distributorMinId = id;
    }

    public void payBills() {
        for (Distributor distributor : distributors) {
            distributor.payBills();
        }
    }

    public int getDistributorMinId() {
        return distributorMinId;
    }

    public void setDistributorMinId(int distributorMinId) {
        this.distributorMinId = distributorMinId;
    }

    public static Distributors getInstance() {
        if (singleInstance == null)
            singleInstance = new Distributors();

        return singleInstance;
    }

    public void prepareExport() {
        for (Distributor distributor : distributors) {
            distributor.exportContracts();
        }
    }

    @Override
    public String toString() {
        return "" + distributors;
    }
}
