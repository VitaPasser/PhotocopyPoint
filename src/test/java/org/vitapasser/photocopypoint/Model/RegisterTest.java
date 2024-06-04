package org.vitapasser.photocopypoint.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTest {

    Connection connection = Mysql.getConnection();

    RegisterTest() throws SQLException, IOException {
    }

    @Test
    void getTerm() {
        TypeList typeList = new TypeList(connection);
        PickUpStation pickUpStation = new PickUpStation(connection,
                "Київ, вул. Хрещатик, 1");
        TicketList ticketList = new TicketList(connection);

        Register register = new Register(typeList, pickUpStation, ticketList);

        register.newOrder();
        register.addType("Друк", 57);
        Term term = register.getTerm();

        Term testTerm = new Term(57 * 60);

        Assertions.assertEquals(testTerm, term);
    }

    @Test
    void getPrice() {
        TypeList typeList = new TypeList(connection);
        PickUpStation pickUpStation = new PickUpStation(connection,
                "Київ, вул. Хрещатик, 1");
        TicketList ticketList = new TicketList(connection);

        Register register = new Register(typeList, pickUpStation, ticketList);

        register.newOrder();
        register.addType("Друк", 57);
        Money price = register.getPrice();

        Money testPrice = new Money(57 * 100.0000, "UAH");

        Assertions.assertEquals(testPrice, price);
    }

    @Test
    void makePayment() {
        TypeList typeList = new TypeList(connection);
        PickUpStation pickUpStation = new PickUpStation(connection,
                "Київ, вул. Хрещатик, 1");
        TicketList ticketList = new TicketList(connection);

        Register register = new Register(typeList, pickUpStation, ticketList);

        register.newOrder();
        register.addType("Друк", 57);
        register.getTerm();
        register.getPrice();
        Money oddMoney = register.makePayment(new Money(60 * 100.0000, "UAH"),
                "Сергій Сергійович Сержі",
                "+3809844678983");

        Money testOddMoney = new Money(3 * 100.0000, "UAH");

        Assertions.assertEquals(testOddMoney, oddMoney);
    }
}