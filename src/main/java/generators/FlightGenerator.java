package generators;

import model.AirPort;
import services.AirportService;
import model.Flight;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by patrykks on 28/03/16.
 */
public class FlightGenerator {

    private BufferedReader getFile() {
        BufferedReader br =
                new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("routes.dat")));
        return br;
    }

    public ArrayList<Flight> load(int size) {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        BufferedReader br = getFile();
        int i = 0;
        try {
            String line;
            skipDescriptionLine(br);
            while ((line = br.readLine()) != null && i < size) {
                String[] flight = line.split(",");
                int sourceAirportID = Integer.parseInt(flight[0]);
                int destinationAirportID = Integer.parseInt(flight[1]);
                AirPort startAirport = AirportService.getInstance().getByID(sourceAirportID);
                AirPort endAirport = AirportService.getInstance().getByID(destinationAirportID);
                if (startAirport == null)
                    throw new RuntimeException("There is no airport with id:" + flight[0] + "in airport databse");
                if (endAirport == null)
                    throw new RuntimeException("There is no airport with id:" + flight[1] + "in airport databse");

                Date plannedDepTIme = RandomDateGenerator.generateRandomDate();
                int numbuerOfPassnger = randInt(10,213);
                int cancelledFlightPenalty = randInt(10000,10000000);
                int flightdelayPenealty = randInt(100, 50000);

                flights.add(new Flight(plannedDepTIme, startAirport, endAirport,numbuerOfPassnger, cancelledFlightPenalty, flightdelayPenealty));
                i++;
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
        return flights;
    }

    private int randInt(int minimum, int maximum) {
        Random rn = new Random();
        int n = maximum - minimum + 1;
        int i = Math.abs(rn.nextInt() % n);
        int randomNum =  minimum + i;
        return  randomNum;
    }

    private void skipDescriptionLine(BufferedReader br) {
        try {
            br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error in skiping description line in FlightGenerator");
        }
    }
}
