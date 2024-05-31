package org.vitapasser.photocopypoint.Model;

import java.util.List;

public class Calc {
    public static Money createPrice(PriceList priceList,
                                    List<Type> types) {
        Double countMoney = 0.0;
        String unit = "";
        for (int i = 0; i < types.size(); i++) {
            Money money = priceList.getPrice(types.get(i).name());
            Count count = types.get(i).count();

            countMoney += count.count() * money.count();
            unit = money.unit();
        }

        if (unit == "") return null;

        return new Money(countMoney, unit);
    }
}
