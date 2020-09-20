package com.thoughtworks.tdd.locker.storeable.robot;

import com.thoughtworks.tdd.locker.Bag;
import com.thoughtworks.tdd.locker.Constants;
import com.thoughtworks.tdd.locker.Ticket;
import com.thoughtworks.tdd.locker.exception.InvalidTicketException;
import com.thoughtworks.tdd.locker.exception.LockerFullException;
import com.thoughtworks.tdd.locker.exception.TicketSizeNotMatchException;
import com.thoughtworks.tdd.locker.storeable.Locker;
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

    @Test
    public void should_return_bag_when_xiaoying_pick_up_bag_given_one_SuperLockerRobot_one_valid_ticket() {
        Bag bag = new Bag(Constants.SIZE_L);
        SuperLockerRobot superLockerRobot = new SuperLockerRobot();
        Locker locker = initLocker(Constants.SIZE_L, 1, 1);
        superLockerRobot.addLocker(locker);
        Ticket ticket = superLockerRobot.depositBag(bag);
        Bag result = superLockerRobot.pickUpBag(ticket);
        Assert.assertEquals(result, bag);
    }

    @Test(expected = InvalidTicketException.class)
    public void should_return_prompt_when_xiaoying_pick_up_bag_given_one_SuperLockerRobot_one_invalid_ticket() {
        Bag bag = new Bag(Constants.SIZE_L);
        SuperLockerRobot superLockerRobot = new SuperLockerRobot();
        Locker locker = initLocker(Constants.SIZE_L, 1, 1);
        superLockerRobot.addLocker(locker);
        superLockerRobot.depositBag(bag);
        superLockerRobot.pickUpBag(new Ticket(Constants.SIZE_M));
    }

    @Test(expected = TicketSizeNotMatchException.class)
    public void should_return_prompt_when_xiaoying_pick_up_bag_given_valid_M_size_ticket() {
        Bag bag = new Bag(Constants.SIZE_M);
        PrimaryLockerRobot primaryLockerRobot = new PrimaryLockerRobot();
        Locker locker1 = initLocker(Constants.SIZE_M, 1, 1);
        primaryLockerRobot.addLocker(locker1);
        primaryLockerRobot.depositBag(bag);

        SuperLockerRobot superLockerRobot = new SuperLockerRobot();
        Locker locker2 = initLocker(Constants.SIZE_L, 1, 1);
        superLockerRobot.addLocker(locker2);
        Ticket ticket = superLockerRobot.depositBag(bag);
        locker1.pickUpBag(ticket);
    }
}
