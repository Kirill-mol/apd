package org.apd.algorithm;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Класс для тестирования класса <b>AlgorithmAPD</b>
 * @author Ilya
 */
public class AlgorithmAPDTest {
    private static AlgorithmAPD jpd;
    private static Graph graph;
    private final int resultForRemoveEdge = 150;

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


    @Test
    public void clear() {
    }


    @Ignore
    @Test
    public void removeEdgeTest(){
        File file = new File(AlgorithmAPDParametriseTest.graphsForApdPath + 7 + AlgorithmAPDParametriseTest.testType);
        try {
            jpd.readGraphFromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Добавление корректного ребра.
     * @result Ребро будет добавлено без каких либо ошибок и икслючений.
     */
    @Test
    public void addEdge() {
        Edge edge = new Edge('s', 'e', 10);
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
        Edge edge_first = new Edge('s', 'e', 10);
        Edge edge_second = new Edge('s', 'e', 1);
        Edge edge_third = new Edge('e', 's', 23);
        try {
            jpd.addEdge(edge_first);
        } catch (Exception e) {
            Assert.fail("Ошибка при корректном добавлении.");
            e.printStackTrace();
        }
        jpd.addEdge(edge_second);
        jpd.addEdge(edge_third);
    }


    /**
     * Попытка считывания графа из несуществующего файла.
     * @result Метод должен для некорректного файла возвращать исключение <code>FileNotFoundException</code>.
     * @throws FileNotFoundException
     */
    @Test(expected = Exception.class)
    public void readGraphFromNotExistFile() throws Exception {
        File file = new File("notexist.txt");
        jpd.readGraphFromFile(file);
    }


    /**
     * Попытка считывания графа из корректного файла.
     * @result Метод должен завершить работу без ошибок и исключений.
     */
    @Test
    public void readGraphFromExistFile() {
        File file = new File(AlgorithmAPDParametriseTest.graphsForApdPath+1+AlgorithmAPDParametriseTest.testType);
        try {
            jpd.readGraphFromFile(file);
        } catch (Exception e) {
            Assert.fail("Ошибка открытия существующего файла.");
            e.printStackTrace();
            Assert.fail();
        }
    }

}