class MatrixLibrary {
    public static void main(String[] args) {
        Matrix m = new Matrix(2, 3);
        Matrix n = new Matrix(3, 3);
        float[][] test = { 
            {3, 6, 2},
            {5, 9, 4}
        };
        float[][] test2 = {
            {3, 4, 5},
            {1, 5, 6},
            {8, 6, 2}
        };

        //VALUE ASSIGNMENT AND RECEPTION AND MULTIPLICATION ACCURATE
        // m.setVals(test); //CORRECT
        // m.print();
        // n.setVals(test2);
        // n.print();
        // Matrix o = m.multiply(n); //CORRECT
        // o.print();
        // Matrix n = m.duplicate(); //CORRECT
        // n.print(); 
        // m.setVal(1, 2, 2.5f); //CORRECT
        // m.print();
        // System.out.println(m.getVal(0, 1)); //CORRECT
        // Matrix o = new Matrix(2, 3);
        // o.setVals(m); //CORRECT
        // o.print();

        //RANDOMIZATION FUNCTIONS ACCURATE
        // Matrix p = new Matrix(5, 4);
        // p.randomize(); //CORRECT
        // p.print();
        // Matrix s = Matrix.randomize(3, 3); //CORRECT
        // s.print();

        //ADDITION AND SUBTRACTION FUNCTIONS ACCURATE
        // Matrix t = m.duplicate();
        // t.diff(3); //CORRECT
        // t.print();
        // Matrix u = m.duplicate();
        // Matrix.diff(u, 3); //CORRECT
        // u.print();
        // Matrix.diff(u, t).print();
        // u.diff(t);
        // u.print();
        // u.diff(Matrix.randomize(3, 3));
        // u.print();
    }
}