package org.vitapasser.photocopypoint.Test.TicketList;

import org.vitapasser.photocopypoint.Model.TicketList;
import org.vitapasser.photocopypoint.Test.AbstractTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateTicket extends AbstractTest {
    public CreateTicket(Connection connectionToDataBase) {
        super(connectionToDataBase);
    }

    @Override
    public boolean test() {
        TicketList ticketList = new TicketList(connectionToDataBase);

        long idOrder = 1;
        long idTicket = ticketList.createTicket(idOrder);

        String sqlCheck = "SELECT `PhotocopyPoint`.`Order`.id as OrderId, `PhotocopyPoint`.`Ticket`.id as TicketId\n" +
                "FROM `PhotocopyPoint`.`Ticket`\n" +
                "INNER JOIN `PhotocopyPoint`.`Order` " +
                "on `PhotocopyPoint`.`Order`.`id` = `PhotocopyPoint`.Ticket.order_id\n" +
                "WHERE `PhotocopyPoint`.`Order`.`id` = "+idOrder+"\n" +
                "GROUP BY OrderId, TicketId " +
                "ORDER BY OrderId, TicketId DESC LIMIT 1;";

        try {
            Statement statement = connectionToDataBase.createStatement();

            ResultSet sqlResult = statement.executeQuery(sqlCheck);
            sqlResult.next();

            long testOrderId = sqlResult.getLong("OrderId");
            long testTicketId = sqlResult.getLong("TicketId");

            boolean testOrder = idOrder == testOrderId;
            boolean testTicket = testTicketId == idTicket;
            return testOrder == testTicket;
        } catch (Exception e)
        {
            System.out.println("Error on test 'FixSale': " +e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
