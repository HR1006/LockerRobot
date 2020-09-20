package com.thoughtworks.tdd.locker;

import java.util.HashMap;
import java.util.Map;

public class Locker {
    private final String size;
    private final int capacity;
    private final Map<Ticket, Bag> mapping = new HashMap<>();

    public Locker(String size, int capacity) {
        this.size = size;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int freeCapacity() {
        return getCapacity() -  mapping.size();
    }

    public Ticket depositBag(Bag bag) {
        Ticket ticket;
        if (freeCapacity() == 0) {
            throw new LockerFullException();
        } else {
            ticket = new Ticket(size);
            mapping.put(ticket, bag);
        }
        return ticket;
    }
}
