package graphics;

import UI.Game;
import states.*;

public class MoveFrameManager {
    private static Sprite[] playerMoveFrame = new Sprite[8];
    private static Sprite[] prizeFrame = new Sprite[8];

    public static Sprite[] getPlayerMoveFrame(StateMachine currentState) {
        int y = 0;
        if(currentState instanceof Standing) {
           y = 14;
        }
        if(currentState instanceof Running) {
            y = 15;
        }
        if(currentState instanceof StandingJumping) {
            y = 13;
        }
        if(currentState instanceof RunningJumping) {
            y = 13;
        }
        if(currentState instanceof Falling) {
            y = 11;
        }
        if(currentState instanceof Dashing) {
            y = 16;
        }
        if(currentState instanceof DashJumping) {
            y = 10;
        }
        if(currentState instanceof Sliding) {
            y = 12;
        }
        if(currentState instanceof Bouncing) {
            y = 13;
        }
        if(currentState instanceof DashingInTheAir) {
            y = 10;
        }
        for(int i=0;i<playerMoveFrame.length;i++) {
            playerMoveFrame[i] = new Sprite(Game.spriteSheet,i + 1, y);
        }
        return playerMoveFrame;
    }

    public static Sprite[] getPrizeFrame() {
        for(int i=0;i<prizeFrame.length;i++) {
            prizeFrame[i] = new Sprite(Game.spriteSheet,i + 1, 9);
        }
        return prizeFrame;
    }
}
