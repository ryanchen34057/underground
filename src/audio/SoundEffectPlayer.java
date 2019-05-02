package audio;

import java.util.HashMap;

public class SoundEffectPlayer {
    // SoundEffect
    private static HashMap<String, AudioFile> soundEffects;

    public SoundEffectPlayer() {
        soundEffects = new HashMap<>();
        soundEffects.put("Landing", new AudioFile("./src/audio/res/landing.wav"));
        soundEffects.put("Dashing", new AudioFile("./src/audio/res/dashing.wav"));
        soundEffects.put("Jumping", new AudioFile("./src/audio/res/jumping.wav"));
        soundEffects.put("SpringJumping", new AudioFile("./src/audio/res/springJumping.wav"));
        soundEffects.put("Death", new AudioFile("./src/audio/res/death.wav"));
        soundEffects.put("Prize", new AudioFile("./src/audio/res/prize.wav"));
        soundEffects.put("FallingRockHit", new AudioFile("./src/audio/res/fallingRockHit.wav"));
        soundEffects.put("FallingRockShaking", new AudioFile("./src/audio/res/fallingRockShaking.wav"));
        soundEffects.put("Cursor", new AudioFile("./src/audio/res/cursor.wav"));
        soundEffects.put("Enter", new AudioFile("./src/audio/res/enter.wav"));
        soundEffects.put("Portal", new AudioFile("./src/audio/res/portal.wav"));
    }

    public static void playSoundEffect(String move) {
        soundEffects.get(move).play();
    }
}
