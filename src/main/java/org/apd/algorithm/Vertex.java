package org.apd.algorithm;

import java.util.Objects;

public class Vertex {
    public String name;

    public Vertex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return getName().equals(vertex.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
