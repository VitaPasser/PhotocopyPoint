package org.vitapasser.photocopypoint.Controller.CreatingOrder;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import org.vitapasser.photocopypoint.Model.Type;
import org.vitapasser.photocopypoint.Model.TypeItem;

import java.time.LocalTime;

public class TypeView {
    long id;
    String name;
    String info;
    LocalTime term;
    Double countMoney;
    String currencyMoney;
    int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public TypeView (Type type){
        this.id = type.id();
        this.name = type.name();
        this.info = type.info();
        this.term = type.term().toLocalTime();
        this.countMoney = type.money().value();
        this.currencyMoney = type.money().unit();
        this.count = 1;
    }

    public TypeView (TypeItem type){
        this.id = type.type().id();
        this.name = type.type().name();
        this.info = type.type().info();
        this.term = type.getTerm().toLocalTime();
        this.countMoney = type.getMoney().value();
        this.currencyMoney = type.getMoney().unit();
        this.count = type.count();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalTime getTerm() {
        return term;
    }

    public void setTerm(LocalTime term) {
        this.term = term;
    }

    public Double getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(Double countMoney) {
        this.countMoney = countMoney;
    }

    public String getCurrencyMoney() {
        return currencyMoney;
    }

    public void setCurrencyMoney(String currencyMoney) {
        this.currencyMoney = currencyMoney;
    }
}
