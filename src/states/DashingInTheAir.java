package states;

import gameObject.character.Player;
import input.Input;

import java.util.List;

public class DashingInTheAir implements State {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {

    }

    @Override
    public void update(Player player) {
        player.setVelX(player.currentDashSpeed * player.getFacing());
        player.currentDashTimer -= (60.0f / 1000.0f);
        player.currentDashSpeed -= Player.DASH_SPEED_BUMP;
        if(player.currentDashTimer <= 0.07) {
            player.setGravity(Player.FALLING_GRAVITY_VEL);
            player.setCurrentState(PlayerState.falling);
        }
    }

    @Override
    public String toString() {
        return "DashingInTheAir";
    }
}
