package gameStates;

import UI.Game;
import audio.MusicPlayer;
import audio.SoundEffectPlayer;
import fonts.Words;
import static gameStates.GameState.locked;
import gameStates.level.Level0State;
import input.Input;
import java.awt.Graphics;
import java.util.ArrayList;
import map.Background;

public class StoryState extends GameState {

      ArrayList<Words> words;
      ArrayList<String> sent;
      Words hint;
      private float count;
      private int sentCount;
      private int currentLength;
     

      public StoryState(GameStateManager gameStateManager) {
            super(gameStateManager);
            this.gameStateManager = gameStateManager;
            init();
            count = 0;
            sentCount = 0;

            words = new ArrayList<>();
            sent = new ArrayList<>();
            hint = new Words("Press Enter to skip", 30, (int) (Game.WIDTH * Game.SCALE / 2), (int) (Game.HEIGHT * Game.SCALE * 4 / 5));
            //sentences
            sent.add(new String("2110/4/10 The expedition started from Camp in Antarctica."));
            sent.add(new String("Coordinates -69.715560, -66.484216."));
            sent.add(new String("The weather is bad, visibility 5m to 10m."));
            sent.add(new String("11:20 After break, we go to the cave with captain."));
            sent.add(new String("My comm was interrupted temporarily , but it didn't matter."));
            sent.add(new String("13:30 Our teammates find a mystery deep hole."));
            sent.add(new String("There seems to be a distortion around the area."));
            sent.add(new String("14:00 Passed through the hole into the unknown area,"));
            sent.add(new String("it transfers my location , I ask for... help but..."));
            sent.add(new String("Restart record...     By  Lara Croft"));
            //words
            for (int i = 0; i < sent.size(); i++) {
                  words.add(new Words(sent.get(i).substring(0, (int) count), 30, (int) (Game.WIDTH * Game.SCALE / 2), (int) (Game.HEIGHT * Game.SCALE / 2)));
            }
            currentLength = sent.get(0).length();
            //audio
            
      }

      public void init() {
            background = new Background("/res/Black.png", Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);          
      }

      public void handleKeyInput() {
            if(!locked){
                  if(Input.keys.get(7).down){//Enter
                        gameStateManager.setLevelState(new Level0State(gameStateManager));
                        locked = true;
                  }
            }
            if(!Input.keys.get(0).down &&!Input.keys.get(1).down && !Input.keys.get(7).down){//放開
                  locked = false;
            }
      }

      public void update() {
            
            if(sentCount<sent.size()){
                  count += 0.2;
                  type(count, sentCount);
                  if(sentCount<sent.size()-1){
                        SoundEffectPlayer.playSoundEffect("Type");
                  }
                  
            }//控制速率
            
             handleKeyInput();       
      }

      public void paint(Graphics g) {
            background.paint(g);
            //Words
            hint.paint(g);
            for (Words n : words) {
                  n.paint(g);
            }
      }

      public void type(float count, int sentCount) { 
            if (count > currentLength - 1) {
                  for (int i = 0; i <= sentCount; i++) {//最後一個字 全部上移
                        words.get(i).addWordY(-10);
                  }
            }
            if (count >= currentLength) {//換行計數
                  setCurrentLength(++this.sentCount);
                  this.count = 0;
                  return;
            }
            words.get(sentCount).setWord(sent.get(sentCount).substring(0, (int)count));
      }

      public void setCurrentLength(int sentCount) {
            if(sentCount<sent.size()){
                  currentLength = sent.get(sentCount).length();
            }
      }

}
