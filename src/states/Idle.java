package states;

import gameObject.character.Player;
import input.Input;

import java.util.List;

public class Idle implements State {
    
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
        }
        player.setCurrentState(PlayerState.running);
    }

    @Override
    public void update(Player player) {
        player.setVelX(5);
        if(player.getVelX() >= 15) {
            player.setCurrentState(PlayerState.running);
        }
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
