package com.example.Main;
import java.io.Serializable;
import java.util.ArrayList;

class Chunk implements Serializable{
  int chunkSize;
  int chunkXPos;
  int chunkYPos;
  transient Block[][] blocks;
  ArrayList<Block> alteredBlocks;
  public boolean selected;
  Chunk(int s,int xPos, int yPos, World w){
    chunkSize = s;
    chunkXPos = xPos;
    chunkYPos = yPos;
    blocks = new Block[chunkSize][chunkSize];
    for(int i = chunkXPos * chunkSize; i < chunkSize + chunkXPos * chunkSize; i++){
      for(int j = chunkYPos * chunkSize; j < chunkSize + chunkYPos * chunkSize; j++){
        float x = w.sketch.noise(i * .03f, j * .03f);
        String t;
        if (w.sketch.noise(i * 0.005f) * 200 > j) {
          x = 0.5f;
        }
        if (x < 0.57 && x > 0.43) {
          t = "";
        } else {
          t = "f";
        }
        blocks[i - chunkXPos * chunkSize][j - chunkYPos * chunkSize] = new Block(i - chunkXPos * chunkSize, j - chunkYPos * chunkSize, t);
      }
    }
  }

  public void display(World w){
    w.sketch.pushMatrix();
    // System.out.println(chunkXPos * 64 * 10 + "," + chunkYPos * 64 * 10);
    w.sketch.translate(chunkXPos * chunkSize * 10, chunkYPos * chunkSize * 10);
    for(int i = 0; i < chunkSize; i++){
      for(int j = 0; j < chunkSize; j++){
        w.sketch.fill(125);
        // w.sketch.fill(255f * (chunkXPos % 3)/3f, 0, 255f * (chunkYPos % 3)/3f);
        blocks[i][j].display(w); 
      }
    }
    w.sketch.fill(125);
    w.sketch.fill(255);
    w.sketch.textSize(100);
    w.sketch.text(chunkXPos + "," + chunkYPos, 0, 100);
    w.sketch.popMatrix();
  }

  public String toString(){
    return chunkXPos + "," + chunkYPos;
  }
}
