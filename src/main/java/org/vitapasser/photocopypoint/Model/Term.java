package org.vitapasser.photocopypoint.Model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record Term(Integer value) {

    public Term sum(Term term) {
        return new Term(this.value() + term.value());
    }

    public LocalTime toLocalTime(){
        return LocalTime.ofSecondOfDay(value);
    }

    @Override
    public String toString() {
        LocalTime time = toLocalTime();

        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
