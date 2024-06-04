package org.vitapasser.photocopypoint.Model;

import org.vitapasser.photocopypoint.Exception.NotExistTypeException;

import java.util.List;

public class Register {
    private Order order;
    private final TypeList typeList;
    private final PickUpStation pickUpStation;
    private final TicketList ticketList;

    public Register(TypeList typeList, PickUpStation pickUpStation, TicketList ticketList) {
        this.typeList = typeList;
        this.pickUpStation = pickUpStation;
        this.ticketList = ticketList;
    }

    public void newOrder() {
        this.order = Order.create();
    }

    public List<TypeItem> addType(String name, int count) {
        order.addType(name, typeList, count);
        return order.getTypeItems();
    }

    public Term getTerm() {
        return order.getTerm();
    }

    public Money getPrice() {
        return order.getPrice();
    }

    public Money makePayment(Money countPayMoney,
                             String fullName,
                             String phoneNumber){
         return order.makePayment(countPayMoney,
                fullName,
                phoneNumber,
                pickUpStation,
                 ticketList);
    }

    public List<Type> getTypes(String likeNameTickets) throws NotExistTypeException {
        return typeList.getTypes(likeNameTickets);
    }

    public List<TypeItem> getOrderTypes() {
        return order.getTypeItems();
    }

    public List<Ticket> getAllTickets() {
        return ticketList.getAllTickets();
    }
}
