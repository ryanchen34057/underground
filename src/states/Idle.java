package states;

import UI.Game;
import audio.SoundEffectPlayer;
import effects.DashEffect;
import gameObject.character.Player;
import input.Input;

import java.util.List;

public class Idle implements State {
    
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            player.setCurrentState(PlayerState.running);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
            player.setCurrentState(PlayerState.running);
        }
        if(keys.get(4).down && !player.isTired()) {
            SoundEffectPlayer.playSoundEffect("Dashing");
            player.setCurrentState(PlayerState.dashing);
            player.setCurrentEffect(DashEffect.getInstance(player));
            player.setTired(true);
            player.currentDashTimer = Player.DASH_TIMER;
            player.currentDashSpeed = Player.DASH_SPEED;
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(10* Game.widthRatio);
        if(player.getVelX() >= 15 * Game.widthRatio) {
            player.setCurrentState(PlayerState.running);
        }
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
