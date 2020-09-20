package com.thoughtworks.tdd.locker.storeable.robot;

import com.thoughtworks.tdd.locker.Bag;
import com.thoughtworks.tdd.locker.storeable.Locker;
import com.thoughtworks.tdd.locker.Ticket;
import com.thoughtworks.tdd.locker.exception.InvalidTicketException;
import com.thoughtworks.tdd.locker.storeable.Storeable;

import java.util.ArrayList;
import java.util.List;

public abstract class Robot implements Storeable {

    private final List<Locker> lockers = new ArrayList<>();

    public List<Locker> getLockers() {
        return lockers;
    }

    public void addLocker(Locker locker) {
        lockers.add(locker);
    }

    @Override
    public boolean isValidTicket(Ticket ticket) {
        for (Locker locker : getLockers()) {
            if (locker.isValidTicket(ticket)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Bag pickUpBag(Ticket ticket) {
        for (Locker locker : getLockers()) {
            if (locker.isValidTicket(ticket)) {
                return locker.pickUpBag(ticket);
            }
        }
        throw new InvalidTicketException();
    }

}
