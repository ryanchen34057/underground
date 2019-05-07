package graphics;

import enums.Direction;
import enums.Id;
import effects.Effect;
import states.*;

import java.awt.*;

import static enums.Direction.*;

public class FrameManager {
    public static Sprite[] playerMoveFrame = new Sprite[8];
    public static Sprite[] prizeFrame = new Sprite[8];
    public static Sprite[] effectFrame = new Sprite[30];
    public static Sprite[] deathFrame = new Sprite[8];
    public static Sprite[] bluePortalFrame = new Sprite[9];
    public static Sprite[] purplePortalFrame = new Sprite[9];
    public static Sprite[] torchFrame = new Sprite[10];
    public static Sprite[] springFrame = new Sprite[11];

    public static Sprite[] getPlayerMoveFrame(State currentState) {
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
        if(currentState instanceof IceSkating) {
            y = 23;
        }
        if(currentState instanceof SpringHorizontal) {
            y = 23;
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
        else if(id == Id.dashInTheAirEffect) {
            for(int i=0;i<24;i++) {
                effectFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 18,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
            }
        }
        else if(id == Id.rockLandingEffect) {
            for(int i=0;i<30;i++) {
                effectFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 14,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
            }
        }
        return effectFrame;
    }

    public static Sprite[] getVerticalDashEffectFrame(Direction dir) {
        if(dir == UP ) {
            for(int i=0;i<12;i++) {
                effectFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 15,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
            }
        }
        else if(dir == DOWN) {
            for(int i=0;i<12;i++) {
                effectFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 13, 15,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
            }
        }
        else if(dir == DOWN_RIGHT) {
            for(int i=0;i<12;i++) {
                effectFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 16,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
            }
        }
        else if(dir == DOWN_LEFT) {
            for(int i=0;i<12;i++) {
                effectFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 13, 16,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
            }
        }
        else if(dir == UP_RIGHT) {
            for(int i=0;i<12;i++) {
                effectFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 17,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
            }
        }
        else if(dir == UP_LEFT) {
            for(int i=0;i<12;i++) {
                effectFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 13, 17,Effect.EFFECT_SIZE, Effect.EFFECT_SIZE);
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

    public static Sprite[] getPortalFrame(Color color, Direction direction) {
        if(color == Color.BLUE) {
            int y = (direction == LEFT) ? 10 :11;
            for(int i = 0; i< bluePortalFrame.length; i++) {
                bluePortalFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, y, 32,  32);
            }
            return bluePortalFrame;
        }
        else {
            int y = (direction == LEFT) ? 12 :13;
            for(int i = 0; i< purplePortalFrame.length; i++) {
                purplePortalFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, y, 32,  32);
            }
            return purplePortalFrame;
        }
    }

    public static Sprite[] getTorchFrame() {
        for(int i = 0; i< torchFrame.length; i++) {
            torchFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 7, 32,  32);
        }
        return torchFrame;
    }

    public static Sprite[] getSpringFrame(Direction direction) {
        if(direction == UP) {
            for(int i = 0; i< springFrame.length; i++) {
                springFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 4, 32,  32);
            }
        }
        else if(direction == DOWN) {
            for(int i = 0; i< springFrame.length; i++) {
                springFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 1, 3, 32,  32);
            }
        }
        else if(direction == LEFT) {
            for(int i = 0; i< springFrame.length; i++) {
                springFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 12, 3, 32,  32);
            }
        }
        else if(direction == RIGHT) {
            for(int i = 0; i< springFrame.length; i++) {
                springFrame[i] = new Sprite(SpriteManager.spriteSheet,i + 12, 4, 32,  32);
            }
        }
        return springFrame;
    }
}
