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
            skipDescriptionLine(br);
            while ((line = br.readLine()) != null) {

                String[] airport = line.split(",");
                int id = Integer.parseInt(airport[0]);
                String IATA = airport[1];
                String ICAO = airport[2];
                double latitude = Double.parseDouble(airport[3]);
                double longitude = Double.parseDouble(airport[4]);
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

    private void skipDescriptionLine(BufferedReader br) {
        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error in skiping description line in AirportGenerator");
        }
    }

}
