package com.thoughtworks.tdd.locker;

import com.thoughtworks.tdd.locker.exception.InvalidTicketException;
import com.thoughtworks.tdd.locker.exception.LockerFullException;
import com.thoughtworks.tdd.locker.storeable.Locker;
import com.thoughtworks.tdd.locker.storeable.Storeable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LockerRobotManager {
    private final List<Storeable> storeables = new ArrayList<>();

    public void addStoreable(Storeable storeable) {
        storeables.add(storeable);
    }

    public Ticket depositBagByLocker(Bag bag) {
        Ticket ticket = null;
        List<Locker> lockers = storeables
                .stream()
                .filter(storeable -> storeable instanceof Locker)
                .map(storeable -> (Locker) storeable)
                .collect(Collectors.toList());
        for (Locker locker : lockers) {
            if (locker.freeCapacity() > 0) {
                ticket = locker.depositBag(bag);
                break;
            }
        }
        return ticket;
    }

    public Ticket depositBag(Bag bag) {
        Ticket ticket;
        ticket = depositBagByLocker(bag);
        if (ticket != null) {
            return ticket;
        }
        throw new LockerFullException();
    }

    public Bag pickUpBag(Ticket ticket) {
        for (Storeable storeable : storeables) {
            if (storeable.isValidTicket(ticket)) {
                return storeable.pickUpBag(ticket);
            }
        }
        throw new InvalidTicketException();
    }
}
