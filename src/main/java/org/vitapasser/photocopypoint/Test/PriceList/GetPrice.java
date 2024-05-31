package org.vitapasser.photocopypoint.Test.PriceList;

import org.vitapasser.photocopypoint.Model.Money;
import org.vitapasser.photocopypoint.Model.PriceList;
import org.vitapasser.photocopypoint.Test.AbstractTest;

import java.sql.Connection;
import java.util.Objects;

public class GetPrice extends AbstractTest {
    public GetPrice(Connection connectionToDataBase) {
        super(connectionToDataBase);
    }

    @Override
    public boolean test() {
        PriceList priceList = new PriceList(connectionToDataBase);
        Money priceGot = priceList.getPrice("Копіювання");

        Money priceReference = new Money(200.0000, "UAH");

        return Objects.equals(priceGot, priceReference);
    }
}
