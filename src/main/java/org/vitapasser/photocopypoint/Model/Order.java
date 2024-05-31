package org.vitapasser.photocopypoint.Model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<Type> types;
    private Term term;
    private Money price;
    private String fullName;
    private String phoneNumber;
    private Operator operator;

    private Order() {}

    public static Order create() {
        Order order = new Order();
        order.types = new ArrayList<Type>();
        return order;
    }

    public void addType(String name, TypeList typeList, int count) {
        Type type = typeList.addType(name, count);
        types.add(type);
    }
    public Term getTerm(TypeList typeList) {
        term = Term.getTerm(typeList, types);
        return term;
    }
    public Money getPrice(PriceList priceList) {
        price = Calc.createPrice(priceList, types);
        return price;
    }
    public Money makePayment(Money countPayMoney,
                             String fullName,
                             String phoneNumber,
                             PickUpStation pickUpStation) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        operator = pickUpStation.getOperator();
        Money change = pickUpStation.fixSale(this, countPayMoney);
        print();
        return change;
    }
    public void print() {
        /*
        * Друкує інформацію об об'єкті.
        * */
    }

    /*
    * Якщо потрібно буде отримати данні замовлення для інших програмних потреб.
    * */
    public List<Type> getTypes() {
        return types;
    }

    public Term getTerm() {
        if (term == null) {
            throw new NullPointerException();
        }
        return term;
    }

    public Money getPrice() {
        if (price == null) {
            throw new NullPointerException();
        }
        return price;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
