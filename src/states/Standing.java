package states;

import character.Player;
import input.Input;

import java.util.List;

public class Standing implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            player.currentState = PlayerState.running;
        }
        if(keys.get(3).down) {
            player.setFacing(1);
            player.currentState = PlayerState.running;
        }
        else if(keys.get(5).down && Player.isOnTheGround) {
            player.setVelX(0);
            player.setGravity(Player.STANDINGJUMPING_GRAVITY);
            player.currentState = PlayerState.standingJumping;
        }
        else if(keys.get(4).down && !Player.isTired) {
            player.currentState = PlayerState.dashing;
            Player.isTired = true;
        }
        else if(!keys.get(4).down) {
            Player.isTired = false;
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(0);
    }

    @Override
    public String toString() {
        return "Standing";
    }
}
