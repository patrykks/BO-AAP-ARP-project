package model;

import java.util.Date;

/**
 * Created by patrykks on 16/04/16.
 */
public class FlightTableItem {
    private int cancelledFlightPenalty;
    private int flightdelayPenealty;
    private int id;
    private int numbuerOfPassnger;
    private Date plannedDepTIme;
    private Date realdDepTIme;
    private String endAirport;
    private String startAirport;
    private String airplaneId;
    private boolean isCancelled;

    public FlightTableItem(int cancelledFlightPenalty, int flightdelayPenealty, int id, int numbuerOfPassnger, Date plannedDepTIme, String endAirport, String startAirport) {
        this.cancelledFlightPenalty = cancelledFlightPenalty;
        this.flightdelayPenealty = flightdelayPenealty;
        this.id = id;
        this.numbuerOfPassnger = numbuerOfPassnger;
        this.plannedDepTIme = plannedDepTIme;
        this.endAirport = endAirport;
        this.startAirport = startAirport;
        this.isCancelled = true;
        this.realdDepTIme = null;
        this.airplaneId = null;
    }

    public int getCancelledFlightPenalty() {
        return cancelledFlightPenalty;
    }

    public void setCancelledFlightPenalty(int cancelledFlightPenalty) {
        this.cancelledFlightPenalty = cancelledFlightPenalty;
    }

    public String getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(String airplaneId) {
        this.airplaneId = airplaneId;
    }

    public String getStartAirport() {
        return startAirport;
    }

    public void setStartAirport(String startAirport) {
        this.startAirport = startAirport;
    }

    public String getEndAirport() {
        return endAirport;
    }

    public void setEndAirport(String endAirport) {
        this.endAirport = endAirport;
    }

    public Date getRealdDepTIme() {
        return realdDepTIme;
    }

    public void setRealdDepTIme(Date realdDepTIme) {
        this.realdDepTIme = realdDepTIme;
    }

    public Date getPlannedDepTIme() {
        return plannedDepTIme;
    }

    public void setPlannedDepTIme(Date plannedDepTIme) {
        this.plannedDepTIme = plannedDepTIme;
    }

    public int getNumbuerOfPassnger() {
        return numbuerOfPassnger;
    }

    public void setNumbuerOfPassnger(int numbuerOfPassnger) {
        this.numbuerOfPassnger = numbuerOfPassnger;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlightdelayPenealty() {
        return flightdelayPenealty;
    }

    public void setFlightdelayPenealty(int flightdelayPenealty) {
        this.flightdelayPenealty = flightdelayPenealty;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
