package org.vitapasser.photocopypoint.Model;

import java.util.Objects;

public record Money(Double value, String unit) {
    public Money sum(Money money) {
        try {
            if (!Objects.equals(unit, money.unit())){
                throw new Exception("Money units don't match");
            }
            return new Money(value + money.value(), unit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f", value) + " " + unit;
    }
}
