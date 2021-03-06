package states;

import UI.Game;
import audio.SoundEffectPlayer;
import enums.Direction;
import gameObject.character.Player;
import effects.DashInTheAirEffect;
import effects.VerticalDashEffect;
import input.Input;

import java.util.List;

public class Falling implements State {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            player.setVelX(-player.FALLING_VELX);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
            player.setVelX(player.FALLING_VELX);
        }
        else {
            player.setVelX(0);
        }
        //Vertical Dashing
        Direction dir = verticalDashCondition(keys, player);
        if(dir != null && !player.isTired()) {
            if(keys.get(2).down || keys.get(3).down) {
                player.setVelX(player.VERTICALDASHING_VELX * player.getFacing());
            }
            SoundEffectPlayer.playSoundEffect("Dashing");
            player.setCurrentState(PlayerState.verticalDashing);
            player.setCurrentEffect(VerticalDashEffect.getInstance(player, dir));
            player.setTired(true);
            player.currentDashSpeed = player.VERTICALDASHING_SPEED;
            player.currentDashTimer = player.VERTICALDASHING_TIMER;
        }

        //Dashing in the air
        else if(keys.get(4).down && !player.isTired()) {
            player.setVelY(0);
            SoundEffectPlayer.playSoundEffect("Dashing");
            player.setCurrentState(PlayerState.dashingInTheAir);
            player.setCurrentEffect(DashInTheAirEffect.getInstance(player));
            player.setTired(true);
            player.currentDashTimer = player.DASH_TIMER;
            player.currentDashSpeed = player.DASH_SPEED;
        }
    }

    @Override
    public void update(Player player) {
        player.setGravity(player.getGravity() + player.FALLING_GRAVITY_VEL);
        player.setVelY(player.getGravity());
        if(player.getGravity() > player.RUNNINGJUMPING_GRAVITY) {
            player.setGravity(player.RUNNINGJUMPING_GRAVITY);
        }
    }

    @Override
    public String toString() {
        return "Falling";
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
