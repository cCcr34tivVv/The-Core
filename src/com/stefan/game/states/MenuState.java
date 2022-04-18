package com.stefan.game.states;

import com.stefan.game.entity.Player;
import com.stefan.game.graphics.Sprite;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.Vector2f;

import java.awt.*;

public class MenuState extends GameState {

    private Sprite menu_photo;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        menu_photo=new Sprite("state/Menu-state.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (key.escape.down){
            System.exit(0);
        }

        if(mouse.getButton()==1){
            //verificare daca apas pe new game
            if(mouse.getX()>=176 && mouse.getX()<=400 && mouse.getY()>=417 && mouse.getY()<=444){
                gsm.add(GameStateManager.PLAY);
                gsm.pop(GameStateManager.PLAY);
            }

            //verificare daca apas pe settings
            if(mouse.getX()>=460 && mouse.getX()<=685 && mouse.getY()>=417 && mouse.getY()<=444){
                gsm.add(GameStateManager.SETTINGS);
                gsm.pop(GameStateManager.PLAY);
            }

            //verificare daca apas pe load game
            if(mouse.getX()>=179 && mouse.getX()<=404 && mouse.getY()>=472 && mouse.getY()<=510){
                gsm.add(GameStateManager.LOADGAME);
                gsm.pop(GameStateManager.PLAY);
            }

            //verificare daca apas pe credits
            if(mouse.getX()>=460 && mouse.getX()<=653 && mouse.getY()>=472 && mouse.getY()<=510){
                gsm.add(GameStateManager.CREDITS);
                gsm.pop(GameStateManager.PLAY);
            }
        }

    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawPhoto(g, menu_photo.getSpriteSheet(),new Vector2f(0,0),1280,720);
    }
}
