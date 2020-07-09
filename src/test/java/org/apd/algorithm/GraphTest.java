package org.apd.algorithm;
import org.junit.*;
import static org.junit.Assert.*;

public class GraphTest {
    private Graph graph;

    @Before
    public void setUp() throws Exception {
        graph = new Graph();
    }

    @After
    public void tearDown() throws Exception {
        graph.clear();
    }

    /**
     * Добавление корректного ребра в граф.
     * @result Метод должен завершить работу без ошибок и исключений.
     */
    @Test
    public void addEdge() {
        Edge edge = new Edge(new Vertex("s"), new Vertex("e"), 10);
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
        Edge edge_first = new Edge(new Vertex("s"), new Vertex("e"), 10);
        Edge edge_second = new Edge(new Vertex("s"), new Vertex("e"), 1);
        Edge edge_third = new Edge(new Vertex("e"), new Vertex("s"), 23);
        boolean actual =  graph.addEdge(edge_first);
        Assert.assertTrue("Ошибка при добавлении корректного ребра.", actual);
        actual = graph.addEdge(edge_second);
        Assert.assertFalse("Ошибка! Добавлено одинаковое ребро.", actual);
        Assert.assertFalse("Ошибка! Добавлено одинаковое ребро.", graph.addEdge(edge_third));
    }



    @Test
    public void clearTest(){
        Vertex start1 = new Vertex("a"), start2 = new Vertex("b"),
                end1 = new Vertex("b"), end2 = new Vertex("c");
        Edge first = new Edge(start1, end1, 2);
        Edge second = new Edge(start2, end2, 1);
        graph.addEdge(first);
        graph.addEdge(second);
        graph.clear();
        int actual = graph.getVertexesList().size();
        Assert.assertEquals(0, actual);
        actual = graph.getEdgesList().size();
        Assert.assertEquals(0, actual);
    }


    /**
     * Удаление вершины.
     * @result Метод должен удалить вершину и смежные ей ребра.
     */
    @Test
    public void removeVertex() throws Exception {
        Vertex start1 = new Vertex("a"), start2 = new Vertex("b"),
                end1 = new Vertex("b"), end2 = new Vertex("c");
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
}