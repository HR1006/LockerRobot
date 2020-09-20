package com.thoughtworks.tdd.locker;

import com.thoughtworks.tdd.locker.exception.InvalidTicketException;
import com.thoughtworks.tdd.locker.exception.LockerFullException;
import com.thoughtworks.tdd.locker.exception.TicketSizeNotMatchException;

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

    public boolean isValidTicket(Ticket ticket) {
        return mapping.containsKey(ticket);
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

    public Bag pickUpBag(Ticket ticket) {
        if (size.equals(ticket.getSize())) {
            Bag bag = mapping.remove(ticket);;
            if (bag == null) {
                throw new InvalidTicketException();
            }
            return bag;
        } else {
            throw new TicketSizeNotMatchException();
        }
    }
}
