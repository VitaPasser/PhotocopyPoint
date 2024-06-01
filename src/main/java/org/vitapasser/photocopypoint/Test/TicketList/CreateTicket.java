package org.vitapasser.photocopypoint.Test.TicketList;

import org.vitapasser.photocopypoint.Model.TicketList;
import org.vitapasser.photocopypoint.Test.AbstractTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class CreateTicket extends AbstractTest {
    public CreateTicket(Connection connectionToDataBase) {
        super(connectionToDataBase);
    }

    @Override
    public boolean test() {
        TicketList ticketList = new TicketList(connectionToDataBase);

        long idOrder = 1;
        long idTicket = ticketList.createTicket(idOrder);

        String sqlCheck = "SELECT `PhotocopyPoint`.`Order`.id\n" +
                "FROM `PhotocopyPoint`.`Ticket`\n" +
                "INNER JOIN `PhotocopyPoint`.`Order` " +
                "on `PhotocopyPoint`.`Order`.`id` = `PhotocopyPoint`.Ticket.order_id\n" +
                "WHERE `PhotocopyPoint`.`Order`.`id` = "+idOrder+"\n" +
                "GROUP BY `PhotocopyPoint`.`Order`.id " +
                "ORDER BY `PhotocopyPoint`.`Order`.id DESC LIMIT 1;";

        try {
            Statement statement = connectionToDataBase.createStatement();

            ResultSet sqlResult = statement.executeQuery(sqlCheck);
            sqlResult.next();

            long testTicketId = sqlResult.getLong("id");

            return Objects.equals(testTicketId, idTicket);
        } catch (Exception e)
        {
            System.out.println("Error on test 'FixSale': " +e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
}
