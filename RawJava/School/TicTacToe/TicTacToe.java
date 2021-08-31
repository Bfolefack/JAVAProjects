import java.util.*;
import java.io.*;

/**
 * TicTacToe
 */
public class TicTacToe {
    private String[][] board;
    private boolean turn;
    private boolean gameOver;

    public TicTacToe()
    {
        board = new String[3][3];
        turn = true;
        gameOver = false;

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                board[i][j] = " ";
            }
        }
    }

    void display(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                System.out.print(board[i][j]);
                if(j < board[0].length - 1){
                    System.out.print("|");
                }
            }
            if(i < board.length - 1){
                System.out.print("\n------\n");
            }
        }
    }

    boolean playTurn(int row, int col){
        if(row - 1 < board.length && col - 1 < board[0].length){
            if(board[row - 1][col - 1] == " "){
                if(turn){
                    board[row - 1][col - 1] = "X";
                    return true;
                } else {
                    board[row - 1][col - 1] = "O";
                    return true;
                }
            }
        }
        return false;
    }

    void playGame(){
        Scanner game = new Scanner(System.in);
        \[] 
    }
}