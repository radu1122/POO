package dataset;

import entities.MonthlyStats;

import java.util.ArrayList;
import java.util.Collections;

public final class Producer {
  private int id;
  private String energyType;
  private int maxDistributors;
  private double priceKW;
  private int energyPerDistributor;
  private int actualDistributors;
  private final int greenEnergy;
  private final ArrayList<Integer> distributorsList = new ArrayList<>();
  private final ArrayList<MonthlyStats> monthlyStats = new ArrayList<>();

  public Producer(final int id, final String energyTypeX,
                  final int maxDistributorsX, final double priceKWX,
                  final int energyPerDistributorX) {
    this.id = id;
    this.energyType = energyTypeX;
    this.maxDistributors = maxDistributorsX;
    this.priceKW = priceKWX;
    this.energyPerDistributor = energyPerDistributorX;
    this.actualDistributors = 0;
    if (energyTypeX.equals("COAL") || energyTypeX.equals("NUCLEAR")) {
      greenEnergy = 1;
    } else {
      greenEnergy = 0;
    }
  }

  /**
   * trigger for remove distributor
   *
   */
  public void deleteDistributor(int idX) {
    actualDistributors--;
    distributorsList.remove(Integer.valueOf(idX));
  }

  /**
   * trigger for add distributor
   *
   */
  public void addDistributor(int idX) {
    actualDistributors++;
    distributorsList.add(idX);
  }

  /**
   * make monthly stats for distributors
   *
   */
  public void computeMonthlyStats() {
    Collections.sort(distributorsList);
    monthlyStats.add(new MonthlyStats(monthlyStats.size() + 1, new ArrayList<>(distributorsList)));
  }

  /**
   * update energyPerDistributor with new value
   *
   */
  public void updatesCosts(final int energyPerDistributorX) {
    this.energyPerDistributor = energyPerDistributorX;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEnergyType() {
    return energyType;
  }

  public void setEnergyType(String energyType) {
    this.energyType = energyType;
  }

  public int getMaxDistributors() {
    return maxDistributors;
  }

  public void setMaxDistributors(int maxDistributors) {
    this.maxDistributors = maxDistributors;
  }

  public double getPriceKW() {
    return priceKW;
  }

  public void setPriceKW(double priceKW) {
    this.priceKW = priceKW;
  }

  public int getEnergyPerDistributor() {
    return energyPerDistributor;
  }

  public void setEnergyPerDistributor(int energyPerDistributor) {
    this.energyPerDistributor = energyPerDistributor;
  }

  public int getActualDistributors() {
    return actualDistributors;
  }

  public void setActualDistributors(int actualDistributors) {
    this.actualDistributors = actualDistributors;
  }

  public int getGreenEnergy() {
    return greenEnergy;
  }

  public ArrayList<Integer> getDistributorsList() {
    return distributorsList;
  }

  @Override
  public String toString() {
    return "{"
            + "\"id\":" + id
            + ", \"maxDistributors\":" + maxDistributors
            + ", \"priceKW\":" + priceKW
            + ", \"energyType\":\"" + energyType
            + "\", \"energyPerDistributor\":" + energyPerDistributor
            + ", \"monthlyStats\":" + monthlyStats
            + '}';
  }
}
