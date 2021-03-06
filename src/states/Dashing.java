package states;

import UI.Game;
import gameObject.character.Player;
import input.Input;

import java.util.List;

public class Dashing implements State {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
        }
        if(keys.get(5).down) {
            player.setGravity(player.DASHJUMPING_GRAVITY);
            player.setCurrentState(PlayerState.dashJumping);
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(player.currentDashSpeed * player.getFacing());
        player.currentDashTimer -= (Game.UPDATES / 1000.0f);
        player.currentDashSpeed -= player.DASH_SPEED_BUMP;
        if(player.currentDashTimer <= (Game.UPDATES / 1000.0f)) {
            player.setCurrentState(PlayerState.standing);
        }
    }

    @Override
    public String toString() {
        return "Dashing";
    }
}
