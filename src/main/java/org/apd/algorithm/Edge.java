package org.apd.algorithm;

public class Edge {

    private Character begin, end;
    private int weight;

    public Edge(Character start, Character stop, int weight) {
        this.begin = start;
        this.end = stop;
        this.weight = weight;
    }

    public Character getBegin() {
        return begin;
    }

    public Character getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return begin + " " + end + " " + weight;
    }
}
