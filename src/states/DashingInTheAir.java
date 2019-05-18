package states;

import UI.Game;
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
        player.currentDashTimer -= (Game.UPDATES / 1000.0f);
        player.currentDashSpeed -= player.DASH_SPEED_BUMP;
        if(player.currentDashTimer <= (Game.UPDATES / 1000.0f)) {
            player.setGravity(player.FALLING_GRAVITY_VEL);
            player.setCurrentState(PlayerState.falling);
        }
    }

    @Override
    public String toString() {
        return "DashingInTheAir";
    }
}
