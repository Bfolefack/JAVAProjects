package com.example.Main;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.TreeSet;

import com.example.Terrariaish;

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
    genChunks();
    player.update();
    player.display(this);
    sketch.translate(-player.pos.x, -player.pos.y);
    for(int i = 0; i < chunks.length; i++){
      for(int j = 0; j < chunks[0].length; j++){
        chunks[i][j].display(this);
        // System.out.println(i + "," + j);
      }
    }
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
        chunks[i][j] = new Chunk(64, offX + i, offY + j, this);
      }
    }
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
