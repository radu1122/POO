package strategy;

import dataset.Producer;

import java.util.ArrayList;
import java.util.Comparator;

public class QuantityStrategy implements Strategy{
  @Override
  public ArrayList<Producer> sortProducers(ArrayList<Producer> producers) {
    Comparator<Producer> comparatorProducers = (Producer o1, Producer o2) ->  {
      if (o1.getEnergyPerDistributor() == o2.getEnergyPerDistributor()) {
        return o1.getId() - o2.getId();
      } else {
        return o2.getEnergyPerDistributor() - o1.getEnergyPerDistributor();
      }
    };

    producers.sort(comparatorProducers);

    return producers;
  }
}
