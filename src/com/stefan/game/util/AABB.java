package com.stefan.game.util;

//Axis-Aligned Bounding Boxes -- reprezinta cel mai rapid algoritm de determinare daca 2 entitati ale jocului se suprapun sau nu.
//Aceasta presupune "acoperirea" entitatilor jocului intr-o caseta care nu este rotita (deci aliniata la axa) si verificarea pozitiilor acestor casete in spatiul de coordonate 3D
// pentru a vedea daca acestea se suprapun

import com.stefan.game.entity.Entity;
import com.stefan.game.tiles.TileMapObj;
import com.stefan.game.tiles.blocks.Block;
import com.stefan.game.tiles.blocks.HoleBlock;
import com.stefan.game.tiles.blocks.SpikeBlock;
import com.stefan.game.tiles.blocks.TrapBlock;

import java.awt.*;

public class AABB {

    private Vector2f pos;
    private float xOffset=0;
    private float yOffset=0;
    private float width;
    private float height;
    private float radius;
    private int size;
    private Entity entity;

    public AABB(Vector2f pos,int width,int height){
        this.pos=pos;
        this.width=width;
        this.height=height;

        size=Math.max(width,height);
    }

    public AABB(Vector2f pos, int radius){
        this.pos=pos;
        this.radius=radius;
        this.entity=entity;

        size=radius;
    }

    public Vector2f getPos(){
        return pos;
    }

    public float getRadius(){
        return radius;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }

    public void setBox(Vector2f pos,int width,int height){
        this.pos=pos;
        this.width=width;
        this.height=height;

        size=Math.max(width,height);
    }

    public void setCircle(Vector2f pos,int radius){
        this.pos=pos;
        this.radius=radius;

        size=radius;
    }

    public void setWidth(float f){
        width=f;
    }

    public void setHeight(float f){
        height=f;
    }

    public void setxOffset(float f){
        xOffset=f;
    }

    public void setyOffset(float f){
        yOffset=f;
    }

    public float getXOffset(){
        return xOffset;
    }

    public float getYOffset(){
        return yOffset;
    }

    public boolean collides(AABB bBox){
        //mijlocul pozitiei
        float ax=((pos.getWorldVar().x+(xOffset))+(this.width/2));
        float ay=((pos.getWorldVar().y+(yOffset))+(this.height/2));
        float bx=((bBox.getPos().getWorldVar().x+(bBox.getXOffset()))+(bBox.getWidth()/2));
        float by=((bBox.getPos().getWorldVar().y+(bBox.getYOffset()))+(bBox.getHeight()/2));

        //verificam daca se cele 2 entitati se intersecteaza
        if(Math.abs(ax-bx)<(this.width/2)+(bBox.getWidth()/2)){
            if(Math.abs(ay-by)<(this.height/2)+(bBox.getHeight()/2)){
                return true;
            }
        }
        return false;
    }

    //clasa pentru a verifica coliziunea cu un cerc
    public boolean colCircleBox(AABB aBox){
        float dx=Math.max(aBox.getPos().getWorldVar().x+aBox.getXOffset(),Math.min(pos.getWorldVar().x+(radius/2),aBox.getPos().getWorldVar().x+aBox.getXOffset()+aBox.getWidth()));
        float dy=Math.max(aBox.getPos().getWorldVar().y+aBox.getYOffset(),Math.min(pos.getWorldVar().y+(radius/2),aBox.getPos().getWorldVar().y+aBox.getYOffset()+aBox.getHeight()));

        dx=pos.getWorldVar().x+(radius/2)-dx;
        dy=pos.getWorldVar().y+(radius/2)-dy;

        if(Math.sqrt(dx*dx+dy*dy)<radius/2){
            return true;
        }

        return false;
    }

}
