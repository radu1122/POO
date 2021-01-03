package dataset;

import java.util.ArrayList;

public final class Producers {
  private static Producers singleInstance = null;

  private final ArrayList<Producer> producers = new ArrayList<>();

  public void addProducer(final Producer producer) {
    this.producers.add(producer);
  }

  /**
   * add new producer to the dataset
   *
   */
  public ArrayList<Producer> getProducers() {
    return producers;
  }

  /**
   * update energyPerDistributor
   *
   */
  public void updateCosts(final int id, final int energyPerDistributor) {
    producers.get(id).updatesCosts(energyPerDistributor);
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
