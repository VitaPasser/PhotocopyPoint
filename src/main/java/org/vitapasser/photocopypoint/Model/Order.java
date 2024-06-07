package org.vitapasser.photocopypoint.Model;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<TypeItem> typeItems;
    private Term term;
    private Money price;
    private String fullName;
    private String phoneNumber;
    private Operator operator;

    private Order() {}

    public static Order create() {
        Order order = new Order();
        order.typeItems = new ArrayList<>();
        return order;
    }

    public void addType(String name, TypeList typeList, int count) {
        Type type = typeList.getType(name);
        typeItems.add(new TypeItem(type, count));
    }
    public Term getTerm() {
        term = typeItems.stream()
                .map(TypeItem::getTerm)
                .reduce(Term::sum)
                .orElseThrow();
        return term;
    }
    public Money getPrice() {
        return typeItems.stream()
                .map(TypeItem::getMoney)
                .reduce(Money::sum)
                .orElseThrow();
    }
    public Money makePayment(Money countPayMoney,
                             String fullName,
                             String phoneNumber,
                             PickUpStation pickUpStation,
                             TicketList ticketList) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        operator = pickUpStation.getOperator();

        OrderIDAndOddMany orderIDAndOddMoney = pickUpStation.fixSale(this, countPayMoney);
        assert orderIDAndOddMoney != null;
        Money change = orderIDAndOddMoney.oddMoney();
        ticketList.createTicket(orderIDAndOddMoney.orderID());
        return change;
    }

    /*
    * Якщо потрібно буде отримати данні замовлення для інших програмних потреб.
    * */
    public List<TypeItem> getTypeItems() {
        return typeItems;
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
