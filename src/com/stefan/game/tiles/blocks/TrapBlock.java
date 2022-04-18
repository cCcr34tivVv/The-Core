package com.stefan.game.tiles.blocks;

import com.stefan.game.util.AABB;
import com.stefan.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TrapBlock extends Block{
    public TrapBlock(BufferedImage img, Vector2f pos, int w, int h) {
        super(img, pos, w, h);
    }

    @Override
    public boolean update(AABB p) {
        //System.out.println("Trap!");
        return false;
    }

    @Override
    public boolean isInside(AABB p) {
        if(p.getPos().x+p.getXOffset()<pos.x)
            return false;

        if(p.getPos().y+p.getYOffset()<pos.y)
            return false;

        if(w+pos.x<p.getWidth()+(p.getPos().x+p.getXOffset()))
            return false;

        if(h+pos.y<p.getHeight()+(p.getPos().y+p.getYOffset()))
            return false;

        return true;
    }

    public void render(Graphics2D g){
        super.render(g);
        //g.setColor(Color.yellow);
        //g.drawRect((int) pos.getWorldVar().x,(int) pos.getWorldVar().y,w,h);
    }
}
