package services;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import model.SimulationStep;
import model.StatsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrykks on 22/04/16.
 */
public class StatsService {
    private static StatsService instance;
    private ArrayList<StatsItem> items;
    private int idGenerator;
    private DoubleProperty alphaCoefficient;
    private SimulationStep simulationStep;

    private StatsService(){
        initialize();
    }

    public static synchronized StatsService getInstance(){
        if(instance == null){
            instance = new StatsService();
        }
        return instance;
    }

    private void initialize(){
        items = new ArrayList<StatsItem>();
        idGenerator = 1;
    }

    public void addStatsItem(double time, double minCost, long iteration) {
        StatsItem beforeStatsItem;
        double procentageImprovment;
        beforeStatsItem = getByID(idGenerator - 1);
        if (beforeStatsItem == null) {
            procentageImprovment = 100;
        } else {
            procentageImprovment =  (beforeStatsItem.getMincost() - minCost) / (minCost);
            procentageImprovment*= 100;
        }
        StatsItem item = new StatsItem(idGenerator++, minCost, time, procentageImprovment, iteration);
        items.add(item);
    }

    public List<StatsItem> getAll(){
        return items;
    }

    public StatsItem getByID(int id) {
        for (StatsItem statsItem : items) {
            if (statsItem.getId() == id)
                return statsItem;
        }
        return null;
    }

    public DoubleProperty getAlphaCoefficient() {
        return alphaCoefficient;
    }

    public void addAlphaCoefficientProperty(StringProperty stringProperty) {
        StringConverter<Number> converter = new NumberStringConverter();
        alphaCoefficient = new SimpleDoubleProperty(1.0);
        Bindings.bindBidirectional(stringProperty,alphaCoefficient, converter);
    }

    public synchronized SimulationStep getSimulationStep() {
        return simulationStep;
    }

    public synchronized void setSimulationStep(SimulationStep simulationStep) {
        this.simulationStep = simulationStep;
    }
}
