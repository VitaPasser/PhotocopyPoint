package org.vitapasser.photocopypoint.Model;

public record Count(int count) {
    @Override
    public int count() {
        return count;
    }
}
