package generators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Created by patrykks on 22/03/16.
 */
public class ConfigReader {
    private static ConfigReader instance;
    private  Properties prop;
    private  InputStream input;

    private ConfigReader(){
        initialize();
    }

    public static synchronized ConfigReader getInstance(){
        if(instance == null){
            instance = new ConfigReader();
        }
        return instance;
    }

    private void initialize() {
        prop = new Properties();
        try {
            String filename = "config.properties";
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            prop.load(input);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File config.properites not found exception");
        } catch (IOException e) {
            throw new RuntimeException("File config.properites not found exception");
        }
    }

    public double getEvaporationCoefficient() {
        return Double.parseDouble(prop.getProperty("EVAPORATION_COEFFICIENT"));
    }

    public double getAcoConstance(){
        return Double.parseDouble(prop.getProperty("ACO_CONSTANCE"));
    }

    public  int getNumberOfAnts()
    {
        return Integer.parseInt(prop.getProperty("NUMBER_OF_ANTS"));
    }


    public long getBeginTime() {
        return Timestamp.valueOf(prop.getProperty("beginTime")).getTime();
    }

    public long getEndTime() {
        return Timestamp.valueOf(prop.getProperty("endTime")).getTime();
    }


}
