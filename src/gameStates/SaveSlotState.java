package gameStates;

import UI.Game;
import fonts.Words;
import gameStates.level.Level1State;
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
                    gameStateManager.setLevelState(new Level1State(gameStateManager));
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
            else {
                Record record = loadRecord(i + 1);
                new Words(record.getId() + " " + record.getLevel() + " " + record.getTime() + " " + record.getEmeraldCount() + " " + record.getDeathCount(),
                        30, (int) (rectangle.x + rectangle.getWidth() / 2),
                        (int) (rectangle.y + rectangle.getHeight() / 2 + 35)).paint(g);
            }
        }
    }
}
