package org.vitapasser.photocopypoint.Model;

import org.vitapasser.photocopypoint.Controller.DTO.OrderView;
import org.vitapasser.photocopypoint.Exception.NotExistTypeException;

import java.util.List;

public class Register {
    private Order order;
    private final TypeList typeList;
    private final PickUpStation pickUpStation;
    private final TicketList ticketList;
    private final OrderList orderList;

    public Register(TypeList typeList, PickUpStation pickUpStation, TicketList ticketList, OrderList orderList) {
        this.typeList = typeList;
        this.pickUpStation = pickUpStation;
        this.ticketList = ticketList;
        this.orderList = orderList;
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

    public List<Type> getTypes(String likeNameTypes) throws NotExistTypeException {
        return typeList.getTypes(likeNameTypes);
    }

    public List<TypeItem> getOrderTypes() {
        return order.getTypeItems();
    }

    public List<Ticket> getAllTickets() {
        return ticketList.getAllTickets();
    }

    public List<Ticket> getAllMadeTickets() {
        return ticketList.getAllMadeTickets();
    }

    public OrderViewAndTypesViews getFullInfoAboutOrder(long TicketId) {
        return orderList.getFullInfoAboutOrder(TicketId);
    }

    public void giveTheOrder(long TicketId) {
        ticketList.setEndOrderTicket(TicketId);
    }

    public void madeTheOrder(long TicketId) {
        ticketList.setMadeOrderTicket(TicketId);
    }

    public List<Ticket> getAllNotMadeTickets(String fullNameStaff) {
        return ticketList.getAllNotMadeTickets(fullNameStaff);
    }
}
