package services;

import generators.FlightGenerator;
import model.Flight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrykks on 28/03/16.
 */
public class FlightService {
    private static FlightService instance;
    private List<Flight> flights;
    private final int SIZE = 1000;
    private FlightService(){
        initialize();
    }

    public static synchronized FlightService getInstance(){
        if(instance == null){
            instance = new FlightService();
        }
        return instance;
    }

    private void initialize() {
        flights = new ArrayList<Flight>();
        FlightGenerator flightGenerator = new FlightGenerator();
        flights.addAll(flightGenerator.load(SIZE));
    }

    public  List<Flight> getAllFlights(){
        return flights;
    }


}
