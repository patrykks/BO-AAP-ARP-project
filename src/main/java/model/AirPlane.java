package model;

/**
 * Created by patrykks on 27/03/16.
 */
public class AirPlane extends Node {
    protected String id;
    private String name;
    private AirPort base;
    private int capacity;
    private int velocity;
    // per assigment to flight
    private int rentCost;
    //per 1 h of airplane flight
    private int costOfUnitOfTime;

    public AirPlane(String id, String name,AirPort base, int capacity, int velocity, int rentCost, int costOfUnitOfTime) {
        this.id = id;
        this.name = name;
        this.base = base;
        this.capacity = capacity;
        this.velocity = velocity;
        this.rentCost = rentCost;
        this.costOfUnitOfTime = costOfUnitOfTime;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AirPort getBase() {
        return base;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getRentCost() {
        return rentCost;
    }

    public int getCostOfUnitOfTime() {
        return costOfUnitOfTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AirPlane airPlane = (AirPlane) o;

        return !(id != null ? !id.equals(airPlane.id) : airPlane.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AirPlane{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", base=" + base +
                ", capacity=" + capacity +
                ", velocity=" + velocity +
                ", rentCost=" + rentCost +
                ", costOfUnitOfTime=" + costOfUnitOfTime +
                '}';
    }
}
