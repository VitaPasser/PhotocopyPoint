package org.vitapasser.photocopypoint.Test.Register;

import org.vitapasser.photocopypoint.Model.*;
import org.vitapasser.photocopypoint.Test.AbstractTest;

import java.sql.Connection;
import java.util.Objects;

public class GetPrice extends AbstractTest {
    public GetPrice(Connection connectionToDataBase) {
        super(connectionToDataBase);
    }

    @Override
    public boolean test() {
        TypeList typeList = new TypeList(connectionToDataBase);
        PriceList priceList = new PriceList(connectionToDataBase);
        PickUpStation pickUpStation = new PickUpStation(connectionToDataBase,
                "Київ, вул. Хрещатик, 1");
        TicketList ticketList = new TicketList(connectionToDataBase);

        Register register = new Register(typeList, priceList, pickUpStation,
                ticketList);

        register.newOrder();
        register.addType("Друк", 57);
        Money price = register.getPrice();

        Money testPrice = new Money(57 * 100.0000, "UAH");

        return Objects.equals(price, testPrice);
    }
}
