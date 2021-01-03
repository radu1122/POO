package dataset;


public class Entity {

  void payBills() {

  }

  /**
   * populate Entity for Consumer
   *
   */
  public Consumer populateEntity(final int id, final int initialBudget, final int monthlyIncome) {
    return (Consumer) this;
  }

  /**
   * populate Entity for Distributor
   *
   */
  public Distributor populateEntity(final int id,
                                    final int contractLength,
                                    final int initialBudget,
                                    final int initialInfrastructureCost,
                                    final int energyNeededKW,
                                    final String producerStrategy) {
    return (Distributor) this;
  }
}
