package states;

import gameObject.character.Player;
import input.Input;

import java.util.List;

public class DashJumping implements State {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {

    }

    @Override
    public void update(Player player) {
        player.setGravity(player.getGravity() - Player.DASHJUMPING_GRAVITY_OFFSET);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0) {
            player.setCurrentState(PlayerState.falling);
        }
    }

    @Override
    public String toString() {
        return "DashJumping";
    }
}
