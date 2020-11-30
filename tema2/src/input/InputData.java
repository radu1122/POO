package input;

import java.util.List;

public class InputData {
    private int numberOfTurns;
    private InitialDataInput initialData;
    private List<MonthlyUpdateInput> monthlyUpdates;

    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void setNumberOfTurns(int numberOfTurns) {
        this.numberOfTurns = numberOfTurns;
    }

    public InitialDataInput getInitialData() {
        return initialData;
    }

    public void setInitialData(InitialDataInput initialData) {
        this.initialData = initialData;
    }

    public List<MonthlyUpdateInput> getMonthlyUpdates() {
        return monthlyUpdates;
    }

    public void setMonthlyUpdates(List<MonthlyUpdateInput> monthlyUpdates) {
        this.monthlyUpdates = monthlyUpdates;
    }

    @Override
    public String toString() {
        return "InputData{" +
                "numberOfTurns=" + numberOfTurns +
                ", initialData=" + initialData +
                ", monthlyUpdates=" + monthlyUpdates +
                '}';
    }
}
