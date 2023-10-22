import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        Scanner in = new Scanner(System.in);
        int length = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Choose difficalty level 1-5: ");

            if (in.hasNextInt()) {
                length = in.nextInt();

                if (length >= 1 && length <= 5) {
                    validInput = true;
                } else {
                    System.out.println("Difficalty level out of range. Please type a number between 1-5: ");
                }
            } else {
                in.next();
                System.out.println("Type error. Please type a valid integer between 1-5.");
            }
        }

        Player playerWhite = new Player(length, 1);
        Player playerBlack = new Player(length, 2);
        Board board = new Board();
        board.print();
    }
}
