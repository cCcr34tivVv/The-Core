package com.stefan.game.states;

import com.stefan.game.graphics.Sprite;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.Vector2f;

import java.awt.*;

public class GameOver2State extends GameState{
    private Sprite gameOver2_photo;


    public GameOver2State(GameStateManager gsm) {
        super(gsm);
        gameOver2_photo=new Sprite("state/GameOver-state.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if(mouse.getButton()==1){
            //verificare daca apas pe main menu
            if(mouse.getX()>=288 && mouse.getX()<=530 && mouse.getY()>=436 && mouse.getY()<=461){
                gsm.add(GameStateManager.MENU);
                gsm.pop(GameStateManager.PLAY);
            }

            //verificare daca apas pe reload level
            //trb verificat la ce nivel sunt
            if(mouse.getX()>=283 && mouse.getX()<=553 && mouse.getY()>=493 && mouse.getY()<=519){
                gsm.add(GameStateManager.PLAY2);
                gsm.pop(GameStateManager.PLAY);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawPhoto(g, gameOver2_photo.getSpriteSheet(),new Vector2f(0,0),1280,720);
    }
}
