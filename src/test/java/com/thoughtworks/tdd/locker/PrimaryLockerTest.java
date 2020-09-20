package com.thoughtworks.tdd.locker;

import com.thoughtworks.tdd.locker.exception.InvalidTicketException;
import com.thoughtworks.tdd.locker.exception.LockerFullException;
import com.thoughtworks.tdd.locker.exception.TicketSizeNotMatchException;
import com.thoughtworks.tdd.locker.storeable.Locker;
import com.thoughtworks.tdd.locker.storeable.robot.PrimaryLockerRobot;
import org.junit.Assert;
import org.junit.Test;

public class PrimaryLockerTest {
    private Locker initLocker(String lockerSize, int totalCapacity, int freeCapacity) {
        Locker locker = new Locker(lockerSize, totalCapacity);
        for (int i = 0; i < totalCapacity - freeCapacity; i++) {
            locker.depositBag(new Bag(lockerSize));
        }
        return locker;
    }

    @Test
    public void should_return_ticket_when_xiaoying_deposit_bag_given_one_PrimaryLockerRobot_with_two_locker_with_free_capacity() {
        Bag bag = new Bag(Constants.SIZE_M);
        PrimaryLockerRobot primaryLockerRobot = new PrimaryLockerRobot();
        Locker locker1 = initLocker(Constants.SIZE_M, 1, 1);
        primaryLockerRobot.addLocker(locker1);
        Locker locker2 = initLocker(Constants.SIZE_M, 1, 1);
        primaryLockerRobot.addLocker(locker2);
        Ticket ticket = primaryLockerRobot.depositBag(bag);
        Bag result = locker1.pickUpBag(ticket);
        Assert.assertEquals(result, bag);
    }

    @Test(expected = LockerFullException.class)
    public void should_return_prompt_when_xiaoying_deposit_bag_given_one_PrimaryLockerRobot_with_one_locker_with_full_capacity() {
        Bag bag = new Bag(Constants.SIZE_M);
        PrimaryLockerRobot primaryLockerRobot = new PrimaryLockerRobot();
        Locker locker = initLocker(Constants.SIZE_M, 1, 0);
        primaryLockerRobot.addLocker(locker);
        primaryLockerRobot.depositBag(bag);
    }

    @Test
    public void should_return_bag_when_xiaoying_pick_up_bag_given_one_PrimaryLockerRobot_one_valid_ticket() {
        Bag bag = new Bag(Constants.SIZE_M);
        PrimaryLockerRobot primaryLockerRobot = new PrimaryLockerRobot();
        Locker locker = initLocker(Constants.SIZE_M, 1, 1);
        primaryLockerRobot.addLocker(locker);
        Ticket ticket = primaryLockerRobot.depositBag(bag);
        Bag result = primaryLockerRobot.pickUpBag(ticket);
        Assert.assertEquals(result, bag);
    }

    @Test(expected = InvalidTicketException.class)
    public void should_return_prompt_when_xiaoying_pick_up_bag_given_one_PrimaryLockerRobot_one_invalid_ticket() {
        Bag bag = new Bag(Constants.SIZE_M);
        PrimaryLockerRobot primaryLockerRobot = new PrimaryLockerRobot();
        Locker locker = initLocker(Constants.SIZE_M, 1, 1);
        primaryLockerRobot.addLocker(locker);
        primaryLockerRobot.depositBag(bag);
        primaryLockerRobot.pickUpBag(new Ticket(Constants.SIZE_M));
    }

    @Test(expected = TicketSizeNotMatchException.class)
    public void should_return_prompt_when_xiaoying_pick_up_bag_given_valid_S_size_ticket() {
        Bag bag = new Bag(Constants.SIZE_S);
        Locker locker = initLocker(Constants.SIZE_S, 1, 1);
        Ticket ticket = locker.depositBag(bag);

        PrimaryLockerRobot primaryLockerRobot = new PrimaryLockerRobot();
        Locker locker2 = initLocker(Constants.SIZE_M, 1, 1);
        primaryLockerRobot.addLocker(locker2);
        primaryLockerRobot.depositBag(bag);

        locker2.pickUpBag(ticket);
    }
}
