package com.stefan.game.entity;

import com.stefan.game.graphics.Sprite;
import com.stefan.game.util.AABB;
import com.stefan.game.util.TileCollision;
import com.stefan.game.util.Vector2f;

import java.awt.*;

public class Enemy extends Entity {

    private AABB sense;
    private int radius;
    public float initialPoz;

    //1--varianta 1  2--varianta 2
    public int varianta;

    public int initalLife;

    public boolean moveRight=true;

    public Enemy(Sprite sprite, Vector2f origin, int size,int life,int varianta) {
        super(sprite, origin, size);
        this.life=life;
        this.varianta=varianta;

        initalLife=life;

        initialPoz=this.getPos().x;

        acc = 1f;
        maxSpeed = 1.5f;

        radius = 300;

        bounds.setWidth(46);
        bounds.setHeight(54);
        bounds.setxOffset(8);
        bounds.setyOffset(5);

        sense = new AABB(new Vector2f(origin.x + size / 2 - radius / 2, origin.y + size / 2 - radius / 2), radius);
    }

    public void move(Player player,int varianta) {

        //varianta de inamic : Gardian1 sau Gardian2

        //Gardian1
        if(varianta==1) {

            //gravitatie
            if (dy > 2 * maxSpeed) {
                dy = 2 * maxSpeed;
            } else {
                dy += fallSpeed;
            }

            //left
            if (left) {
                dx -= acc;
                right = false;
                left = true;
                if (dx < -maxSpeed) {
                    dx = -maxSpeed;
                }
            }//right
            else if (right) {
                dx += acc;
                left = false;
                right = true;
                if (dx > maxSpeed) {
                    dx = maxSpeed;
                }
            } else {
                dx = 0;
                right = false;
                left = false;
            }


            //miscarea inamicilor stanga-dreapta
            if(pos.x>=initialPoz+200)
                moveRight=false;
            else if(pos.x<=initialPoz)
                moveRight=true;

            if (moveRight) {
                right = true;
                left = false;
            } else {
                left = true;
                right = false;
            }


        }//Gardian2
        else if(varianta==2){

            //daca intra in proximitatea inamicului care este reprezentata printr un cerc in jurul acestuia
            if(sense.colCircleBox(player.getBounds())) {

                //gravitatie
                if (dy > 2 * maxSpeed) {
                    dy = 2 * maxSpeed;
                } else {
                    dy += fallSpeed;
                }

                //left
                if (pos.x>player.pos.x+1) {
                    dx -= acc;
                    right = false;
                    left = true;
                    if (dx < -maxSpeed) {
                        dx = -maxSpeed;
                    }
                }//right
                else if (pos.x<player.pos.x-1) {
                    dx += acc;
                    left = false;
                    right = true;
                    if (dx > maxSpeed) {
                        dx = maxSpeed;
                    }
                } else {
                    dx = 0;
                    right = false;
                    left = false;
                }
            }
            else{
                right=false;
                left=false;
                dx=0;
                dy=0;
            }
        }

    }

    public void update(Player player){
        super.update();

        move(player,varianta);
        if(!fallen) {
            if (!tc.collisionTile(dx, 0)) {
                sense.getPos().x += dx;
                pos.x += dx;
            }
            if (!tc.collisionTile(0, dy)) {
                sense.getPos().y += dy;
                pos.y += dy;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        //g.setColor(Color.pink);
        //g.drawRect((int)(pos.getWorldVar().x+bounds.getXOffset()),(int)(pos.getWorldVar().y+bounds.getYOffset()),(int)bounds.getWidth(),(int)bounds.getHeight());

        //g.setColor(Color.orange);
        //g.drawOval((int)(sense.getPos().getWorldVar().x),(int)(sense.getPos().getWorldVar().y),radius,radius);

        g.drawImage(ani.getImage(),(int)(pos.getWorldVar().x),(int)(pos.getWorldVar().y),size,size,null);
    }
}
