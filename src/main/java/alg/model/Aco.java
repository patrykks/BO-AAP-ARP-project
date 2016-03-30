package alg.model;

import generators.ConfigReader;
import model.AirPlane;
import model.Flight;
import model.Node;
import services.AirplaneService;
import services.FlightService;

import java.util.*;

/**
 * Created by patrykks on 28/03/16.
 */
public class Aco {
    private static final double EVAPORATION_COEFFICIENT = ConfigReader.getInstance().getEvaporationCoefficient();
    private double minCost = Double.POSITIVE_INFINITY;
    private List<Ant> ants;
    private Solution bestSolution = null;
    private Map<Edge,Double> pheromonMap;


    public Aco(int antsNumber) {
        pheromonMap = new HashMap<Edge,Double>();
        initializeEgdeMap();
        ants = new ArrayList<>(antsNumber);
        for (int i = 0; i < antsNumber; i++) {
            ants.add(new Ant(this));
        }

    }

    public void solve() {
        while (true) {
            for (Ant ant : ants) {
                ant.findSolution();
                Solution solution = ant.getSolution();
                double solutionCost = ant.evaluateSolutionCost();
                System.out.println("solution " + solutionCost);
                System.out.println(solution);
                System.out.println("Actual min cost" + minCost);
                System.out.println(bestSolution);
                if (Double.compare(solutionCost, minCost) < 0) {
                    //System.out.println(solution);
                    minCost = solutionCost;
                    bestSolution = solution.cloneSolution();
                }
                ant.updatePheromones();
                ant.clearSolution();
            }
            doEvaporation();
        }
    }

    private void doEvaporation() {
        for (Map.Entry<Edge, Double> entry : pheromonMap.entrySet()) {
            pheromonMap.put(entry.getKey(), Math.max(entry.getValue()*EVAPORATION_COEFFICIENT, 1));
        }
    }

    public double getPheromoneValue(Edge edge) {
            return pheromonMap.get(edge);
    }

    public Set<Edge> getEdges() {
        return pheromonMap.keySet();
    }

    public double getPhoromesSumValue(Node node) {
        double sum = 0.0;
        for (Map.Entry<Edge, Double> entry : pheromonMap.entrySet()) {
            if(entry.getKey().getNodeStart().equals(node))
                sum += entry.getValue();
        }
        return sum;
    }


    public void updateEdgePheromon(Edge edge,double value) {
        pheromonMap.put(edge, value);
    }

    private void initializeEgdeMap() {
        for (AirPlane endAirplane : AirplaneService.getInstance().getAllAirplanes()) {
            Node end = endAirplane;
            for (AirPlane startAirplane : AirplaneService.getInstance().getAllAirplanes()) {
                Node start = startAirplane;
                if (!start.equals(end)){
                    pheromonMap.put(new Edge(start, end),1.0);
                }
            }

            for (Flight flight : FlightService.getInstance().getAllFlights()) {
                Node start = flight;
                pheromonMap.put(new Edge(start, end),1.0);
            }
        }

        for (AirPlane airplane : AirplaneService.getInstance().getAllAirplanes()) {
            for (Flight flight : FlightService.getInstance().getAllFlights()) {
                ;if (flight.getNumbuerOfPassnger() <= airplane.getCapacity())
                    if (flight.getStartAirport().equals(airplane.getBase()))
                        pheromonMap.put(new Edge(airplane, flight),1.0);

            }
        }

        for (Flight startFlight : FlightService.getInstance().getAllFlights()) {
            for (Flight endFlight : FlightService.getInstance().getAllFlights()) {
                if (startFlight.getEndAirport().equals(endFlight.getStartAirport()))
                    pheromonMap.put(new Edge(startFlight, endFlight), 1.0);
            }
        }
    }

}
