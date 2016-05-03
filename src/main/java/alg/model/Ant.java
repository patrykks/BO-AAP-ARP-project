package alg.model;

import javafx.beans.property.DoubleProperty;
import model.AirPlane;
import model.Flight;
import model.Node;
import services.AirplaneService;
import services.StatsService;

import java.util.*;

/**
 * Created by patrykks on 27/03/16.
 */
public class Ant {
    public static final long HOUR = 3600*1000;
    private Solution actualSolution;
    private Set<Flight> visitedFlights;
    private Set<AirPlane> toVisitAirplanes;
    private Set<AirPlane> visitedAirplanes;
    private Aco aco;
    private DoubleProperty alphaCoefficient = StatsService.getInstance().getAlphaCoefficient();

    public Ant(Aco aco) {
        this.aco = aco;
        actualSolution = new Solution();
        visitedAirplanes = new HashSet<AirPlane>();
        toVisitAirplanes = new HashSet<AirPlane>(AirplaneService.getInstance().getAllAirplanes());
        visitedFlights = new HashSet<Flight>();
    }

    public Solution findSolution() {
        Edge startEdge = findStartEdge();
        actualSolution.addEdge(startEdge);
        AirPlane airplane = (AirPlane) startEdge.getNodeStart();
        toVisitAirplanes.remove(airplane);
        visitedAirplanes.add(airplane);
        Node lastNode = startEdge.getNodeEnd();
        Edge lastEdge = null;
        boolean findNode = false;
        Double alhpa = alphaCoefficient.doubleValue();
        if (alhpa == null) alhpa = 1.0;
        while(!toVisitAirplanes.isEmpty()) {
            findNode = false;
            lastEdge = null;
            for (Edge edge : aco.getEdges(lastNode)) {
                if (edge.getNodeStart().equals(lastNode)) {
                    if (Flight.class.isInstance(edge.getNodeEnd())) {
                        Flight flight = (Flight) edge.getNodeEnd();
                        if (!visitedFlights.contains(flight)) {
                            double pheromoneValueForEdge =  Math.pow(aco.getPheromoneValue(edge), alhpa)/aco.getPhoromesSumValue(lastNode, alhpa);
                            //System.out.println("Pheromone value for edge" + pheromoneValueForEdge);
                            if (Double.compare(Math.random(), pheromoneValueForEdge ) < 0) {
                                lastNode = flight;
                                findNode = true;
                                visitedFlights.add(flight);
                                actualSolution.addEdge(edge);
                                break;
                            }
                        }
                    }
                }
            }
            if (!findNode) {
                for (Edge edge : aco.getEdges(lastNode)) {
                    if (edge.getNodeStart().equals(lastNode)) {
                        if (AirPlane.class.isInstance(edge.getNodeEnd())) {
                            airplane = (AirPlane) edge.getNodeEnd();
                            if (!visitedAirplanes.contains(airplane)) {
                                lastEdge = edge;
                                double pheromoneValueForEdge =  Math.pow(aco.getPheromoneValue(edge), alhpa) /aco.getPhoromesSumValue(lastNode, alhpa);
                                if (Double.compare(Math.random(), pheromoneValueForEdge ) < 0) {
                                    lastNode = airplane;
                                    findNode = true;
                                    actualSolution.addEdge(edge);
                                    visitedAirplanes.add(airplane);
                                    toVisitAirplanes.remove(airplane);
                                    break;
                                }
                            }

                        }
                    }
                }
            }
            if (!findNode) {
                airplane = (AirPlane)lastEdge.getNodeEnd();
                if (!visitedAirplanes.contains(airplane)) {
                    lastNode = airplane;
                    actualSolution.addEdge(lastEdge);
                    visitedAirplanes.add(airplane);
                    toVisitAirplanes.remove(airplane);
                }

            }

        }
        updateflightTime();
        return actualSolution;
    }

    private Edge findStartEdge(){
        for (Edge edge : aco.getAllEdges()) {
            if (AirPlane.class.isInstance(edge.getNodeStart()) && Flight.class.isInstance(edge.getNodeEnd()))
                return edge;
        }
        throw new RuntimeException("In graph should be at least one edge airplane->flight");
    }

    public void clearSolution() {
        actualSolution.clear();
        visitedAirplanes.clear();
        visitedFlights.clear();
        toVisitAirplanes.clear();
        toVisitAirplanes = new HashSet<AirPlane>(AirplaneService.getInstance().getAllAirplanes());
    }

    private void updateflightTime() {
        LinkedList<Edge> solutionEdge = actualSolution.getEdges();
        AirPlane airPlane = null;
        ListIterator<Edge> edgeIterator = solutionEdge.listIterator();
        Date date = null;
        while (edgeIterator.hasNext()) {
            Edge edge = edgeIterator.next();
            Node start = edge.getNodeStart();
            Node end = edge.getNodeEnd();
            if (AirPlane.class.isInstance(start) && AirPlane.class.isInstance(end)) {
                airPlane = (AirPlane) end;
                date = null;
            }
            if (AirPlane.class.isInstance(start) && Flight.class.isInstance(end)) {
                airPlane = (AirPlane) start;
                Flight flight = (Flight) end;
                date = flight.getPlannedDepTIme();
                flight.setRealdDepTIme(date);
                double flightTime = flight.getDistance() / airPlane.getVelocity();
                date = new Date(date.getTime() + (long)flightTime * HOUR + 1);
            }
            if (Flight.class.isInstance(start) && Flight.class.isInstance(end)) {
                Flight endFlight = (Flight) end;
                double flightTime = endFlight.getDistance() / airPlane.getVelocity();
                date = new Date(Long.max(date.getTime() + (long)flightTime * HOUR + 1,((Flight) end).getPlannedDepTIme().getTime() ));
                endFlight.setRealdDepTIme(date);
            }
            if (Flight.class.isInstance(start) && AirPlane.class.isInstance(end)) {
                airPlane = (AirPlane) end;
            }

        }

    }
}
