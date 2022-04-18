package com.stefan.game.entity;


import com.stefan.game.graphics.Sprite;
import com.stefan.game.states.GameState;
import com.stefan.game.states.Play2State;
import com.stefan.game.states.PlayState;
import com.stefan.game.util.KeyHandler;
import com.stefan.game.util.MouseHandler;
import com.stefan.game.util.Vector2f;

import java.awt.*;
import java.util.Vector;

public class Player extends Entity{

    public GameState gs;
    public static int scor=0;

    public Font font;

    protected int contorAttack=21;
    protected int contorjump=50;

    public Player(Sprite sprite, Vector2f origin, int size, int life,int whichPlayState) {
        super(sprite, origin, size);
        this.life=life;
        this.whichPlayState=whichPlayState;
        /*acc=2f;
        maxSpeed=3f;*/

        bounds.setWidth(50);
        bounds.setHeight(60);
        bounds.setxOffset(4);
        bounds.setyOffset(5);
    }


    private boolean isInAir=false;

    public void move(){

        //gravitatie
        if(dy>2*maxSpeed){
            dy=2*maxSpeed;
        }
        else{
            dy+=fallSpeed;
        }

        //up
        if(up && solid && contorjump==0){
            dy-=jump_height;
            solid=false;
            contorjump=1;
        }

        //left
        if(left){
            dx-=acc;
            if(dx<-maxSpeed){
                dx=-maxSpeed;
            }
        }
        else{
            if(dx<0){
                dx+=deacc;
                if(dx>0){
                    dx=0;
                }
            }
        }

        //right
        if(right){
            dx+=acc;
            if(dx>maxSpeed){
                dx=maxSpeed;
            }
        }
        else{
            if(dx>0){
                dx-=deacc;
                if(dx<0){
                    dx=0;
                }
            }
        }

    }

    public boolean getAttack(){
        return attack;
    }

    public int getContorAttack(){
        return contorAttack;
    }

    public void setContorAttack(int i){
        contorAttack=i;
    }

    public int getWichPlayState()
    {
        return whichPlayState;
    }

    public void update(Vector<Enemy> enemy){
        super.update();

        //folosim o variabila contorAttack pentru a limita cat de repede poate ataca protagonistul
        if(contorAttack==21){
            contorAttack=0;
        }
        if(contorAttack!=0)
            contorAttack++;

        if(!fallen) {
            move();
            //folosim o variabila contorjump pentru a lasa player ul sa sara numai dupa ce acesta a ajuns din nou pe solid
            if(contorjump==50){
                contorjump=0;
            }
            if(contorjump>0){
                contorjump++;
            }
            if (!tc.collisionTile(dx, 0)) {
                //whichPlayState o variabila care retine 1 sau 2 ce reprezinta play-state ul in care ne aflam, PlayState, respectiv Play2State
                if(this.whichPlayState==1) {
                    PlayState.map.x += dx;
                }
                else if(this.whichPlayState==2){
                    Play2State.map.x+=dx;
                }
                pos.x += dx;
            }
            if (!tc.collisionTile(0, dy)) {
                //whichPlayState o variabila care retine 1 sau 2 ce reprezinta play-state ul in care ne aflam, PlayState, respectiv Play2State
                if(this.whichPlayState==1) {
                    PlayState.map.y += dy;
                }
                else if(this.whichPlayState==2) {
                    Play2State.map.y+=dy;
                    update();
                }
                pos.y += dy;
            } else if (tc.collisionTile(0, dy)) {
                solid = true;
            }
        }
        else{
            if(ani.hasPlayedOnce()){
                isAlive=false;
                dx=0;
                dy=0;
                fallen=false;
            }
        }
    }

    public int getScor(){
        return scor;
    }

    public void setScor(int i){
        scor=i;
    }

    public void incrementScor(int i){
        scor+=i;
    }

    @Override
    public void render(Graphics2D g) {
       // g.setColor(Color.blue);
        //g.drawRect((int)(pos.getWorldVar().x+bounds.getXOffset()),(int)(pos.getWorldVar().y+bounds.getYOffset()),(int)bounds.getWidth(),(int)bounds.getHeight());

        if(attack){
            //g.setColor(Color.red);
            //g.drawRect((int)(hitBounds.getPos().getWorldVar().x+hitBounds.getXOffset()),(int)(hitBounds.getPos().getWorldVar().y+hitBounds.getYOffset()),(int)hitBounds.getWidth(),(int)hitBounds.getHeight());
        }

        g.drawImage(ani.getImage(),(int)(pos.getWorldVar().x),(int)(pos.getWorldVar().y),size,size,null);
    }

    public void input(MouseHandler mouse, KeyHandler key){

        if(!fallen) {
            //vedem ce tasta apasam pentru a alege corespunzator actiunile ce trebuiesc facute
            if (key.up.down) {
                up = true;
            } else {
                up = false;
            }
            if (key.left.down) {
                //variabila lastPressed retine 1 sau 2 pentru a sti care dintre tastele stanga sau dreapta au fost apasate
                //necesar pentru a sti in ce parte ataca playerul
                left = true;
                lastPressed=1;
            } else {
                left = false;
            }
            if (key.right.down) {
                right = true;
                lastPressed=2;
            } else {
                right = false;
            }
            if (key.attack.down) {
                attack = true;
            } else {
                attack = false;
            }
            if(right && left){
                right=false;
                left=false;
            }
        }
        else{
            up=false;
            right=false;
            left=false;
            attack=false;
        }
    }
}
