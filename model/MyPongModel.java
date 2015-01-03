package model;

import controller.*;
import view.PongView;

import java.awt.*;
import java.util.Map;
import java.util.Set;

/**
 * Created by ErikWE on 02/01/15.
 */
public class MyPongModel implements PongModel{
    
    private String leftPlayerName = "player1";
    private String rightPlayerName = "player2";
    private int barPosLeft = 100;
    private int barPosRight = 500;
    private int barHeightLeft = 100;
    private int barHeightRight = 100;
    private int ballPosX = 500;
    private int ballPosY = 500;
    private Point ballPos = new Point(ballPosX,ballPosY);
    private String message = "START!";
    private String scoreLeft = "0";
    private String scoreRight = "0";
    private int ballSpeed = 10;
    private Dimension fieldSize = new Dimension(1000, 1000);
    
    public void compute(Set<Input> input, long delta_t){
        if (input.contains(BarKey.LEFT)){
            /* check UP or DOWN */
            
        }
        if(ballPosX == 980){
            if (((ballPosY-10) <= barPosRight+(barHeightRight/2)) && ((ballPosY+10) >= barPosRight-(barHeightRight/2))) {
                this.ballSpeed = ((this.ballSpeed) * -1);
            }else{
                Integer tempScore = Integer.parseInt(scoreLeft) + 1;
                this.scoreLeft = Integer.toString(tempScore);
                ballPosX = 500;
                ballPosY = 500;
                ballPos = new Point(ballPosX,ballPosY);
                barPosRight = 500;
            }
        }
        if(ballPosX == 20) {
            if (((ballPosY-10) <= barPosLeft+(barHeightLeft/2)) && ((ballPosY+10) >= barPosLeft-(barHeightLeft/2))) {
                this.ballSpeed = ((this.ballSpeed) * -1);
            }else{
                Integer tempScore = Integer.parseInt(scoreRight) + 1;
                this.scoreRight = Integer.toString(tempScore);
                ballPosX = 500;
                ballPosY = 500;
                ballPos = new Point(ballPosX,ballPosY);
                barPosLeft = 500;
            }
        }
        this.ballPosX += ballSpeed;
        this.ballPos = new Point(ballPosX,ballPosY);
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
