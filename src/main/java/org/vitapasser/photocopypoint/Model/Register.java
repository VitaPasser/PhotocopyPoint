package org.vitapasser.photocopypoint.Model;

public class Register {
    private Order order;
    private final TypeList typeList;
    private final PriceList priceList;
    private final PickUpStation pickUpStation;
    private final TicketList ticketList;

    public Register(TypeList typeList, PriceList priceList, PickUpStation pickUpStation, TicketList ticketList) {
        this.typeList = typeList;
        this.priceList = priceList;
        this.pickUpStation = pickUpStation;
        this.ticketList = ticketList;
    }

    public void newOrder() {
        this.order = Order.create();
    }

    public void addType(String name, int count) {
        order.addType(name, typeList, count);
    }

    public Term getTerm() {
        return order.getTerm(typeList);
    }

    public Money getPrice() {
        return order.getPrice(priceList);
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
}
