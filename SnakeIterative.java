package AdvanceCode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;



// Try iterative approach on your own (logic is same as recursive approach). If you got struck, come here
public class SnakeIterative {
    private char[][] snakeBoard = null;
    private Queue<Node> queue = new LinkedList<Node>();
    private Queue<Node> food = new LinkedList<Node>();  // to store the food positions
    Scanner s = new Scanner(System.in);

    SnakeIterative(int row, int col) {
        snakeBoard = new char[row][col];
        queue.add(new Node(0,0));

        // Positions of food (X) to display
        food.add(new Node(1,0));
        food.add(new Node(2,2));
        food.add(new Node(3,4));
        food.add(new Node(5,2));
        food.add(new Node(4,5));
        // Display first food (X)
        displayFood(food.poll());
    }

    // Iterative approach (Just placed the while loop outside of snakeMove method to make it iterative)
    public void initiateSnake() {
        int row = 0, col = 0;
        snakeBoard[row][col] = '.';  // Initial snake ('.') in 0,0 position
        while(true) {
            printSnake();
            System.out.print("Enter a position : ");
            
            char direction = s.next().charAt(0);

            if (direction == 'U') {
                snakeMove(--row, col);
            }
            else if (direction == 'D') {
                snakeMove(++row, col);
            }
            else if (direction == 'R') {
                snakeMove(row, ++col);
            }
            else if (direction == 'L') {
                snakeMove(row, --col);
            }
            else{
                System.exit(0);
            }
        }
    }

    public void snakeMove(int row, int col) {
        if(row >= 0 && row < snakeBoard.length && col >=0 && col < snakeBoard.length) {

            queue.add(new Node(row, col));

            // Snake bites itself
            if(snakeBoard[row][col] == '.') {
                System.out.println("Game Over!!!");
                System.exit(0);
            }

            // Remove the tail
            if(snakeBoard[row][col] != 'X') {
                Node node = queue.poll();
                int r = node.getRow();
                int c = node.getColumn();
                snakeBoard[r][c] = '\0';
            }

            
            if(snakeBoard[row][col] == 'X') {
                if(food.isEmpty()) {
                    System.out.println("Game Over!!!");
                    System.exit(0);
                }
                displayFood(food.poll());
            }

            
            // Placing '.' 
            snakeBoard[row][col] = '.';
        } else {
            System.out.println("Invalid move");
            System.exit(0);
        }
    }

    // Displays new food in appropriate position
    public void displayFood(Node node) {
        int r = node.getRow();
        int c = node.getColumn();
        snakeBoard[r][c] = 'X';
    }

    public void printSnake() {
        for (char[] chars : snakeBoard) {
            for (char c:chars) {
                System.out.print(c+" ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }
    public static void main(String[] args) {
        /* Recursive approach
        Snake snake = new Snake(6,6);
        snake.snakeMove(0,0); */

        //Iterative approach
        SnakeIterative snakeIterative = new SnakeIterative(6,6);
        snakeIterative.initiateSnake();
    }
}
class Node {
    private int row = 0;
    private int column = 0;

    Node(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
