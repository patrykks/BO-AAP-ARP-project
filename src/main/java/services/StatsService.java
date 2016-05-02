package services;

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
            procentageImprovment =  (beforeStatsItem.getMincost() - minCost) / (beforeStatsItem.getMincost());
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


}