package com.stefan.game.states;

import com.stefan.game.GamePanel;
import com.stefan.game.graphics.Font;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.Vector2f;

import java.awt.Graphics2D;
import java.util.Vector;

public class GameStateManager {

    private Vector <GameState> states;

    public static Vector2f map;

    public static final int PLAY=0;
    public static final int MENU=1;
    public static final int PAUSE=2;
    public static final int GAMEOVER=3;
    public static final int SETTINGS=4;
    public static final int LOADGAME=5;
    public static final int CREDITS=6;
    public static final int START=7;
    public static final int PLAY2=8;
    public static final int WIN=9;
    public static final int WINGAME=10;
    public static final int GAMEOVER2=11;

    public int onTopState=0;

    public static Font font;

    public GameStateManager(){
        map=new Vector2f(GamePanel.width,GamePanel.height);
        Vector2f.setWorldVar(map.x, map.y);

        font=new Font("font/font.png",10,10);

        states=new Vector<>();

        states.add(new StartState(this));
    }

    public void pop(int state){
        states.remove(state);
    }

    public void add(int state){
        switch (state){
            case PLAY:
                states.add(new PlayState(this));
                break;
            case MENU:
                states.add(new MenuState(this));
                break;
            case PAUSE:
                states.add(new PauseState(this));
                break;
            case GAMEOVER:
                states.add(new GameOverState(this));
                break;
            case SETTINGS:
                states.add(new SettingsState(this));
                break;
            case LOADGAME:
                states.add(new LoadGameState(this));
                break;
            case CREDITS:
                states.add(new CreditsState(this));
                break;
            case START:
                states.add(new StartState(this));
                break;
            case PLAY2:
                states.add(new Play2State(this));
                break;
            case WIN:
                states.add(new WinLevelState(this));
                break;
            case WINGAME:
                states.add(new WinGameState(this));
                break;
            case GAMEOVER2:
                states.add(new GameOver2State(this));
                break;
        }
    }

    public void addAndPop(int state){
        addAndPop(state,0);
    }

    public void addAndPop(int state,int remove) {
        pop(state);
        add(state);
    }

    public void update(){
        Vector2f.setWorldVar(map.x,map.y);
        states.lastElement().update();
    }

    public void input(MouseHandler mouse, KeyHandler key){

        states.lastElement().input(mouse,key);
    }

    public void render(Graphics2D g){
        states.lastElement().render(g);
    }
}
