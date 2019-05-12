package states;

import audio.SoundEffectPlayer;
import gameObject.character.Player;
import effects.DashEffect;
import input.Input;
import util.Camera;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public class Standing implements State {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        //Dash Jumping
        if(keys.get(5).down && keys.get(4).down && !player.isTired()) {
            SoundEffectPlayer.playSoundEffect("Dashing");
            player.setGravity(Player.DASHJUMPING_GRAVITY);
            player.setCurrentState(PlayerState.dashJumping);
        }
        else if(keys.get(2).down) {
            player.setFacing(-1);
            player.setCurrentState(PlayerState.running);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
            player.setCurrentState(PlayerState.running);
        }
        //Standing Jump
        if(keys.get(5).down && player.isOnTheGround() && !player.isJumped()) {
            player.setVelX(0);
            player.setJumped(true);
            player.setGravity(Player.STANDINGJUMPING_GRAVITY);
            player.setCurrentState(PlayerState.standingJumping);
            SoundEffectPlayer.playSoundEffect("Jumping");
        }
        //Dashing
        else if(keys.get(4).down && !player.isTired()) {
            SoundEffectPlayer.playSoundEffect("Dashing");
            player.setCurrentState(PlayerState.dashing);
            player.setCurrentEffect(DashEffect.getInstance(player));
            player.setTired(true);
            player.currentDashTimer = Player.DASH_TIMER;
            player.currentDashSpeed = Player.DASH_SPEED;
        }
        if(!keys.get(4).down) {
            player.setTired(false);
        }
        if(!keys.get(5).down) {
            player.setJumped(false);
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
