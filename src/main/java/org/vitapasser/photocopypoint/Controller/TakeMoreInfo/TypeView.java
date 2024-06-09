package org.vitapasser.photocopypoint.Controller.TakeMoreInfo;

import org.vitapasser.photocopypoint.Model.Type;
import org.vitapasser.photocopypoint.Model.TypeItem;

import java.time.LocalTime;

public class TypeView {
    String id;
    String name;
    String info;
    String term;
    String money;
    String count;

    public TypeView(TypeItem type){
        this.id = type.type().id().toString();
        this.name = type.type().name();
        this.info = type.type().info();
        this.term = type.type().term().toLocalTime().toString();
        this.money = type.getMoney().toString();
        this.count = String.valueOf(type.count());
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
