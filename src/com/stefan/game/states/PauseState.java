package com.stefan.game.states;

import com.stefan.game.graphics.Sprite;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.Vector2f;

import java.awt.*;

public class PauseState extends GameState {

    private Sprite pause_photo;

    public PauseState(GameStateManager gsm) {
        super(gsm);

        pause_photo=new Sprite("state/Pause-state.png");
    }

    @Override
    public void update() {
        //System.out.println("PAUSED");
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if(mouse.getButton()==1){
            //verificare daca apas pe main menu
            if(mouse.getX()>=288 && mouse.getX()<=530 && mouse.getY()>=417 && mouse.getY()<=461){
                gsm.add(GameStateManager.MENU);
                gsm.pop(GameStateManager.PLAY);
                gsm.pop(GameStateManager.PLAY);
            }

            //verificare daca apas pe return to game
            if(mouse.getX()>=255 && mouse.getX()<=565 && mouse.getY()>=491 && mouse.getY()<=517){
                gsm.pop(GameStateManager.MENU);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawPhoto(g, pause_photo.getSpriteSheet(),new Vector2f(0,0),1280,720);
    }
}
