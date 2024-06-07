package org.vitapasser.photocopypoint.Model;

import java.util.Objects;

public final class Ticket {
    private long id;
    private long orderId;
    private String name;
    private boolean isReady;
    private String fullName;
    private String phoneNumber;

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

    public Ticket(long id,
                  long orderId,
                  String name,
                  boolean isReady,
                  String fullName,
                  String phoneNumber) {
        this.id = id;
        this.orderId = orderId;
        this.name = name;
        this.isReady = isReady;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Ticket) obj;
        return this.id == that.id &&
               this.orderId == that.orderId &&
               Objects.equals(this.name, that.name) &&
               this.isReady == that.isReady &&
               Objects.equals(this.fullName, that.fullName) &&
               Objects.equals(this.phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, name, isReady, fullName, phoneNumber);
    }

    @Override
    public String toString() {
        return "Ticket[" +
               "id=" + id + ", " +
               "orderId=" + orderId + ", " +
               "name=" + name + ", " +
               "isReady=" + isReady + ", " +
               "fullName=" + fullName + ", " +
               "phoneNumber=" + phoneNumber + ']';
    }
}
