package org.vitapasser.photocopypoint.Controller.DTO;

public record OrderView (
    long idOrder,
    long idTicket,
    boolean isReady,
    String clientFullName,
    String clientPhoneNumber,
    String staffFullName,
    String staffPhoneNumber,
    String staffSpecialization,
    String addressMake,
    Double countMoney,
    String currencyMoney,
    String stationName
){}
