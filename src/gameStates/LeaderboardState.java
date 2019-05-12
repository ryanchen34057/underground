package gameStates;

import UI.Game;
import UI.Window;
import fonts.Words;
import static gameStates.GameState.locked;
import graphics.SpriteManager;
import input.Input;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import map.Background;
import record.Record;


public class LeaderboardState extends GameState {
      private Words wordTitle;
      
      //讀檔
      private Words[] words;
      private ArrayList<Record> records;

      public LeaderboardState(GameStateManager gameStateManager) {
            super(gameStateManager);
             init();
            
      }

      public void init() {
            background = new Background("/res/Cave1.png", Window.scaledGameWidth, Window.scaledGameHeight);
            wordTitle = new Words("Leaderboard", 60, Window.scaledGameWidth /2,  Window.scaledGameHeight / 2 - 180);
              
                        records = gameStateManager.readEndGameRecord();//讀取
                        words = new Words[5];
                        for(int i=0;i<5;i++){//無紀錄，先給空值
                              words[i] = new Words((i+1)+". noRecord", 40, Window.scaledGameWidth /3, Window.scaledGameHeight/2+20+60*i);
                        }
                        System.out.println(records.size());
                        if(records.size() >= 1){
                              for(int i=0;i<records.size();i++){
                                    System.out.println(records.get(i));
                              }
                              Collections.sort(records, new Comparator<Record>(){//排序
                                    @Override
                                    public int compare(Record r1, Record r2) {
                                          return (int)r1.getTime() - (int)r2.getTime();
                                    }   
                              });
                               for(int i=0;i<records.size();i++){//設定
                                    if(i>=5){
                                          break;
                                    }
                                    if(records.get(i) != null){
                                          words[i].setWord((i+1)+". "+records.get(i).getTimeString().substring(7, 15));
            //                              words[i].setWordX(Game.WIDTH * Game.SCALE/3);
                                    }
                              }
                        }
      }

     

      @Override
      public void handleKeyInput() {
            if (!locked) {
                  if (Input.keys.get(7).down) {//Enter
                        gameStateManager.back();
                        locked = true;
                  }        
            }
            if (!Input.keys.get(0).down && !Input.keys.get(1).down && !Input.keys.get(7).down) {//放開
                  locked = false;
            }
      }

      @Override
      public void update() {
            handleKeyInput();
      }

      @Override
      public void paint(Graphics g) {
            background.paint(g);
            wordTitle.paint(g);

            for(int i=0;i<5;i++){
                  if(records.size() < 1){
                        words[i].paint(g);
                  }else{
                        words[i].paint(g);
                        if(i<records.size()){      
                              //寶石
                              Words eCWords = new Words("X " + records.get(i).getEmeraldCount(), 30,Window.scaledGameWidth/2+80,words[i].getWordY());
                              eCWords.paint(g);
                              g.drawImage(SpriteManager.emerald.getBufferedImage(), eCWords.getWordX()-104 , words[i].getWordY()-64, 64, 64, null);
                              //死亡
                              Words eDWords = new Words("X " + records.get(i).getDeathCount(), 30,Window.scaledGameWidth/2+280,words[i].getWordY());
                              eDWords.paint(g);
                              g.drawImage(SpriteManager.skull.getBufferedImage(), eDWords.getWordX()-104, words[i].getWordY()-64, 64, 64, null); 
                        }
                  }
            }
     
      }
      
     
}
