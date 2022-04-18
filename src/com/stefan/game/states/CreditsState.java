package com.stefan.game.states;

import com.stefan.game.graphics.Sprite;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.Vector2f;

import java.awt.*;

public class CreditsState extends GameState{

    private Sprite credits_photo;

    public CreditsState(GameStateManager gsm){
        super(gsm);
        credits_photo=new Sprite("state/Credits-state.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if(mouse.getButton()==1){
            //verificare daca apas pe back
            if(mouse.getX()>=82 && mouse.getX()<=185 && mouse.getY()>=569 && mouse.getY()<=598){
                gsm.add(GameStateManager.MENU);
                gsm.pop(GameStateManager.PLAY);
            }

        }
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawPhoto(g, credits_photo.getSpriteSheet(),new Vector2f(0,0),1280,720);
    }
}
