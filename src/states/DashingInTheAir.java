package states;

import character.Player;
import input.Input;

import java.util.List;

public class DashingInTheAir implements StateMachine{
    private float dashTimer = Player.DASH_TIMER;
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {

    }

    @Override
    public void update(Player player) {
        player.setVelX(player.CURRENT_DASH_SPEED * player.getFacing());
        dashTimer -= (60.0f / 1000.0f);
        player.CURRENT_DASH_SPEED -= Player.DASH_SPEED_BUMP;
        if(dashTimer <= 0) {
            //Reset timer
            dashTimer = Player.DASH_TIMER;
            player.setGravity(Player.FALLING_GRAVITY_VEL);
            player.currentState = PlayerState.falling;
            player.CURRENT_DASH_SPEED = Player.DASH_SPEED;
        }
    }

    @Override
    public String toString() {
        return "DashingInTheAir";
    }
}
