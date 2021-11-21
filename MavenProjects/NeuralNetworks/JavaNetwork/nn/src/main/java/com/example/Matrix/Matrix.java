package com.example.Matrix;

import java.util.Arrays;

public class Matrix {
    float[][] data;
    int rows, cols;

    public Matrix(int r, int c){
        rows = r;
        cols = c;
        
        data = new float[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = (float) (Math.random() * 2 - 1);
            }
        }
    }

    public Matrix(float[][] f){
        rows = f.length;
        cols = f[0].length;
        data = f;
    }



    public void add(float scalar){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] += scalar;
            }
        }
    }

    public void add(Matrix m){
        if(!match(m)){
            System.out.println("MATRIX SIZE MISMATCH IN ADD(M)");
            return;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] += m.data[i][j];
            }
        }
    }




    public void subtract(float scalar){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] -= scalar;
            }
        }
    }

    public static Matrix subtract(Matrix a, Matrix b){
        Matrix temp = new Matrix(a.rows, a.cols);
        if(!a.match(b)){
            System.out.println("MATRIX SIZE MISMATCH IN SUBTRACT(M1, M2)");
            return null;
        }
        for (int i = 0; i < a.rows; i++) {
            for (int j = 0; j < a.cols; j++) {
                temp.data[i][j] = a.data[i][j] - b.data[i][j];
            }
        }
        return temp;
    }




    public static Matrix transpose(Matrix m){
        float[][] temp = new float[m.cols][m.rows];
        for (int i = 0; i < temp[0].length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[j][i] = m.data[i][j];
            }
        }
        return new Matrix(temp);
    }




    public void multiply(float scalar) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] *= scalar;
            }
        }
    }

    // True matrix multiplication
    public static Matrix multiply(Matrix a, Matrix b){
        if(a.cols != b.rows){
            System.out.println("MATRIX SIZE MISMATCH IN MULTIPLY(M1, M2)");
            return null;
        }
        Matrix temp = new Matrix(a.rows, b.cols);
        for(int i = 0; i < temp.rows; i++){
            for(int j = 0; j < temp.cols; j++){
                float sum = 0;
                for (int k = 0; k < a.cols; k++) {
                    sum += a.data[i][k] * b.data[k][j];
                }
                temp.data[i][j] = sum;
            }
        }
        return temp;
    }

    public void multiply(Matrix m){
        if(!match(m)){
            System.out.println("MATRIX SIZE MISMATCH IN MULTIPLY(M)");
            return;
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] *= m.data[i][j];
            }
        }
    }



    public void sigmoid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = (float)(1/(1 + Math.exp(-data[i][j])));
            }
        }
    }

    public Matrix dsigmoid(){
        Matrix temp = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                temp.data[i][j] = (float)(data[i][j] * (1-data[i][j]));
            }
        }
        return temp;
    }



    public static Matrix fromArray(float[] f){
        return Matrix.transpose(new Matrix(new float[][]{f}));
    }

    public float[] toArray(){
        if(cols != 1){
            System.out.println("MULTIDIMENSIONAL ARRAY ERROR IN TOARRAY()");
        }
        
        float[] out = new float[rows];

        for(int i = 0; i < rows; i++){
            out[i] = data[i][0];
        }
        return out;
    }


    public float average(){
        float avg = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                avg += data[i][j];
            }
        }
        return avg/(rows * cols);
    }



    public boolean match(Matrix m){
        if(rows == m.rows && cols == m.cols){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String s = "";
        for (int i = 0; i < data.length; i++) {
            s += Arrays.toString(data[i]) + "\n";
        }
        return s;
    }
}
