package model;

import org.junit.Test;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MyPongModelTest {

    public MyPongModel testModel = new MyPongModel("pl1","pl2");
    
    @Test
    public void testResetAfterScore() throws Exception {
        testModel.resetAfterScore();
        assertTrue(3 == testModel.getBallSpeedX());
        assertTrue(5 == testModel.getBarSpeed());
    }

    @Test
    public void testBarHit() throws Exception {
        testModel.setBallPosY(414);
        boolean barHit = testModel.barHit("left");
        assertFalse(barHit);
        testModel.setBallPosY(415);
        barHit = testModel.barHit("left");
        assertTrue(barHit);
        testModel.setBallPosY(585);
        barHit = testModel.barHit("left");
        assertTrue(barHit);
        testModel.setBallPosY(586);
        barHit = testModel.barHit("left");
        assertFalse(barHit);
    }

    @Test
    public void testDecideCornerHit() throws Exception {
        testModel.setBallPosY(420);
        testModel.setBallSpeedY(-4);
        testModel.decideCornerHit();
        //assertTrue(4 == testModel.getBallSpeedY());
        System.out.printf("%f\n",testModel.getBallSpeedY());
    }

    @Test
    public void testDecideCurveHit() throws Exception {
    
    }

    @Test
    public void testCurveBall() throws Exception {

    }

    @Test
    public void testResetGame() throws Exception {
        testModel.setScoreLeft("5");
        testModel.setScoreRight("6");
        testModel.setBarHeightLeft(90);
        testModel.setBarHeightRight(50);
        String leftScore = testModel.getScore(BarKey.LEFT);
        String rightScore = testModel.getScore(BarKey.RIGHT);
        int leftBarHeight = testModel.getBarHeight(BarKey.LEFT);
        int rightBarHeight = testModel.getBarHeight(BarKey.RIGHT);
        testModel.resetGame();
        leftScore = testModel.getScore(BarKey.LEFT);
        rightScore = testModel.getScore(BarKey.RIGHT);
        leftBarHeight = testModel.getBarHeight(BarKey.LEFT);
        rightBarHeight = testModel.getBarHeight(BarKey.RIGHT);
        assertEquals("0", leftScore);
        assertEquals("0", rightScore);
        assertEquals(150, leftBarHeight);
        assertEquals(150, rightBarHeight);
    }

    @Test
    public void testBarHitCompute() throws Exception {
        Input t1 = new Input(BarKey.LEFT, Input.Dir.DOWN);
        Set<Input> input = new HashSet<Input>();
        double xSpeed = 1.0;
        double ySpeed = 1.0;
        double barSpeed = 1.0;
        int hitCount = 0;
        testModel.setBarHitCount(hitCount);
        testModel.setBallSpeedX(xSpeed);
        testModel.setBallSpeedY(ySpeed);
        testModel.setBarSpeed(barSpeed);
        testModel.barHitCompute(input);
        xSpeed = testModel.getBallSpeedX();
        ySpeed = testModel.getBallSpeedY();
        barSpeed = testModel.getBarSpeed();
        hitCount = testModel.getBarHitCount();
        assertEquals(-1.07, xSpeed, 0.0);
        assertEquals(1.07, ySpeed, 0.0);
        assertEquals(1.05, barSpeed, 0.0);
        assertEquals(50, hitCount);
        testModel.barHitCompute(input);

        //Should be unchanged
        assertEquals(-1.07, xSpeed, 0.0);
        assertEquals(1.07, ySpeed, 0.0);
        assertEquals(1.05, barSpeed, 0.0);
        assertEquals(50, hitCount);

    }

    @Test
    public void testCompute() throws Exception {

    }
}