package com.stefan.game.states;

import com.stefan.game.graphics.Sprite;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.Vector2f;

import java.awt.*;

public class StartState extends GameState{

    private Sprite start_photo;

    public StartState(GameStateManager gsm){
        super(gsm);

        start_photo=new Sprite("state/Start-state.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (key.escape.down){
            System.exit(0);
        }

        if(key.enter.down){
            gsm.add(GameStateManager.MENU);
            gsm.pop(GameStateManager.PLAY);
        }
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawPhoto(g, start_photo.getSpriteSheet(),new Vector2f(0,0),1280,720);
    }
}
