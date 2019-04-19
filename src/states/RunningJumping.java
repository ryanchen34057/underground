package states;

import character.Player;
import effects.DashInTheAirEffect;
import input.Input;
import util.Handler;

import java.util.List;

public class RunningJumping implements StateMachine{
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
        }
        if(keys.get(4).down && !Player.isTired && (!keys.get(0).down && !keys.get(1).down)) {
            player.setVelY(0);
            player.currentState = PlayerState.dashingInTheAir;
            Player.isTired = true;
            Handler.addObject(DashInTheAirEffect.getInstance(player));
        }
        //Vertical Dashing
        if(verticalDashCondition(keys)) {
            player.setVelX(Player.VERTICAL_DASHING_VELX * player.getFacing());
            player.currentState = PlayerState.verticalDashing;
            Player.isTired = true;
            player.CURRENT_DASH_SPEED = Player.VERTICAL_DASH_SPEED;
            Handler.addObject(DashInTheAirEffect.getInstance(player));
        }
        //Vertical Dashing
        else if(keys.get(4).down && (keys.get(0).down || keys.get(1).down) && !Player.isTired) {
            player.currentState = PlayerState.verticalDashing;
            Player.isTired = true;
            player.CURRENT_DASH_SPEED = Player.VERTICAL_DASH_SPEED;
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(player.getFacing() * (Player.STEP) - player.getFacing() * 2);
        player.setGravity(player.getGravity() - Player.RUNNINGJUMPING_GRAVITY_OFFSET);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0) {
            player.currentState = PlayerState.falling;
        }
    }

    @Override
    public String toString() {
        return "RunningJumping";
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
