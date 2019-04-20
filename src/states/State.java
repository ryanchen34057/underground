package states;

import gameObject.character.Player;
import input.Input;

import java.util.List;

public interface State {
    void handleKeyInput(Player player, List<Input.Key> keys);
    void update(Player player);
}
