package org.vitapasser.photocopypoint.Test.Register;

import org.vitapasser.photocopypoint.Model.Register;
import org.vitapasser.photocopypoint.Model.*;
import org.vitapasser.photocopypoint.Test.AbstractTest;

import java.sql.Connection;
import java.util.Objects;

public class MakePayment extends AbstractTest {

    public MakePayment(Connection connectionToDataBase) {
        super(connectionToDataBase);
    }

    @Override
    public boolean test() {
        TypeList typeList = new TypeList(connectionToDataBase);
        PickUpStation pickUpStation = new PickUpStation(connectionToDataBase,
                "Київ, вул. Хрещатик, 1");
        TicketList ticketList = new TicketList(connectionToDataBase);

        Register register = new Register(typeList, pickUpStation, ticketList);

        register.newOrder();
        register.addType("Друк", 57);
        register.getTerm();
        register.getPrice();
        Money oddMoney = register.makePayment(new Money(60 * 100.0000, "UAH"),
                "Сергій Сергійович Сержі",
                "+3809844678983");

        Money testOddMoney = new Money(3 * 100.0000, "UAH");

        return Objects.equals(oddMoney, testOddMoney);
    }
}
