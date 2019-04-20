package states;

import gameObject.character.Player;
import input.Input;

import java.util.List;

public class Sliding implements State {
    private float friction = 0;
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(5).down) {
            player.setGravity(Player.BOUNCING_GRAVITY);
            player.setCurrentState(PlayerState.bouncing);
        }
        if(player.getFacing() == -1) {
            if(!keys.get(2).down) {
                player.setGravity(Player.FALLING_GRAVITY_VEL);
                player.setCurrentState(PlayerState.falling);
            }
        }
        else {
            if(!keys.get(3).down) {
                player.setGravity(Player.FALLING_GRAVITY_VEL);
                player.setCurrentState(PlayerState.falling);
            }
        }
    }

    @Override
    public void update(Player player) {
        player.accumulateFatigue();
        friction += 0.1;
        player.setVelY(friction);
        if(friction >= 5 || player.getFatigue() >= player.getSTAMINA()) {
            player.setCurrentState(PlayerState.falling);
            friction = 0;
        }
    }

    @Override
    public String toString() {
        return "Sliding";
    }
}
