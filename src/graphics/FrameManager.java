package graphics;

import effects.Effect;
import states.*;

public class FrameManager {
    public static Sprite[] playerMoveFrame = new Sprite[8];
    public static Sprite[] prizeFrame = new Sprite[8];
    public static Sprite[] effectFrame = new Sprite[30];
    public static Sprite[] deathFrame = new Sprite[8];

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
        if(currentState instanceof VerticalDashing) {
            y = 10;
        }
        for(int i=0;i<playerMoveFrame.length;i++) {
            playerMoveFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, y, 32, 32);
        }
        return playerMoveFrame;
    }

    public static Sprite[] getPrizeFrame() {
        for(int i=0;i<prizeFrame.length;i++) {
            prizeFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 9, 32, 32);
        }
        return prizeFrame;
    }

    public static Sprite[] getEffectFrame() {
        for(int i=0;i<effectFrame.length;i++) {
            effectFrame[i] = new Sprite(SpriteManager.dashSpriteSheet,i + 1, 1,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
        }
        return effectFrame;
    }

    public static Sprite[] getDeathFrame() {
        for(int i=0;i<deathFrame.length;i++) {
            deathFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 8,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
        }
        return deathFrame;
    }
}
