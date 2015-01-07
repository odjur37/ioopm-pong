package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyPongModelTest {

    public MyPongModel testModel = new MyPongModel("pl1","pl2");
    
    @Test
    public void testResetAfterScore() throws Exception {

    }

    @Test
    public void testBarHit() throws Exception {
        testModel.setBallPosY(20);
        boolean barHit = testModel.barHit("left");
        assertTrue(barHit);
    }

    @Test
    public void testDecideCornerHit() throws Exception {

    }

    @Test
    public void testDecideCurveHit() throws Exception {

    }

    @Test
    public void testCurveBall() throws Exception {

    }

    @Test
    public void testResetGame() throws Exception {

    }

    @Test
    public void testBarHitCompute() throws Exception {

    }

    @Test
    public void testCompute() throws Exception {

    }
}