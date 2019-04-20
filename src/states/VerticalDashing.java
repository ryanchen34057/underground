package states;

import gameObject.character.Player;
import input.Input;
import java.util.List;

public class VerticalDashing implements State {
    private float dashTimer = Player.VERTICALDASHING_TIMER;
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(0).down) {
            player.setVelY(-player.currentDashSpeed);
        }
        else if(keys.get(1).down) {
            player.setVelY(player.currentDashSpeed);
        }
    }

    @Override
    public void update(Player player) {
        dashTimer -= (60.0f / 1000.0f);
        player.currentDashSpeed -= Player.DASH_SPEED_BUMP;
        if(dashTimer <= 0) {
            //Reset timer
            dashTimer = Player.VERTICALDASHING_TIMER;
            player.setCurrentState(PlayerState.falling);
            player.currentDashSpeed = Player.DASH_SPEED;
        }
    }

    @Override
    public String toString() {
        return "VerticalDashing";
    }
}
