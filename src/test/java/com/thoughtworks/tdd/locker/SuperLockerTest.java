package com.thoughtworks.tdd.locker;

import com.thoughtworks.tdd.locker.exception.LockerFullException;
import org.junit.Assert;
import org.junit.Test;

public class SuperLockerTest {
    private Locker initLocker(String lockerSize, int totalCapacity, int freeCapacity) {
        Locker locker = new Locker(lockerSize, totalCapacity);
        for (int i = 0; i < totalCapacity - freeCapacity; i++) {
            locker.depositBag(new Bag(lockerSize));
        }
        return locker;
    }

    @Test
    public void should_return_ticket_when_xiaoying_deposit_bag_given_one_SuperLockerRobot_with_two_locker_with_free_capacity() {
        Bag bag = new Bag(Constants.SIZE_L);
        SuperLockerRobot superLockerRobot = new SuperLockerRobot();
        Locker locker1 = initLocker(Constants.SIZE_L, 3, 2);
        superLockerRobot.addLocker(locker1);
        Locker locker2 = initLocker(Constants.SIZE_L, 4, 3);
        superLockerRobot.addLocker(locker2);
        Ticket ticket = superLockerRobot.depositBag(bag);
        Bag result = locker2.pickUpBag(ticket);
        Assert.assertEquals(result, bag);
    }

    @Test(expected = LockerFullException.class)
    public void should_return_prompt_when_xiaoying_deposit_bag_given_one_SuperLockerRobot_with_one_locker_with_full_capacity() {
        Bag bag = new Bag(Constants.SIZE_L);
        SuperLockerRobot superLockerRobot = new SuperLockerRobot();
        Locker locker = initLocker(Constants.SIZE_L, 1, 0);
        superLockerRobot.addLocker(locker);
        superLockerRobot.depositBag(bag);
    }
}