package generators;

import model.AirPlane;
import model.AirPort;
import services.AirportService;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by patrykks on 28/03/16.
 */
public class AirplaneGenerator {
    private BufferedReader getFile() {
        BufferedReader br =
                new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("aircrafts.dat")));
        return br;
    }

    public ArrayList<AirPlane> load() {
        ArrayList<AirPlane> airplanes = new ArrayList<AirPlane>();
        BufferedReader br = getFile();;
        try {
            String line;
            skipDescriptionLine(br);
            while ((line = br.readLine()) != null) {
                String[] airplane = line.split(",");
                String id = airplane[0];
                String name = airplane[1];
                int airportId = Integer.parseInt(airplane[3]);
                int capacity = Integer.parseInt(airplane[2].substring(airplane[2].length() - 3));
                AirPort base = AirportService.getInstance().getByID(airportId);
                //AirPort base = AirportService.getInstance().getRandomAirport();
                int velocity = (int) (450 - Math.random() * capacity);
                int rentCost = (int) (Math.random()*capacity*1000);
                int costOfUnitOfTime = (int) (Math.random()*capacity*100);

                airplanes.add(new AirPlane(id, name, base, capacity, velocity, rentCost, costOfUnitOfTime));
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
        return airplanes;
    }

    private int randInt(int minimum, int maximum) {
        Random rn = new Random();
        int n = maximum - minimum + 1;
        int i = rn.nextInt() % n;
        int randomNum =  minimum + i;
        return  randomNum;
    }

    private void skipDescriptionLine(BufferedReader br) {
        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error in skiping description line in AirplaneGenerator");
        }
    }
}
