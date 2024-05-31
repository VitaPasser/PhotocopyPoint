package org.vitapasser.photocopypoint.Test;

import java.util.ArrayList;
import java.util.List;

public class TestResult {

    static List<TestResult> testResults;

    Boolean isEnter;
    String testName;

    TestResult(Test test)
    {
        if (testResults == null)
        {
            testResults = new ArrayList<TestResult>();
        }

        isEnter = test.test();
        testName = test.toString();
        testResults.add(this);
    }

    public Boolean getEnter() {
        return isEnter;
    }

    public String getTestName() {
        return testName;
    }
}
