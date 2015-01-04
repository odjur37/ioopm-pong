package model;

import java.awt.*;
import java.util.Random;
import java.util.Set;

/**
 * Created by ErikWE on 02/01/15.
 */
public class MyPongModel implements PongModel{
    
    private String leftPlayerName = "player1";
    private String rightPlayerName = "player2";
    boolean genStartDir = true;
    private int barPosLeft = 100;
    private int barPosRight = 500;
    private int barHeightLeft = 100;
    private int barHeightRight = 100;
    private int ballPosX = 500;
    private int ballPosY = 500;
    private Point ballPos = new Point(ballPosX,ballPosY);
    private String message = "GAME ON!  ";
    private String scoreLeft = "0";
    private String scoreRight = "0";
    private int ballSpeedX = 3;
    private int ballSpeedY = 0;
    private Dimension fieldSize = new Dimension(1000, 1000);
    
    public void generateStartDirection () {
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
    }
    
    public void compute(Set<Input> input, long delta_t) {
        if (genStartDir){
            generateStartDirection();
            this.genStartDir = false;
        }
        Integer scoreLeftInt = Integer.parseInt(scoreLeft);
        Integer scoreRightInt = Integer.parseInt(scoreRight);
        if (scoreLeftInt.equals(10)) {
            setMessage(leftPlayerName + " wins!!");
        }
        else if (scoreRightInt.equals(10)) {
            setMessage(rightPlayerName + " wins!!");
        } else {
            for (Input eachSet : input) {
                if (eachSet.key.equals(BarKey.LEFT)) {
                    if (eachSet.dir.equals(Input.Dir.UP)) {
                        this.barPosLeft -= 5;
                    } else {
                        this.barPosLeft += 5;
                    }
                }
                if (eachSet.key.equals(BarKey.RIGHT)) {
                    if (eachSet.dir.equals(Input.Dir.UP)) {
                        this.barPosRight -= 5;
                    } else {
                        this.barPosRight += 5;
                    }
                }
            }
            if (ballPosX == 980) {
                if (((ballPosY - 10) <= barPosRight + (barHeightRight / 2)) && ((ballPosY + 10) >= barPosRight - (barHeightRight / 2))) {
                    this.ballSpeedX = ((this.ballSpeedX) * -1);
                    setMessage(null);
                } else {
                    Integer tempScore = Integer.parseInt(scoreLeft) + 1;
                    this.scoreLeft = Integer.toString(tempScore);
                    ballPosX = 500;
                    ballPosY = 500;
                    ballPos = new Point(ballPosX, ballPosY);
                    barPosRight = 500;
                    setMessage(leftPlayerName + " scores!");
                    this.genStartDir = true;
                }
            }
            if (ballPosX == 20) {
                if (((ballPosY - 10) <= barPosLeft + (barHeightLeft / 2)) && ((ballPosY + 10) >= barPosLeft - (barHeightLeft / 2))) {
                    this.ballSpeedX = ((this.ballSpeedX) * -1);
                    setMessage(null);
                } else {
                    Integer tempScore = Integer.parseInt(scoreRight) + 1;
                    this.scoreRight = Integer.toString(tempScore);
                    ballPosX = 500;
                    ballPosY = 500;
                    ballPos = new Point(ballPosX, ballPosY);
                    barPosLeft = 500;
                    setMessage(rightPlayerName + " scores!");
                    this.genStartDir = true;
                }
            }
            if ((ballPosY >= 990)||(ballPosY <= 10)) {
                this.ballSpeedY = this.ballSpeedY * -1;                
            }
            
            this.ballPosY += ballSpeedY;
            this.ballPosX += ballSpeedX;
            
            this.ballPos = new Point(ballPosX, ballPosY);
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