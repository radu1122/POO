package dataset;

import java.util.ArrayList;

public final class Consumers {

    private static Consumers singleInstance = null;

    private ArrayList<Consumer> consumers = new ArrayList<>();

    public ArrayList<Consumer> getConsumers() {
        return consumers;
    }

    /**
     * terminate all expired contracts
     *
     */
    public void checkContracts() {
        for (Consumer consumer : consumers) {
            if (consumer.getRemainedContractMonths() == 0 && consumer.getHasContract()) {
                if (!consumer.isBankrupt()) {
                    consumer.resetContract();
                }
            }
        }
    }

    /**
     * make new contracts where needed
     *
     */
    public void makeContracts() {
        for (Consumer consumer : consumers) {
            if (!consumer.getHasContract()) {
                if (!consumer.isBankrupt()) {
                    int distributorId = Distributors.getInstance().getDistributorMinId();
                    Distributors.getInstance().getDistributors().
                            get(distributorId).addContract(consumer.getId());
                }
            }
        }
    }

    /**
     * generate all consumers bills
     *
     */
    public void generateBills() {
        for (Consumer consumer : consumers) {
            consumer.generateBill();
        }
        for (Distributor distributor : Distributors.getInstance().getDistributors()) {
            distributor.setMonthlyClients();
        }
    }

    /**
     * pay all consumers bills
     *
     */
    public void payBills() {
        for (Consumer consumer : consumers) {
            if (!consumer.isBankrupt()) {
                consumer.payBills();
            }
        }
    }

    public void setConsumers(final ArrayList<Consumer> consumers) {
        this.consumers = consumers;
    }

    /**
     * add new customer to the dataset
     *
     */
    public void addConsumer(final Consumer consumer) {
        this.consumers.add(consumer);
    }

    /**
     * get instance of the dataSet
     *
     */
    public static Consumers getInstance() {
        if (singleInstance == null) {
            singleInstance = new Consumers();
        }

        return singleInstance;
    }

    @Override
    public String toString() {
        return "" + consumers;
    }
}
