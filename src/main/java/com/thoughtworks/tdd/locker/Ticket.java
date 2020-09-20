package com.thoughtworks.tdd.locker;

public class Ticket {
    private final String size;

    public Ticket(String size){
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
