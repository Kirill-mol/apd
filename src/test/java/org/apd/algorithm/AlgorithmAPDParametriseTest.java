package org.apd.algorithm;

import javafx.scene.control.TextArea;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class AlgorithmAPDParametriseTest {
    static final String badConTestPath ="src/test/java/org/apd/algorithm/TestResources/AlgorithmAPDTests/BadConnectivity/test";
    static final String correctConTestPath = "src/test/java/org/apd/algorithm/TestResources/AlgorithmAPDTests/CorrectConnectivity/test";
    static final String graphsForApdPath = "src/test/java/org/apd/algorithm/TestResources/AlgorithmAPDTests/GraphsForApd/test";
    static final String testType = ".txt";
    static final int countTest = 10;

    private File fileBadConnectivity;
    private File fileCorrectConnectivity;
    private File fileGraphAPD;
    private AlgorithmAPD jpd;
    private GraphReader graphReader;
    private Graph graph;
    private int resultAPDalgo;

    /**
     * Конструктор параметризованного теста класса <code>AlgorithmAPD</code>
     * @param fileBadCon - файл с несвязным графом.
     * @param fileCorrCon - файл со связным (корректным) графом.
     * @param fileGraph - файл с графом для вычисления остова.
     * @param resultAPD - результат остова для текущего теста.
     */
    public AlgorithmAPDParametriseTest(File fileBadCon, File fileCorrCon, File fileGraph, int resultAPD){
        fileBadConnectivity = fileBadCon;
        fileCorrectConnectivity = fileCorrCon;
        fileGraphAPD = fileGraph;
        resultAPDalgo = resultAPD;
    }


    @Parameterized.Parameters(name = "{index}:Test")
    public static Iterable<Object[]> dataForTest() {
        int [] resultTests = new int[]{150, 9, 5, 75, 37, 9, 17, 172, 34, 25};
        Object[][] data = new Object[countTest][];
        for (int i = 0; i < countTest; i++){
            data[i] = new Object[]{
                    new File(badConTestPath+(i+1)+testType),
                    new File(correctConTestPath + (i+1) + testType),
                    new File(graphsForApdPath + (i+1) + testType),
                    resultTests[i]
            };
        }
        return Arrays.asList(data);
    }


    /**
     * Метод выполняющийся перед каждым тестовым методом.
     */
    @Before
    public void setUp(){
        graph = new Graph();
        jpd = new AlgorithmAPD(graph);
        graphReader = new GraphReader(graph);
    }

    /**
     * Метод, очищающий данные после каждого тестового метода.
     */
    @After
    public void afterMethod() {
        jpd.clear();
        graph.clear();
    }


    /**
     * Проверка на связность корректных графов. <br>(Алгоритм Прима работает только со связанными графами).
     * @result Метод должен проверить все переданные файлы с корректными графами
     * и закончить работу без ошибок и исключений.
     */
    @Test
    public void corConnectivityGraphCheck() {
        try {
            graphReader.readGraphFromFile(fileCorrectConnectivity);
        } catch (Exception e){
            if (e instanceof FileNotFoundException)
                System.out.println("Test correctConnectivity fall because of FileNotException.");
            System.out.println("Test correctConnectivity for file " + fileCorrectConnectivity.getName() + " fall");
            e.printStackTrace();
            Assert.fail();
        }

        try {
            jpd.result();
        } catch (Exception e) {
            Assert.fail("Error, graph correct, but method result another. File " + fileCorrectConnectivity.getName());
            e.printStackTrace();
        }
    }


    /**
     * Проверка на связность некорректных графов. <br>(Алгоритм Прима работает только со связанными графами).
     * @result Метод должен для каждого файла возвращать исключение <code>Exception</code>.
     * @throws Exception
     */
    @Test(expected = Exception.class)
    public void badConnectivityGraphCheck() throws Exception {
        try {
            graphReader.readGraphFromFile(fileBadConnectivity);
        } catch (Exception e) {
            if (e instanceof FileNotFoundException)
                System.out.println("Test badConnectivity fall because of FileNotException.");
            System.out.println("Test badConnectivity for file " + fileBadConnectivity.getName() + " fall");
            Assert.fail();
            e.printStackTrace();
        }
        jpd.result();
    }


    /**
     * Вычисление минимального остова.
     * @result Метод проверяет работу метода <code>result()</code> для каждого тестового файла.
     * Должен получить правильный результат и закончиться без исключений.
     */
    @Test
    public void resultTest(){
        File file;
        int actual = 0;

        try {
            graphReader.readGraphFromFile(fileGraphAPD);
        } catch (Exception e) {
            if (e instanceof FileNotFoundException)
                System.out.println("Test resultTest fall because of FileNotException.");
            System.out.println("Test resultTest for file" + fileGraphAPD.getName() + " fall.");
            e.printStackTrace();
            Assert.fail();
        }

        try {
            List<Edge> list = jpd.result();
            actual = 0;
            for (Edge edge: list){
                actual += edge.getWeight();
            }
        } catch (Exception e) {
            System.out.println("Correct file, but resultTest for file" + fileGraphAPD.getName() + " fall.");
            e.printStackTrace();
            Assert.fail();
        }

        Assert.assertEquals("Неверный результат.",resultAPDalgo, actual);
    }
}