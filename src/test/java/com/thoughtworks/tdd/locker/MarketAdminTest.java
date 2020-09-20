package com.thoughtworks.tdd.locker;

import com.thoughtworks.tdd.locker.exception.SizeNotMatchException;
import com.thoughtworks.tdd.locker.storeable.Locker;
import com.thoughtworks.tdd.locker.storeable.robot.PrimaryLockerRobot;
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

    @Test(expected = SizeNotMatchException.class)
    public void should_prompt_when_add_locker_to_manager_given_one_M_locker_one_L_locker() {
        LockerRobotManager manager = new LockerRobotManager();
        Locker locker1 = new Locker(Constants.SIZE_M, 1);
        manager.addStoreable(locker1);
        Locker locker2 = new Locker(Constants.SIZE_L, 1);
        manager.addStoreable(locker2);
    }

    @Test
    public void should_success_when_add_locker_to_PrimaryLockerRobot_given_one_M_locker() {
        PrimaryLockerRobot primaryLockerRobot = new PrimaryLockerRobot();
        Locker locker = new Locker(Constants.SIZE_M, 1);
        primaryLockerRobot.addLocker(locker);
        Assert.assertEquals(primaryLockerRobot.getLockers().get(0), locker);
    }

    @Test(expected = SizeNotMatchException.class)
    public void should_prompt_when_add_locker_to_PrimaryLockerRobot_given_one_S_locker_one_L_locker() {
        PrimaryLockerRobot primaryLockerRobot = new PrimaryLockerRobot();
        Locker locker1 = new Locker(Constants.SIZE_S, 1);
        primaryLockerRobot.addLocker(locker1);
        Locker locker2 = new Locker(Constants.SIZE_L, 1);
        primaryLockerRobot.addLocker(locker2);
    }
}
