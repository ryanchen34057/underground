package states;

import character.Player;
import input.Input;

import java.util.List;

public interface StateMachine {
    void handleKeyInput(Player player, List<Input.Key> keys);
    void update(Player player);
}
