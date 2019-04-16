package states;

import character.Player;
import input.Input;

import java.util.List;

public class Bouncing implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        //DASHING_IN_THE_AIR
        if(keys.get(4).down && !Player.isTired) {
            player.setVelY(0);
            player.setFacing(player.getFacing());
            player.currentState = PlayerState.dashingInTheAir;
            Player.isTired = true;
        }
    }

    @Override
    public void update(Player player) {
        player.accumulateFatigue();
        player.setVelX(player.getFacing() * -1 *  Player.BOUNCING_RANGE);
        player.setGravity(player.getGravity() - 0.3);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0 || player.getFatigue() >= player.getSTAMINA()) {
            player.currentState = PlayerState.falling;
        }
    }

    @Override
    public String toString() {
        return "Bouncing";
    }
}
