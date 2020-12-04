package dataset;

public final class EntityFactory {

  private EntityFactory() {

  }

  /**
   * factory of entity DISTRIBUTOR or CONSUMER
   *
   */
  public static Entity getEntity(final String shapeType) {
    if (shapeType == null) {
      return null;
    }

    if (shapeType.equalsIgnoreCase("DISTRIBUTOR")) {
      return new Distributor();
    } else if (shapeType.equalsIgnoreCase("CONSUMER")) {
      return new Consumer();
    }

    return null;
  }
}
