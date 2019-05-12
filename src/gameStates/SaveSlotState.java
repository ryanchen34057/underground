package gameStates;

import UI.Game;
import UI.Window;
import audio.SoundEffectPlayer;
import fonts.Words;
import gameStates.level.Level0State;
import graphics.SpriteManager;
import input.Input;
import map.Background;
import record.Record;
import record.Timer;
import java.awt.*;
import java.io.File;
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
    private Words[] startNewGameWords;
    private boolean isLoaded;

    public SaveSlotState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    public GameState getInstance() { return new SaveSlotState(gameStateManager); }

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
        background = new Background("/res/SaveSlot.png", Window.scaledGameWidth, Window.scaledGameHeight);
        words = new ArrayList<>();
        rectangles = new ArrayList<>();
        saveDatas = new Record[3];
        saveSlotWordsMap = new HashMap<>();
        startNewGameWords = new Words[3];
        selected = 0;
        isLoaded = false;
        words.add(new Words("Load Game", (int) (Window.scaledGameWidth* 0.04), (int) (Window.scaledGameWidth* 0.16), (int) (Window.scaledGameHeight * 0.14)));
        words.add(new Words("Load", (int) (Window.scaledGameWidth* 0.02), (int)(Window.scaledGameWidth/1.33),(int)(Window.scaledGameHeight/1.04)));
        words.add(new Words("Delete", (int) (Window.scaledGameWidth* 0.02), (int)(Window.scaledGameWidth/1.13),(int)(Window.scaledGameHeight/1.04)));
        rectangles.add(new Rectangle((Window.scaledGameWidth) / 11, (Window.scaledGameHeight) / 6, (int) ((Window.scaledGameWidth) * 0.83), (int) ((Window.scaledGameHeight) * 0.22)));
        rectangles.add(new Rectangle((Window.scaledGameWidth) / 11, (int) ((Window.scaledGameHeight) / 2.4), (int) ((Window.scaledGameWidth) * 0.83), (int) ((Window.scaledGameHeight) * 0.22)));
        rectangles.add(new Rectangle((Window.scaledGameWidth) / 11, (int) ((Window.scaledGameHeight) / 1.49), (int) ((Window.scaledGameWidth) * 0.83), (int) ((Window.scaledGameHeight) * 0.22)));
    }

    @Override
    public void handleKeyInput() {
        if (!locked) {
            if (Input.keys.get(7).down) {//Enter
                SoundEffectPlayer.playSoundEffect("Enter");
                if (saveDatas[selected] == null) {
                    gameStateManager.setTimer(new Timer());
                    gameStateManager.setSlotId(selected + 1);
                    gameStateManager.setDeathCount(0);
                    gameStateManager.setEmeraldCount(0);
                    gameStateManager.setLevelState(new Level0State(gameStateManager));
                } else {
                    gameStateManager.loadRecord(saveDatas[selected]);
                }
            }
            if (Input.keys.get(0).down) {//上
                SoundEffectPlayer.playSoundEffect("Cursor");
                locked = true;
                selected -= 1;
                selected %= 3;
                if (selected < 0) {
                    selected = 0;
                }
            }
            if (Input.keys.get(1).down) {//下
                SoundEffectPlayer.playSoundEffect("Cursor");
                locked = true;
                selected += 1;
                selected %= 3;
            }
            if(Input.keys.get(4).down) { // X key
                SoundEffectPlayer.playSoundEffect("Enter");
                locked = true;
                File recordToRemove = new File("./record/record" + (selected+1) + ".ser");
                recordToRemove.delete();
                saveDatas[selected] = null;
                gameStateManager.updateGameState(new SaveSlotState(gameStateManager));
            }
        }
        if (!Input.keys.get(0).down && !Input.keys.get(1).down && !Input.keys.get(7).down && !Input.keys.get(4).down) {//放開
            locked = false;
        }
    }

    @Override
    public void update() {
        handleKeyInput();

        if(!isLoaded) {
            for (int i = 0; i < saveDatas.length; i++) {
                Rectangle rectangle = rectangles.get(i);
                Record record = loadRecord(i + 1);
                saveDatas[i] = record;
                if (record == null) {
                    if (startNewGameWords[i] == null) {
                        Words temp = new Words("Start New Game", (int) (Window.scaledGameWidth* 0.04));
                        temp.setPos((int) (rectangle.x + rectangle.getWidth() / 2), (int) (rectangle.y + rectangle.getHeight() / 1.4));
                        startNewGameWords[i] = temp;
                    }
                } else {
                    if (!(saveSlotWordsMap.containsKey(record))) {
                        saveSlotWordsMap.put(record, new ArrayList<>());
                        Words saveSlotWords = new Words("Save Slot" + record.getId(), (int) (Window.scaledGameWidth* 0.025), (int) (rectangle.x + rectangle.getWidth()/7), (int) (rectangle.y + (rectangle.getHeight()/3.33)));
                        Words levelWords = new Words("Level: " + record.getLevel(), (int) (Window.scaledGameWidth* 0.023), (int) (rectangle.x + rectangle.getWidth()/5), (int) (rectangle.y + (rectangle.getHeight()/1.75)));
                        Words timeWords = new Words(record.getTimeString(), (int) (Window.scaledGameWidth* 0.023), (int) (rectangle.x + rectangle.getWidth()/2), (int) (rectangle.y + (rectangle.getHeight()/1.75)));
                        Words emeraldCountWords = new Words("X " + record.getEmeraldCount(), (int) (Window.scaledGameWidth* 0.023), rectangle.x + (int) (rectangle.getWidth()/4.5), (int) (rectangle.y + (rectangle.getHeight()/1.1)));
                        Words deathCountWords = new Words("X " + record.getDeathCount(), (int) (Window.scaledGameWidth* 0.023), rectangle.x + (int) (rectangle.getWidth()/2.05), emeraldCountWords.getWordY());
                        saveSlotWordsMap.get(record).add(saveSlotWords);
                        saveSlotWordsMap.get(record).add(levelWords);
                        saveSlotWordsMap.get(record).add(timeWords);
                        saveSlotWordsMap.get(record).add(emeraldCountWords);
                        saveSlotWordsMap.get(record).add(deathCountWords);
                    }
                }
            }
            isLoaded = true;
        }

    }

    @Override
    public void paint(Graphics g) {
        background.paint(g);
        // Paint Load game word
        for (Words word : words) {
            word.paint(g);
        }
        for (int i = 0; i < rectangles.size(); i++) {
            // Determine border of rectangle
            Rectangle rectangle = rectangles.get(i);
            if (i == selected) {
                Graphics2D g2 = (Graphics2D) g;
                Color newColor = new Color(139, 0, 150);
                g2.setColor(newColor);
                float thickness = Window.scaledGameWidth* 0.01f;
                Stroke oldStroke = g2.getStroke();
                g2.setStroke(new BasicStroke(thickness));
                g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                g2.setStroke(oldStroke);
            }

            // Paint record data words
            for (HashMap.Entry<Record, ArrayList<Words>> entry : saveSlotWordsMap.entrySet()) {
                for (Words words : entry.getValue()) {
                    words.paint(g);
                }
                if(saveDatas[i] != null) {
                    g.drawImage(SpriteManager.emerald.getBufferedImage(), (int)(rectangle.x + rectangle.getWidth()/8.5), (int)(rectangle.y + rectangle.getHeight()/1.65), (int)(Window.scaledGameWidth*0.04), (int)(Window.scaledGameWidth*0.04), null);
                    g.drawImage(SpriteManager.skull.getBufferedImage(), (int)(rectangle.x + rectangle.getWidth()/2.7),  (int)(rectangle.y + rectangle.getHeight()/1.65), (int)(Window.scaledGameWidth*0.04), (int)(Window.scaledGameWidth*0.04), null);
                }
            }

            // Paint start new game word
            for(Words words: startNewGameWords) {
                if(words != null) {
                    words.paint(g);
                }
            }

            // Paint key hint
            g.drawImage(SpriteManager.enterKey.getBufferedImage(), (int)(Window.scaledGameWidth/1.5), (int)(Window.scaledGameHeight/1.12), (int)(Window.scaledGameWidth*0.05), (int)(Window.scaledGameWidth*0.05), null);
            g.drawImage(SpriteManager.xKey.getBufferedImage(), (int)(Window.scaledGameWidth/1.25), (int)(Window.scaledGameHeight/1.12), (int)(Window.scaledGameWidth*0.05), (int)(Window.scaledGameWidth*0.05), null);
        }
    }
}