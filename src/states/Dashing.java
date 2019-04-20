package states;

import gameObject.character.Player;
import input.Input;

import java.util.List;

public class Dashing implements State {
    private float dashTimer = Player.DASH_TIMER;
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
        }
        if(keys.get(5).down) {
            player.setGravity(Player.DASHJUMPING_GRAVITY);
            player.setCurrentState(PlayerState.dashJumping);
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(player.currentDashSpeed * player.getFacing());
        dashTimer -= (60.0f / 1000.0f);
        player.currentDashSpeed -= Player.DASH_SPEED_BUMP;
        if(dashTimer <= 0) {
            //Reset timer
            dashTimer = Player.DASH_TIMER;
            player.setCurrentState(PlayerState.standing);
            player.currentDashSpeed = Player.DASH_SPEED;
        }
    }

    @Override
    public String toString() {
        return "Dashing";
    }
}
