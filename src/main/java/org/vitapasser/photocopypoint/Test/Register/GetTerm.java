package org.vitapasser.photocopypoint.Test.Register;

import org.vitapasser.photocopypoint.Model.*;
import org.vitapasser.photocopypoint.Test.AbstractTest;

import java.sql.Connection;
import java.util.Objects;

public class GetTerm extends AbstractTest {
    public GetTerm(Connection connectionToDataBase) {
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
        Term term = register.getTerm();

        Term testTerm = new Term(57 * 60);

        return Objects.equals(term, testTerm);
    }
}
