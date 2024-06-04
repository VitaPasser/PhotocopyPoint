package org.vitapasser.photocopypoint.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        List<Ticket> ticketReference = new ArrayList<Ticket>();

        ticketReference.add(new Ticket(1, 1, "Друк", false, "Іван Іванов", "+380501234567"));

        Assertions.assertEquals(ticketReference.getFirst(), tickets.getFirst());
    }
}