import java.util.Scanner;

public class LightTemp{
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while (true){
            System.out.println("\nnext");
            double dist = scan.nextDouble();
            double relativeLum = 1/(dist * dist);
            double lum = relativeLum * 1.37e3;
            double relativeTemp = 1/Math.pow(dist, 0.5);
            Double temp = relativeTemp * 288;
            System.out.println("Distance:" + dist + "\nRelative Luminosity:" + relativeLum + "\nLuminosity:" + lum + "\nRelative Temperature:" + relativeTemp + "\nTemperature:" + temp + "\n\n");
        }
    }
}