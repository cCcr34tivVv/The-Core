package com.stefan.game.tiles;

import com.stefan.game.graphics.Sprite;
import com.stefan.game.tiles.blocks.*;
import com.stefan.game.util.Vector2f;

import java.awt.*;
import java.util.HashMap;

public class TileMapObj extends TileMap{

    public static HashMap<String,Block> tmo_blocks;

    public TileMapObj(String data, Sprite sprite, int width, int height, int tileWidth, int tileHeight, int tileColumns){
       Block tempBlock;
       tmo_blocks=new HashMap<String,Block>();

        String[] block=data.split(",");
        for (int i=0;i<(width*height);i++){
            int temp=Integer.parseInt(block[i].replaceAll("\\s+",""));
            if(temp!=0){
                //indexul 77 - tepi
                if(temp==77){
                    tempBlock=new SpikeBlock(sprite.getSprite((int)((temp-1)%tileColumns),(int)((temp-1)/tileColumns)),new Vector2f((int)(i%width)*tileWidth,(int)(i/width)*tileHeight),tileWidth,tileHeight);
                }//indexul 357 - trapa
                else if(temp==357){
                    tempBlock=new TrapBlock(sprite.getSprite((int)((temp-1)%tileColumns),(int)((temp-1)/tileColumns)),new Vector2f((int)(i%width)*tileWidth,(int)(i/width)*tileHeight),tileWidth,tileHeight);
                }//indexul 103 - gaura
                else if(temp==103){
                    tempBlock=new HoleBlock(sprite.getSprite((int)((temp-1)%tileColumns),(int)((temp-1)/tileColumns)),new Vector2f((int)(i%width)*tileWidth,(int)(i/width)*tileHeight),tileWidth,tileHeight);
                }//restul - solide
                else{
                    tempBlock=new ObjBlock(sprite.getSprite((int)((temp-1)%tileColumns),(int)((temp-1)/tileColumns)),new Vector2f((int)(i%width)*tileWidth,(int)(i/width)*tileHeight),tileWidth,tileHeight);
                }
                tmo_blocks.put(String.valueOf((int)(i%width))+","+String.valueOf((int)(i/width)),tempBlock);
            }
        }
    }

    public void render(Graphics2D g){
        for(Block block:tmo_blocks.values()){
            block.render(g);
        }
    }

}
