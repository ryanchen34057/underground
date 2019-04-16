package states;

import character.Player;
import input.Input;

import java.util.List;

public class RunningJumping implements StateMachine{
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
        }
        if(keys.get(4).down && !Player.isTired) {
            player.setVelY(0);
            player.currentState = PlayerState.dashingInTheAir;
            Player.isTired = true;
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(player.getFacing() * (Player.STEP) - player.getFacing() * 2);
        player.setGravity(player.getGravity() - Player.RUNNINGJUMPING_GRAVITY_OFFSET);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0) {
            player.currentState = PlayerState.falling;
        }
    }

    @Override
    public String toString() {
        return "RunningJumping";
    }
}
