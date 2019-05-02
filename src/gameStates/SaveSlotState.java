package gameStates;

import UI.Game;
import audio.SoundEffectPlayer;
import fonts.Words;
import gameStates.level.Level1State;
import graphics.SpriteManager;
import input.Input;
import map.Background;
import record.Record;
import record.Timer;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveSlotState extends GameState {
    private ArrayList<Words> words;
    private ArrayList<Rectangle> rectangles;
    private int selected;
    private Record[] saveDatas;
    private HashMap<Record, ArrayList<Words>> saveSlotWordsMap;
    private Words startNewGameWords;

    public SaveSlotState(GameStateManager gameStateManager) {
        super(gameStateManager);
        words = new ArrayList<>();
        rectangles = new ArrayList<>();
        saveDatas = new Record[3];
        saveSlotWordsMap = new HashMap<>();
        startNewGameWords = new Words("Start New Game", 50);
        selected = 0;
        init();
    }

    public Record loadRecord(int num) {
        Record record = null;
        try {
            FileInputStream fileIn = new FileInputStream("./record/record" + num + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            record = (Record) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            //i.printStackTrace();
        }
        return record;
    }

    @Override
    public void init() {
        background = new Background("/res/SaveSlot.png", Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
        words.add(new Words("Load Game", 50, 200, 120));
        rectangles.add(new Rectangle(85, 200, 1070, 190));
        rectangles.add(new Rectangle(85, 425, 1070, 190));
        rectangles.add(new Rectangle(85, 645, 1070, 190));
    }

    @Override
    public void handleKeyInput() {
        if(!locked){
            if(Input.keys.get(7).down) {//Enter
                SoundEffectPlayer.playSoundEffect("Enter");
                if(saveDatas[selected] == null) {
                    gameStateManager.setTimer(new Timer());
                    gameStateManager.setSlotId(selected + 1);
                    gameStateManager.setLevelState(new Level1State(gameStateManager));
                }
                else {
                    gameStateManager.loadRecord(saveDatas[selected]);
                }
            }
            if(Input.keys.get(0).down){//上
                SoundEffectPlayer.playSoundEffect("Cursor");
                locked = true;
                selected -= 1;
                selected %= 3;
                if(selected < 0) {
                    selected = 0;
                }
            }
            if(Input.keys.get(1).down){//下
                SoundEffectPlayer.playSoundEffect("Cursor");
                locked = true;
                selected += 1;
                selected %= 3;
            }
        }
        if(!Input.keys.get(0).down &&!Input.keys.get(1).down && !Input.keys.get(7).down){//放開
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
        for(Words word: words) {
            word.paint(g);
        }
        for(int i=0;i<rectangles.size();i++) {
            if(i == selected) {
                Rectangle rectangle = rectangles.get(i);
                Graphics2D g2 = (Graphics2D) g;
                Color newColor = new Color(139,0,150);
                g2.setColor(newColor);
                float thickness = 13;
                Stroke oldStroke = g2.getStroke();
                g2.setStroke(new BasicStroke(thickness));
                g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                g2.setStroke(oldStroke);
            }
        }

        for(int i=0;i<saveDatas.length;i++) {
            Rectangle rectangle = rectangles.get(i);
            Record record = loadRecord(i + 1);
            saveDatas[i] = record;
            if(record == null){
                startNewGameWords.setPos((int) (rectangle.x + rectangle.getWidth() / 2), (int) (rectangle.y + rectangle.getHeight() / 2 + 35));
                startNewGameWords.paint(g);
            }
            else {
                if(saveSlotWordsMap.get(record) == null) {
                    saveSlotWordsMap.put(record, new ArrayList<>());
                    Words saveSlotWords = new Words("Save Slot" + record.getId(), 40, rectangle.x+150, rectangle.y+60);
                    Words levelWords = new Words("Level: " + record.getLevel(), 30,rectangle.x+150, rectangle.y+110);
                    Words timeWords = new Words(record.getTimeString(), 30,rectangle.x+500, rectangle.y+110);
                    Words emeraldCountWords = new Words("X " + record.getEmeraldCount(), 30, levelWords.getWordX() - levelWords.getWidth()/2+20, levelWords.getWordY() + 60);
                    Words deathCountWords = new Words("X " + record.getDeathCount(), 30, timeWords.getWordX() - timeWords.getWidth()/2-20, timeWords.getWordY() + 60);
                    saveSlotWordsMap.get(record).add(saveSlotWords);
                    saveSlotWordsMap.get(record).add(levelWords);
                    saveSlotWordsMap.get(record).add(timeWords);
                    saveSlotWordsMap.get(record).add(emeraldCountWords);
                    saveSlotWordsMap.get(record).add(deathCountWords);
                }
                for(Words words: saveSlotWordsMap.get(record)) {
                    words.paint(g);
                }
                g.drawImage(SpriteManager.emerald.getBufferedImage(), saveSlotWordsMap.get(record).get(1).getWordX() - saveSlotWordsMap.get(record).get(1).getWidth()/2-20, saveSlotWordsMap.get(record).get(1).getWordY() + 5, 64, 64, null);
                g.drawImage(SpriteManager.skull.getBufferedImage(), saveSlotWordsMap.get(record).get(2).getWordX() - saveSlotWordsMap.get(record).get(1).getWidth()/2-70, saveSlotWordsMap.get(record).get(2).getWordY() + 5, 64, 64, null);
            }
        }
    }
}
