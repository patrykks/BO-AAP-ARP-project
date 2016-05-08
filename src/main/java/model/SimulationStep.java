package model;

/**
 * Created by patrykks on 4/05/16.
 */
public class SimulationStep {
    private Flight flightTo;
    private Flight flightFrom;
    private AirPlane airPlaneFrom;
    private AirPlane airPlaneTo;

    public SimulationStep(Flight flightTo, AirPlane airPlaneTo, Flight flightFrom, AirPlane airPlaneFrom) {
        this.flightTo = flightTo;
        this.airPlaneTo = airPlaneTo;
        this.flightFrom = flightFrom;
        this.airPlaneFrom = airPlaneFrom;
    }

    public Flight getFlightTo() {
        return flightTo;
    }

    public void setFlightTo(Flight flightTo) {
        this.flightTo = flightTo;
    }

    public AirPlane getAirPlaneTo() {
        return airPlaneTo;
    }

    public void setAirPlaneTo(AirPlane airPlaneTo) {
        this.airPlaneTo = airPlaneTo;
    }

    public AirPlane getAirPlaneFrom() {
        return airPlaneFrom;
    }

    public void setAirPlaneFrom(AirPlane airPlaneFrom) {
        this.airPlaneFrom = airPlaneFrom;
    }

    public Flight getFlightFrom() {
        return flightFrom;
    }

    public void setFlightFrom(Flight flightFrom) {
        this.flightFrom = flightFrom;
    }

    @Override
    public String toString() {
        return "SimulationStep{" +
                "flightTo=" + flightTo +
                ", flightFrom=" + flightFrom +
                ", airPlaneFrom=" + airPlaneFrom +
                ", airPlaneTo=" + airPlaneTo +
                '}';
    }
}
