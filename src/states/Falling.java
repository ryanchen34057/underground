package states;

import character.Player;
import effects.DashInTheAirEffect;
import effects.VerticalDashEffect;
import input.Input;
import util.Handler;

import java.util.List;

public class Falling implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            player.setVelX(-Player.FALLING_VELX);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
            player.setVelX(Player.FALLING_VELX);
        }
        else {
            player.setVelX(0);
        }
        //Vertical Dashing - UP_LEFT
        //Vertical Dashing
        if(verticalDashCondition(keys)) {
            player.setVelX(Player.VERTICAL_DASHING_VELX * player.getFacing());
            player.currentState = PlayerState.verticalDashing;
            Player.isTired = true;
            player.CURRENT_DASH_SPEED = Player.VERTICAL_DASH_SPEED;
            Handler.addObject(VerticalDashEffect.getInstance(player));
        }
        //Vertical Dashing
        else if(keys.get(4).down && (keys.get(0).down || keys.get(1).down) && !Player.isTired) {
            player.currentState = PlayerState.verticalDashing;
            Player.isTired = true;
            player.CURRENT_DASH_SPEED = Player.VERTICAL_DASH_SPEED;
            Handler.addObject(DashInTheAirEffect.getInstance(player));
        }
        //Dashing in the air
        else if(keys.get(4).down && !Player.isTired) {
            player.setVelY(0);
            player.currentState = PlayerState.dashingInTheAir;
            Player.isTired = true;
            Handler.addObject(DashInTheAirEffect.getInstance(player));
        }
    }

    @Override
    public void update(Player player) {
        player.setGravity(player.getGravity() + Player.FALLING_GRAVITY_VEL);
        player.setVelY((int) player.getGravity());
        if(player.getGravity() > 20) {
            player.setGravity(20);
        }
    }

    @Override
    public String toString() {
        return "Falling";
    }

    public boolean verticalDashCondition(List<Input.Key> keys) {
        return          // UP LEFT
                keys.get(4).down && keys.get(0).down && keys.get(2).down && !Player.isTired
                        // UP RIGHT
                        || keys.get(4).down && keys.get(0).down && keys.get(3).down && !Player.isTired
                        // DOWN LEFT
                        || keys.get(4).down && keys.get(1).down && keys.get(2).down && !Player.isTired
                        // DOWN RIGHT
                        || keys.get(4).down && keys.get(1).down && keys.get(3).down && !Player.isTired;
    }
}
