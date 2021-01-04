package dataset;

import java.util.ArrayList;

public final class Producers {
  private static Producers singleInstance = null;

  private final ArrayList<Producer> producers = new ArrayList<>();

  /**
   * add new producer to the dataset
   *
   */
  public void addProducer(final Producer producer) {
    this.producers.add(producer);
  }

  public void computeMonthlyStats() {
    for (Producer producer : producers) {
      producer.computeMonthlyStats();
    }
  }

  public ArrayList<Producer> getProducers() {
    return producers;
  }

  /**
   * update energyPerDistributor
   *
   */
  public void updateCosts(final int id, final int energyPerDistributor) {
    producers.get(id).updatesCosts(energyPerDistributor);
    for (Integer distributorId : producers.get(id).getDistributorsList()) {
      Distributors.getInstance().getDistributors().get(distributorId).selectProducers();
    }
  }

  /**
   * get instance of the dataSet
   *
   */
  public static Producers getInstance() {
    if (singleInstance == null) {
      singleInstance = new Producers();
    }

    return singleInstance;
  }

  @Override
  public String toString() {
    return "" + producers;
  }
}
