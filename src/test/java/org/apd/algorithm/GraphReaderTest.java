package org.apd.algorithm;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.*;


public class GraphReaderTest {
    private GraphReader graphReader;
    private Graph graph;

    @Before
    public void setUp() throws Exception {
        graph = new Graph();
        graphReader = new GraphReader(graph);
    }

    @After
    public void tearDown() throws Exception {
        graph.clear();
    }

    /**
     * Добавление корректного ребра.
     * @result Ребро будет добавлено без каких либо ошибок и икслючений.
     */
    @Test
    public void addEdge() {
        int expected = graph.getEdgesList().size() + 1;
        Edge edge = new Edge(new Vertex("l"), new Vertex("v"), 10);
        try {
            graph.addEdge(edge);
        } catch (Exception e) {
            Assert.fail("Ошибка при корректном добавлении.");
            e.printStackTrace();
        }
        Assert.assertEquals(expected, graph.getEdgesList().size());
    }

    /**
     * Добавление идентичных ребер в граф.
     * @result Метод добавления ребра <code>addEdge()</code> должен вернуть исключение <code>Exception</code>.
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void addSameEdge() throws Exception {
        int expected = graph.getEdgesList().size() + 1;
        Vertex start = new Vertex("st");
        Vertex end = new Vertex("end");
        Edge edge_first = new Edge(start,end, 10);
        Edge edge_second = new Edge(start, end, 1);
        Edge edge_third = new Edge(end, start, 23);
        try {
            graphReader.addEdge(edge_first);
        } catch (Exception e) {
            Assert.fail("Ошибка при корректном добавлении.");
            e.printStackTrace();
        }
        graphReader.addEdge(edge_second);
        graphReader.addEdge(edge_third);
        Assert.assertEquals(expected, graph.getEdgesList().size());
    }


    /**
     * Попытка считывания графа из несуществующего файла.
     * @result Метод должен для некорректного файла возвращать исключение <code>FileNotFoundException</code>.
     * @throws FileNotFoundException
     */
    @Test(expected = Exception.class)
    public void readGraphFromNotExistFile() throws Exception {
        File file = new File("notexist.txt");
        graphReader.readGraphFromFile(file);
    }


    /**
     * Попытка считывания графа из корректного файла.
     * @result Метод должен завершить работу без ошибок и исключений.
     */
    @Test
    public void readGraphFromExistFile() {
        File file = new File(AlgorithmAPDParametriseTest.graphsForApdPath+1+AlgorithmAPDParametriseTest.testType);
        try {
            graphReader.readGraphFromFile(file);
        } catch (Exception e) {
            Assert.fail("Ошибка открытия существующего файла.");
            e.printStackTrace();
            Assert.fail();
        }
    }


}