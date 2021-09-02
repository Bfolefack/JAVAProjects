package com.example;

import com.example.Network.*;
import com.example.Data.*;
import org.ejml.All;

public class NetworkLibrary{
  public float[] a;
  public float[] outputs;
  public float[] targets;
  public XORData[] data;
  public static void main(String[] args){
    Network nn = new Network(2, 4, 1);
    XORData[] data = new XORData[]{
      new XORData(1, 1, 0f),
      new XORData(1, 0, 0f),
      new XORData(0, 1, 0f),
      new XORData(0, 0, 0f)
    };
    for(int i = 0; i < 100000; i++){
      int a = (int) Math.floor(Math.random() * 4);
      XORData d = data[a];
      System.out.println(nn.train(d.inputs, d.ans));
      //n.train(new float[]{1}, new float[]{0});
    }

    nn.print();

    System.out.println(nn.guess(new float[]{1, 1})[0]);
    System.out.println(nn.guess(new float[]{1, 0})[0]);
    System.out.println(nn.guess(new float[]{0, 1})[0]);
    System.out.println(nn.guess(new float[]{0, 0})[0]);
  }
}