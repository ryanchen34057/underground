package states;

import effects.DashInTheAirEffect;
import effects.VerticalDashEffect;
import gameObject.character.Player;
import input.Input;
import java.util.List;

public class Bouncing implements State {
    @Override
    public void handleKeyInput(Player player, List<Input.Key> keys) {
        keys.get(5).down = false;
        //Vertical Dashing
        if(verticalDashCondition(keys, player)) {
            player.setVelX(Player.VERTICALDASHING_VELX * player.getFacing());
            player.setCurrentState(PlayerState.verticalDashing);
            player.setCurrentEffect(VerticalDashEffect.getInstance(player));
            player.setTired(true);
            player.currentDashSpeed = Player.VERTICALDASHING_SPEED;
        }

        //DASHING_IN_THE_AIR
        if(keys.get(4).down && !player.isTired()) {
            player.setVelY(0);
            player.setFacing(player.getFacing());
            player.setCurrentState(PlayerState.dashingInTheAir);
            player.setCurrentEffect(DashInTheAirEffect.getInstance(player));
            player.setTired(true);
        }
    }

    @Override
    public void update(Player player) {
        player.accumulateFatigue();
        player.setVelX(player.getFacing() * -1 *  Player.BOUNCING_RANGE);
        player.setGravity(player.getGravity() - Player.BOUNCING_GRAVITY_OFFSET);
        player.setVelY((int) -player.getGravity());
        if (player.getGravity() <= 0.0 || player.getFatigue() >= player.getSTAMINA()) {
            player.setCurrentState(PlayerState.falling);
        }
    }

    @Override
    public String toString() {
        return "Bouncing";
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
