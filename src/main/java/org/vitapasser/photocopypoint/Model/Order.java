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
        price = typeItems.stream()
                .map(TypeItem::getMoney)
                .reduce(Money::sum)
                .orElseThrow();
        return price;
    }
    public Money makePayment(Money countPayMoney,
                             String fullName,
                             String phoneNumber,
                             PickUpStation pickUpStation,
                             TicketList ticketList) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        operator = pickUpStation.getOperator();

        OrderIDAndOddMany OrderIDAndOddMany = pickUpStation.fixSale(this, countPayMoney);
        assert OrderIDAndOddMany != null;
        Money change = OrderIDAndOddMany.oddMoney();
        ticketList.createTicket(OrderIDAndOddMany.orderID());
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
