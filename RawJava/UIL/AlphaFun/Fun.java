public class Fun implements Comparable<Fun> {


    String word;
    String compWord;

    public Fun(String w){
        word = w;
        compWord = "";
        if(w.length() < 2){
            compWord += "  ";
        } else if (w.length() > 3){
            compWord += w.charAt(1);
            compWord += w.charAt(3);
        } else {
            compWord += w.charAt(1);
            compWord += " ";
        }
        compWord += word.charAt(word.length() - 1);
        compWord += word.charAt(0);
    }
    @Override
    public int compareTo(Fun o) {
        
        if(compWord.compareTo(o.compWord) == 0){
            return word.compareTo(o.word);
        }
        return compWord.compareTo(o.compWord);
    }
    
}
