package org.vitapasser.photocopypoint.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class TicketListTest {

    Connection connection = Mysql.getConnection();

    TicketListTest() throws SQLException, IOException {
    }

    @Test
    void getAllTickets() {
        TypeList typeList = new TypeList(connection);
        PickUpStation pickUpStation = new PickUpStation(connection,"Київ, вул. Хрещатик, 1");
        TicketList ticketList = new TicketList(connection);
        Register register = new Register(typeList, pickUpStation, ticketList);

        List<Ticket> tickets = register.getAllTickets();

        List<Ticket> ticketReference = new ArrayList<>();

        ticketReference.add(new Ticket(1, 1, "Друк", false, "Іван Іванов", "+380501234567"));

        Assertions.assertEquals(ticketReference.getFirst(), tickets.getFirst());
    }

    @Test
    void createTicket() {
        TicketList ticketList = new TicketList(connection);

        long idOrder = 1;
        long idTicket = ticketList.createTicket(idOrder);

        String sqlCheck = "SELECT `PhotocopyPoint`.`Order`.id as OrderId, `PhotocopyPoint`.`Ticket`.id as TicketId\n" +
                          "FROM `PhotocopyPoint`.`Ticket`\n" +
                          "INNER JOIN `PhotocopyPoint`.`Order` " +
                          "on `PhotocopyPoint`.`Order`.`id` = `PhotocopyPoint`.Ticket.order_id\n" +
                          "WHERE `PhotocopyPoint`.`Order`.`id` = "+idOrder+"\n" +
                          "GROUP BY OrderId, TicketId " +
                          "ORDER BY OrderId, TicketId DESC LIMIT 1;";

        long testOrderId = -1, testTicketId = -1;
        try {
            Statement statement = connection.createStatement();

            ResultSet sqlResult = statement.executeQuery(sqlCheck);
            sqlResult.next();

            testOrderId = sqlResult.getLong("OrderId");
            testTicketId = sqlResult.getLong("TicketId");

        } catch (Exception e)
        {
            System.out.println("Error on test 'FixSale': " +e.getMessage());
            e.printStackTrace();
        }

        Assertions.assertEquals(idOrder, testOrderId);
        Assertions.assertEquals(idTicket, testTicketId);
    }
}