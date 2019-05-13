package gameStates;

import UI.Game;
import UI.Window;
import audio.SoundEffectPlayer;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import fonts.Words;
import graphics.SpriteManager;
import input.Input;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

import map.Background;
import record.Record;


public class LeaderboardState extends GameState {
      private Words wordTitle;
      private Words enterWords;
      private ArrayList<BufferedImage> options;
      private int trophySelected;
      
      //讀檔
      private Words[] nameWords;
      private Words[] timeWords;
      private ArrayList<Record> records;

      public LeaderboardState(GameStateManager gameStateManager) {
            super(gameStateManager);
             init();
            
      }

      public void init() {
            background = new Background("/res/Cave1.png", Window.scaledGameWidth, Window.scaledGameHeight);
            wordTitle = new Words("Leaderboard", (int)(60*Game.widthRatio), Window.scaledGameWidth/2,  Window.scaledGameHeight / 4);
            enterWords = new Words("Back", (int) (Window.scaledGameWidth* 0.02), (int)(Window.scaledGameWidth/1.12),(int)(Window.scaledGameHeight/1.04));
            records = gameStateManager.readEndGameRecord();//讀取
            records.sort((o1, o2) -> (int)(o1.getTime() - o2.getTime()));
            nameWords = new Words[5];
            timeWords = new Words[5];
            for(int i=0;i<5;i++){//無紀錄，先給空值
                  nameWords[i] = new Words(String.format("%14s", "-"), (int)(40*Game.widthRatio), Window.scaledGameWidth/5, (int)(Window.scaledGameHeight/1.75) +(int)(70*Game.heightRatio)*i);
                  if(timeWords[i] == null && records.size() > i) {
                        timeWords[i] = new Words(records.get(i).getTimeString().substring(7, 15), (int)(40*Game.widthRatio), (int)(500* Game.widthRatio), nameWords[i].getWordY());
                  }
            }
            options = new ArrayList<>();
            options.add(SpriteManager.clock.getBufferedImage());
            options.add(SpriteManager.emerald.getBufferedImage());
            options.add(SpriteManager.skull.getBufferedImage());
            trophySelected = 0;
            resetWords();
      }

     

      @Override
      public void handleKeyInput() {
            if (!locked) {
                  if (Input.keys.get(7).down) {//Enter
                        gameStateManager.back();
                        locked = true;
                  }
                  if (Input.keys.get(2).down || Input.keys.get(3).down) {//左
                        if(Input.keys.get(2).down) {
                              if (trophySelected > 0) {
                                    trophySelected--;
                              }
                              SoundEffectPlayer.playSoundEffect("Cursor");
                              locked = true;
                        }
                        else if (Input.keys.get(3).down) {//右
                              if (trophySelected < 2) {
                                    trophySelected++;
                              }
                              SoundEffectPlayer.playSoundEffect("Cursor");
                              locked = true;
                        }
                        if(trophySelected == 0) {
                              records.sort(Comparator.comparingLong(Record::getTime));
                        }
                        else if(trophySelected == 1) {
                              records.sort((o1, o2) -> (o2.getEmeraldCount() - o1.getEmeraldCount()));
                        }
                        else if(trophySelected == 2) {
                              records.sort(Comparator.comparingInt(Record::getDeathCount));
                        }
                        resetWords();
                  }
            }
            if (Input.isAllReleased()) {//放開
                  locked = false;
            }
      }

      @Override
      public void update() {
            handleKeyInput();
      }

      private void resetWords() {
            for(int i=0;i<records.size();i++){//設定
                  if(i>=5){
                        break;
                  }
                  if(records.get(i) != null){
                        nameWords[i].setWord(String.format("%6s", records.get(i).getName()));
                        timeWords[i] = new Words(records.get(i).getTimeString().substring(7, 15), (int)(40*Game.widthRatio), (int)(500* Game.widthRatio), nameWords[i].getWordY());
                  }
            }
      }

      @Override
      public void paint(Graphics g) {
            background.paint(g);
            wordTitle.paint(g);
            enterWords.paint(g);

            int j = 0;
            //Paint Options
            for(BufferedImage image: options) {
                  g.drawImage(image, (int)(450* Game.widthRatio) + (int)(200*Game.widthRatio) * j++, (int)(320*Game.heightRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), null);
            }

            //Paint Trophy
            g.drawImage(SpriteManager.trophy.getBufferedImage(), (int)(450* Game.widthRatio) + (int)(200*Game.widthRatio) * trophySelected, (int)(230*Game.heightRatio), (int)(64*Game.widthRatio), (int)(64*Game.widthRatio), null);


            //Paint enter Key
            g.drawImage(SpriteManager.enterKey.getBufferedImage(), (int) (Window.scaledGameWidth / 1.25), (int) (Window.scaledGameHeight / 1.12), (int) (Window.scaledGameWidth * 0.05), (int) (Window.scaledGameWidth * 0.05), null);

            for(int i=0;i<5;i++){
                  g.drawString((i+1) + ".",  (int)(100*Game.widthRatio), (int)(480*Game.heightRatio) + (int)(70*Game.heightRatio) * i);
                  if(records.size() < 1){
                        nameWords[i].paint(g);
                  }else{
                        nameWords[i].paint(g);
                        if(i<records.size()){      
                              //寶石
                              Words eCWords = new Words("X " + records.get(i).getEmeraldCount(), (int)(30* Game.widthRatio),(int)(750*Game.widthRatio) ,(int)(nameWords[i].getWordY()*0.97));
                              eCWords.paint(g);
                              g.drawImage(SpriteManager.emerald.getBufferedImage(), (int)(eCWords.getWordX()*0.88), nameWords[i].getWordY()-(int)(57*Game.widthRatio), (int)(45* Game.widthRatio), (int)(45* Game.widthRatio), null);
                              //死亡
                              Words eDWords = new Words("X " + records.get(i).getDeathCount(), (int)(30* Game.widthRatio),(int)(eCWords.getWordX()*1.25), (int)(nameWords[i].getWordY()*0.97));
                              eDWords.paint(g);
                              g.drawImage(SpriteManager.skull.getBufferedImage(), eDWords.getWordX()-(int)(97*Game.widthRatio), nameWords[i].getWordY()-64, (int)(45* Game.widthRatio), (int)(45* Game.widthRatio), null);
                        }
                  }
            }

            //Paint Timer
            for(Words words: timeWords) {
                  if(words != null) {
                        words.paint(g);
                  }
            }
      }
}
