package com.stefan.game.tiles;

import com.stefan.game.graphics.Sprite;
import java.util.ArrayList;
import java.awt.Graphics2D;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class TileManager {

    public static ArrayList<TileMap> tm;

    public TileManager(){
        tm=new ArrayList<TileMap>();
    }

    public TileManager(String path){
        tm=new ArrayList<TileMap>();
        addTileMap(path,64,64);
    }

    private void addTileMap(String path,int blockWidth, int blockHeight){
        String imagePath;

        int width=0;
        int height=0;
        int tileWidth;
        int tileHeight;
        int tileCount;
        int tileColumns;
        int layers=0;
        Sprite sprite;

        // 10 numar maxim de layere
        String[] data=new String[10];

        try{
            DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=builderFactory.newDocumentBuilder();
            Document doc=builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list=doc.getElementsByTagName("tileset");
            Node node=list.item(0);
            Element eElement=(Element) node;

            //imi iau fiecare atribut din fiserul xml
            imagePath=eElement.getAttribute("name");
            tileWidth=Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight=Integer.parseInt(eElement.getAttribute("tileheight"));
            tileCount=Integer.parseInt(eElement.getAttribute("tilecount"));
            tileColumns=Integer.parseInt(eElement.getAttribute("columns"));
            sprite=new Sprite("tile/"+imagePath+".png",tileWidth,tileHeight);

            list=doc.getElementsByTagName("layer");
            layers=list.getLength();

            for(int i=0;i<layers;i++){
                node=list.item(i);
                eElement=(Element) node;
                if(i<=0){
                    width=Integer.parseInt(eElement.getAttribute("width"));
                    height=Integer.parseInt(eElement.getAttribute("height"));
                }

                data[i]=eElement.getElementsByTagName("data").item(0).getTextContent();

                //index 1 este indexul layer-ului solid
                if(i!=1){
                    //asa ca restul vor fi normale
                    tm.add(new TileMapNorm( data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                }
                else{
                    //celelalte sunt solide
                    tm.add(new TileMapObj(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                }
            }
        }
        catch (Exception e){
            System.out.println("ERROR - TILEMANAGER: can not read tilemap!");
        }
    }

    public void render(Graphics2D g){
        for(int i=0;i<tm.size();i++){
            tm.get(i).render(g);
        }
    }

}
