 package DSA;
import java.util.*;
public class TicTacTeo {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        char board[][]= new char[3][3];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c]=' ';
            }
        }
        boolean res=false;
        char player='X';
        while (!res) {
            print(board);
            System.out.print("Player : "+player+", Enter R,C :");
            int row= sc.nextInt();
            int col= sc.nextInt();
            if (board[row][col]==' ') {
                board[row][col]=player;
                res =hasWin(board,player);
                if (res) {
                    System.out.println("Player "+player+" has won");
                }else{
                    player=(player=='X')?'O':'X';
                }
                
            }else{
                System.out.println("you are selecting a existing node.. Please try again");
            }
        }
        print(board);
    }

    private static boolean hasWin(char[][] board, char player) {
        for (int r = 0; r < board.length; r++) {
            if (board[r][0]==player&&board[r][1]==player&&board[r][2]==player) {
                return true;
            }
        }
        for (int c = 0; c < board.length; c++) {
            if (board[0][c]==player&&board[1][c]==player&&board[2][c]==player) {
                return true;
            }
        }
        if (board[0][0]==player&&board[1][1]==player&&board[2][2]==player) {
            return true;
        }
        if (board[2][0]==player&&board[1][1]==player&&board[0][2]==player) {
            return true;
        }
        return false;
    }

    private static void print(char[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(board[r][c]+" | ");
            }
            System.out.println();
        }
    }
}