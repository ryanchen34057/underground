package states;

import UI.Game;
import gameObject.character.Player;
import input.Input;

import java.util.List;

public class Sliding implements State {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(5).down && !player.isJumped()) {
            player.setGravity(Player.BOUNCING_GRAVITY);
            player.setCurrentState(PlayerState.bouncing);
            player.setJumped(true);
        }
        if(!keys.get(5).down) {
            player.setJumped(false);
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
        player.setFriction(1);
        player.setVelY(player.getFriction());
        player.setVelX(player.getFacing());
        if((player.getFriction() >= (Game.UPDATES *5)/Game.UpdatesRatio *Game.heightRatio) || player.getFatigue() >= player.getSTAMINA() || !player.isOnTheWall()) {
            player.setCurrentState(PlayerState.falling);
            player.setFriction(0);
        }
    }

    @Override
    public String toString() {
        return "Sliding";
    }
}
