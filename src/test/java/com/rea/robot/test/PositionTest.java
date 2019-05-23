package com.rea.robot.test;
import org.junit.Assert;
import org.junit.Test;

import com.rea.robot.simulator.Direction;
import com.rea.robot.simulator.Position;

public class PositionTest {

    @Test
    public void testGetNextPosition() throws Exception {
        Position position = new Position(0, 0, Direction.EAST);

        Position newPosition = position.getNextPosition();
        Assert.assertEquals(newPosition.getX(), 1);
        Assert.assertEquals(newPosition.getY(), 0);
        Assert.assertEquals(newPosition.getDirection(), Direction.EAST);

        newPosition = newPosition.getNextPosition();
        Assert.assertNotEquals(newPosition.getX(), 1);
        Assert.assertEquals(newPosition.getY(), 0);
        Assert.assertEquals(newPosition.getDirection(), Direction.EAST);

        newPosition.setDirection(Direction.NORTH);
        newPosition = newPosition.getNextPosition();
        Assert.assertNotEquals(newPosition.getX(), 1);
        Assert.assertEquals(newPosition.getY(), 1);
        Assert.assertNotEquals(newPosition.getDirection(), Direction.EAST);

    }
}
