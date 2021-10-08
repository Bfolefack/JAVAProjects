package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import processing.core.PApplet;

import com.example.Main.*;

public class Terrariaish extends PApplet implements Serializable{
	public Set<Character> keys;
	private World w;

    public void settings() {
		size(1280, 640);
	}

	public void setup(){
		keys = new TreeSet<Character>();
		// try{
		// 	openSave("Saves/temp.tws");
		// } catch (Exception e){
		// 	w = new World(this);
		// 	println(e);
		// 	println("ur dum");
		// }
		textSize(20);
		noStroke();
		noiseSeed(123456789);
		w = new World(this);
	}

	public void draw(){
		background(0, 200, 255);
		// System.out.println(w.player.pos);
		// w.player.display();
		w.display();
		w.sketch = this;
	}
	public static void main(String[] passedArgs) {
		String[] processingArgs = {"MySketch"};
		Terrariaish mySketch = new Terrariaish();
		PApplet.runSketch(processingArgs, mySketch);
    }

	public void keyPressed(){
		keys.add(key);
		if(key == '\\'){
			try{
				createSave(w);
			} catch (Exception e){
				println(e);
				e.printStackTrace();
				println("ur even more dum");
			}
		} else if(key == ']'){
			try{
				openSave("Saves/temp.tws");
			} catch (Exception e){
				System.out.println(e);
				e.printStackTrace();
			}
		}
	}
	
	public void keyReleased(){
		keys.remove(key);
	}
	
	public void openSave(String s) throws IOException, ClassNotFoundException{
		File f = new File("Saves/temp.tws");
		FileInputStream fileInputStream = new FileInputStream(f);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		w = (World) objectInputStream.readObject();
		objectInputStream.close(); 
		w.onLoad(this);
	}
	
	public void createSave(World world) throws IOException, ClassNotFoundException {
		File f = new File("Saves/temp.tws");
		try {
			System.out.println(f.getAbsolutePath());
			if(f.createNewFile()){
				System.out.println("Created save: " + f);
			} else {
				System.out.println("Saving game: " + f);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		FileOutputStream fileOutputStream = new FileOutputStream(f);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(world);
		objectOutputStream.flush();
		objectOutputStream.close();
		}
}
