package states;

import character.Player;
import input.Input;

import java.util.List;

public class Falling implements StateMachine {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
            player.setVelX(-Player.FALLING_VELX);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
            player.setVelX(Player.FALLING_VELX);
        }
        else {
            player.setVelX(0);
        }
        //Vertical Dashing - UP_LEFT
        if(keys.get(4).down && keys.get(0).down && keys.get(2).down && !Player.isTired) {
            player.setVelX(Player.VERTICAL_DASHING_VELX * player.getFacing());
            player.currentState = PlayerState.verticalDashing;
            Player.isTired = true;
            player.CURRENT_DASH_SPEED = Player.VERTICAL_DASH_SPEED;
        }
        //Vertical Dashing - UP_RIGHT
        else if(keys.get(4).down && keys.get(0).down && keys.get(3).down && !Player.isTired) {
            player.setVelX(Player.VERTICAL_DASHING_VELX * player.getFacing());
            player.currentState = PlayerState.verticalDashing;
            Player.isTired = true;
            player.CURRENT_DASH_SPEED = Player.VERTICAL_DASH_SPEED;
        }
        //Vertical Dashing - DOWN_LEFT
        else if(keys.get(4).down && keys.get(1).down && keys.get(2).down && !Player.isTired) {
            player.setVelX(Player.VERTICAL_DASHING_VELX * player.getFacing());
            player.currentState = PlayerState.verticalDashing;
            Player.isTired = true;
            player.CURRENT_DASH_SPEED = Player.VERTICAL_DASH_SPEED;
        }
        //Vertical Dashing - DOWN_RIGHT
        else if(keys.get(4).down && keys.get(1).down && keys.get(3).down && !Player.isTired) {
            player.setVelX(Player.VERTICAL_DASHING_VELX * player.getFacing());
            player.currentState = PlayerState.verticalDashing;
            Player.isTired = true;
            player.CURRENT_DASH_SPEED = Player.VERTICAL_DASH_SPEED;
        }
        //Vertical Dashing
        else if(keys.get(4).down && (keys.get(0).down || keys.get(1).down) && !Player.isTired) {
            player.currentState = PlayerState.verticalDashing;
            Player.isTired = true;
            player.CURRENT_DASH_SPEED = Player.VERTICAL_DASH_SPEED;
        }
        //Dashing in the air
        else if(keys.get(4).down && !Player.isTired) {
            player.setVelY(0);
            player.currentState = PlayerState.dashingInTheAir;
            Player.isTired = true;
        }
    }

    @Override
    public void update(Player player) {
        player.setGravity(player.getGravity() + Player.GRAVITY_OFFSET);
        player.setVelY((int) player.getGravity());
        if(player.getGravity() > 10) {
            player.setGravity(10);
        }
    }

    @Override
    public String toString() {
        return "Falling";
    }
}
