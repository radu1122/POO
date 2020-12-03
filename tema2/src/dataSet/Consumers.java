package dataSet;

import java.util.ArrayList;

public class Consumers {
    private static Consumers singleInstance = null;

    private ArrayList<Consumer> consumers = new ArrayList<>();

    public ArrayList<Consumer> getConsumers() {
        return consumers;
    }

    public void checkContracts() {
        for (Consumer consumer : consumers) {
            if (consumer.getRemainedContractMonths() == 0 && consumer.getHasContract()) {
                if (!consumer.isBankrupt()) {
                    consumer.resetContract();
                }
            }
        }
    }

    public void makeContracts() {
        for (Consumer consumer : consumers) {
            if (!consumer.getHasContract()) {
                if (!consumer.isBankrupt()) {
                    int distributorId = Distributors.getInstance().getDistributorMinId();
                    Distributors.getInstance().getDistributors().get(distributorId).addContract(consumer.getId());
                }
            }
        }
    }

    public void generateBills() {
        for (Consumer consumer : consumers) {
            consumer.generateBill();
        }
        for (Distributor distributor : Distributors.getInstance().getDistributors()) {
            distributor.setMonthlyClients();
        }
    }

    public void payBills() {
        for (Consumer consumer : consumers) {
            if (!consumer.isBankrupt()) {
                consumer.payBills();
            }
        }
    }

    public void setConsumers(ArrayList<Consumer> consumers) {
        this.consumers = consumers;
    }

    public void addConsumer(Consumer consumer) {
        this.consumers.add(consumer);
    }

    public static Consumers getInstance() {
        if (singleInstance == null)
            singleInstance = new Consumers();

        return singleInstance;
    }

    @Override
    public String toString() {
        return "" + consumers;
    }
}
