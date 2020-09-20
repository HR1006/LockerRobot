package com.thoughtworks.tdd.locker;

import com.thoughtworks.tdd.locker.exception.LockerFullException;
import com.thoughtworks.tdd.locker.storeable.Locker;
import org.junit.Assert;
import org.junit.Test;

public class LockerRobotManagerTest {

    private Locker initLocker(String lockerSize, int totalCapacity, int freeCapacity) {
        Locker locker = new Locker(lockerSize, totalCapacity);
        for (int i = 0; i < totalCapacity - freeCapacity; i++) {
            locker.depositBag(new Bag(lockerSize));
        }
        return locker;
    }

    @Test
    public void should_return_ticket_when_deposit_bag_given_one_S_bag_one_S_locker_with_free_capacity() {
        LockerRobotManager manager = new LockerRobotManager();
        Locker locker = initLocker(Constants.SIZE_S, 1, 1);
        manager.addStoreable(locker);
        Bag bag = new Bag(Constants.SIZE_S);
        Ticket ticket = manager.depositBag(bag);
        Bag result = locker.pickUpBag(ticket);
        Assert.assertEquals(result, bag);
    }

    @Test(expected = LockerFullException.class)
    public void should_return_prompt_when_deposit_bag_given_one_S_bag_one_S_locker_with_full_capacity() {
        LockerRobotManager manager = new LockerRobotManager();
        Locker locker = initLocker(Constants.SIZE_S, 1, 0);
        manager.addStoreable(locker);
        Bag bag = new Bag(Constants.SIZE_S);
        manager.depositBag(bag);
    }
}
