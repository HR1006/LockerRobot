package com.thoughtworks.tdd.locker;

import java.util.ArrayList;
import java.util.List;

public class PrimaryLockerRobot extends Robot {
    private final List<Locker> lockers = new ArrayList<>();

    public List<Locker> getLockers() {
        return lockers;
    }

    public void addLocker(Locker locker) {
        lockers.add(locker);
    }

    @Override
    public Ticket depositBag(Bag bag) {
        Ticket ticket = null;
        for (Locker locker : getLockers()) {
            if (locker.freeCapacity() > 0) {
                ticket = locker.depositBag(bag);
                break;
            }
        }
        if (ticket == null) {
            throw new LockerFullException();
        } else {
            return ticket;
        }
    }
}
