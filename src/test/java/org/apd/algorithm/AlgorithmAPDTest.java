package org.apd.algorithm;

import org.junit.*;

import static org.junit.Assert.*;


/**
 * Класс для тестирования класса <b>AlgorithmAPD</b>
 * @author Ilya
 */
public class AlgorithmAPDTest {
    private AlgorithmAPD jpd;

    /**
     * Метод инициализации, выполняющийся до любого метода.
     */
    @BeforeClass
    public static void globalSetUp(){
    }

    /**
     * Метод для подготовки данных, выполняется перед каждым тестовым методом.
     */
    @Before
    public void setUp(){
        jpd = new AlgorithmAPD(new Graph());
    }

    /**
     * Метод, очищающий данные после каждого тестового метода.
     */
    @After
    public void afterMethod() {
        jpd.clear();
    }

    /**
     * Добавление корректного ребра.
     * @result Ребро будет добавлено без каких либо ошибок и икслючений.
     */
    @Test
    public void addEdge() {
        Edge edge = new Edge(new Vertex("s"), new Vertex("e"), 10);
        try {
            jpd.addEdge(edge);
        } catch (Exception e) {
            Assert.fail("Ошибка при корректном добавлении.");
            e.printStackTrace();
        }
    }

    /**
     * Добавление идентичных ребер в граф.
     * @result Метод добавления ребра <code>addEdge()</code> должен вернуть исключение <code>Exception</code>.
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void addSameEdge() throws Exception {
        Edge edge_first = new Edge(new Vertex("s"), new Vertex("e"), 10);
        Edge edge_second = new Edge(new Vertex("s"), new Vertex("e"), 1);
        Edge edge_third = new Edge(new Vertex("e"), new Vertex("s"), 23);
        try {
            jpd.addEdge(edge_first);
        } catch (Exception e) {
            Assert.fail("Ошибка при корректном добавлении.");
            e.printStackTrace();
        }
        jpd.addEdge(edge_second);
        jpd.addEdge(edge_third);
    }


    /*
    @Test
    public void removeVertex() {
    }
    */

}