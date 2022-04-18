package com.stefan.game.tiles;

import com.stefan.game.graphics.Sprite;
import com.stefan.game.tiles.blocks.NormBlock;
import com.stefan.game.util.Vector2f;
import com.stefan.game.tiles.blocks.Block;

import java.awt.*;
import java.util.ArrayList;

public class TileMapNorm extends TileMap{

    private ArrayList<Block> blocks;
    public TileMapNorm(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns){
        blocks=new ArrayList<Block>();

        String[] block=data.split(",");
        for (int i=0;i<(width*height);i++){
            int temp=Integer.parseInt(block[i].replaceAll("\\s+",""));
            if(temp!=0){
                blocks.add(new NormBlock(sprite.getSprite((int)((temp-1)%tileColumns),(int)((temp-1)/tileColumns)),new Vector2f((int)(i%width)*tileWidth,(int)(i/width)*tileHeight),tileWidth,tileHeight));
            }
        }
    }

    public void render(Graphics2D g){
        for(int i=0;i<blocks.size();i++){
            blocks.get(i).render(g);
        }
    }

}
