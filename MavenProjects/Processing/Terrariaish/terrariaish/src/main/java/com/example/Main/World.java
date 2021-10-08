package com.example.Main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

import com.example.Terrariaish;

import processing.core.PVector;

import java.io.Serializable;

public class World implements Serializable {
  transient public Terrariaish sketch;
  
  transient private Chunk[][] chunks;

  private Set<Chunk> modifiedChunks;

  private int chunkX;
  private int chunkY;

  public Player player;
  public World(Terrariaish sk) {
    sketch = sk;
    player = new Player(this);
    chunks = new Chunk[sketch.width/(64 * 10) + 1][sketch.height/(64 * 10) + 2];
    System.out.println(chunks.length + "," + chunks[0].length );
    for (int i = 0; i < chunks.length; i++){
      for (int j = 0; j < chunks[0].length; j++){
        chunks[i][j] = new Chunk(64, i, j, this);
      }
    }
    modifiedChunks = new TreeSet<Chunk>();
  }

  public void display(){
    if(sketch.keys.contains('p')){
      System.out.println("stop");
    }
    genChunks();
    player.update();
    sketch.translate(-player.pos.x + sketch.width/2, -player.pos.y + sketch.height/2);
    player.display(this);
    for(int i = 0; i < chunks.length; i++){
      for(int j = 0; j < chunks[0].length; j++){
        chunks[i][j].display(this);
        // System.out.println(i + "," + j);
      }
    }
    genChunks();
    // getPlayerChunk();
  }

  public void genChunks(){
    int offX = (int) player.pos.x/(64 * 10);
    int offY = (int) player.pos.y/(64 * 10);
    for (int i = 0; i < chunks.length; i++){
      for (int j = 0; j < chunks[0].length; j++){
        for(Chunk c : modifiedChunks){
          if(c.toString().equals(i + "," + j)){
            chunks[i][j] = c;
            continue;
          }
        }
        chunks[i][j] = new Chunk(64, offX + i - 1, offY + j - 1, this);
      }
    }
  }

  public float getCeiling(){
    PVector p = getPlayerChunk();
    int chunkX = (int)p.x;
    int chunkY = (int)p.y;
    int blockX = (int)(Math.abs(player.pos.x/10f) % 64);
    int blockY = (int)(Math.abs(player.pos.y/10f) % 64);
    int floor;
    int[] c = getCell(chunkX, chunkY, blockX, blockY - 2);
    int i;
    int j = 0;
    for (i = c[1]; i >= 0; i--){
      for(j = 63 ; j >= 0; j--){
        if(i == chunkY && j > c[3]){
            j = c[3];
        }
        if(!chunks[chunkX][i].blocks[blockX][j].type.equals(chunks[chunkX][chunkY].blocks[blockX][c[3]].type)){
          chunks[chunkX][i].blocks[blockX][j].selected = 1;
          floor = ((chunkY - i) * 64 + (blockY - j)) * 10 - 10;
          return floor;
        }
      }
    }
    return  Integer.MAX_VALUE;
  }

  public int[] getCeilingChunk(){
    PVector p = getPlayerChunk();
    int chunkX = (int)p.x;
    int chunkY = (int)p.y;
    int blockX = (int)(Math.abs(player.pos.x/10f) % 64);
    int blockY = (int)(Math.abs(player.pos.y/10f) % 64);
    int chunkOff;
    int floor;
    if(blockY > 0){
      blockY-= 1;
    } else if(chunkY > 0){
      chunkY -= 1;
      blockY = 63;
    }
    int i;
    int j = 0;
    for (i = chunkY; i >= 0; i--){
      for(j = 63 ; j >= 0; j--){
        if(i == chunkY && j > blockY - 1){
          if(blockY != 0){
            j = blockY - 1;
          } else if (i > 0){
            i -= 1;
          }
        }
        if(!chunks[chunkX][i].blocks[blockX][j].type.equals(chunks[chunkX][chunkY].blocks[blockX][blockY].type)){
          chunkOff = i - chunkY;
          chunks[chunkX][i].blocks[blockX][j].selected = 1;
          floor = (chunkOff * 64 + j - blockX) * 10;
          return new int[] {chunkX, i, blockX, j};
        }
      }
    }
    return null;
  }

  public float getFloor(){
    // PVector p = getPlayerChunk();
    int[] c = getCeilingChunk();
    int chunkX;
    int chunkY;
    int blockX;
    int blockY;
    if(c != null){
      chunkX = c[0];
      chunkY = c[1];
      blockX = c[2];
      blockY = c[3];
      PVector p = getPlayerChunk();
      int chunkX2 = (int)p.x;
      int chunkY2 = (int)p.y;
      int blockX2 = (int)(Math.abs(player.pos.x/10f) % 64);
      int blockY2 = (int)(Math.abs(player.pos.y/10f) % 64);
      if(chunks[chunkX2][chunkY2].blocks[blockX2][blockY2].type.equals("f")){
        return (int)player.pos.y + ((chunkY - chunkY2) * 64 + (blockY - blockY2)) * 10 + 10;
      }
    } else {
      // System.out.println("e");
      PVector p = getPlayerChunk();
      chunkX = (int)p.x;
      chunkY = 0;
      blockX = (int)(Math.abs(player.pos.x/10f) % 64);
      blockY = 0;
      int chunkX2 = (int)p.x;
      int chunkY2 = (int)p.y;
      int blockX2 = (int)(Math.abs(player.pos.x/10f) % 64);
      int blockY2 = (int)(Math.abs(player.pos.y/10f) % 64);
      if(chunks[chunkX2][chunkY2].blocks[blockX2][blockY2].type.equals("f")){
        return (int)player.pos.y + ((chunkY - chunkY2) * 64 + (blockY - blockY2)) * 10 + 10;
      }
    }
    int chunkOff;
    int floor;
    for (int i = chunkY; i < chunks[0].length; i++){
      for(int j = 0 ; j < 64; j++){
        if(i == chunkY && j < blockY){
          if(blockY < 63){
            j = blockY + 1;
          } else if(chunkY < chunks[0].length - 1) {
            chunkY++;
          }
        }
        if(chunks[chunkX][i].blocks[blockX][j].type == "f"){
          chunkOff = i - chunkY;
          chunks[chunkX][i].blocks[blockX][j].selected =2;
          floor = (int)player.pos.y + (chunkOff * 64 + j - blockY) * 10;
          return floor;
        }
      }
    }
    return Integer.MAX_VALUE;
  }

  public boolean getLeftWall(){
    PVector p = getPlayerChunk();
    // System.out.println(p);
    int chunkX = (int)p.x;
    int chunkY = (int)p.y;
    int blockX = (int)(Math.abs(player.pos.x/10f) % 64);
    int blockY = (int)(Math.abs(player.pos.y/10f) % 64);
    int[] c = getCell(chunkX, chunkY, blockX - 1, blockY - 2);
    int[] b = getCell(chunkX, chunkY, blockX - 1, blockY - 3);
    chunks[c[0]][c[1]].blocks[c[2]][c[3]].selected = 1;
    chunks[b[0]][b[1]].blocks[b[2]][b[3]].selected = 1;
    if(!chunks[c[0]][c[1]].blocks[c[2]][c[3]].type.equals("") || !chunks[b[0]][b[1]].blocks[b[2]][b[3]].type.equals(""))
      return true;
    return false;
  }

  public int[] getCell(int chunkX, int chunkY, int blockX, int blockY){
    if(blockX < 0){
      if(chunkX > 0){
        chunkX--;
        blockX = 63;
      }
    } else if(blockX > 63){
      if(chunkX < chunks.length){
        chunkX++;
        blockX = 0;
      }
    }

    if(blockY < 0){
      if(chunkY > 0){
        chunkY--;
        blockY = 63;
      }
    } else if(blockY > 63){
      if(chunkY < chunks[0].length){
        chunkY++;
        blockY = 0;
      }
    }

    return new int[] {chunkX, chunkY, blockX, blockY};
  }

  public boolean getRightWall(){
    PVector p = getPlayerChunk();
    // System.out.println(p);
    int chunkX = (int)p.x;
    int chunkY = (int)p.y;
    int blockX = (int)(Math.abs(player.pos.x/10f) % 64);
    int blockY = (int)(Math.abs(player.pos.y/10f) % 64);
    int[] c = getCell(chunkX, chunkY, blockX + 1, blockY - 2);
    int[] b = getCell(chunkX, chunkY, blockX + 1, blockY - 3);
    chunks[c[0]][c[1]].blocks[c[2]][c[3]].selected = 1;
    chunks[b[0]][b[1]].blocks[b[2]][b[3]].selected = 1;
    if(!chunks[c[0]][c[1]].blocks[c[2]][c[3]].type.equals("") || !chunks[b[0]][b[1]].blocks[b[2]][b[3]].type.equals(""))
      return true;
    return false;
  }

  public PVector getPlayerChunk(){
    for(int i = 0; i < chunks.length; i++){
      for(int j = 0; j < chunks[0].length; j++){
        if(chunks[i][j].chunkXPos == (int) ((player.pos.x)/(64 * 10))){
          if(chunks[i][j].chunkYPos == (int) ((player.pos.y)/(64 * 10))){
            chunks[i][j].selected = true;
            chunks[i][j].blocks[(int)(Math.abs(player.pos.x/10f) % 64)][(int)(Math.abs((player.pos.y/10f)) % 64)].selected = 3;
            return new PVector(i, j);
          }
        }
      }
    }
    System.out.println("Cauldron");
    for(int i = 0; i < chunks.length; i++){
      for(int j = 0; j < chunks[0].length; j++){
        System.out.print(chunks[i][j] + ", ");
      }
      System.out.print("\n");
    }
    System.out.println(player.pos.x+ ", " + player.pos.y);
    System.out.println((int) player.pos.x/(64 * 10) + ", " + (int) player.pos.y/(64 * 10));
    try{
      int i = 1/0;
    } catch (Exception e){
      e.printStackTrace();
    }
    return new PVector(1, 0);
  }

  public void setPlayer(Player p){
    player = p;
  }

  public void onLoad(Terrariaish sk) {
    sketch = sk;
    chunks = new Chunk[sketch.width/(64 * 10) + 1][sketch.height/(64 * 10) + 2];
    genChunks();
    player.world = this;
  }
}
