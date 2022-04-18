package com.stefan.game.states;

import com.stefan.game.graphics.Sprite;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.Vector2f;

import java.awt.*;

public class WinLevelState extends GameState{
    private Sprite winLevel_photo;

    public WinLevelState(GameStateManager gsm){
        super(gsm);

        winLevel_photo=new Sprite("state/WinLevel-state.png");
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

            //verificare daca apas pe next level
            if(mouse.getX()>=300 && mouse.getX()<=513 && mouse.getY()>=492 && mouse.getY()<=519){
                gsm.add(GameStateManager.PLAY2);
                gsm.pop(GameStateManager.PLAY);

            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        Sprite.drawPhoto(g, winLevel_photo.getSpriteSheet(),new Vector2f(0,0),1280,720);
    }
}
