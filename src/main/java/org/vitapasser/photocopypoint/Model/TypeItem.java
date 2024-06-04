package org.vitapasser.photocopypoint.Model;

public record TypeItem(Type type, int count) {

    public Money getMoney() {
        return new Money(type.money().value() * count, type.money().unit());
    }

    public Term getTerm() {
        return new Term(type.term().value() * count);
    }
}
