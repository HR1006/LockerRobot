package com.thoughtworks.tdd.locker;

import com.thoughtworks.tdd.locker.exception.InvalidTicketException;
import com.thoughtworks.tdd.locker.exception.LockerFullException;
import com.thoughtworks.tdd.locker.exception.SizeNotMatchException;
import com.thoughtworks.tdd.locker.storeable.Locker;
import com.thoughtworks.tdd.locker.storeable.Storeable;
import com.thoughtworks.tdd.locker.storeable.robot.PrimaryLockerRobot;
import com.thoughtworks.tdd.locker.storeable.robot.SuperLockerRobot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LockerRobotManager {
    private final List<Storeable> storeables = new ArrayList<>();

    public void addStoreable(Storeable storeable) {
        Storeable addStoreable = null;
        if (storeable instanceof Locker
                && Constants.SIZE_S.equals(((Locker) storeable).getSize())) {
            addStoreable = storeable;
        }
        if (addStoreable == null) {
            throw new SizeNotMatchException();
        } else {
            storeables.add(storeable);
        }
    }

    public Ticket depositBagByStoreableType(Class<? extends Storeable> clazz, Bag bag) {
        Ticket ticket = null;
        for (Storeable storeable : getStoreablesByType(clazz)) {
            ticket = storeable.depositBagOrNot(bag);
            if (ticket != null) {
                break;
            }
        }
        return ticket;
    }

    public List<? extends Storeable> getStoreablesByType(Class<? extends Storeable> clazz) {
        return storeables
                .stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    public Ticket depositBag(Bag bag) {
        Ticket ticket = null;
        if (Constants.SIZE_S.equals(bag.getSize())) {
            ticket = depositBagByStoreableType(Locker.class, bag);
        }
        if (Constants.SIZE_M.equals(bag.getSize())) {
            ticket = depositBagByStoreableType(PrimaryLockerRobot.class, bag);
        }
        if (Constants.SIZE_L.equals(bag.getSize())) {
            ticket = depositBagByStoreableType(SuperLockerRobot.class, bag);
        }
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
