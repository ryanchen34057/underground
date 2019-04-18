package graphics;

import character.Id;
import effects.Effect;
import states.*;

public class FrameManager {
    public static Sprite[] playerMoveFrame = new Sprite[8];
    public static Sprite[] prizeFrame = new Sprite[8];
    public static Sprite[] effectFrame = new Sprite[30];
    public static Sprite[] deathFrame = new Sprite[8];
    public static Sprite[] bluePortalFrame = new Sprite[9];
    public static Sprite[] purplePortalFrame = new Sprite[9];
    public static Sprite[] torchFrame = new Sprite[10];

    public static Sprite[] getPlayerMoveFrame(StateMachine currentState) {
        int y = 0;
        if(currentState instanceof Standing) {
           y = 28;
        }
        if(currentState instanceof Running) {
            y = 29;
        }
        if(currentState instanceof StandingJumping) {
            y = 27;
        }
        if(currentState instanceof RunningJumping) {
            y = 27;
        }
        if(currentState instanceof Falling) {
            y = 25;
        }
        if(currentState instanceof Dashing) {
            y = 30;
        }
        if(currentState instanceof DashJumping) {
            y = 24;
        }
        if(currentState instanceof Sliding) {
            y = 26;
        }
        if(currentState instanceof Bouncing) {
            y = 27;
        }
        if(currentState instanceof DashingInTheAir) {
            y = 24;
        }
        if(currentState instanceof VerticalDashing) {
            y = 24;
        }
        if(currentState instanceof Idle) {
            y = 28;
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

    public static Sprite[] getEffectFrame(Id id) {
        if(id == Id.dashEffect) {
            for(int i=0;i<effectFrame.length;i++) {
                effectFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 20,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
            }
        }
        else if(id == Id.landingEffect) {
            for(int i=0;i<18;i++) {
                effectFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 19,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
            }
        }

        return effectFrame;
    }

    public static Sprite[] getDeathFrame() {
        for(int i=0;i<deathFrame.length;i++) {
            deathFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 8,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
        }
        return deathFrame;
    }

    public static Sprite[] getBluePortalFrame() {
        for(int i = 0; i< bluePortalFrame.length; i++) {
            bluePortalFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 10, 32,  32);
        }
        return bluePortalFrame;
    }

    public static Sprite[] getPurplePortalFramePortalFrame() {
        for(int i = 0; i< purplePortalFrame.length; i++) {
            purplePortalFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 11, 32,  32);
        }
        return purplePortalFrame;
    }

    public static Sprite[] getTorchFrame() {
        for(int i = 0; i< torchFrame.length; i++) {
            torchFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 7, 32,  32);
        }
        return torchFrame;
    }
}
