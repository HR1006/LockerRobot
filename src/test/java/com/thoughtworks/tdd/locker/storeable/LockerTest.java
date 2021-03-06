package com.thoughtworks.tdd.locker.storeable;

import com.thoughtworks.tdd.locker.Bag;
import com.thoughtworks.tdd.locker.Constants;
import com.thoughtworks.tdd.locker.LockerRobotManager;
import com.thoughtworks.tdd.locker.Ticket;
import com.thoughtworks.tdd.locker.exception.InvalidTicketException;
import com.thoughtworks.tdd.locker.exception.LockerFullException;
import com.thoughtworks.tdd.locker.exception.SizeNotMatchException;
import com.thoughtworks.tdd.locker.exception.TicketSizeNotMatchException;
import com.thoughtworks.tdd.locker.storeable.robot.PrimaryLockerRobot;
import org.junit.Assert;
import org.junit.Test;

public class LockerTest {
    private Locker initLocker(String lockerSize, int totalCapacity, int freeCapacity) {
        Locker locker = new Locker(lockerSize, totalCapacity);
        for (int i = 0; i < totalCapacity - freeCapacity; i++) {
            locker.depositBag(new Bag(lockerSize));
        }
        return locker;
    }

    @Test
    public void should_return_ticket_when_xiaoying_deposit_bag_given_one_S_bag_one_S_locker_with_free_capacity() {
        Bag bag = new Bag(Constants.SIZE_S);
        Locker locker = initLocker(Constants.SIZE_S, 1, 1);
        Ticket ticket = locker.depositBag(bag);
        Assert.assertNotNull(ticket);
    }

    @Test(expected = LockerFullException.class)
    public void should_return_prompt_when_xiaoying_deposit_bag_given_one_S_bag_one_S_locker_with_full_capacity() {
        Bag bag = new Bag(Constants.SIZE_S);
        Locker locker = initLocker(Constants.SIZE_S, 1, 0);
        locker.depositBag(bag);
    }

    @Test
    public void should_return_bag_when_xiaoying_pick_up_bag_given_valid_S_size_ticket() {
        Bag bag = new Bag(Constants.SIZE_S);
        Locker locker = initLocker(Constants.SIZE_S, 1, 1);
        Ticket ticket = locker.depositBag(bag);
        Bag result = locker.pickUpBag(ticket);
        Assert.assertEquals(bag, result);
    }

    @Test(expected = InvalidTicketException.class)
    public void should_return_prompt_when_xiaoying_pick_up_bag_given_invalid_S_size_ticket() {
        Bag bag = new Bag(Constants.SIZE_S);
        Locker locker = initLocker(Constants.SIZE_S, 1, 1);
        locker.depositBag(bag);
        locker.pickUpBag(new Ticket(Constants.SIZE_S));
    }

    @Test(expected = TicketSizeNotMatchException.class)
    public void should_return_prompt_when_xiaoying_pick_up_bag_given_valid_M_size_ticket() {
        Bag bag = new Bag(Constants.SIZE_S);
        Locker locker = initLocker(Constants.SIZE_S, 1, 1);
        locker.depositBag(bag);

        PrimaryLockerRobot primaryLockerRobot = new PrimaryLockerRobot();
        Locker locker2 = initLocker(Constants.SIZE_M, 1, 1);
        primaryLockerRobot.addLocker(locker2);
        Ticket ticket = primaryLockerRobot.depositBag(bag);

        locker.pickUpBag(ticket);
    }

    @Test
    public void should_success_when_add_locker_to_manager_given_one_S_locker() {
        LockerRobotManager manager = new LockerRobotManager();
        Locker locker = new Locker(Constants.SIZE_S, 1);
        manager.addStoreable(locker);
        Assert.assertEquals(manager.getStoreablesByType(Locker.class).get(0), locker);
    }

    @Test(expected = SizeNotMatchException.class)
    public void should_prompt_when_add_locker_to_manager_given_one_M_locker_one_L_locker() {
        LockerRobotManager manager = new LockerRobotManager();
        Locker locker1 = new Locker(Constants.SIZE_M, 1);
        manager.addStoreable(locker1);
        Locker locker2 = new Locker(Constants.SIZE_L, 1);
        manager.addStoreable(locker2);
    }
}
