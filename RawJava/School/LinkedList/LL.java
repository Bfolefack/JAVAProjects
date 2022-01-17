import java.util.LinkedList;
import java.util.Scanner;

/**
 * LL
 */
public class LL {

    public static void main(String[] args) {
        // lab1("4 5 6 7 8 9 10 11 12 13");
        // lab1("24 75 86 37 82 94 111 82 43");
        // lab1("0 4 5 2 1 4 6");
        ListNode front = null, back = null;
        for (int i = 0; i < 4; i++) {
            if(front == null) front = back = new ListNode(new Integer(i), front);
            else {
                ListNode temp = new ListNode(i, null);
                back.setNext(temp);
                back = temp;
            }
        }
        
        ListFunHouse.print(front);
        ListFunHouse.print(back);
        
    }

    public static void lab1(String s){
        LinkedList<Integer> ll = new LinkedList<>();
        Scanner scan = new Scanner(s);
        while(scan.hasNextInt()){
            ll.add(scan.nextInt());
        }

        int min = ll.getLast();
        int max = ll.getLast();
        int sum = 0;

        for(Integer i : ll){
            sum += i;
            if(i  < min){
                min = i;
            }
            if (i > max){
                max = i;
            }
        }

        System.out.println("SUM: " + sum);
        System.out.println("AVERAGE: " + (sum/(double)ll.size()));
        System.out.println("MIN: " + min);
        System.out.println("MAX: " + max);
        System.out.println();
    }
}