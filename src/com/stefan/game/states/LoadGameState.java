package com.stefan.game.states;

import com.stefan.game.graphics.Sprite;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.Vector2f;

import java.awt.*;

public class LoadGameState extends GameState{

    private Sprite loadGame_sprite;

    public LoadGameState(GameStateManager gsm){
        super(gsm);

        loadGame_sprite=new Sprite("state/LoadGame-state.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if(mouse.getButton()==1){
            //verificare daca apas pe back
            if(mouse.getX()>=58 && mouse.getX()<=161 && mouse.getY()>=592 && mouse.getY()<=621){
                gsm.add(GameStateManager.MENU);
                gsm.pop(GameStateManager.PLAY);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawPhoto(g, loadGame_sprite.getSpriteSheet(),new Vector2f(0,0),1280,720);
    }
}
