package alg.model;

import model.Node;

import java.util.*;

/**
 * Created by patrykks on 2/04/16.
 */
public class NodeNeighborhood {
    Map<Edge, Double> neigboursMap;
    Node node;

    public NodeNeighborhood(Node node) {
        neigboursMap = new HashMap<Edge, Double>();
        this.node = node;
    }

    public Set<Map.Entry<Edge, Double>> getEntry(){
        return neigboursMap.entrySet();
    }

    public void put(Edge edge, double value) {
        neigboursMap.put(edge, value);
    }

    public double get(Edge edge) {
        return neigboursMap.get(edge);
    }

    public Set<Edge> getEdges() {
        return  neigboursMap.keySet();
    }

    public Collection<Double> values() {
        return neigboursMap.values();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeNeighborhood that = (NodeNeighborhood) o;

        return !(node != null ? !node.equals(that.node) : that.node != null);

    }

    @Override
    public int hashCode() {
        return node != null ? node.hashCode() : 0;
    }
}
