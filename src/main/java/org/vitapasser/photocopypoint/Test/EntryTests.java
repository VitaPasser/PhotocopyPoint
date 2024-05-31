package org.vitapasser.photocopypoint.Test;

import org.vitapasser.photocopypoint.Test.PickUpStation.FixSale;
import org.vitapasser.photocopypoint.Test.PickUpStation.GetOperator;
import org.vitapasser.photocopypoint.Test.PriceList.GetPrice;
import org.vitapasser.photocopypoint.Test.Sql.Get;
import org.vitapasser.photocopypoint.Test.TypeList.GetTimePerTime;
import org.vitapasser.photocopypoint.Test.TypeList.GetType;

import java.sql.Connection;

public class EntryTests {

    public EntryTests(Connection connection)
    {
        System.out.println("Testing TypeList... ");
        new TestResult(new Get(connection));
        new TestResult(new GetType(connection));
        new TestResult(new GetTimePerTime(connection));
        System.out.println("Testing PriceList... ");
        new TestResult(new GetPrice(connection));
        System.out.println("Testing PickUpStation... ");
        new TestResult(new GetOperator(connection));
        // Доробити після
        new TestResult(new FixSale(connection));

        System.out.println("Test successful complete!");

        TestResult.testResults.forEach(x -> System.out.println(x.testName + ": " + x.isEnter));
    }
}
