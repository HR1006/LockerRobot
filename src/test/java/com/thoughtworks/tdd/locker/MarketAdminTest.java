package com.thoughtworks.tdd.locker;

import com.thoughtworks.tdd.locker.storeable.Locker;
import org.junit.Assert;
import org.junit.Test;

public class MarketAdminTest {
    @Test
    public void should_success_when_add_locker_to_manager_given_one_S_locker() {
        LockerRobotManager manager = new LockerRobotManager();
        Locker locker = new Locker(Constants.SIZE_S, 1);
        manager.addStoreable(locker);
        Assert.assertEquals(manager.getStoreablesByType(Locker.class).get(0), locker);
    }
}
