package org.vitapasser.photocopypoint.Controller.DTO;

public record OrderView (
    long idOrder,
    long idTicket,
    String isReady,
    String clientFullName,
    String clientPhoneNumber,
    String staffFullName,
    String staffPhoneNumber,
    String staffSpecialization,
    String addressMake,
    String money,
    String stationName,
    String term
){}
