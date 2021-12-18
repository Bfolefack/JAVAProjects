import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * Querunner
 */
public class QueueRunner {

    public static void main(String[] args) {

        lab1("one two three two one");
        lab1("1 2 3 4 5 one two three four five");
        lab1("a b c d e f g x y z g f h");
        lab1("racecar is racecar");
        lab1("1 2 3 a b c c b a 3 2 1");
        lab1("chicken is a chicken");
        System.out.println();
        lab2("one two three four five six seven");
        lab2("1 2 3 4 5 one two three four five");
        lab2("a p h j e f m c i d k l g n o b");

        MyQueue<Integer> test = new MyQueue<>();
        test.add(5);
        test.add(7);
        test.add(9);
        System.out.println(test);
        System.out.println(test.isEmpty());
        System.out.println(test.remove());
        System.out.println(test.peek());
        System.out.println(test.remove());
        System.out.println(test.remove());
        System.out.println(test.isEmpty());
        System.out.println(test);
    }

    public static void lab1(String in) {
        if (isPalin(in)) {
            System.out.println(in + " is a palinlist");
        } else {
            System.out.println(in + " is not a palinlist");
        }
    }

    private static boolean isPalin(String in) {
        String[] strings = in.split(" ");
        Stack<String> myStack = new Stack<>();
        Queue<String> queue = new LinkedList<String>();

        for (String s : strings) {
            myStack.add(s);
            queue.add(s);
        }

        for (int i = 0; i <= strings.length / 2; i++) {
            if (!myStack.pop().equals(queue.poll())) {
                return false;
            }
        }
        return true;
    }

    private static void lab2(String in) {
        String[] strings = in.split(" ");
        PriorityQueue<String> pq = new PriorityQueue<>();
        for (int i = 0; i < strings.length; i++) {
            pq.add(strings[i]);
        }
        System.out.println("toString() - " + pq.toString());
        String out = "";
        for (int i = 0; i < strings.length; i++) {
            out += pq.poll() + " ";
        }
        System.out.println("getMin() - " + out.split(" ")[0]);
        System.out.println("getNaturalOrder() - " + out);
        System.out.println();
    }
}