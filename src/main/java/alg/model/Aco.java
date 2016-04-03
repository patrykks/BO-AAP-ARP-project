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
    private static final double ACO_CONSTANCE = ConfigReader.getInstance().getAcoConstance();
    private static final double EVAPORATION_COEFFICIENT = ConfigReader.getInstance().getEvaporationCoefficient();
    private double minCost = Double.POSITIVE_INFINITY;
    private List<Ant> ants;
    private Solution bestSolution = null;
    private Map <Node, NodeNeighborhood> neighborhoodMap;


    public Aco(int antsNumber) {
        neighborhoodMap = new HashMap<Node, NodeNeighborhood>();
        initializeEgdeMap();
        ants = new ArrayList<>(antsNumber);
        for (int i = 0; i < antsNumber; i++) {
            ants.add(new Ant(this));
        }
    }

    public void solve() {
        while (true) {
            System.out.println("Actual min cost" + minCost);
            System.out.println(bestSolution);
            for (Ant ant : ants) {
                Solution solution = ant.findSolution();
                double solutionCost = solution.evaluateSolutionCost();
                if (Double.compare(solutionCost, minCost) < 0) {
                    minCost = solutionCost;
                    bestSolution = solution.cloneSolution();
                }
                updatePheromones(solution, solutionCost);
                ant.clearSolution();
            }
            doEvaporation();
        }
    }

    private void doEvaporation() {
        for (NodeNeighborhood neighborhood : neighborhoodMap.values()) {
            for (Map.Entry<Edge, Double> entry : neighborhood.getEntry()) {
                neighborhood.put(entry.getKey(), Math.max(entry.getValue()*EVAPORATION_COEFFICIENT, 1) );
            }
        }
    }

    public double getPheromoneValue(Edge edge) {
            return neighborhoodMap.get(edge.getNodeStart()).get(edge);
    }

    public Set<Edge> getEdges(Node node) {
        return neighborhoodMap.get(node).getEdges();
    }

    public Set<Edge> getAllEdges() {
        Set<Edge> edgeSet = new HashSet<Edge>();
        for (NodeNeighborhood neighborhood : neighborhoodMap.values()) {
            edgeSet.addAll(neighborhood.getEdges());
        }
        return edgeSet;
    }

    public double getPhoromesSumValue(Node node) {
        double sum = 0.0;
        for (Double value : neighborhoodMap.get(node).values()) {
            sum += value;
        }
        return sum;
    }

    public void updateEdgePheromon(Edge edge,double value) {
        neighborhoodMap.get(edge.getNodeStart()).put(edge, value);
    }

    public void updatePheromones(Solution antSolution, double solutionCost) {
        for (Edge edge : antSolution.getEdges()) {
            updateEdgePheromon(edge, ACO_CONSTANCE / solutionCost);
        }
    }

    private void initializeEgdeMap() {
        addAirplaneToAirplaneEdges();
        addFlightToAirplaneEdges();
        addAirplaneToFlightEdges();
        addFlightToFlightEdges();

    }

    private void addAirplaneToAirplaneEdges() {
        for (AirPlane endAirplane : AirplaneService.getInstance().getAllAirplanes()) {
            Node end = endAirplane;
            for (AirPlane startAirplane : AirplaneService.getInstance().getAllAirplanes()) {
                Node start = startAirplane;
                if (!start.equals(end)){
                    NodeNeighborhood neighborhood= neighborhoodMap.get(start);
                    if (neighborhood == null) {
                        neighborhood = new NodeNeighborhood(start);
                        neighborhoodMap.put(start, neighborhood);
                    }
                    neighborhood.put(new Edge(start, end),1.0);

                }
            }
        }
    }

    private void addFlightToAirplaneEdges() {
        for (AirPlane endAirplane : AirplaneService.getInstance().getAllAirplanes()) {
            Node end = endAirplane;
            for (Flight flight : FlightService.getInstance().getAllFlights()) {
                Node start = flight;
                NodeNeighborhood neighborhood= neighborhoodMap.get(start);
                if (neighborhood == null) {
                    neighborhood = new NodeNeighborhood(start);
                    neighborhoodMap.put(start, neighborhood);
                }
                neighborhood.put(new Edge(start, end),1.0);
            }
        }
    }

    private void addAirplaneToFlightEdges() {
        for (AirPlane airplane : AirplaneService.getInstance().getAllAirplanes()) {
            for (Flight flight : FlightService.getInstance().getAllFlights()) {
                ;if (flight.getNumbuerOfPassnger() <= airplane.getCapacity())
                    if (flight.getStartAirport().equals(airplane.getBase())) {
                        NodeNeighborhood neighborhood= neighborhoodMap.get(airplane);
                        if (neighborhood == null) {
                            neighborhood = new NodeNeighborhood(airplane);
                            neighborhoodMap.put(airplane, neighborhood);
                        }
                        neighborhood.put(new Edge(airplane, flight),1.0);
                    }
            }
        }
    }

    private void addFlightToFlightEdges() {
        for (Flight startFlight : FlightService.getInstance().getAllFlights()) {
            for (Flight endFlight : FlightService.getInstance().getAllFlights()) {
                if (startFlight.getEndAirport().equals(endFlight.getStartAirport())) {
                    NodeNeighborhood neighborhood= neighborhoodMap.get(startFlight);
                    if (neighborhood == null) {
                        neighborhood = new NodeNeighborhood(startFlight);
                        neighborhoodMap.put(startFlight, neighborhood);
                    }
                    neighborhood.put(new Edge(startFlight, endFlight), 1.0);
                }

            }
        }
    }



}
