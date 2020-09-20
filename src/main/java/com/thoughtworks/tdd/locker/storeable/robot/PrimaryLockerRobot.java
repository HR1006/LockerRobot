package com.thoughtworks.tdd.locker.storeable.robot;

import com.thoughtworks.tdd.locker.Bag;
import com.thoughtworks.tdd.locker.Constants;
import com.thoughtworks.tdd.locker.exception.SizeNotMatchException;
import com.thoughtworks.tdd.locker.storeable.Locker;
import com.thoughtworks.tdd.locker.Ticket;
import com.thoughtworks.tdd.locker.exception.LockerFullException;

import java.util.ArrayList;
import java.util.List;

public class PrimaryLockerRobot extends Robot {
    private final List<Locker> lockers = new ArrayList<>();

    public List<Locker> getLockers() {
        return lockers;
    }

    public void addLocker(Locker locker) {
        if (Constants.SIZE_M.equals(locker.getSize())) {
            lockers.add(locker);
        } else {
            throw new SizeNotMatchException();
        }
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
