package org.vitapasser.photocopypoint.Model;

public record Ticket (long id,
                      long orderId,
                      String name,
                      boolean isReady,
                      boolean isDone,
                      String fullName,
                      String phoneNumber) {}