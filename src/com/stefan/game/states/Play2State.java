package com.stefan.game.states;

import com.stefan.game.GamePanel;
import com.stefan.game.entity.Enemy;
import com.stefan.game.entity.Player;
import com.stefan.game.graphics.Font;
import com.stefan.game.graphics.Sprite;
import com.stefan.game.tiles.TileManager;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.TileCollision;
import com.stefan.game.util.Vector2f;

import java.awt.*;
import java.util.Vector;

public class Play2State extends GameState{

    private Font font;
    private Player player;
    public Vector<Enemy> enemy=new Vector<>();
    private TileManager tm;

    public static Vector2f map;

    public Play2State(GameStateManager gsm){
        super(gsm);

        TileCollision.isTrapActivated=false;
        map=new Vector2f(200,2210);
        Vector2f.setWorldVar(map.x,map.y);

        tm=new TileManager("tile/map_lvl2.xml");
        font=new Font("font/font.png",10,10);

        enemy.add(new Enemy(new Sprite("entity/enemy1.png",16,16),new Vector2f(3380,2366),64,1,1));
        enemy.add(new Enemy(new Sprite("entity/enemy1.png",16,16),new Vector2f(3630,2297),64,1,1));
        enemy.add(new Enemy(new Sprite("entity/enemy1.png",16,16),new Vector2f(3900,2232),64,1,1));
        enemy.add(new Enemy(new Sprite("entity/enemy1.png",16,16),new Vector2f(4150,2171),64,1,1));
        enemy.add(new Enemy(new Sprite("entity/enemy2.png",16,16),new Vector2f(3520,958),64,2,2));
        enemy.add(new Enemy(new Sprite("entity/enemy2.png",16,16),new Vector2f(2002,1143),64,2,2));
        player = new Player(new Sprite("entity/player.png"),new Vector2f(800,2550),64,5,2);
    }

    @Override
    public void update() {
        Vector2f.setWorldVar(map.x, map.y);

        player.update(enemy);

        //verific daca ma intersectez cu un inamic, in caz afirmativ playerul va avea cu o viata mai putin.
        for(Enemy enemy_ceva:enemy) {
            enemy_ceva.update(player);
            if(enemy_ceva.getContorLife()==200) {

                if(enemy_ceva.getBounds().collides(player.getBounds())){
                    player.decrementLife();
                    enemy_ceva.setContorLife(0);
                }
            }
            else{
                enemy_ceva.incrementContorLife();
            }

            if(player.getAttack() && player.hitBounds.collides(enemy_ceva.getBounds()) && player.getContorAttack()==0){
                enemy_ceva.decrementLife();
                player.setContorAttack(1);
                //System.out.println(enemy_ceva.getLife());
            }

            if(enemy_ceva.getLife()==0){
                player.incrementScor(enemy_ceva.initalLife);
                //System.out.println(player.getScor());
            }
        }

        enemy.removeIf(enemy_ceva -> enemy_ceva.getLife() == 0);

        if(player.getLife()==0){
            player.setIsAlive(false);
        }

        if(player.getIsAlive()==false){
            gsm.add(GameStateManager.GAMEOVER2);
            gsm.pop(GameStateManager.PLAY);
        }

        if (TileCollision.getisTrapActivated()==true){
            gsm.add(GameStateManager.WINGAME);
            gsm.pop(GameStateManager.PLAY);
        }

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        player.input(mouse,key);

        if(key.escape.down){
            gsm.add(GameStateManager.PAUSE);
        }
    }

    public void draw_Life(Graphics2D g,int number){
        switch (number) {
            case 5:
                Sprite.drawPhoto(g, new Sprite("life/life_5.png").getSpriteSheet(), new Vector2f(16, 16), 32, 32);
                break;
            case 4:
                Sprite.drawPhoto(g, new Sprite("life/life_4.png").getSpriteSheet(), new Vector2f(16, 16), 32, 32);
                break;
            case 3:
                Sprite.drawPhoto(g, new Sprite("life/life_3.png").getSpriteSheet(), new Vector2f(16, 16), 32, 32);
                break;
            case 2:
                Sprite.drawPhoto(g, new Sprite("life/life_2.png").getSpriteSheet(), new Vector2f(16, 16), 32, 32);
                break;
            case 1:
                Sprite.drawPhoto(g, new Sprite("life/life_1.png").getSpriteSheet(), new Vector2f(16, 16), 32, 32);
                break;
            case 0:
                Sprite.drawPhoto(g, new Sprite("life/life_0.png").getSpriteSheet(), new Vector2f(16, 16), 32, 32);
                break;
        }
    }

    @Override
    public void render(Graphics2D g) {
        tm.render(g);
        Sprite.drawArray(g,font, "Scor: "+player.getScor(),new Vector2f(GamePanel.width-256,32),32,32,24,0);
        draw_Life(g, player.getLife());
        player.render(g);
        for(Enemy enemy_ceva:enemy) {
            enemy_ceva.render(g);
        }
    }
}
