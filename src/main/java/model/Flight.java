package model;

import java.util.Date;

/**
 * Created by patrykks on 27/03/16.
 */
public class Flight extends Node {
    public static int idGenerator = 0;
    private int cancelledFlightPenalty;
    private AirPort endAirport;
    private int flightdelayPenealty;
    private int id;
    private int numbuerOfPassnger;
    private Date plannedDepTIme;
    private Date realdDepTIme;
    private AirPort startAirport;

    public Flight(Date plannedDepTIme, AirPort startAirport, AirPort endAirport, int numbuerOfPassnger, int cancelledFlightPenalty, int flightdelayPenealty) {
        this.id = idGenerator;
        idGenerator++;
        this.plannedDepTIme = plannedDepTIme;
        this.startAirport = startAirport;
        this.endAirport = endAirport;
        this.numbuerOfPassnger = numbuerOfPassnger;
        this.cancelledFlightPenalty = cancelledFlightPenalty;
        this.flightdelayPenealty = flightdelayPenealty;
        this.realdDepTIme = null;
    }

    public int getId() {
        return id;
    }

    public int getCancelledFlightPenalty() {
        return cancelledFlightPenalty;
    }

    public int getFlightdelayPenealty() {
        return flightdelayPenealty;
    }

    public int getNumbuerOfPassnger() {
        return numbuerOfPassnger;
    }

    public AirPort getStartAirport() {
        return startAirport;
    }

    public AirPort getEndAirport() {
        return endAirport;
    }

    public Date getPlannedDepTIme() {
        return plannedDepTIme;
    }

    public Date getRealdDepTIme() {
        return realdDepTIme;
    }

    public void setRealdDepTIme(Date realdDepTIme) {
        this.realdDepTIme = realdDepTIme;
    }

    public double getDistance() {
        return startAirport.distance(endAirport);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", cancelledFlightPenalty=" + cancelledFlightPenalty +
                ", flightdelayPenealty=" + flightdelayPenealty +
                ", numbuerOfPassnger=" + numbuerOfPassnger +
                ", startAirport=" + startAirport +
                ", endAirport=" + endAirport +
                ", plannedDepTIme=" + plannedDepTIme +
                ", realdDepTIme=" + realdDepTIme +
                '}';
    }
}
