import java.util.HashMap;
import java.util.Iterator;

public class Histo implements Iterable<Character> {
    HashMap<Character, Integer> map;

    Histo() {
        map = new HashMap<>();
    }

    Histo(String s){
        map = new HashMap<>();
        for(char c : s.toCharArray()){
            map.put(c, 0);
        }
    }

    public void put(char c) {
        if (map.containsKey(c)) {
            map.put(c, map.get(c) + 1);
        } else {
            map.put(c, 1);
        }
    }

    public int get(char c) {
        return map.get(c);
    };

    public boolean contains(char c) {
        if (map.keySet().contains(c)) {
            if (map.get(c) > 0)
                return true;
        }
        return false;
    };

    public Iterator<Character> iterator() {
        return map.keySet().iterator();
    };

}