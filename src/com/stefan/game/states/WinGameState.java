package com.stefan.game.states;

import com.stefan.game.graphics.Sprite;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.Vector2f;

import java.awt.*;

public class WinGameState extends GameState{

    private Sprite winGame_photo;

    public WinGameState(GameStateManager gsm){
        super(gsm);

        winGame_photo=new Sprite("state/WinGame-state.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if(mouse.getButton()==1) {
            //verificare daca apas pe exit
            if (mouse.getX() >= 365 && mouse.getX() <= 460 && mouse.getY() >= 479 && mouse.getY() <= 505) {
                gsm.pop(GameStateManager.PLAY);
                System.exit(0);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawPhoto(g, winGame_photo.getSpriteSheet(),new Vector2f(0,0),1280,720);
    }
}
