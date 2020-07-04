package org.apd;

import org.apd.algorithm.Graph;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //App.main(args);
        var scanner = new Scanner(System.in);
        Graph graph = new Graph();
        for (int i = 0; i < 6; i++) {
            String[] curLine = scanner.nextLine().split(" ");
            graph.addEdge(curLine[0].charAt(0), curLine[1].charAt(0), Integer.parseInt(curLine[2]));
        }
        System.out.println(graph.findMinimumSpanningTree().toString());
        System.out.println(graph.toString());
    }
}
/*
a b 5
a e 2
a c 1
b c 3
e d 11
c d 4
*/
