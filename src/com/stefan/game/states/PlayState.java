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

import java.awt.Graphics2D;
import java.util.Vector;

public class PlayState extends GameState{

    private Font font;
    private Player player;
    public Vector<Enemy> enemy=new Vector<>();
    private TileManager tm;

    public static Vector2f map;

    public PlayState(GameStateManager gsm){
        super(gsm);

        map=new Vector2f(800,1000);
        Vector2f.setWorldVar(map.x, map.y);

        tm=new TileManager("tile/map_lvl1.xml");
        font=new Font("font/font.png",10,10);

        enemy.add(new Enemy(new Sprite("entity/enemy1.png",16,16),new Vector2f(1810,1340),64,1,1));
        enemy.add(new Enemy(new Sprite("entity/enemy1.png",16,16),new Vector2f(4410,1790),64,1,1));
        player = new Player(new Sprite("entity/player.png"),new Vector2f(1400,1330),64,5,1);

        player.setScor(0);
    }

    public void update(){
        Vector2f.setWorldVar(map.x, map.y);

        player.update(enemy);

        //verific daca ma intersectez cu un inamic, in caz afirmativ playerul va avea cu o viata mai putin.
        for(Enemy enemy_ceva:enemy) {
            enemy_ceva.update(player);
            //contorLife este o variabila ce nu permite inamicului sa atace imediat, fiindu-i necesar
            //un traseu (miscare stanga-dreapta) aproape complet pentru a putea ataca din nou
            if(enemy_ceva.getContorLife()==200) {
                if(enemy_ceva.getBounds().collides(player.getBounds())){
                    player.decrementLife();
                    enemy_ceva.setContorLife(0);
                }
            }
            else{
                enemy_ceva.incrementContorLife();
            }

            //daca pot ataca si o fac ii scad inamicului o viata
            if(player.getAttack() && player.hitBounds.collides(enemy_ceva.getBounds()) && player.getContorAttack()==0){
                enemy_ceva.decrementLife();
                player.setContorAttack(1);
                //System.out.println(enemy_ceva.getLife());
            }

            //daca omor un inamic, scorul imi creste
            if(enemy_ceva.getLife()==0){
                player.incrementScor(enemy_ceva.initalLife);
                //System.out.println(player.getScor());
            }
        }

        //daca un inamic nu mai are viata il elimin de pe harta
        enemy.removeIf(enemy_ceva -> enemy_ceva.getLife() == 0);

        //daca playerul are viata 0, inseamna ca nu mai poate juca
        if(player.getLife()==0){
            player.setIsAlive(false);
        }

        //daca playerul nu mai are viata eliminam starea cu nivelul1 si o initializam pe cea cu GameOver
        if(player.getIsAlive()==false){
            gsm.add(GameStateManager.GAMEOVER);
            gsm.pop(GameStateManager.PLAY);
        }

        //daca activeaza trapa poate trece la nivelul urmator deci eliminam starea cu nivelul 1 si o initializam pe cea cu WinLevel
        if (TileCollision.getisTrapActivated()==true){
            gsm.add(GameStateManager.WIN);
            gsm.pop(GameStateManager.PLAY);
        }
    }

    public void input(MouseHandler mouse, KeyHandler key){
        player.input(mouse,key);

        //daca apasam Esc initializam starea Pause
        if(key.escape.down){
            gsm.add(GameStateManager.PAUSE);
        }
    }

    //desenam bateria care este o analogie pentru viata protagonistului
    //in functie de cate vieti mai are atatea linii la baterie vor aparea in coltul ecranului
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

    public void render(Graphics2D g){
        tm.render(g);
        //desenez scorul in coltul din dreapta sus
        Sprite.drawArray(g,font, "Scor: "+player.getScor(),new Vector2f(GamePanel.width-256,32),32,32,24,0);
        draw_Life(g, player.getLife());
        player.render(g);
        for(Enemy enemy_ceva:enemy) {
            enemy_ceva.render(g);
        }
    }
}
