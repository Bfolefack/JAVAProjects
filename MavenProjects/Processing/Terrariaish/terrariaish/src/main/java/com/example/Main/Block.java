package com.example.Main;
import java.io.*;
class Block implements Serializable{
    int xPos;
    int yPos;
    String type;
    int selected;
    Block(int x, int y, String t){
        xPos = x;
        yPos = y;
        type = t;
    }
    void display(World w){
        if(selected == 1){ 
            w.sketch.fill(0, 255, 0);
            w.sketch.rect(xPos * 10, yPos * 10, 10, 10);
        }  else if(selected == 3){ 
            w.sketch.fill(255, 0, 255);
            w.sketch.rect(xPos * 10, yPos * 10, 10, 10);
        }   else if(selected == 2){ 
            w.sketch.fill(0);
            w.sketch.rect(xPos * 10, yPos * 10, 10, 10);
        } else if(type == "f"){
            w.sketch.rect(xPos * 10, yPos * 10, 10, 10);
        }
    }
}
