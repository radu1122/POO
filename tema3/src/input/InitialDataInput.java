package input;

import java.util.List;

public final class InitialDataInput {
    private List<ConsumerInput> consumers;
    private List<DistributorInput> distributors;
    private List<ProducerInput> producers;

    public List<ConsumerInput> getConsumers() {
        return consumers;
    }

    public void setConsumers(final List<ConsumerInput> consumers) {
        this.consumers = consumers;
    }

    public List<DistributorInput> getDistributors() {
        return distributors;
    }

    public void setDistributors(final List<DistributorInput> distributors) {
        this.distributors = distributors;
    }

    public List<ProducerInput> getProducers() {
        return producers;
    }

    public void setProducers(List<ProducerInput> producers) {
        this.producers = producers;
    }

    @Override
    public String toString() {
        return "InitialDataInput{" +
                "consumers=" + consumers +
                ", distributors=" + distributors +
                ", producers=" + producers +
                '}';
    }
}
