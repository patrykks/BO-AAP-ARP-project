package alg.model;

import model.AirPlane;
import model.Flight;
import model.Node;
import services.FlightService;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by patrykks on 27/03/16.
 */
public class Solution  {
    private LinkedList<Edge> edges;

    public Solution() {
        edges = new LinkedList<Edge>();
    }

    public LinkedList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public double evaluateSolutionCost() {
        double cost = 0.0;
        double costOfAssignmentPlaneToFlight = 0.0;
        double costOfDelay = 0.0;
        double costOfFlight = 0.0;
        double costofFlightCancel = 0.0;
        double delay = 0.0;
        for (Flight flight : FlightService.getInstance().getAllFlights()) {
            costOfAssignmentPlaneToFlight = 0.0;
            costOfDelay = 0.0;
            costOfFlight = 0.0;
            costofFlightCancel = 0.0;
            delay = 0.0;
            if (!isSolutionContainsFlight(flight)){
                costofFlightCancel = flight.getCancelledFlightPenalty();
            } else {
                AirPlane airplane = findAirplaneForFlight(flight);
                costOfAssignmentPlaneToFlight = airplane.getRentCost();
                double flightDuration = flight.getDistance() / airplane.getVelocity();
                costOfFlight = flightDuration * airplane.getCostOfUnitOfTime();
                if (flight.getRealdDepTIme() == null)
                    throw new RuntimeException("Real Departure Date should not be null");
                long diff = flight.getRealdDepTIme().getTime() - flight.getPlannedDepTIme().getTime();
                if (diff > 0) {
                    delay = diff / (1000*60);
                    costOfDelay = delay * flight.getFlightdelayPenealty();
                }
            }
            cost += costOfAssignmentPlaneToFlight + costOfDelay + costOfFlight + costofFlightCancel;
        }
        return cost;
    }

    public void setEdges(LinkedList<Edge> edges) {
        this.edges = edges;
    }

    public AirPlane findAirplaneForFlight(Flight flight) {
        int indexReprFlightNode = findIndexForFlightNode(flight);
        ListIterator<Edge> listIterator = edges.listIterator(indexReprFlightNode + 1);
        while(listIterator.hasPrevious()) {
            Edge edge = listIterator.previous();
            if (AirPlane.class.isInstance(edge.getNodeStart()))
                return (AirPlane) edge.getNodeStart();
        }
        return null;
    }

    private Integer findIndexForFlightNode(Flight flight) {
        for (Edge edge : edges) {
            Node nodeStart = edge.getNodeStart();
            Node nodeEnd = edge.getNodeEnd();
            if (nodeStart.equals(flight) || nodeEnd.equals(flight))
                return edges.indexOf(edge);
        }
        throw new RuntimeException("Didnt found flight in edges in solution, but it shoul be fonund");
    }


    public boolean isSolutionContainsFlight(Flight flight) {
        for (Edge edge : edges) {
            Node nodeStart = edge.getNodeStart();
            Node nodeEnd = edge.getNodeEnd();
            if (flight.equals(nodeStart))
                return true;
            if (flight.equals(nodeEnd))
                return true;
        }
        return false;
    }

    public void clear() {
        edges.clear();
    }

    public Solution cloneSolution() {
        Solution solution = new Solution();
        solution.setEdges(new LinkedList<Edge>(edges));
        return solution;
    }


    @Override
    public String toString() {
        return "Solution{" +
                "edges=" + edges +
                '}';
    }
}
