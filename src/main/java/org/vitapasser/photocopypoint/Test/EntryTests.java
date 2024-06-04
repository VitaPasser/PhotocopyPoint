package org.vitapasser.photocopypoint.Test;

import org.vitapasser.photocopypoint.Test.PickUpStation.FixSale;
import org.vitapasser.photocopypoint.Test.PickUpStation.GetOperator;
import org.vitapasser.photocopypoint.Test.Register.GetPrice;
import org.vitapasser.photocopypoint.Test.Register.GetTerm;
import org.vitapasser.photocopypoint.Test.Register.MakePayment;
import org.vitapasser.photocopypoint.Test.TicketList.CreateTicket;

import java.sql.Connection;

public class EntryTests {

    public EntryTests(Connection connection)
    {
        System.out.println("Testing TypeList... ");
        //new TestResult(new Get(connection));
        //new TestResult(new GetType(connection));
        //new TestResult(new GetTimePerTime(connection));
        System.out.println("Testing PickUpStation... ");
        new TestResult(new GetOperator(connection));
        new TestResult(new FixSale(connection));
        System.out.println("Testing TicketList... ");
        new TestResult(new CreateTicket(connection));
        System.out.println("Testing Register... ");
        new TestResult((new GetTerm(connection)));
        new TestResult((new GetPrice(connection)));
        new TestResult((new MakePayment(connection)));

        System.out.println("Test successful complete!");

        TestResult.testResults.forEach(x -> System.out.println(x.testName + ": " + x.isEnter));
    }
}
