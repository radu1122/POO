package entities;

import java.util.ArrayList;

public final class MonthlyStats {
  private final int month;
  private final ArrayList<Integer> distributorsIds;

  public MonthlyStats(int month, ArrayList<Integer> distributorsIds) {
    this.month = month;
    this.distributorsIds = distributorsIds;
  }

  @Override
  public String toString() {
    return "{"
            + "\"month\":" + month
            + ", \"distributorsIds\":" + distributorsIds
            + '}';
  }
}
