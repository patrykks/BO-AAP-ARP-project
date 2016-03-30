package services;

import generators.AirplaneGenerator;
import model.AirPlane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrykks on 28/03/16.
 */
public class AirplaneService {
    private static AirplaneService instance;
    private List<AirPlane> airplanes;

    private AirplaneService(){
        initialize();
    }

    public static synchronized AirplaneService getInstance(){
        if(instance == null){
            instance = new AirplaneService();
        }
        return instance;
    }

    private void initialize(){
        airplanes = new ArrayList<AirPlane>();
        AirplaneGenerator airplaneGenerator = new AirplaneGenerator();
        airplanes.addAll(airplaneGenerator.load());
    }

    public List<AirPlane> getAllAirplanes(){
        return airplanes;
    }
}
