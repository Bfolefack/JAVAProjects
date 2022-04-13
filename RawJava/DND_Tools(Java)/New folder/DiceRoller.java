/**
 * DiceRoller
 */
public class DiceRoller {

    public static void main(String[] args) {
        int die = 8;
        float epochs = 5000000;

        int total1 = 0;
        int total2 = 0;
        int total3 = 0;
        for (int i = 0; i < epochs; i++) {
            int die1 = rollDice(1, die);
            int die2 = rollDice(1, die);
            total1 += die1 < die2 ? die1 : die2;
            total3 += rollDice(1, die);
            total2 += die1 > die2 ? die1 : die2;
        }
        System.out.println(total1/epochs);
        System.out.println(total3/epochs);
        System.out.println(total2/epochs);

    }

    public static int rollDice(int numDice, int numSides) {
        int total = 0;
        for (int i = 0; i < numDice; i++) {
            total += (int) (Math.random() * numSides) + 1;
        }
        return total;
    }
}