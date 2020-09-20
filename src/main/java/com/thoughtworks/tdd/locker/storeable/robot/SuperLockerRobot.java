package com.thoughtworks.tdd.locker.storeable.robot;

import com.thoughtworks.tdd.locker.Bag;
import com.thoughtworks.tdd.locker.Constants;
import com.thoughtworks.tdd.locker.exception.SizeNotMatchException;
import com.thoughtworks.tdd.locker.storeable.Locker;
import com.thoughtworks.tdd.locker.Ticket;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SuperLockerRobot extends Robot {
    private final List<Locker> lockers = new ArrayList<>();

    public List<Locker> getLockers() {
        return lockers;
    }

    public void addLocker(Locker locker) {
        if (Constants.SIZE_L.equals(locker.getSize())) {
            lockers.add(locker);
        } else {
            throw new SizeNotMatchException();
        }
    }

    public List<Locker> sortedByVacancyRate() {
        return lockers
                .stream()
                .sorted(Comparator.comparingDouble(locker -> - ((double)(locker.freeCapacity()) / (double)(locker.getCapacity()))))
                .collect(Collectors.toList());
    }

    @Override
    public Ticket depositBag(Bag bag) {
        return sortedByVacancyRate().get(0).depositBag(bag);
    }
}
