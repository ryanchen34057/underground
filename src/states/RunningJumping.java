package states;

import gameObject.character.Player;
import effects.DashInTheAirEffect;
import effects.VerticalDashEffect;
import input.Input;

import java.util.List;

public class RunningJumping implements State {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        if(keys.get(2).down) {
            player.setFacing(-1);
        }
        else if(keys.get(3).down) {
            player.setFacing(1);
        }
        if(keys.get(4).down && !player.isTired() && (!keys.get(0).down && !keys.get(1).down)) {
            player.setVelY(0);
            player.setCurrentState(PlayerState.dashingInTheAir);
            player.setCurrentEffect(DashInTheAirEffect.getInstance(player));
            player.setTired(true);
        }
        //Vertical Dashing
        if(verticalDashCondition(keys, player)) {
            player.setVelX(Player.VERTICALDASHING_VELX * player.getFacing());
            player.setCurrentState(PlayerState.verticalDashing);
            player.setCurrentEffect(VerticalDashEffect.getInstance(player));
            player.setTired(true);
            player.currentDashSpeed = Player.VERTICALDASHING_SPEED;
        }

        else if(keys.get(4).down && (keys.get(0).down || keys.get(1).down) && !player.isTired()) {
            player.setCurrentState(PlayerState.verticalDashing);
            player.setCurrentEffect(VerticalDashEffect.getInstance(player));
            player.setTired(true);
            player.currentDashSpeed = Player.VERTICALDASHING_SPEED;
        }
    }

    @Override
    public void update(Player player) {
        player.setVelX(player.getFacing() * (Player.RUNNINGJUMPING_STEP));
        player.setGravity(player.getGravity() - Player.RUNNINGJUMPING_GRAVITY_OFFSET);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0) {
            player.setCurrentState(PlayerState.falling);
        }
    }

    @Override
    public String toString() {
        return "RunningJumping";
    }

    public boolean verticalDashCondition(List<Input.Key> keys, Player player) {
        return          // UP LEFT
                keys.get(4).down && keys.get(0).down && keys.get(2).down && !player.isTired()
                        // UP RIGHT
                        || keys.get(4).down && keys.get(0).down && keys.get(3).down && !player.isTired()
                        // DOWN LEFT
                        || keys.get(4).down && keys.get(1).down && keys.get(2).down && !player.isTired()
                        // DOWN RIGHT
                        || keys.get(4).down && keys.get(1).down && keys.get(3).down && !player.isTired();
    }
}
