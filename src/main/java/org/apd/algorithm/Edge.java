package org.apd.algorithm;

import java.util.Objects;

public class Edge {

    private Vertex begin, end;
    private int weight;

    public Edge(Vertex start, Vertex stop, int weight) {
        this.begin = start;
        this.end = stop;
        this.weight = weight;
    }

    public Vertex getBegin() {
        return begin;
    }

    public Vertex getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return begin + " " + end + " " + weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return ((this.getBegin().equals(edge.getBegin())) && (this.getEnd().equals(edge.getEnd()))) ||
                ((this.getEnd().equals(edge.getBegin())) && (this.getBegin().equals(edge.getEnd())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBegin(), getEnd(), getWeight());
    }
}
