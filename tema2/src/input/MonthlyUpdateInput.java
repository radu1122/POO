package input;

import java.util.List;

public class MonthlyUpdateInput {
    private List<ConsumerInput> newConsumers;
    private List<CostChange> costsChanges;

    public List<ConsumerInput> getNewConsumers() {
        return newConsumers;
    }

    public void setNewConsumers(List<ConsumerInput> newConsumers) {
        this.newConsumers = newConsumers;
    }

    public List<CostChange> getCostsChanges() {
        return costsChanges;
    }

    public void setCostsChanges(List<CostChange> costsChanges) {
        this.costsChanges = costsChanges;
    }

    @Override
    public String toString() {
        return "MonthlyUpdateInput{" +
                "newConsumers=" + newConsumers +
                ", costsChanges=" + costsChanges +
                '}';
    }
}
