package gameStates;

import UI.Game;
import fonts.Words;
import gameStates.level.Level0State;
import graphics.SpriteManager;
import input.Input;
import map.Background;
import record.Record;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class SaveSlotState extends GameState {
    private ArrayList<Words> words;
    private ArrayList<Rectangle> rectangles;
    private int selected;
    private File[] saveDatas;

    public SaveSlotState(GameStateManager gameStateManager) {
        super(gameStateManager);
        words = new ArrayList<>();
        rectangles = new ArrayList<>();
        saveDatas = new File[3];
        selected = 0;
        init();
    }

    public File[] findSaveFile() {
        File f = new File("./record");
        return f.listFiles((dir, name) -> name.startsWith("record") && name.endsWith("ser"));
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
            i.printStackTrace();
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
        File[] files = findSaveFile();
        System.arraycopy(files, 0, saveDatas, 0, files.length);
    }

    @Override
    public void handleKeyInput() {
        if(!locked){
            if(Input.keys.get(7).down) {//Enter
                if(saveDatas[selected] == null) {
                    gameStateManager.setSlotId(selected + 1);
                    gameStateManager.setLevelState(new Level0State(gameStateManager));
                }
                else {
                    gameStateManager.loadRecord(loadRecord(selected + 1));
                }
            }
            if(Input.keys.get(0).down){//上
                locked = true;
                selected -= 1;
                selected %= 3;
                if(selected < 0) {
                    selected = 0;
                }
            }
            if(Input.keys.get(1).down){//下
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
            if(saveDatas[i] == null) {
                new Words("Start New Game", 50, (int) (rectangle.x + rectangle.getWidth() / 2),
                        (int) (rectangle.y + rectangle.getHeight() / 2 + 35)).paint(g);
            }
            // Display record data
            else {
                Record record = loadRecord(i + 1);
                Words saveSlotWords = new Words("Save Slot" + record.getId(), 40, rectangle.x+150, rectangle.y+60);
                Words levelWords = new Words("Level: " + record.getLevel(), 30,rectangle.x+150, rectangle.y+110);
                Words timeWords = new Words(record.getTimeString(), 30,rectangle.x+500, rectangle.y+110);
                Words emeraldCountWords = new Words("X " + record.getEmeraldCount(), 30, levelWords.getWordX() - levelWords.getWidth()/2+20, levelWords.getWordY() + 60);
                Words deathCountWords = new Words("X " + record.getDeathCount(), 30, timeWords.getWordX() - timeWords.getWidth()/2-20, timeWords.getWordY() + 60);
                saveSlotWords.paint(g);
                levelWords.paint(g);
                timeWords.paint(g);
                emeraldCountWords.paint(g);
                deathCountWords.paint(g);
                g.drawImage(SpriteManager.emerald.getBufferedImage(), levelWords.getWordX() - levelWords.getWidth()/2-20, levelWords.getWordY() + 5, 64, 64, null);
                g.drawImage(SpriteManager.skull.getBufferedImage(), timeWords.getWordX() - levelWords.getWidth()/2-70, timeWords.getWordY() + 5, 64, 64, null);
            }
        }
    }
}
