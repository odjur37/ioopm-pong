package model;

import java.awt.*;
import java.util.Random;
import java.util.Set;
import java.lang.String;

/**
 * Created by ErikWE on 02/01/15.
 */
public class MyPongModel implements PongModel{
    
    private String leftPlayerName = "player1";
    private String rightPlayerName = "player2";
    boolean genStartDir = true;
    private int barPosLeft = 500;
    private int barPosRight = 500;
    private int barHeightLeft = 150;
    private int barHeightRight = 150;
    private double barSpeed = 5;
    private int ballPosX = 500;
    private int ballPosY = 500;
    private Point ballPos = new Point(ballPosX,ballPosY);
    private String message = "GAME ON!  ";
    private String scoreLeft = "0";
    private String scoreRight = "0";
    private double ballSpeedX = 3;
    private double ballSpeedY = 0;
    private int curveBallCount = 0;
    private int wallHitCount = 0;
    private int barHitCount = 0;
    private String curveBallDirection = "NONE";
    private Dimension fieldSize = new Dimension(1000, 1000);
    
    public void resetAfterScore() {
        this.ballSpeedX = 3;
        Random rand1 = new Random();
        int ySpeed = 0;
        while (ySpeed == 0){
            ySpeed = rand1.nextInt(5);
        }
        int negOrPosY = rand1.nextInt(2);
        int negOrPosX = rand1.nextInt(2);
        if (negOrPosY == 1) {
            this.ballSpeedY = ySpeed * -1;
        } else {
            this.ballSpeedY = ySpeed;
        }
        if (negOrPosX == 1) {
            this.ballSpeedX = this.ballSpeedX * -1;
        }
        this.barSpeed = 5;
    }
    
    public boolean barHit(String side){
        switch (side){
            case "left":
                if (((ballPosY - 10) <= barPosLeft + (barHeightLeft / 2)) &&
                    ((ballPosY + 10) >= barPosLeft - (barHeightLeft / 2))){
                    return true;
                }else{
                    return false;
                }
            case "right":
                if (((ballPosY - 10) <= barPosRight + (barHeightRight / 2)) &&
                    ((ballPosY + 10) >= barPosRight - (barHeightRight / 2))){
                    return true;
                }else{
                    return false;
                }
            default: 
                return false;
            }
    }

    public void decideCornerHit () {
        if (((ballPosY - 10) <= barPosRight + (barHeightRight / 2)) &&
                ((ballPosY - 10) >= (barPosRight + (barHeightRight / 2)) - 10) &&
                (ballSpeedY <= 0)) {
            this.ballSpeedY = this.ballSpeedY * -1;
        }
        if (((ballPosY + 10) >= barPosRight - (barHeightRight / 2)) &&
                ((ballPosY + 10) <= (barPosRight - (barHeightRight / 2)) + 10) &&
                (ballSpeedY >= 0)) {
            this.ballSpeedY = this.ballSpeedY * -1;
        }
        if (((ballPosY - 10) <= barPosLeft + (barHeightLeft / 2)) &&
                ((ballPosY - 10) >= (barPosLeft + (barHeightLeft / 2)) - 10) &&
                (ballSpeedY <= 0)) {
            this.ballSpeedY = this.ballSpeedY * -1;
        }
        if (((ballPosY + 10) >= barPosLeft - (barHeightLeft / 2)) &&
                ((ballPosY + 10) <= (barPosLeft - (barHeightLeft / 2)) + 10) &&
                (ballSpeedY >= 0)) {
            this.ballSpeedY = this.ballSpeedY * -1;
        }
    }

    public void decideCurveHit(Set<Input> input) {
        for (Input eachSet : input) {
            if (eachSet.key.equals(BarKey.LEFT)) {
                if (eachSet.dir.equals(Input.Dir.UP)) {
                    this.curveBallCount = 100;
                    this.curveBallDirection = "DOWN";
                } else if (eachSet.dir.equals(Input.Dir.DOWN)) {
                    this.curveBallCount = 100;
                    this.curveBallDirection = "UP";
                }
            }
            if (eachSet.key.equals(BarKey.RIGHT)) {
                if (eachSet.dir.equals(Input.Dir.UP)) {
                    this.curveBallCount = 100;
                    this.curveBallDirection = "DOWN";
                } else if (eachSet.dir.equals(Input.Dir.DOWN)) {
                    this.curveBallCount = 100;
                    this.curveBallDirection = "UP";
                }
            }
        }
    }
 

    public void curveBall (String direction) {
        if (curveBallCount > 0) {
            if (direction.equals("UP")){
                this.ballSpeedY = this.ballSpeedY - 0.06;
                this.curveBallCount = this.curveBallCount - 1;
            }
            if (direction.equals("DOWN")) {
                this.ballSpeedY = this.ballSpeedY + 0.06;
                this.curveBallCount = this.curveBallCount - 1;
            }
        }
    }
    
    public void resetGame(){
        this.scoreRight = "0";
        this.scoreLeft = "0";
        this.barHeightLeft = 150;
        this.barHeightRight = 150;
        try {
            Thread.sleep(5000);
        }catch (Exception ignore){}
        setMessage("GAME ON!");
    }
    
    public void barHitCompute(Set<Input> input) {
        //decideCurveHit(input);
        decideCornerHit();
        if (barHitCount == 0) {
            this.ballSpeedY = this.ballSpeedY * 1.07;
            this.ballSpeedX = this.ballSpeedX * 1.07;
            this.ballSpeedX = ((this.ballSpeedX) * -1);
            this.barSpeed = this.barSpeed * 1.05;
            setMessage(null);
            this.barHitCount += 50;
        }
    }
    
    public void compute(Set<Input> input, long delta_t) {
        if (genStartDir){
            resetAfterScore();
            this.genStartDir = false;
        }
        Integer scoreLeftInt = Integer.parseInt(scoreLeft);
        Integer scoreRightInt = Integer.parseInt(scoreRight);
        if (scoreLeftInt.equals(10)) {
            setMessage(leftPlayerName + " wins!!");
            resetGame();
        }
        else if (scoreRightInt.equals(10)) {
            setMessage(rightPlayerName + " wins!!");
            resetGame();
        } else {
            /* moving the bars */
            for (Input eachSet : input) {
                if (eachSet.key.equals(BarKey.LEFT)) {
                    if (eachSet.dir.equals(Input.Dir.UP)) {
                        if((barPosLeft - (barHeightLeft/2)) > 0) {
                            this.barPosLeft -= barSpeed;
                        }
                    } else {
                        if((barPosLeft + (barHeightLeft/2)) < 1000) {
                            this.barPosLeft += barSpeed;
                        }
                    }
                }
                if (eachSet.key.equals(BarKey.RIGHT)) {
                    if (eachSet.dir.equals(Input.Dir.UP)) {
                        if ((barPosRight - (barHeightRight / 2)) > 0) {
                            this.barPosRight -= barSpeed;
                        }
                    } else {
                        if ((barPosRight + (barHeightRight / 2)) < 1000) {
                            this.barPosRight += barSpeed;
                        }
                    }
                }
            }
            /* deciding if the ball hits a bar */
            if (ballPosX >= 980) {
                if (barHit("right")) {
                    barHitCompute(input);
                } else {
                    Integer tempScore = Integer.parseInt(scoreLeft) + 1;
                    this.scoreLeft = Integer.toString(tempScore);
                    ballPosX = 500;
                    ballPosY = 500;
                    ballPos = new Point(ballPosX, ballPosY);
                    barPosLeft = 500;
                    barPosRight = 500;
                    if (this.scoreLeft.equals("10")) {
                        setMessage(leftPlayerName + " wins!");

                    } else {
                        setMessage(leftPlayerName + " scores!");
                    }
                    this.barHeightLeft -= 7;
                    this.genStartDir = true;
                }
            }
            if (ballPosX <= 20) {
                if (barHit("left")) {
                    barHitCompute(input);
                } else {
                    Integer tempScore = Integer.parseInt(scoreRight) + 1;
                    this.scoreRight = Integer.toString(tempScore);
                    ballPosX = 500;
                    ballPosY = 500;
                    ballPos = new Point(ballPosX, ballPosY);
                    barPosLeft = 500;
                    barPosRight = 500;
                    if (this.scoreRight.equals("10")) {
                        setMessage(rightPlayerName + " wins!");

                    } else {
                        setMessage(rightPlayerName + " scores!");
                    }
                    this.barHeightRight -= 7;
                    this.genStartDir = true;
                }
            }
            /* deciding if the ball hits a wall */
            if ((ballPosY >= 980)||(ballPosY <= 20)) {
                if (wallHitCount == 0) {
                    this.ballSpeedY = this.ballSpeedY * -1;
                    this.wallHitCount += 50;
                }
            }
            curveBall(curveBallDirection);
            this.ballPosY += ballSpeedY;
            this.ballPosX += ballSpeedX;

            this.ballPos = new Point(ballPosX, ballPosY);
            if (wallHitCount > 0) {
                this.wallHitCount -= 1;
            }
            if (barHitCount > 0){
                this.barHitCount -= 1;
            }
        }
    }
    
    public MyPongModel(String p1, String p2){
        this.leftPlayerName = p1;
        this.rightPlayerName = p2;
    }

    public int getBarPos(BarKey k){
        if(k.equals(BarKey.LEFT)){
            return this.barPosLeft;  
        }
        return this.barPosRight;
    }

    public int getBarHeight(BarKey k){
        if(k.equals(BarKey.LEFT)){
            return this.barHeightLeft;
        }
        return this.barHeightRight;
    }

    public Point getBallPos(){
        return this.ballPos;
    }
    
    public void setMessage(String msg){
        this.message = msg;
    }
    public String getMessage(){
        return this.message;
    }

    public String getScore(BarKey k) {
        if(k.equals(BarKey.LEFT)){
            return this.scoreLeft;
        }
        return this.scoreRight;
    }   

    public Dimension getFieldSize(){
        return this.fieldSize;
    }
}