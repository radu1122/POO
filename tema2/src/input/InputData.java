package input;

import java.util.List;

public final class InputData {
    private int numberOfTurns;
    private InitialDataInput initialData;
    private List<MonthlyUpdateInput> monthlyUpdates;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(final int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public InitialDataInput getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialDataInput initialData) {
        this.initialData = initialData;
    }

    public List<MonthlyUpdateInput> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(final List<MonthlyUpdateInput> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }

    @Override
    public String toString() {
        return "InputData{"
                + "numberOfTurns=" + numberOfTurns
                + ", initialData=" + initialData
                + ", monthlyUpdates=" + monthlyUpdates
                + '}';
    }
}
