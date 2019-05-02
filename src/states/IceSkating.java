package states;

import audio.SoundEffectPlayer;
import gameObject.character.Player;
import input.Input;

import java.util.List;

public class IceSkating implements State {
    private float dashTimer = 999;
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
        }
        if(keys.get(5).down) {
            SoundEffectPlayer.playSoundEffect("Dashing");
            player.setGravity(Player.DASHJUMPING_GRAVITY);
            player.setCurrentState(PlayerState.dashJumping);
            player.setOnTheIce(false);
            player.setTired(true);
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(Player.ICESKATING_SPEED * player.getFacing());
        dashTimer -= (60.0f / 10000.0f);
    }

    @Override
    public String toString() {
        return "IceSkating";
    }
}
