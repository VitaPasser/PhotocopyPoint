package org.vitapasser.photocopypoint.Model;

public record Money(Double count, String unit) {
    @Override
    public Double count() {
        return count;
    }
}
