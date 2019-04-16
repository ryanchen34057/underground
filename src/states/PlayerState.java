package states;

public class PlayerState {

    //State Object
    public static Standing standing = new Standing();
    public static StandingJumping standingJumping = new StandingJumping();
    public static RunningJumping runningJumping = new RunningJumping();
    public static Falling falling = new Falling();
    public static Running running = new Running();
    public static Dashing dashing = new Dashing();
    public static DashJumping dashJumping = new DashJumping();
    public static Sliding sliding = new Sliding();
    public static Bouncing bouncing = new Bouncing();
    public static DashingInTheAir dashingInTheAir = new DashingInTheAir();
}
