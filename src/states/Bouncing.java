package states;

import audio.SoundEffectPlayer;
import effects.DashInTheAirEffect;
import effects.VerticalDashEffect;
import enums.Direction;
import gameObject.character.Player;
import input.Input;
import java.util.List;

public class Bouncing implements State {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(!keys.get(5).down) {
            player.setJumped(false);
        }
        //Vertical Dashing
        Direction dir = verticalDashCondition(keys, player);
        if(dir != null && !player.isTired()) {
            SoundEffectPlayer.playSoundEffect("Dashing");
            player.setVelX(Player.VERTICALDASHING_VELX * player.getFacing());
            player.setCurrentState(PlayerState.verticalDashing);
            player.setCurrentEffect(VerticalDashEffect.getInstance(player, dir));
            player.setTired(true);
            player.currentDashSpeed = Player.VERTICALDASHING_SPEED;
        }

        //DASHING_IN_THE_AIR
        if(keys.get(4).down && !player.isTired()) {
            player.setVelY(0);
            SoundEffectPlayer.playSoundEffect("Dashing");
            player.setFacing(player.getFacing());
            player.setCurrentState(PlayerState.dashingInTheAir);
            player.setCurrentEffect(DashInTheAirEffect.getInstance(player));
            player.setTired(true);
        }
    }

    @Override
    public void update(Player player) {
        player.accumulateFatigue();
        player.setVelX(player.getFacing() * -1 *  Player.BOUNCING_RANGE);
        player.setGravity(player.getGravity() - Player.BOUNCING_GRAVITY_OFFSET);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0 || player.getFatigue() >= player.getSTAMINA()) {
            player.setCurrentState(PlayerState.falling);
        }
    }

    @Override
    public String toString() {
        return "Bouncing";
    }

    public Direction verticalDashCondition(List<Input.Key> keys, Player player) {
                  // UP LEFT
                if(keys.get(4).down && keys.get(0).down && keys.get(2).down && !player.isTired()) return Direction.UP_LEFT;
                // UP RIGHT
                if(keys.get(4).down && keys.get(0).down && keys.get(3).down && !player.isTired()) return Direction.UP_RIGHT;
                // DOWN LEFT
                if(keys.get(4).down && keys.get(1).down && keys.get(2).down && !player.isTired()) return Direction.DOWN_LEFT;
                // DOWN RIGHT
                if(keys.get(4).down && keys.get(1).down && keys.get(3).down && !player.isTired()) return Direction.DOWN_RIGHT;
                // UP
                if(keys.get(4).down && keys.get(0).down && !player.isTired()) return Direction.UP;
                // DOWN
                if(keys.get(4).down && keys.get(1).down && !player.isTired()) return Direction.DOWN;
                else {
                    return null;
                }
    }
}
