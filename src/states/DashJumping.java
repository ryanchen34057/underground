package states;

import character.Player;
import input.Input;

import java.util.List;

public class DashJumping implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {

    }

    @Override
    public void update(Player player) {
        player.setVelX(player.getFacing() * (Player.STEP * 1.5));
        player.setGravity(player.getGravity() - Player.DASHJUMPING_GRAVITY_OFFSET);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0) {
            player.currentState = PlayerState.falling;
        }
    }

    @Override
    public String toString() {
        return "DashJumping";
    }
}
