package com.example.Data;

public class XORData {
    public float[] inputs;
    public float[] outputs;
    public XORData(int num1, int num2, int ans1){
        inputs = new float[]{num1, num2};
        outputs = new float[]{ans1};
    }
}
