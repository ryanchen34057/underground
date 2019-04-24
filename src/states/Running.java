package states;

import gameObject.character.Player;
import effects.DashEffect;
import input.Input;

import java.util.List;

public class Running implements State {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
        }
        if(!keys.get(2).down && !keys.get(3).down) {
            player.setCurrentState(PlayerState.standing);
        }
        if(keys.get(5).down && !player.isJumped()) {
            player.setVelX(0);
            player.setGravity(Player.RUNNINGJUMPING_GRAVITY);
            player.setCurrentState(PlayerState.runningJumping);
            player.setJumped(true);
        }
        else if(!keys.get(5).down) {
            player.setJumped(false);
        }
        if(keys.get(4).down && !player.isTired()) {
            player.setCurrentState(PlayerState.dashing);
            player.setCurrentEffect(DashEffect.getInstance(player));
            player.setTired(true);

        }
        else if(!keys.get(4).down) {
            player.setTired(false);
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
