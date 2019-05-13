package gameStates;

import UI.Game;
import UI.Window;
import audio.MusicPlayer;
import audio.SoundEffectPlayer;
import fonts.Words;
import gameStates.level.Level0State;
import graphics.FrameManager;
import graphics.SpriteManager;
import input.Input;
import map.Background;
import record.Record;
import record.Timer;
import java.awt.*;
import java.awt.event.KeyEvent;
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
    private boolean inputState;
    private Words inputWords;
    private int frame;
    private int frameDelay;
    private Words playerName;


    public SaveSlotState(GameStateManager gameStateManager) {
        super(gameStateManager);
    }

    public GameState getInstance() { return new SaveSlotState(gameStateManager); }

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
        inputState = false;
        inputWords = new Words("Enter your name: ", (int)(40*Game.widthRatio), Window.scaledGameWidth/2, Window.scaledGameHeight/2);
        frame = 0;
        frameDelay = 0;
        playerName = new Words("", (int)(60*Game.widthRatio), 0, 0);
    }

    @Override
    public void handleKeyInput() {
        if (!locked) {
            if (inputState) {
                if (Input.keys.get(7).down) {
                    if(playerName.getMsg().length() > 0) {
                        gameStateManager.setPlayerName(playerName.getMsg());
                        gameStateManager.setTimer(new Timer());
                        gameStateManager.setSlotId(selected + 1);
                        gameStateManager.setDeathCount(0);
                        gameStateManager.setEmeraldCount(0);
                        gameStateManager.resetEmerald();
                        gameStateManager.setGameState(new StoryState(gameStateManager));
                        inputState = false;
                        locked = true;
                    }
                }

            } else {
                if (Input.keys.get(7).down) {//Enter
                    SoundEffectPlayer.playSoundEffect("Enter");
                    if (saveDatas[selected] == null) {
                        Input.alphaBet.clear();
                        inputState = true;
                    }
                    else {
                        gameStateManager.loadRecord(saveDatas[selected]);
                        if(gameStateManager.getCurrentLevel() == 5) {
                            MusicPlayer.isOn = false;
                            MusicPlayer.changeSong(1);
                            MusicPlayer.isOn = true;
                        }
                    }
                    locked = true;
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
                if (Input.keys.get(4).down) { // X key
                    SoundEffectPlayer.playSoundEffect("Enter");
                    locked = true;
                    File recordToRemove = new File("./record/record" + (selected + 1) + ".ser");
                    recordToRemove.delete();
                    saveDatas[selected] = null;
                    gameStateManager.updateGameState(new SaveSlotState(gameStateManager));
                }
            }
        }
        if (Input.isAllReleased()){//放開
            locked = false;
        }
    }

    @Override
    public void update() {
        handleKeyInput();

        if(!isLoaded) {
            for (int i=0; i<saveDatas.length;i++) {
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
                        Words nameWords = new Words("Name: " + record.getName(), (int) (Window.scaledGameWidth* 0.023), (int) (rectangle.x + rectangle.getWidth()/8.7), (int) (rectangle.y + (rectangle.getHeight()/1.75)));
                        Words saveSlotWords = new Words("Save Slot" + record.getId(), (int) (Window.scaledGameWidth* 0.025), (int) (rectangle.x + rectangle.getWidth()/7), (int) (rectangle.y + (rectangle.getHeight()/3.33)));
                        Words levelWords = new Words("Level: " + record.getLevel(), (int) (Window.scaledGameWidth* 0.023), (int) (rectangle.x + rectangle.getWidth()/2.8), (int) (rectangle.y + (rectangle.getHeight()/1.75)));
                        Words timeWords = new Words(record.getTimeString(), (int) (Window.scaledGameWidth* 0.023), (int) (rectangle.x + rectangle.getWidth()/1.6), (int) (rectangle.y + (rectangle.getHeight()/1.75)));
                        Words emeraldCountWords = new Words("X " + record.getEmeraldCount(), (int) (Window.scaledGameWidth* 0.023), rectangle.x + (int) (rectangle.getWidth()/2.74), (int) (rectangle.y + (rectangle.getHeight()/1.1)));
                        Words deathCountWords = new Words("X " + record.getDeathCount(), (int) (Window.scaledGameWidth* 0.023), rectangle.x + (int) (rectangle.getWidth()/1.7), emeraldCountWords.getWordY());
                        saveSlotWordsMap.get(record).add(nameWords);
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
        for (int i = 0;i<rectangles.size();i++) {
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
                g2.setColor(Color.white);
            }

            // Paint record data words
            for (HashMap.Entry<Record, ArrayList<Words>> entry : saveSlotWordsMap.entrySet()) {
                for (Words words : entry.getValue()) {
                    words.paint(g);
                }
                if(saveDatas[i] != null) {
                    g.drawImage(SpriteManager.emerald.getBufferedImage(), (int)(rectangle.x + rectangle.getWidth()/3.7), (int)(rectangle.y + rectangle.getHeight()/1.65), (int)(Window.scaledGameWidth*0.04), (int)(Window.scaledGameWidth*0.04), null);
                    g.drawImage(SpriteManager.skull.getBufferedImage(), (int)(rectangle.x + rectangle.getWidth()/2.05),  (int)(rectangle.y + rectangle.getHeight()/1.65), (int)(Window.scaledGameWidth*0.04), (int)(Window.scaledGameWidth*0.04), null);
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

        if(inputState) {
            // Transparent background
            Graphics2D g2 = (Graphics2D) g;
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.7f);
            g2.setComposite(ac);
            g.setColor(Color.black);
            g.fillRect(0, 0, Window.scaledGameWidth+10, Window.scaledGameHeight+10);
            ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1);

            // Input rectangle
            g2.setComposite(ac);
            inputWords.paint(g);
            g.setColor(Color.GRAY);
            int rectX = inputWords.getWordX() - inputWords.getWidth()/2;
            int rectY = inputWords.getWordY() + inputWords.getHeight()/2;
            int rectWidth = inputWords.getWidth();
            int rectHeight = (int)(90*Game.widthRatio);
            g.fillRect(rectX, rectY, rectWidth, rectHeight);
            playerName.setPos((int)(rectX * 1.01) + rectWidth/2, rectY + rectHeight);

            // Cursor
            if (frame <= 60) {
                frame++;
                g2.setColor(Color.white);
                g2.drawLine(playerName.getWordX() + playerName.getWidth()/2, rectY
                        , playerName.getWordX() + playerName.getWidth()/2, rectY + rectHeight);
            }
            else {
                if(frameDelay <= 60) {
                    frameDelay++;
                }
                else {
                    frame = 0;
                    frameDelay = 0;
                }
            }

            String name = "";
            // Display name input
            for(Character character: Input.alphaBet) {
                name += character;
            }
            playerName.setWord(name);
            playerName.paint(g);

            //Enter Key
            g2.drawImage(SpriteManager.enterKey.getBufferedImage(), (int)(Window.scaledGameWidth/1.3), (int)(Window.scaledGameHeight/1.12),
                    (int)(Window.scaledGameWidth*0.05), (int)(Window.scaledGameWidth*0.05), null);


        }
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
}