package com.stefan.game.entity;

import com.stefan.game.graphics.Animation;
import com.stefan.game.graphics.Sprite;
import com.stefan.game.util.AABB;
import com.stefan.game.util.TileCollision;
import com.stefan.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    //indexul randului din sprite sheet potrivit miscarilor
    protected final int RIGHT=0;
    protected final int LEFT=1;
    protected final int UP=2;
    protected final int FALLEN=3;
    protected final int ATTACK_RIGHT=4;
    protected final int ATTACK_LEFT=5;

    protected Animation ani;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    protected int currentAniamtion;

    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected boolean attack;
    protected boolean fallen;

    //protected int attackSpeed;
    //protected int attackDuration;

    protected int lastPressed;

    //1--PlayState  2--Play2State
    protected int whichPlayState=0;

    protected int life;
    protected int contorLife=200;

    protected float dx;
    protected float dy;

    protected float maxSpeed=4f;
    protected float acc=3f;
    protected float deacc=0.3f;

    protected float fallSpeed=0.4f;
    //protected float jumpSpeed=4f;

    protected boolean isAlive=true;

    protected boolean solid=true;
    protected float jump_height=20f;

    public AABB hitBounds;
    protected AABB bounds;

    protected TileCollision tc;

    public Entity(Sprite sprite, Vector2f origin,int size){
        this.sprite=sprite;
        pos=origin;
        this.size=size;

        bounds=new AABB(origin,size,size);
        hitBounds=new AABB(origin,size,size);
        hitBounds.setxOffset(size/2);

        ani=new Animation();
        setAnimation(RIGHT,sprite.getSpriteArray(RIGHT),10);

        tc=new TileCollision(this);
    }

    public void setSprite(Sprite sprite){
        this.sprite=sprite;
    }

    public void setFallen(boolean i){
        fallen=i;
    }

    public void setSize(int i){
        size=i;
    }

    public void setMaxSpeed(float f){
        maxSpeed=f;
    }

    public void setAcc(float f){
        acc=f;
    }

    public void setDeacc(float f){
        deacc=f;
    }

    public AABB getBounds(){
        return bounds;
    }

    public int getSize(){
        return size;
    }

    public Animation getAnimation(){
        return ani;
    }

    public Vector2f getPos(){
        return pos;
    }

    public int getContorLife(){
        return this.contorLife;
    }

    public void setContorLife(int i){
        this.contorLife=i;
    }

    public int getLife(){
        return this.life;
    }

    public void decrementLife(){
        this.life--;
    }

    public void incrementContorLife(){
        this.contorLife++;
    }



    public void setAnimation(int i, BufferedImage[] frames,int delay){
        currentAniamtion=i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    public void animate(){
        if(up){
            if(currentAniamtion!=UP || ani.getDelay()==-1){
                //preia un sir de cadre din sprite sheet pentru a crea animatia
                setAnimation(UP,sprite.getSpriteArray(UP),5);
            }
        }
        else if(right){
            if(currentAniamtion!=RIGHT || ani.getDelay()==-1){
                //preia un sir de cadre din sprite sheet pentru a crea animatia
                setAnimation(RIGHT,sprite.getSpriteArray(RIGHT),5);
            }
        }
        else if(left){
            if(currentAniamtion!=LEFT || ani.getDelay()==-1){
                //preia un sir de cadre din sprite sheet pentru a crea animatia
                setAnimation(LEFT,sprite.getSpriteArray(LEFT),5);
            }
        }
        else if(fallen){
            if(currentAniamtion!=FALLEN || ani.getDelay()==-1){
                //preia un sir de cadre din sprite sheet pentru a crea animatia
                setAnimation(FALLEN,sprite.getSpriteArray(FALLEN),15);
            }
        }
        else if(attack && lastPressed==2){
            if(currentAniamtion!=ATTACK_RIGHT || ani.getDelay()==-1){
                //preia un sir de cadre din sprite sheet pentru a crea animatia
                setAnimation(ATTACK_RIGHT,sprite.getSpriteArray(ATTACK_RIGHT),5);
            }
        }
        else if(attack && lastPressed==1){
            if(currentAniamtion!=ATTACK_LEFT || ani.getDelay()==-1){
                //preia un sir de cadre din sprite sheet pentru a crea animatia
                setAnimation(ATTACK_LEFT,sprite.getSpriteArray(ATTACK_LEFT),5);
            }
        }
        else{
            //idle animation
            setAnimation(currentAniamtion,sprite.getSpriteArray(currentAniamtion),-1);
        }
    }

    private void setHitBoxDirection(){
        if(up){
            hitBounds.setyOffset(-size/2);
            hitBounds.setxOffset(0);
        }
        else if(down){
            hitBounds.setyOffset(size/2);
            hitBounds.setxOffset(0);
        }
        else if(left){
            hitBounds.setyOffset(0);
            hitBounds.setxOffset(-size/2);
        }
        else if(right){
            hitBounds.setyOffset(0);
            hitBounds.setxOffset(size/2);
        }
    }

    public boolean getIsAlive(){
        return isAlive;
    }

    public void setIsAlive(boolean ceva){
        isAlive=ceva;
    }

    public void update(){
        animate();
        setHitBoxDirection();
        ani.update();
    }

    public abstract void render(Graphics2D g);

}
