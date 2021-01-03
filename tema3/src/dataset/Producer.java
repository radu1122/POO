package dataset;

import entities.MonthlyStats;

import java.util.ArrayList;
import java.util.Collections;

public class Producer {
  private int id;
  private String energyType;
  private int maxDistributors;
  private int priceKW;
  private int energyPerDistributor;
  private int actualDistributors;
  private final ArrayList<Integer> distributorsList = new ArrayList<>();
  private final ArrayList<MonthlyStats> monthlyStats = new ArrayList<>();

  public Producer(final int id, final String energyTypeX,
                  final int maxDistributorsX, final int priceKWX,
                  final int energyPerDistributorX) {
    this.id = id;
    this.energyType = energyTypeX;
    this.maxDistributors = maxDistributorsX;
    this.priceKW = priceKWX;
    this.energyPerDistributor = energyPerDistributorX;
    this.actualDistributors = 0;
  }

  public void deleteDistributor(int id) {
    actualDistributors--;
    distributorsList.remove(Integer.valueOf(id));
  }

  public void addDistributor(int id) {
    actualDistributors++;
    distributorsList.add(id);
  }

  public void computeMonthlyStats() {
    Collections.sort(distributorsList);
    monthlyStats.add(new MonthlyStats(monthlyStats.size() + 1, distributorsList));
  }

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

  public int getPriceKW() {
    return priceKW;
  }

  public void setPriceKW(int priceKW) {
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

  @Override
  public String toString() {
    return "{"
            + "\"id\":" + id
            + ", \"maxDistributors\":" + maxDistributors
            + ", \"priceKW\":" + priceKW
            + ", \"energyType\":" + energyType
            + ", \"energyPerDistributor\":" + energyPerDistributor
            + ", \"monthlyStats\":" + monthlyStats
            + '}';
  }
}
