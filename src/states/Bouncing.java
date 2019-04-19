package states;

import character.Player;
import effects.DashInTheAirEffect;
import input.Input;
import util.Handler;

import java.util.List;

public class Bouncing implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        //Vertical Dashing
        if(verticalDashCondition(keys)) {
            player.setVelX(Player.VERTICAL_DASHING_VELX * player.getFacing());
            player.currentState = PlayerState.verticalDashing;
            Player.isTired = true;
            player.CURRENT_DASH_SPEED = Player.VERTICAL_DASH_SPEED;
            Handler.addObject(DashInTheAirEffect.getInstance(player));
        }

        //DASHING_IN_THE_AIR
        if(keys.get(4).down && !Player.isTired) {
            player.setVelY(0);
            player.setFacing(player.getFacing());
            player.currentState = PlayerState.dashingInTheAir;
            Player.isTired = true;
        }
    }

    @Override
    public void update(Player player) {
        player.accumulateFatigue();
        player.setVelX(player.getFacing() * -1 *  Player.BOUNCING_RANGE);
        player.setGravity(player.getGravity() - Player.BOUNCING_GRAVITY_OFFSET);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0 || player.getFatigue() >= player.getSTAMINA()) {
            player.currentState = PlayerState.falling;
        }
    }

    @Override
    public String toString() {
        return "Bouncing";
    }

    public boolean verticalDashCondition(List<Input.Key> keys) {
        return          // UP LEFT
                keys.get(4).down && keys.get(0).down && keys.get(2).down && !Player.isTired
                        // UP RIGHT
                        || keys.get(4).down && keys.get(0).down && keys.get(3).down && !Player.isTired
                        // DOWN LEFT
                        || keys.get(4).down && keys.get(1).down && keys.get(2).down && !Player.isTired
                        // DOWN RIGHT
                        || keys.get(4).down && keys.get(1).down && keys.get(3).down && !Player.isTired;
    }
}
