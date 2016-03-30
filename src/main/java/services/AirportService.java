package services;

import generators.AirPortGenerator;
import model.AirPort;

import java.util.ArrayList;

/**
 * Created by patrykks on 28/03/16.
 */
public class AirportService {
    private static AirportService instance;
    private ArrayList<AirPort> airports;

    private AirportService(){
        initialize();
    }

    public static synchronized AirportService getInstance(){
        if(instance == null){
            instance = new AirportService();
        }
        return instance;
    }

    private void initialize(){
        airports = new ArrayList<AirPort>();
        AirPortGenerator airPortGenerator = new AirPortGenerator();
        airports.addAll(airPortGenerator.load());
    }

    public AirPort getByID(int id) {
        for (AirPort airport : airports) {
            if (airport.getId() == id)
                return airport;
        }
        return null;
    }

}
