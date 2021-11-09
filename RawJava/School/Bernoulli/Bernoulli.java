import java.util.Scanner;

public class Bernoulli{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("height");
        double height = scan.nextDouble()/39.37;
        while (true){
            System.out.println("\nnext");
            double dist = scan.nextDouble()/39.37;
            double time = Math.sqrt(height/4.9);
            double v = dist/time;
            double P = Math.pow(v, 2) * 1000 * 0.5 + 101300;
            System.out.println("Distance:" + dist + "\nTime:" + time + "\nVelocity:" + v + "\nPressure:" + P + "\n");
        }
    }
}