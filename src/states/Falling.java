package states;

import character.Player;
import input.Input;

import java.util.List;

public class Falling implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            player.setVelX(-Player.FALLING_VELX);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
            player.setVelX(Player.FALLING_VELX);
        }
        else if(keys.get(4).down && !Player.isTired) {
            player.setVelY(0);
            player.currentState = PlayerState.dashingInTheAir;
            Player.isTired = true;
        }
        else {
            player.setVelX(0);
        }
    }

    @Override
    public void update(Player player) {
        player.setGravity(player.getGravity() + Player.GRAVITY_OFFSET);
        player.setVelY((int) player.getGravity());
    }

    @Override
    public String toString() {
        return "Falling";
    }
}
