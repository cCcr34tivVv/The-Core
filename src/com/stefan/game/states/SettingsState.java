package com.stefan.game.states;

import com.stefan.game.graphics.Sprite;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.Vector2f;

import java.awt.*;

public class SettingsState extends GameState{

    private Sprite settings_photo;

    public SettingsState(GameStateManager gsm){
        super(gsm);

        settings_photo=new Sprite("state/Settings-state.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if(mouse.getButton()==1){
            //verificare daca apas pe back
            if(mouse.getX()>=65 && mouse.getX()<=168 && mouse.getY()>=599 && mouse.getY()<=628){
                gsm.add(GameStateManager.MENU);
                gsm.pop(GameStateManager.PLAY);
            }

        }
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawPhoto(g, settings_photo.getSpriteSheet(),new Vector2f(0,0),1280,720);
    }
}
