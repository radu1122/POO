package strategy;

import dataset.Producer;

import java.util.ArrayList;

public interface Strategy {
    public ArrayList<Producer> sortProducers(ArrayList<Producer> producers);
}
