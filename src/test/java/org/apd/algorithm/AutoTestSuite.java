package org.apd.algorithm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AlgorithmAPDTest.class,
        AlgorithmAPDParametriseTest.class,
        GraphTest.class,
        GraphReaderTest.class
})
public class AutoTestSuite {
}