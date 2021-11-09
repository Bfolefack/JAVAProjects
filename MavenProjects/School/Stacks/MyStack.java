import java.util.ArrayList;

public class MyStack {
    private ArrayList<Integer> listOfInts;

    public MyStack() {
        listOfInts = new ArrayList<>();
    }

    public void push(int item) {
        listOfInts.add(item);
    }

    public int pop() {
        return listOfInts.remove(listOfInts.size() - 1);
    }

    public boolean isEmpty() {
        return listOfInts.size() == 0;
    }

    public int peek() {
        return listOfInts.get(listOfInts.size() - 1);
    }

    public String toString() {
        return listOfInts.toString();
    }
}
