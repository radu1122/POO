package dataset;

import java.util.ArrayList;

public final class Distributors {
    private static Distributors singleInstance = null;

    private ArrayList<Distributor> distributors = new ArrayList<>();
    private int distributorMinId;
    private boolean hasDistributors;

    public ArrayList<Distributor> getDistributors() {
        return distributors;
    }

    public void setDistributors(final ArrayList<Distributor> distributors) {
        this.distributors = distributors;
    }

    /**
     * add new distributor to the dataset
     *
     */
    public void addDistributor(final Distributor distributor) {
        this.distributors.add(distributor);
    }

    /**
     * update distributor cost
     *
     */
    public void updateCosts(final int id, final int infrastructureCost) {
        distributors.get(id).updatesCosts(infrastructureCost);
    }

    /**
     * trigger compute prices for all distributors
     *
     */
    public void computePrices() {
        int id = 0;
        int minCost = Integer.MAX_VALUE;
        boolean hasDistributorsX = false;
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                hasDistributorsX = true;
                distributor.computePrices();
                int cost =  distributor.getContractCost();
                if (cost < minCost) {
                    minCost = cost;
                    id = distributor.getId();
                }
            }
        }
        this.hasDistributors = hasDistributorsX;
        this.distributorMinId = id;
    }

    /**
     * trigger distributor select producers
     *
     */
    public void selectProducers() {
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                distributor.clearProducers();
            }
        }
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                distributor.selectProducers();
            }
        }
    }

    /**
     * trigger distributor pay bills
     *
     */
    public void payBills() {
        for (Distributor distributor : distributors) {
            if (!distributor.isBankrupt()) {
                distributor.payBills();
            }
        }
    }

    public boolean getHasDistributors() {
        return hasDistributors;
    }

    public void setHasDistributors(final boolean hasDistributors) {
        this.hasDistributors = hasDistributors;
    }

    public int getDistributorMinId() {
        return distributorMinId;
    }

    public void setDistributorMinId(final int distributorMinId) {
        this.distributorMinId = distributorMinId;
    }

    /**
     * get instance of the dataSet
     *
     */
    public static Distributors getInstance() {
        if (singleInstance == null) {
            singleInstance = new Distributors();
        }

        return singleInstance;
    }

    /**
     * trigger arraylist to hashmap of every distributor
     *
     */
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
