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

    /**
     * Resets the position of the bars and the position of the ball after someone scoring a goal.
     * It also generates the starting movement of the ball.
     */
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
        ballPosX = 500;
        ballPosY = 500;
        ballPos = new Point(ballPosX, ballPosY);
        barPosLeft = 500;
        barPosRight = 500;
    }

    /**
     * Decides if the ball hits a bar or not.
     * @param side A string telling which of the bars to compute.
     * @return true if the ball hit the bar, false if it's not a hit.
     */
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

    /**
     * Decides if the ball hits a corner of a bar. If it does the ball is sent in the same
     * direction it came from.
     */
    public void decideCornerHit () {
        if (((ballPosY - 10) <= barPosRight + (barHeightRight / 2)) &&
                ((ballPosY - 10) >= (barPosRight + (barHeightRight / 2)) - 10) &&
                (ballSpeedY <= 0)) {
            this.ballSpeedY = this.ballSpeedY * -1.0;
        }
        if (((ballPosY + 10) >= barPosRight - (barHeightRight / 2)) &&
                ((ballPosY + 10) <= (barPosRight - (barHeightRight / 2)) + 10) &&
                (ballSpeedY >= 0)) {
            this.ballSpeedY = this.ballSpeedY * -1.0;
        }
        if (((ballPosY - 10) <= barPosLeft + (barHeightLeft / 2)) &&
                ((ballPosY - 10) >= (barPosLeft + (barHeightLeft / 2)) - 10) &&
                (ballSpeedY <= 0)) {
            this.ballSpeedY = this.ballSpeedY * -1.0;
        }
        if (((ballPosY + 10) >= barPosLeft - (barHeightLeft / 2)) &&
                ((ballPosY + 10) <= (barPosLeft - (barHeightLeft / 2)) + 10) &&
                (ballSpeedY >= 0)) {
            this.ballSpeedY = this.ballSpeedY * -1.0;
        }
    }

    /**
     * Decides if the ball should be curved or not.
     * @param input A Set-array containing user input.
     */
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


    /**
     * Curves the ball.
     * @param direction A string telling which direction the ball should be curved.
     */
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

    /**
     * Resets the game.
     */
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

    /**
     * Sets the ball in the right direction after a barhit.
     * @param input A Set-array containing user-input.
     */
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

    /**
     * The engine of the game that computes the position of the ball and the bars.
     * @param input
     * @param delta_t
     */
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

    public int getBarHitCount() {
        return barHitCount;
    }

    public int getBarHeight(BarKey k){
        if(k.equals(BarKey.LEFT)){
            return this.barHeightLeft;
        }
        return this.barHeightRight;
    }

    public double getBarSpeed() {
        return barSpeed;
    }

    public Point getBallPos(){
        return this.ballPos;
    }

    public double getBallSpeedX() {
        return ballSpeedX;
    }

    public double getBallSpeedY() {
        return ballSpeedY;
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

    public String getLeftPlayerName() {
        return leftPlayerName;
    }

    public String getRightPlayerName() {
        return rightPlayerName;
    }

    public boolean isGenStartDir() {
        return genStartDir;
    }

    public int getBarPosLeft() {
        return barPosLeft;
    }

    public int getBarPosRight() {
        return barPosRight;
    }

    public int getBarHeightLeft() {
        return barHeightLeft;
    }

    public int getBarHeightRight() {
        return barHeightRight;
    }

    public double getBarSpeed() {
        return barSpeed;
    }

    public int getBallPosX() {
        return ballPosX;
    }

    public int getBallPosY() {
        return ballPosY;
    }

    public String getScoreLeft() {
        return scoreLeft;
    }

    public String getScoreRight() {
        return scoreRight;
    }

    public double getBallSpeedX() {
        return ballSpeedX;
    }

    public double getBallSpeedY() {
        return ballSpeedY;
    }

    public int getCurveBallCount() {
        return curveBallCount;
    }

    public int getWallHitCount() {
        return wallHitCount;
    }

    public int getBarHitCount() {
        return barHitCount;
    }

    public String getCurveBallDirection() {
        return curveBallDirection;
    }

    public void setMessage(String msg){
        this.message = msg;
    }

    public void setLeftPlayerName(String leftPlayerName) {
        this.leftPlayerName = leftPlayerName;
    }

    public void setRightPlayerName(String rightPlayerName) {
        this.rightPlayerName = rightPlayerName;
    }

    public void setGenStartDir(boolean genStartDir) {
        this.genStartDir = genStartDir;
    }

    public void setBarPosLeft(int barPosLeft) {
        this.barPosLeft = barPosLeft;
    }

    public void setBarPosRight(int barPosRight) {
        this.barPosRight = barPosRight;
    }

    public void setBarHeightLeft(int barHeightLeft) {
        this.barHeightLeft = barHeightLeft;
    }

    public void setBarHeightRight(int barHeightRight) {
        this.barHeightRight = barHeightRight;
    }

    public void setBarSpeed(double barSpeed) {
        this.barSpeed = barSpeed;
    }

    public void setBallPosX(int ballPosX) {
        this.ballPosX = ballPosX;
    }

    public void setBallPosY(int ballPosY) {
        this.ballPosY = ballPosY;
    }

    public void setBallPos(Point ballPos) {
        this.ballPos = ballPos;
    }

    public void setScoreLeft(String scoreLeft) {
        this.scoreLeft = scoreLeft;
    }

    public void setScoreRight(String scoreRight) {
        this.scoreRight = scoreRight;
    }

    public void setBallSpeedX(double ballSpeedX) {
        this.ballSpeedX = ballSpeedX;
    }

    public void setBallSpeedY(double ballSpeedY) {
        this.ballSpeedY = ballSpeedY;
    }

    public void setCurveBallCount(int curveBallCount) {
        this.curveBallCount = curveBallCount;
    }

    public void setWallHitCount(int wallHitCount) {
        this.wallHitCount = wallHitCount;
    }

    public void setBarHitCount(int barHitCount) {
        this.barHitCount = barHitCount;
    }

    public void setCurveBallDirection(String curveBallDirection) {
        this.curveBallDirection = curveBallDirection;
    }

    public void setFieldSize(Dimension fieldSize) {
        this.fieldSize = fieldSize;
    }


}