package model;

/**
 * Created by patrykks on 22/04/16.
 */
public class StatsItem {
    private int id;
    private double mincost;
    private double time;
    private long iteration;
    private double procentageImprovment;

    public StatsItem(int id, double mincost, double time, double procentageImprovment, long iteration) {
        this.id = id;
        this.mincost = mincost;
        this.time = time;
        this.procentageImprovment = procentageImprovment;
        this.iteration = iteration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getProcentageImprovment() {
        return procentageImprovment;
    }

    public void setProcentageImprovment(int procentageImprovment) {
        this.procentageImprovment = procentageImprovment;
    }

    public long getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public double getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getMincost() {
        return mincost;
    }

    public void setMincost(int mincost) {
        this.mincost = mincost;
    }
}
