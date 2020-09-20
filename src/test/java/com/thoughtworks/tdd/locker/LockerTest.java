package com.thoughtworks.tdd.locker;

import com.thoughtworks.tdd.locker.exception.InvalidTicketException;
import com.thoughtworks.tdd.locker.exception.LockerFullException;
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
}
