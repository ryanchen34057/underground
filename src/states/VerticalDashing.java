package states;

import UI.Game;
import gameObject.character.Player;
import input.Input;
import java.util.List;

public class VerticalDashing implements State {
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
        player.currentDashTimer -= ((Game.FPS / 1000.0f));
        player.currentDashSpeed -= Player.DASH_SPEED_BUMP;
        if(player.currentDashTimer <= (Game.FPS / 1000.0f)) {
            player.setGravity(0);
            //Reset timer
            player.currentDashTimer = Player.VERTICALDASHING_TIMER;
            player.setCurrentState(PlayerState.falling);
        }
    }

    @Override
    public String toString() {
        return "VerticalDashing";
    }
}
