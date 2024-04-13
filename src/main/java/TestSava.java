package main.java;

import com.AL1S.util.*;
import java.util.Scanner;

public class TestSava {
    public static void main(String[] args) {
        Board board = new Board(0, 4);
        board.init();
        Scanner in = new Scanner(System.in);
        System.out.println(board.toString());
        while (true) {
            System.out.println("Please input a direction: (0: up, 1: down, 2: left, 3: right, -1 to undo, others to exit)");
            int direction = in.nextInt();
            if (direction < -1 || direction > 3) {
                break;
            }
            if (direction == -1) {
                board.undo();
                System.out.println(board.toString());
                continue;
            }
            board.addToHistory();
            board.slip(direction);
            System.out.println(board.toString());
            if (board.isWin()) {
                System.out.println("You win!");
                break;
            }
            if (board.isOver()) {
                System.out.println("Game Over!");
                break;
            }
        }
        Save.saveBoard(board);
        Board newBoard = Save.loadBoard(0);
        System.out.println(newBoard.toString());
        while (true) {
            System.out.println("Please input a direction: (0: up, 1: down, 2: left, 3: right, -1 to undo, others to exit)");
            int direction = in.nextInt();
            if (direction < -1 || direction > 3) {
                break;
            }
            if (direction == -1) {
                board.undo();
                System.out.println(board.toString());
                continue;
            }
            board.addToHistory();
            board.slip(direction);
            System.out.println(board.toString());
            if (board.isWin()) {
                System.out.println("You win!");
                break;
            }
            if (board.isOver()) {
                System.out.println("Game Over!");
                break;
            }
        }
    }
}
