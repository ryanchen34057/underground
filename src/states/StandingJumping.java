package states;

import audio.SoundEffectPlayer;
import enums.Direction;
import gameObject.character.Player;
import effects.DashInTheAirEffect;
import effects.VerticalDashEffect;
import input.Input;

import java.util.List;

public class StandingJumping implements State {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            player.setVelX(-1 * (Player.STEP / Player.STANDINGJUMPING_VELX_OFFSET));
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
            player.setVelX((Player.STEP / Player.STANDINGJUMPING_VELX_OFFSET));
        }
        //Vertical Dashing
        Direction dir = verticalDashCondition(keys, player);
        if(dir != null && !player.isTired()) {
            if(keys.get(2).down || keys.get(3).down) {
                player.setVelX(Player.VERTICALDASHING_VELX * player.getFacing());
            }
            SoundEffectPlayer.playSoundEffect("Dashing");
            player.setCurrentState(PlayerState.verticalDashing);
            player.setCurrentEffect(VerticalDashEffect.getInstance(player, dir));
            player.setTired(true);
            player.currentDashSpeed = Player.VERTICALDASHING_SPEED;
            player.currentDashTimer = Player.VERTICALDASHING_TIMER;
        }
        else if(keys.get(4).down && !player.isTired()) {
            player.setVelY(0);
            SoundEffectPlayer.playSoundEffect("Dashing");
            player.setCurrentState(PlayerState.dashingInTheAir);
            player.setCurrentEffect(DashInTheAirEffect.getInstance(player));
            player.setTired(true);
            player.currentDashTimer = Player.DASH_TIMER;
        }

    }

    @Override
    public void update(Player player) {
        player.setGravity(player.getGravity() - Player.STANDINGJUMPING_GRAVITY_OFFSET);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0) {
            player.setCurrentState(PlayerState.falling);
        }
    }

    @Override
    public String toString() {
        return "StandingJumping";
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
