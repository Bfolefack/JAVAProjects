package com.example.Main;
import java.io.*;
class Block implements Serializable{
    int xPos;
    int yPos;
    String type;
    Block(int x, int y, String t){
        xPos = x;
        yPos = y;
        type = t;
    }
    void display(World w){
        if(type == "f"){
            w.sketch.rect(xPos * 10, yPos * 10, 10, 10);
        }
    }
}
