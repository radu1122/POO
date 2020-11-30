package input;

import java.util.List;

public class InitialDataInput {
    private List<ConsumerInput> consumers;
    private List<DistributorInput> distributors;

    public List<ConsumerInput> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<ConsumerInput> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorInput> getDistributors() {
        return distributors;
    }

    public void setDistributors(List<DistributorInput> distributors) {
        this.distributors = distributors;
    }

    @Override
    public String toString() {
        return "InitialDataInput{" +
                "consumers=" + consumers +
                ", distributors=" + distributors +
                '}';
    }
}
