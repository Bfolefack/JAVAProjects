// Will eventually be replaced by EJML
class Matrix {
  private float[][] matrix;
  private int rows, cols;
  float e = 2.7182818284f;


  Matrix(int w_, int h_) {
    rows = w_;
    cols = h_;
    matrix = new float[rows][cols];
    for (int i = 0; i < rows; i++) {
      float[] temp = new float[cols];
      for (int j = 0; j < cols; j++) {
        temp[j] = 0f;
      }
      matrix[i] = temp;
    }
  }

  //*******************************************************
  //FUNCTIONS FOR SETTING VALUES OF MATRICIES
  
  public void setVal(int row, int col, float f) {
    matrix[row][col] = f;
  }

  public void setVals(float[][] f) {
    if (f.length == matrix.length && f[0].length == matrix[0].length) {
      matrix = f.clone();
    } else { 
      System.out.println("ERROR: LENGTH MISMATCH IN SETVALS()");
    }
  }

  public void setVals(Matrix m) {
      setVals(m.getVals());
  }

  //*******************************************************
  //FUNCTIONS FOR RETRIEVING VALUES FROM MATRICIES

  public float getVal(int row, int col){
    return matrix[row][col];
  }

  public float[][] getVals() {
    float[][] result = new float[rows][cols];
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        result[i][j] = matrix[i][j];
      }
    }
    return result;
  }

  //*******************************************************
  //FUNCTIONS FOR COPYING MATRICIES

  public static Matrix duplicate(Matrix m){
    Matrix result = new Matrix(m.rows, m.cols);
    for(int i = 0; i < m.rows; i++){
      for(int j = 0; j < m.cols; j++){
        result.setVal(i, j, m.getVal(i, j));
      }
    }
    return result;
  }

  public Matrix duplicate(){
    Matrix result = new Matrix(rows, cols);
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        result.setVal(i, j, getVal(i, j));
      }
    }
    return result;
  }

  //*******************************************************
  //FUNCTIONS FOR PRINTING MATRICIES

  String parseString() {
    String s = "";
    for (int i = 0; i < matrix.length; i++) {
      s += "[ ";
      for (int j = 0; j < matrix[i].length; j++) {
        s += matrix[i][j];
        if (j < matrix[i].length -1) {
          s += ", ";
        }
      }
      s += " ] \n";
    }
    return s;
  }

  void print() {
    System.out.println(parseString());
  }

  static void print(Matrix m) {
    System.out.println(m.parseString());
  }


  //*******************************************************
  //FUNCTIONS FOR RANDOMIZATION OF MATRICIES

  public void randomize() {
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        setVal(i, j, (float) (Math.random() - 0.5));
      }
    }
  }

  static Matrix randomize(int rows, int cols) {
    Matrix result = new Matrix(rows, cols);
    for(int i = 0; i < result.rows; i++){
      for(int j = 0; j < result.cols; j++){
        result.setVal(i, j, (float) (Math.random() - 0.5));
      }
    }
    return result;
  }

  //*******************************************************
  //FUNCTIONS FOR ADDITION OF MATRICIES
  public void sum(float f) {
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        matrix[i][j] += f;
      }
    }
  }

  public static Matrix sum(Matrix m, float f) {
    Matrix result = new Matrix(m.rows, m.cols);
    for(int i = 0; i < m.rows; i++){
      for(int j = 0; j < m.cols; j++){
        result.setVal(i, j, m.getVal(i, j) + f);
      }
    }
    return result;
  }

  public void sum(Matrix m){
    if((m.rows == rows) && (m.cols == cols)){
      for(int i = 0; i < rows; i++){
        for(int j = 0; j < cols; j++){
          matrix[i][j] += m.getVal(i, j);
        }
      }
    } else  {
      System.out.println("ERROR: LENGTHE MISMATCH IN SUM: 149");
      System.exit(0);
    }
  }

  public static Matrix sum(Matrix m, Matrix n){
    Matrix result = new Matrix(m.rows, m.cols);
      if((m.rows == n.rows) && (m.cols == n.cols)){
        for(int i = 0; i < m.rows; i++){
          for(int j = 0; j < m.cols; j++){
            result.setVal(i, j, m.getVal(i, j) + n.getVal(i, j));
          }
        }
        return result;
      } else  {
        System.out.println("ERROR: LENGTHE MISMATCH IN SUM: 164");
        System.exit(0);
      }
      return null;
  }

  //*******************************************************
  //FUNCTIONS FOR SUBTRACTION OF MATRICIES
  public void diff(float f) {
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        matrix[i][j] -= f;
      }
    }
  }

  public static Matrix diff(Matrix m, float f) {
    Matrix result = new Matrix(m.rows, m.cols);
    for(int i = 0; i < m.rows; i++){
      for(int j = 0; j < m.cols; j++){
        result.setVal(i, j, m.getVal(i, j) - f);
      }
    }
    return result;
  }

  public void diff(Matrix m){
    if((m.rows == rows) && (m.cols == cols)){
      for(int i = 0; i < rows; i++){
        for(int j = 0; j < cols; j++){
          matrix[i][j] -= m.getVal(i, j);
        }
      }
    } else  {
      System.out.println("ERROR: LENGTHE MISMATCH IN DIFF: 198");
      System.exit(0);
    }
  }

  public static Matrix diff(Matrix m, Matrix n){
    Matrix result = new Matrix(m.rows, m.cols);
      if((m.rows == n.rows) && (m.cols == n.cols)){
        for(int i = 0; i < m.rows; i++){
          for(int j = 0; j < m.cols; j++){
            result.setVal(i, j, m.getVal(i, j) - n.getVal(i, j));
          }
        }
        return result;
      } else  {
        System.out.println("ERROR: LENGTHE MISMATCH IN DIFF: 213");
        System.exit(0);
      }
      return null;
  }

  //*******************************************************
  //FUNCTIONS FOR MULTIPLICATION OF MATRICIES
  public void multiply(float f) {
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < cols; j++){
        matrix[i][j] *= f;
      }
    }
  }

  public static Matrix multiply(Matrix m, float f) {
    Matrix result = new Matrix(m.rows, m.cols);
    for(int i = 0; i < m.rows; i++){
      for(int j = 0; j < m.cols; j++){
        result.setVal(i, j, m.getVal(i, j) * f);
      }
    }
    return result;
  }

  public void hadamard(Matrix m){
    if((m.rows == rows) && (m.cols == cols)){
      for(int i = 0; i < rows; i++){
        for(int j = 0; j < cols; j++){
          matrix[i][j] *= m.getVal(i, j);
        }
      }
    } else  {
      System.out.println("ERROR: LENGTHE MISMATCH IN HADAMARD: 247");
      System.exit(0);
    }
  }

  public static Matrix hadamard(Matrix m, Matrix n){
    Matrix result = new Matrix(m.rows, m.cols);
      if((m.rows == n.rows) && (m.cols == n.cols)){
        for(int i = 0; i < m.rows; i++){
          for(int j = 0; j < m.cols; j++){
            result.setVal(i, j, m.getVal(i, j) * n.getVal(i, j));
          }
        }
        return result;
      } else  {
        System.out.println("ERROR: LENGTHE MISMATCH IN HADAMARD: 262");
        System.exit(0);
      }
      return null;
  }

  public Matrix multiply(Matrix m){
    if(m.rows == cols){
      Matrix result = new Matrix(rows, m.cols);
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < m.cols; j++) {
              int sum = 0;
                for (int k = 0; k < cols; k++) {
                  sum += matrix[i][k] * m.getVal(k, j);
                }
                result.setVal(i, j, sum);
            }
        }
        return result;
    } else  {
      System.out.println("ERROR: LENGTHE MISMATCH IN MULTIPLY: 280");
      System.exit(0);
    }
    return null;
  }
  
  public static Matrix multiply(Matrix m, Matrix n){
    if(n.rows == m.cols){
      Matrix result = new Matrix(m.rows, n.cols);
        for(int i = 0; i < m.rows; i++) {
            for (int j = 0; j < n.cols; j++) {
              int sum = 0;
                for (int k = 0; k < m.cols; k++) {
                    sum += m.getVal(i, k) * n.getVal(k, j);
                }
                result.setVal(i, j, sum);
            }
        }
        return result;
    } else  {
      System.out.println("ERROR: LENGTH MISMATCH IN MULTIPLY: 298");
      System.exit(0);
    }
          return null;
  }

  //*******************************************************
  //FUNCTIONS FOR TRANSPOSITION OF MATRICIES
  static Matrix transpose(Matrix m) {
    Matrix result = new Matrix(m.cols, m.rows);
    for(int i = 0; i < m.rows; i++){
      for(int j = 0; j < m.cols; j++){
        result.setVal(j, i, m.getVal(i, j)); 
      }
    }
    return result;
  }

  //*******************************************************
  //FUNCTIONS FOR ACTIVATION OF MATRICIES
  void activation() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        matrix[i][j] = (float)(1/(1 + Math.exp(-matrix[i][j])));
        //matrix[i][j] = (float) Math.tanh(matrix[i][j]);
      }
    }
  }

  void dActivation() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        matrix[i][j] = matrix[i][j] * (1 - matrix[i][j]);
        //matrix[i][j] = 1f - (matrix[i][j] * matrix[i][j]);
      }
    }
  }

  static Matrix activation(Matrix m) {
    Matrix result = new Matrix(m.rows, m.cols);
    for (int i = 0; i < m.rows; i++) {
      for (int j = 0; j < m.cols; j++) {
        result.matrix[i][j] = (float)(1/(1 + Math.exp(-m.matrix[i][j])));
        //result.matrix[i][j] = (float) Math.tanh(m.matrix[i][j]);
      }
    }
    return result;
  }

  static Matrix dActivation(Matrix m) {
    Matrix result = new Matrix(m.rows, m.cols);
    for (int i = 0; i < m.rows; i++) {
      for (int j = 0; j < m.cols; j++) {
        result.matrix[i][j] = m.matrix[i][j] * (1 - m.matrix[i][j]);
        ///result.matrix[i][j] = (1f - (m.matrix[i][j] * m.matrix[i][j]));
      }
    }
    return result;
  }

  //*******************************************************
  //FUNCTIONS FOR ARRAY CONVERSION OF MATRICIES
  static Matrix fromArray(float[] f) {
    Matrix m = new Matrix(1, f.length);
    m.setVals(new float[][]{f});
    m = Matrix.transpose(m);
    return m;
  }

  float[] toArray() {
    float[] f = new float[rows * cols];
    int count = 0;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        f[count] = matrix[i][j];
        count++;
      }
    }
    return f;
  }
}