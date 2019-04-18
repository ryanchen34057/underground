package states;

import character.Player;
import input.Input;
import util.Handler;

import java.util.List;

public class Dashing implements StateMachine {
    private float dashTimer = Player.DASH_TIMER;
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down && keys.get(5).down) {
            player.setFacing(-1);
            player.setGravity(Player.FALLING_GRAVITY_VEL);
            player.currentState = PlayerState.dashJumping;
        }
        if(keys.get(3).down && keys.get(5).down) {
            player.setFacing(1);
            player.setGravity(Player.FALLING_GRAVITY_VEL);
            player.currentState = PlayerState.dashJumping;
        }
        if(keys.get(5).down) {
            player.setGravity(Player.DASHJUMPING_GRAVITY);
            player.currentState = PlayerState.dashJumping;
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(player.CURRENT_DASH_SPEED * player.getFacing());
        dashTimer -= (60.0f / 1000.0f);
        player.CURRENT_DASH_SPEED -= Player.DASH_SPEED_BUMP;
        if(dashTimer <= 0) {
            //Reset timer
            dashTimer = Player.DASH_TIMER;
            player.currentState = PlayerState.standing;
            player.CURRENT_DASH_SPEED = Player.DASH_SPEED;
        }
    }

    @Override
    public String toString() {
        return "Dashing";
    }
}
