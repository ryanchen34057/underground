package states;

import gameObject.character.Player;
import input.Input;

import java.util.List;

public class DashingInTheAir implements State {
    private float dashTimer = Player.DASH_TIMER;
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {

    }

    @Override
    public void update(Player player) {
        player.setVelX(player.currentDashSpeed * player.getFacing());
        dashTimer -= (60.0f / 1000.0f);
        player.currentDashSpeed -= Player.DASH_SPEED_BUMP;
        if(dashTimer <= 0) {
            //Reset timer
            dashTimer = Player.DASH_TIMER;
            player.setGravity(Player.FALLING_GRAVITY_VEL);
            player.setCurrentState(PlayerState.falling);
            player.currentDashSpeed = Player.DASH_SPEED;
        }
    }

    @Override
    public String toString() {
        return "DashingInTheAir";
    }
}
