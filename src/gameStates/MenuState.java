
package gameStates;

import UI.Game;
import fonts.Words;
import gameStates.level1.Level1AState;
import gameStates.level1.Level1BState;
import input.Input;
import java.awt.Graphics;
import map.Background;


public class MenuState extends GameState{
    private Words wordTitle;
    private Words wordStart;
    private Words wordOption;
    private Words wordExit;
    private GameStateManager gameStateManager;
 
    public MenuState(GameStateManager gameStateManager) {
        super();
        this.gameStateManager = gameStateManager;
        init(); 
    }
    
    public void init() {
        background = new Background("/res/Cave1.png", Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
        wordTitle = new Words("UnderGround", 80, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2-120);
        wordStart = new Words("Start", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+80);
        wordOption = new Words("Option", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+140);
        wordExit = new Words("Exit", 40, Game.WIDTH*Game.SCALE/2, Game.HEIGHT*Game.SCALE/2+200);
    }
    @Override
    public void handleKeyInput() {
        if(Input.keys.get(7).down){
            gameStateManager.setGameState(new Level1AState());
        } 
        
    }
    @Override   
    public void update() {
        
    }
    @Override
    public void paint(Graphics g) {
        background.paint(g);
        wordTitle.paint(g);
        wordStart.paint(g);
        wordOption.paint(g);
        wordExit.paint(g);
    }
}
