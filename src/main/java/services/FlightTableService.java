package services;

import generators.AirPortGenerator;
import model.AirPlane;
import model.Flight;
import model.FlightTableItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by patrykks on 16/04/16.
 */
public class FlightTableService {
    private static FlightTableService instance;
    private ArrayList<FlightTableItem> items;

    private FlightTableService(){
        initialize();
    }

    public static synchronized FlightTableService getInstance(){
        if(instance == null){
            instance = new FlightTableService();
        }
        return instance;
    }

    private void initialize(){
        items = new ArrayList<FlightTableItem>();
        AirPortGenerator airPortGenerator = new AirPortGenerator();
        for (Flight flight : FlightService.getInstance().getAllFlights()) {
            int cancelledFlightPenalty = flight.getCancelledFlightPenalty();
            int flightdelayPenealty = flight.getFlightdelayPenealty();
            int id = flight.getId();
            int numbuerOfPassnger = flight.getNumbuerOfPassnger();
            Date plannedDepTIme = flight.getPlannedDepTIme();
            String startAirport = flight.getStartAirport().getName();
            String endAirport = flight.getEndAirport().getName();
            FlightTableItem flightTableItem = new FlightTableItem(cancelledFlightPenalty, flightdelayPenealty, id, numbuerOfPassnger, plannedDepTIme, endAirport, startAirport);
            items.add(flightTableItem);
        }
    }

    public void updateFlightItem(int id, Date realdDepTIme, AirPlane airPlane) {
        for (FlightTableItem item : items) {
            if (item.getId() == id) {
                String airplaneDescription ="Id:" +  airPlane.getId() + " name:" + airPlane.getName();
                item.setRealdDepTIme(realdDepTIme);
                item.setAirplaneId(airplaneDescription);
                item.setIsCancelled(realdDepTIme == null);
            }
        }
    }
    public void clear() {
        for (FlightTableItem flightTableIte : items) {
            flightTableIte.setIsCancelled(true);
            flightTableIte.setRealdDepTIme(null);
            flightTableIte.setAirplaneId(null);
        }
    }

    public List<FlightTableItem> getAll(){
        return items;
    }

    public FlightTableItem getByID(int id) {
        for (FlightTableItem flightTableItem : items) {
            if (flightTableItem.getId() == id)
                return flightTableItem;
        }
        return null;
    }


}
