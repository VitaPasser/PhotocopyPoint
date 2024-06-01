package org.vitapasser.photocopypoint.Model;

import java.util.List;

public class Calc {
    public static Money createPrice(PriceList priceList,
                                    List<Type> types) {
        double countMoney = 0.0;
        String unit = "";
        for (Type type : types) {
            Money money = priceList.getPrice(type.name());
            Count count = type.count();

            countMoney += count.count() * money.count();
            unit = money.unit();
        }

        return new Money(countMoney, unit);
    }
}
