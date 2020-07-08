package org.apd.algorithm;

import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;


/**
 * Класс для тестирования класса <b>Graph</b>
 * @author Ilya
 */
public class GraphTest {
    private Graph graph;

    @BeforeClass
    public static void globalSetUp() {

    }

    @Before
    public void setUp() throws Exception {
        graph = new Graph();
    }

    @After
    public void afterMethod() {
        graph.clear();
    }

    /**
     * Добавление корректного ребра в граф.
     * @result Метод должен завершить работу без ошибок и исключений.
     */
    @Test
    public void addEdge() {
        Edge edge = new Edge('s', 'e', 10);
        boolean actual;
        actual = graph.addEdge(edge);
        Assert.assertTrue("Error! Не добавлено корректное ребро.", actual);
    }

    /**
     * Добавление идентичных ребер в граф.
     * @result Метод должен закончить работу, вернув <code>false</code>.
     */
    @Test
    public void addSameEdges(){
        Edge edge_first = new Edge('s', 'e', 10);
        Edge edge_second = new Edge('s', 'e', 1);
        Edge edge_third = new Edge('e', 's', 23);
        boolean actual =  graph.addEdge(edge_first);
        Assert.assertTrue("Ошибка при добавлении корректного ребра.", actual);
        actual = graph.addEdge(edge_second);
        Assert.assertFalse("Ошибка! Добавлено одинаковое ребро.", actual);
        Assert.assertFalse("Ошибка! Добавлено одинаковое ребро.", graph.addEdge(edge_third));
    }

    @Ignore
    @Test
    public void removeEdge(){
        /*
         in AlgorithmAPDTest
         */
    }


    /**
     * Удаление вершины.
     * @result Метод должен удалить вершину и смежные ей ребра.
     */
    @Test
    public void removeVertex() throws Exception {
        Character start1 = 'a', start2 = 'b', end1 = 'b', end2 = 'c';
        Edge first = new Edge(start1, end1, 2);
        Edge second = new Edge(start2, end2, 1);
        graph.addEdge(first);
        graph.addEdge(second);
        int expectedVertex = graph.getVertexesList().size() - 1;
        int expectedEdges = 0;
        graph.removeVertex(start2);
        int actual = graph.getVertexesList().size();
        Assert.assertEquals(expectedVertex, actual);
        actual = graph.getEdgesList().size();
        Assert.assertEquals(expectedEdges, actual);
    }

    @Test
    public void clearTest(){
        Character start1 = 'a', start2 = 'b', end1 = 'b', end2 = 'c';
        Edge first = new Edge(start1, end1, 2);
        Edge second = new Edge(start2, end2, 1);
        graph.addEdge(first);
        graph.addEdge(second);
        graph.clear();
        int expected = graph.getVertexesList().size();
        Assert.assertEquals(0, expected);
        expected = graph.getEdgesList().size();
        Assert.assertEquals(0, expected);
    }

}
