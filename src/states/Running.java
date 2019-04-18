package states;

import character.Player;
import effects.DashEffect;
import input.Input;
import util.Handler;

import java.util.List;

public class Running implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
        }
        if(!keys.get(2).down && !keys.get(3).down) {
            player.currentState = PlayerState.standing;
        }
        if(keys.get(5).down) {
            player.setVelX(0);
            player.setGravity(Player.RUNNINGJUMPING_GRAVITY);
            player.currentState = PlayerState.runningJumping;
        }
        if(keys.get(4).down && !Player.isTired) {
            Handler.addObject(DashEffect.getInstance(player));
            player.currentState = PlayerState.dashing;
            Player.isTired = true;

        }
        else if(!keys.get(4).down) {
            Player.isTired = false;
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(player.getFacing() * Player.STEP);
    }

    @Override
    public String toString() {
        return "Running";
    }
}
