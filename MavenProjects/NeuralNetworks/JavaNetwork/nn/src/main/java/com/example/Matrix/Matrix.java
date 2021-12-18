package com.example.Matrix;

import java.io.Serializable;
import java.util.Arrays;

public class Matrix implements Serializable {
    double[][] data;
    int rows, cols;

    public Matrix(int r, int c){
        rows = r;
        cols = c;
        
        data = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = (double) (Math.random() * 2 - 1);
            }
        }
    }

    public Matrix(double[][] f){
        rows = f.length;
        cols = f[0].length;
        data = f;
    }


    public void zero(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = 0;
            }
        }
    }

    public static Matrix average(Matrix[] arr){
        Matrix temp = new Matrix(arr[0].rows, arr[0].cols);
        temp.zero();
        for(Matrix m : arr){
            temp.add(m);
        }
        temp.multiply(1f/arr.length);
        return temp;
    }



    public void add(double scalar){
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




    public void subtract(double scalar){
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
        double[][] temp = new double[m.cols][m.rows];
        for (int i = 0; i < temp[0].length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[j][i] = m.data[i][j];
            }
        }
        return new Matrix(temp);
    }




    public void multiply(double scalar) {
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
                double sum = 0;
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



    public void activation() {
        // for (int i = 0; i < rows; i++) {
        //     for (int j = 0; j < cols; j++) {
        //         data[i][j] = (double)(1/(1 + Math.exp(-data[i][j])));
        //     }
        // }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = Math.tanh(data[i][j]);
            }
        }
    }

    public Matrix deactivation(){
        Matrix temp = new Matrix(rows, cols);
        // for (int i = 0; i < rows; i++) {
        //     for (int j = 0; j < cols; j++) {
        //         temp.data[i][j] = (double)(data[i][j] * (1-data[i][j]));
        //     }
        // }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                temp.data[i][j] = (1/(data[i][j] * data[i][j]  +1));
            }
        }
        return temp;
    }



    public static Matrix fromArray(double[] f){
        return Matrix.transpose(new Matrix(new double[][]{f}));
    }

    public double[] toArray(){
        if(cols != 1){
            System.out.println("MULTIDIMENSIONAL ARRAY ERROR IN TOARRAY()");
        }
        
        double[] out = new double[rows];

        for(int i = 0; i < rows; i++){
            out[i] = data[i][0];
        }
        return out;
    }


    public double average(){
        double avg = 0;
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
