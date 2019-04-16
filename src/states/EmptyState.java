package states;

import character.Player;
import input.Input;

import java.util.List;

public class EmptyState implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {

    }

    @Override
    public void update(Player player) {

    }
}
