package audio;

import java.util.HashMap;

public class SoundEffectPlayer {
    // SoundEffect
    private static HashMap<String, AudioFile> soundEffects;

    public SoundEffectPlayer() {
        soundEffects = new HashMap<>();
        soundEffects.put("Landing", new AudioFile("./src/audio/landing.wav"));
        soundEffects.put("Dashing", new AudioFile("./src/audio/dashing.wav"));
        soundEffects.put("Jumping", new AudioFile("./src/audio/jumping.wav"));
        soundEffects.put("SpringJumping", new AudioFile("./src/audio/springJumping.wav"));
        soundEffects.put("Death", new AudioFile("./src/audio/death.wav"));
        soundEffects.put("Prize", new AudioFile("./src/audio/prize.wav"));
        soundEffects.put("Type", new AudioFile("./src/audio/KeyboardTyping.wav"));
        
//        soundEffects.put("FallingRockHit", new AudioFile("./src/audio/fallingRockHit.wav"));
    }

    public static void playSoundEffect(String move) {
        soundEffects.get(move).play();
    }
}
