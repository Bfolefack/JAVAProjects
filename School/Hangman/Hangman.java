import java.io.*;
import java.util.*;

/**
 * Hangman
 */
public class Hangman{

    public static void main(String[] args) throws FileNotFoundException{
        Scanner file = new Scanner(new File("dictionary.txt"));
        int count = 0;
        while(file.hasNext()){
            file.nextLine();
            count++;
        }
        file.close();
        file = new Scanner(new File("dictionary.txt"));
        String[] dictionary = new String[count];
        for (int i = 0; i < count; i++){
            dictionary[i] = file.nextLine();
        }
        file.close();
        
        // for(int i = 0; i < 8; i++){
        //     stickman(i);
        //     System.out.println();
        // }
        
        String answer = dictionary[(int)(dictionary.length * Math.random())];
        System.out.println(answer);

        
        Scanner player = new Scanner(System.in);

        System.out.println("How many guesses would you like?");
        // int guessesRemaining = Integer.parseInt(player.nextLine());
        int guessesRemaining = 7;
        System.out.println("Your word has " + answer.length() + " letters." );
        

        String guessed = "";
        String unguessed = "abcdefghijklmnopqrstuvwxyz";

        while(guessesRemaining > 0){
            System.out.println("You have " + guessesRemaining + " guesses remaining");
            stickman(guessesRemaining);
            System.out.println(getWord(answer, guessed));
            System.out.println("You have guessed: " + guessed);
            System.out.println("You have yet to guess: " + unguessed);
            String guess = player.nextLine();
            
            if(guess.length() == 1){
                if(!containsLetter(guessed, guess) && !containsLetter(unguessed, guess)){
                    System.out.println("\n Sorry, that is not a valid guess. ");
                    continue;
                } else if(containsLetter(guessed, guess)){
                    System.out.println("\n Sorry, you've already guessed that ");
                    continue;
                } else if (containsLetter(unguessed, guess)){
                    unguessed = unguessed.substring(0, unguessed.indexOf(guess)) + unguessed.substring(unguessed.indexOf(guess) + 1, unguessed.length());
                    guessed += guess;
                    if(containsLetter(answer, guess)){
                        System.out.println("\n Congrats! you guessed correctly ");
                        if(!containsLetter(getWord(answer, guessed), "-")){
                            System.out.println("\n CONGRATUALTIONS! You Win!");
                            player.close();
                            System.exit(0);
                            break;
                        }
                    } else {
                        System.out.println("\n Sorry, you guessed incorrectly ");
                        guessesRemaining--;
                    }
                    continue;
                }
            }
            System.out.println("Sorry, that is not a valid guess. ");
        }
        player.close();
        stickman(guessesRemaining);
        System.out.println("Sorry, you lose. :(");
        
    }

    public static void stickman(int a){
        System.out.print("__");
        System.out.print("\n | ");
        if(a < 7){
            System.out.print("\n O ");
            if(a < 6){
                System.out.print("\n\\");
                if(a < 5){
                    System.out.print("|");
                    if(a < 4){
                        System.out.print("/");
                        if(a < 3){
                            System.out.print("\n | ");
                            if(a < 2){
                                System.out.print("\n/");
                                if(a < 1){
                                    System.out.print(" \\");
                                }
                            }else{
                                System.out.print("\n");
                            }
                        } else{
                            System.out.print("\n\n");
                        }
                    } else{
                        System.out.print("\n\n\n");
                    }
                } else{
                    System.out.print("\n\n\n");
                }
            } else{
                System.out.print("\n\n\n");
            }
        } else{
            System.out.print("\n\n\n\n");
        }
        System.out.println("\n");
    }

    public static String getWord(String a, String b){
        String word = "";
        for(int i = 0; i < a.length(); i++){
            if(containsLetter(b, a.charAt(i) + "")){
                word += a.charAt(i);
            } else {
                word += "-";
            }
        }
        return word;
    }

    public static boolean containsLetter(String a, String b){
        return a.indexOf(b) != -1;
    }
}