package states;

import character.Player;
import input.Input;

import java.util.List;

public class VerticalDashing implements StateMachine {
    private float dashTimer = Player.VERTICAL_DASH_TIMER;
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(0).down) {
            player.setVelY(-player.CURRENT_DASH_SPEED);
        }
        else if(keys.get(1).down) {
            player.setVelY(player.CURRENT_DASH_SPEED);
        }

    }

    @Override
    public void update(Player player) {
        dashTimer -= (60.0f / 1000.0f);
        player.CURRENT_DASH_SPEED -= Player.DASH_SPEED_BUMP;
        if(dashTimer <= 0) {
            //Reset timer
            dashTimer = Player.VERTICAL_DASH_TIMER;
            player.currentState = PlayerState.falling;
            player.CURRENT_DASH_SPEED = Player.DASH_SPEED;
        }
    }

    @Override
    public String toString() {
        return "VerticalDashing";
    }
}
