import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.awt.event.*;

/**
 * words
 */
public class Words {

    static HashSet<String> words = new HashSet<>();
    public static HashSet<String> guesses = new HashSet<>();
    public static ArrayList<String> recommendations;

    public static HashMap<Character, Double> frequency = new HashMap<>();
    public static HashMap<String, Double> commonality = new HashMap<>();
    public static int initalWordNum;

    public static int letterCount = 5;
    
    static String alphabet = "abcdefghianjklmnopqrstuvwxyz";
    
    static String guess;
    public static void reset() throws FileNotFoundException{
        Scanner wordScanner = new Scanner(new File("dictionary.txt"));
        while (wordScanner.hasNext()) {
            String s = wordScanner.next().toLowerCase();
            if (s.length() == letterCount && isAlphabet(s))
                words.add(s);
        }
        initalWordNum = words.size();
        guess = "";
    }
    public static void main(String[] args) throws FileNotFoundException {
        frequency.put('e', .111607);
        frequency.put('a', .084966);
        frequency.put('r', .075809);
        frequency.put('i', .075448);
        frequency.put('o', .071635);
        frequency.put('t', .069509);
        frequency.put('n', .066544);
        frequency.put('s', .057351);
        frequency.put('l', .054893);
        frequency.put('c', .045388);
        frequency.put('u', .036308);
        frequency.put('d', .033844);
        frequency.put('p', .031671);
        frequency.put('m', .030129);
        frequency.put('h', .030034);
        frequency.put('g', .024705);
        frequency.put('b', .020720);
        frequency.put('f', .018121);
        frequency.put('y', .017779);
        frequency.put('w', .012899);
        frequency.put('k', .011016);
        frequency.put('v', .010074);
        frequency.put('x', .002902);        
        frequency.put('z', .002722);
        frequency.put('j', .001965);
        frequency.put('q', .001962);

        Scanner scan = new Scanner(new File("unigram_freq.csv"));
        while(scan.hasNextLine()){
            String[] index = scan.nextLine().split(",");
            if(index[0].length() == letterCount){
                commonality.put(index[0], Long.parseLong(index[1])/23135851162.0);
            }
        }
        
        Scanner input = new Scanner(System.in);
        reset();
        
        gen();
        while (true) {
            System.out.println("Order?: gen, cull, lock");
            String order = input.next();
            System.out.println("\n");
            switch (order) {
                case "cull":
                    String kill = input.next();
                    for (char c : kill.toCharArray()) {
                        cull(c);
                    }
                    break;
                case "lock":
                    int index = input.nextInt();
                    char keep = input.next().toLowerCase().charAt(0);
                    lock(index, keep);
                    break;
                case "keep":
                    String save = input.next();
                    for (char c : save.toCharArray()) {
                        keep(c);
                    }
                    break;
                case "ex":
                    index = input.nextInt();
                    keep = input.next().toLowerCase().charAt(0);
                    exclude(index, keep);
                    break;
                case "color":
                    String word = input.next();
                    auto(word);
                    break;
                case "guess":
                    guess = input.next();
                    break;
                case "a":
                    guess = input.next();
                    guesses.add(guess);
                    word = input.next();
                    auto(word);
                    break;
                case "reset":
                    reset();
                    break;
                default:
                    try{
                        guess = recommendations.get(Integer.parseInt(order));
                    } catch(Exception e){
                        guess = order;
                    }
                    guesses.add(guess);
                    word = input.next();
                    auto(word);
                    break;
            }
            gen();
        }
    }

private static void auto(String word) {
        char[] arr = word.toCharArray();
        char[] gess = guess.toCharArray();
        Histo black = new Histo(guess);
        Histo yellow = new Histo(guess);
        for (int i = 0; i < arr.length; i++) {
            switch (arr[i]) {
                case 'g':
                    lock(i + 1, gess[i]);
                    yellow.put(gess[i]);
                    break;
                case 'y':
                    keep(gess[i]);
                    exclude(i + 1, gess[i]);
                    yellow.put(gess[i]);
                    break;
                case 'b':
                    black.put(gess[i]);
                    break;
            }
        }
        for (char c : black) {
            if(!yellow.contains(c)){
                cull(c);
                // cullGreater(c, yellow.get(c));
            }
        }
        // for (char c : yellow) {
        //     cullLesser(c, yellow.get(c));
        // }
    }

    private static void cullGreater(char c, int count) {
        HashSet<String> newList = new HashSet<>();
        for (String string : words) {
            if (count(string, c) <= count) {
                newList.add(string);
            }
        }
        words = newList;
    }

    public static int repeats(String s){
        Histo temp = new Histo(s);
        int total = 0;
        for(char c : s.toCharArray()){
            temp.put(c);
        }
        for(char c : s.toCharArray()){
            total += temp.get(c);
        }
        return total;
    }

    private static void cullLesser(char c, int count) {
        HashSet<String> newList = new HashSet<>();
        for (String string : words) {
            if (count(string, c) >= count) {
                newList.add(string);
            }
        }
        words = newList;
    }

    private static void exclude(int index, char keep) {
        HashSet<String> newList = new HashSet<>();
        for (String string : words) {
            if (string.charAt(index - 1) != keep) {
                newList.add(string);
            }
        }
        words = newList;
    }

    private static void keep(char c) {
        HashSet<String> newList = new HashSet<>();
        for (String string : words) {
            if (contains(string, c)) {
                newList.add(string);
            }
        }
        words = newList;
    }

    private static int count(String s, char c) {
        int count = 0;
        for (char c2 : s.toCharArray()) {
            if (c == c2) {
                count++;
            }
        }
        return count;
    }

    private static void lock(int index, char keep) {
        HashSet<String> newList = new HashSet<>();
        for (String string : words) {
            if (string.charAt(index - 1) == keep) {
                newList.add(string);
            }
        }
        words = newList;
    }

    private static void cull(char kill) {
        HashSet<String> newList = new HashSet<>();
        for (String string : words) {
            if (!contains(string, kill)) {
                newList.add(string);
            }
        }
        words = newList;
    }

    private static void gen() {
        HashSet<String> chosenWords = new HashSet<>();
        chosenWords.addAll(guesses);
        recommendations = new ArrayList<>();
        if (words.size() > 20) {
            for (int i = 0; i < 10; i++) {
                double leastSimilarity = Integer.MAX_VALUE;
                String leastSimilarWord = "";
                for (String s : words) {
                    double sim = 0;
                    if(chosenWords.size() > 0){
                        for (String str : chosenWords) {
                            sim += similarity(s, str) + (repeats(s) + letterScore(s) * (wordPercentage() * 2) + ((1 - wordPercentage()) * 10) *((commonality(s)) * s.length()))/2.0;
                        }
                    } else {
                        sim += repeats(s) + letterScore(s);
                    }
                    if (sim < leastSimilarity && !chosenWords.contains(s) && !guesses.contains(s)) {
                        leastSimilarWord = s;
                        leastSimilarity = sim;
                    }
                }
                words.remove(leastSimilarWord);
                chosenWords.add(leastSimilarWord);
            }
            chosenWords.removeAll(guesses);
            words.addAll(chosenWords);
            recommendations.addAll(chosenWords);
            for (String string : recommendations) {
                System.out.println(recommendations.indexOf(string) + ": " + string);
            }
        } else {
            recommendations.addAll(words);
            for (String string : recommendations) {
                System.out.println(recommendations.indexOf(string) + ": " + string);
            }
        }
        words.removeAll(guesses);
        System.out.println("\n\n");
    }

    public static double wordPercentage(){
        return Math.pow(words.size()/(double)initalWordNum, 2);
    }

    public static double commonality(String s){
        return commonality.get(s) == null ? 2 :  1 - (commonality.get(s));
    }

    public static boolean contains(String s, char car) {
        for (char c : s.toCharArray()) {
            if (car == c) {
                return true;
            }
        }
        return false;
    }

    public static int similarity(String s1, String s2) {
        int sim = 0;
        char[] a1 = s1.toCharArray();
        char[] a2 = s2.toCharArray();
        for (int i = 0; i < a1.length; i++) {
            if (contains(s1, a2[i]))
                sim++;
            if (contains(s2, a1[i]))
                sim++;
        }
        return sim;
    }

    public static double letterScore(String s){
        double count = 0;
        for(char c : s.toCharArray()){
            count += frequency.get(c);
        }
        return s.length() - count * s.length();
    }

    public static boolean isAlphabet(String s) {
        for (char c : s.toCharArray()) {
            if (!contains(alphabet, c)) {
                return false;
            }
        }
        return true;
    }
}