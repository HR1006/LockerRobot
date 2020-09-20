package com.thoughtworks.tdd.locker.storeable;

import com.thoughtworks.tdd.locker.Bag;
import com.thoughtworks.tdd.locker.Ticket;

public interface Storeable {
    Ticket depositBag(Bag bag);

    Bag pickUpBag(Ticket ticket);

    boolean isValidTicket(Ticket ticket);

    Ticket depositBagOrNot(Bag bag);
}
