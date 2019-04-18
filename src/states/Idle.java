package states;

import character.Player;
import input.Input;

import java.util.List;

public class Idle implements StateMachine {
    
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            player.currentState = PlayerState.running;
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
            player.currentState = PlayerState.running;
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(5);
        if(player.getVelX() >= 15) {
            player.currentState = PlayerState.running;
        }
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
