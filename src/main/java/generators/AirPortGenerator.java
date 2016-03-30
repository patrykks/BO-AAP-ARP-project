package generators;

import model.AirPort;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by patrykks on 27/03/16.
 */
public class AirPortGenerator {

    private BufferedReader getFile() {
        BufferedReader br =
                new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("airports.dat")));
        return br;
    }

    public ArrayList<AirPort> load() {
        ArrayList<AirPort> airports = new ArrayList<AirPort>();
        BufferedReader br = getFile();
        try {
            String line;
            while ((line = br.readLine()) != null) {

                String[] airport = line.split(",");
                int id = Integer.parseInt(airport[0]);
                String IATA = airport[4];
                String ICAO = airport[5];
                double latitude = Double.parseDouble(airport[6]);
                double longitude = Double.parseDouble(airport[7]);
                airports.add(new AirPort(id, IATA, ICAO, latitude, longitude));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return airports;
    }


}
