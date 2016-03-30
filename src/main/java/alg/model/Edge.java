package alg.model;

import model.Node;

/**
 * Created by patrykks on 27/03/16.
 */
public class Edge {
    private Node nodeStart;
    private Node nodeEnd;

    public Edge(Node nodeStart, Node nodeEnd) {
        this.nodeStart = nodeStart;
        this.nodeEnd = nodeEnd;
    }

    public Node getNodeStart() {
        return nodeStart;
    }

    public void setNodeStart(Node nodeStart) {
        this.nodeStart = nodeStart;
    }

    public Node getNodeEnd() {
        return nodeEnd;
    }

    public void setNodeEnd(Node nodeEnd) {
        this.nodeEnd = nodeEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (nodeStart != null ? !nodeStart.equals(edge.nodeStart) : edge.nodeStart != null) return false;
        return !(nodeEnd != null ? !nodeEnd.equals(edge.nodeEnd) : edge.nodeEnd != null);

    }

    @Override
    public int hashCode() {
        int result = nodeStart != null ? nodeStart.hashCode() : 0;
        result = 31 * result + (nodeEnd != null ? nodeEnd.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "nodeStart=" + nodeStart + '\n' +
                ", nodeEnd=" + nodeEnd + '\n' +
                '}';
    }
}
