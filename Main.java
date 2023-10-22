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
                    System.out.println("\nDifficalty level out of range.\nPlease type a number between 1-5");
                }
            } else {
                in.next();
                System.out.println("\nType error. Please type a valid integer between 1-5");
            }
        }
        int turn = 0;
        validInput = false;

        while (!validInput) {
            System.out.print("\nChoose if you want to play first or second.\nType 1 to play first or 2 to play second: ");
            if (in.hasNextInt()) {
                turn = in.nextInt();
            if(turn == 1 || turn == 2){
                validInput = true;
            } else{
                System.out.println("\nTurn number out of range.\nPlease type a number between 1 and 2");
            }
        } else{
            in.next();
            System.out.println("\nType error. Please type a valid integer between 1 and 2");
        }
    }

        Player person = ((turn==1) ? new Player(length, Board.B): new Player(length, Board.W));
        Player AI = ((turn==2) ? new Player(length, Board.W): new Player(length,Board.B));

        
        Board board = new Board();
        board.print();

        while(!board.isTerminal()){
            switch(board.getLastPlayer()){
                case Board.B:
                    if(turn==1) System.out.println("its your turn!");
                    break;
                case Board.W:
                    if(turn==2) System.out.println("its your turn!");
                    break;

            }
            board.print();
        }
    }
}
