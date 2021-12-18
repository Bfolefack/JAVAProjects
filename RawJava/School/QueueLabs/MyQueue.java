import java.util.ArrayList;

public class MyQueue<E> {
    private ArrayList<E> list;

    public MyQueue(){
        list = new ArrayList<>();
    }

    public void add(E e){
        list.add(e);
    }

    public E remove(){
        return list.remove(0);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public E peek(){
        return list.get(0);
    }

    public String toString(){
        return list.toString();
    }
}
