package states;

import character.Id;
import character.Player;
import effects.DashInTheAirEffect;
import input.Input;
import javafx.print.PageLayout;
import util.Handler;

import java.util.List;

public class StandingJumping implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            player.setVelX(-1 * (Player.STEP / Player.STANDINGJUMPING_VELX_OFFSET));
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
            player.setVelX((Player.STEP / Player.STANDINGJUMPING_VELX_OFFSET));
        }
        //Vertical Dashing
        if(verticalDashCondition(keys)) {
            if(keys.get(4).down && (keys.get(0).down || keys.get(1).down) && !Player.isTired) {
                player.setVelX(0);
            }
            else {
                player.setVelX(Player.VERTICAL_DASHING_VELX * player.getFacing());
            }
            player.currentState = PlayerState.verticalDashing;
            Player.isTired = true;
            player.CURRENT_DASH_SPEED = Player.VERTICAL_DASH_SPEED;
        }
        else if(keys.get(4).down && !Player.isTired) {
            player.setVelY(0);
            player.currentState = PlayerState.dashingInTheAir;
            Player.isTired = true;
            Handler.addObject(DashInTheAirEffect.getInstance(player));
        }

    }

    @Override
    public void update(Player player) {
        player.setGravity(player.getGravity() - Player.GRAVITY_OFFSET);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0) {
               player.currentState = PlayerState.falling;
        }
    }

    @Override
    public String toString() {
        return "StandingJumping";
    }

    public boolean verticalDashCondition(List<Input.Key> keys) {
        return          // UP LEFT
                keys.get(4).down && keys.get(0).down && keys.get(2).down && !Player.isTired
                        // UP RIGHT
                || keys.get(4).down && keys.get(0).down && keys.get(3).down && !Player.isTired
                        // DOWN LEFT
                || keys.get(4).down && keys.get(1).down && keys.get(2).down && !Player.isTired
                        // DOWN RIGHT
                || keys.get(4).down && keys.get(1).down && keys.get(3).down && !Player.isTired
                || keys.get(4).down && (keys.get(0).down || keys.get(1).down) && !Player.isTired;
    }
}
