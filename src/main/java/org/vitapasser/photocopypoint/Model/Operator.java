package org.vitapasser.photocopypoint.Model;

public record Operator(Long id, String name) {
    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }
}
