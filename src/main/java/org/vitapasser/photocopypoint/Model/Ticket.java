package org.vitapasser.photocopypoint.Model;

import java.util.Objects;

public final class Ticket {
    private long id;
    private long orderId;
    private String name;
    private boolean isReady;
    private boolean isDone;
    private String fullName;
    private String phoneNumber;

    public Ticket(long id, long orderId, String name, boolean isReady, boolean isDone, String fullName, String phoneNumber) {
        this.id = id;
        this.orderId = orderId;
        this.name = name;
        this.isReady = isReady;
        this.isDone = isDone;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(boolean ready) {
        isReady = ready;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
