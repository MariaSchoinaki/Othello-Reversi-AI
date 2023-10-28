/*This class represents the board of the game. It's contained, the basic methods of how to do the minimax algorithm and how a move influences the current board.  */
import java.util.ArrayList;

class Board {

	public static final int W = 1;
    public static final int B = -1;
    public static final int EMPTY = 0;

    private int[][] gameBoard;

    private int lastPlayer;

    private Move lastMove;
	
	private int dimension;
	
/*Empty constractor. It's used for the initialization of the game. No current move is made here. Just the beggining board with the 4 checkers in the middle.      */
	public Board() { 
        this.lastMove = new Move();
        this.lastPlayer = W;
        this.dimension = 8;
        this.gameBoard = new int[dimension][dimension];

        for(int row = 0; row < this.dimension; row++) {
            for(int col = 0; col < this.dimension; col++) {
                this.gameBoard[row][col] = EMPTY;
            }
        }
        this.gameBoard[3][3] = B;
        this.gameBoard[3][4] = W;
        this.gameBoard[4][3] = W;
        this.gameBoard[4][4] = B;
    }
	
/*Copy constractor. It's used to make the possible moves a player can made(getChildren(int value)). It copies the current board, to not destroy it so to make the 
first possible move, if it's valid, flipping the checkers of the opponent so to be added to the list with the possible board moves without touching the og board. */ 
    public Board(Board board) {
        this.lastMove = board.lastMove;
        this.lastPlayer = board.lastPlayer;
        this.dimension = board.dimension;
        this.gameBoard = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0;j < this.dimension; j++) {
                this.gameBoard[i][j] = board.gameBoard[i][j];
            }
        }
    }

/*It's printing the values of our table with a mini animation(boarders, colours etc.). It's made just to be eazier for the player to understand the board.        */    
	public void print() {
        for(int row = 0; row < this.dimension; row++) {
            System.out.print("\u001B[42;42m|\u001B[0m");
            for(int col = 0; col < this.dimension; col++) {
                switch (this.gameBoard[row][col]) {
                    case W:
                        System.out.print("\u001B[30;47m 2 \u001B[0m");
                        break; 
                    case B:
                        System.out.print("\u001B[97;40m 1 \u001B[0m");
                        break;
                    case EMPTY:
                    System.out.print("\u001B[42;42m__|\u001B[0m"); 
                }
            }
            System.out.println();
        }
    }

/*It's creating a list that contains all the possible boards after, making a possible valid move, flipping the right opponents checkers. Used in the minimax algo.*/
	ArrayList<Board> getChildren(int value) {
        ArrayList<Board> children = new ArrayList<>();
        for(int row = 0; row < this.gameBoard.length; row++) {
            for(int col = 0; col < this.gameBoard.length; col++) {
                if(this.isValid(row, col, value)) {
                    Board child = new Board(this);
                    child.makeMove(row, col, value);
                    children.add(child);
                }
            }
        }
        return children;
    }

/*It's the heuristic that evaluats ........................................................................................................................       */
	public int evaluate () {
        int scoreB = 0;
        int scoreW = 0; 
        
        for(int row = 0; row < this.dimension; row++) {
            for(int col = 0; col < this.dimension; col++) {                
                //edges
                if((row == 0 || row == 7) && (col == 0 || col == 7)){       
                    if(this.gameBoard[row][col] == B){
                        scoreB += 1000;
                    }else if(this.gameBoard[row][col] == W){
                        scoreW += 1000;
                    }
                //purple limits
                }else if(((row == 0 || row == 7) && (col >=1 && col <=6)) || ((row >= 1 && row <= 6) && (col == 0 || col == 7))) {
                    if( this.gameBoard[row][col] == B) scoreB += 100;
                    if( this.gameBoard[row][col] == W) scoreW += 100;
                //everything else
                }else if(row >= 1 && row <=  6 && col >= 1 && col <= 6){
                    if( this.gameBoard[row][col] == B) scoreB += 1;
                    if( this.gameBoard[row][col] == W) scoreW += 1;
                }   
            }
        }
    
        
        return scoreB - scoreW;
    }
	
/*Wether a state is terminal or not. To wit the game is finished, if a player has no checkers left in the table to make a move, or if the board is full.          */
	public boolean isTerminal() {
        int sumB = 0; //black checkers in the board
        int sumW = 0; //white checkers in the board 
        int sumE = 0; //empty squares in the board 

        for (int i = 0; i < this.dimension; i++) {
            for(int j = 0; j < this.dimension; j++) {
                if(this.gameBoard[i][j] == W) {
                    sumW++;
                }else if(this.gameBoard[i][j] == B) {
                    sumB++;
                }else {
                    sumE++;
                }
            }
        }
        if(sumW == 0 || sumB == 0 || sumE == 0) {
            return true;
        }
        return false;
    }

/*It's changing the board so that the current board must have the current move that just had been made. The move must be valid or else a message is printed.      */
    void makeMove(int row, int col, int value) {
        if (isValid(row, col, value)) {
            this.gameBoard[row][col] = value;    //it is valid and the player makes a move in the spot [row,col]
            this.lastMove = new Move(row, col);  //store last players move
            this.lastPlayer = value;             //store last players value
            flipcheckers(row, col, value);
        }else {
            System.out.println("Try again with a valid move such as an empty square that follows the game rules:");
            System.out.println("Vertically, horizontally or diagonally 2 of your checkers enclose 1 or more of the opponent's checkers");
        }
    }

/*It's checking wether a move is valid or not. If it's an empty square that encloses 1 or more of the opponent's checkers at at least one of the 8 directions.    */
    Boolean isValid(int row, int col, int value) {
        boolean islegal = false;
        int opponent = -1 * value;
        int x = row;
        int y = col;
    //Checks if the square is empty
        if(inBoarders(row, col) && this.gameBoard[row][col] != EMPTY) {
            return false;
        }
    //Checks if the move follows the game rules

        //left horizontically
        while(inBoarders(x, y - 1) && this.gameBoard[x][y - 1] == opponent) {
            y--;
            if(inBoarders(x, y - 1) && this.gameBoard[x][y - 1] == EMPTY) break;
            if(inBoarders(x, y - 1) && this.gameBoard[x][y - 1] == value ) {
                islegal = true;
                break;
            }    
        }
        //right horizontically
        y = col;
        while(inBoarders(x, y + 1) && this.gameBoard[x][y + 1] == opponent) {
            y++;
            if(inBoarders(x, y + 1) && this.gameBoard[x][y + 1] == EMPTY) break;
            if(inBoarders(x, y + 1) && this.gameBoard[x][y + 1] == value ) {
                islegal = true;
                break;
            }    
        }
        //up vertically
        y = col;
        while(inBoarders(x - 1, y) && this.gameBoard[x - 1][y] == opponent) {
            x--;
            if(inBoarders(x - 1, y) && this.gameBoard[x - 1][y] == EMPTY) break;
            if(inBoarders(x - 1, y) && this.gameBoard[x - 1][y] == value) {
                islegal = true;
                break;
            }    
        }
        //down vertically
        x = row;
        while(inBoarders(x + 1, y) && this.gameBoard[x + 1][y] == opponent) {
            x++;
            if(inBoarders(x + 1, y) && this.gameBoard[x + 1][y] == EMPTY) break;
            if(inBoarders(x + 1, y) && this.gameBoard[x + 1][y] == value ) {
                islegal = true;
                break;
            }    
        }
        //left up diagonally
        x = row;
        while(inBoarders(x - 1, y - 1) && this.gameBoard[x - 1][y - 1] == opponent) {
            x--;
            y--;
            if(inBoarders(x - 1, y - 1) && this.gameBoard[x - 1][y - 1] == EMPTY) break;
            if(inBoarders(x - 1, y - 1) && this.gameBoard[x - 1][y - 1] == value ) {
                islegal = true;
                break;
            }    
        }
        //right up diagonally
        x = row;
        y = col;
        while(inBoarders(x - 1, y + 1) && this.gameBoard[x - 1][y + 1] == opponent) {
            x--;
            y++;
            if(inBoarders(x - 1, y + 1) && this.gameBoard[x - 1][y + 1] == EMPTY) break;
            if(inBoarders(x - 1, y + 1) && this.gameBoard[x - 1][y + 1] == value ) {
                islegal = true;
                break;
            }    
        }
        //left down diagonally
        x = row;
        y = col;
        while(inBoarders(x + 1, y - 1) && this.gameBoard[x + 1][y - 1] == opponent) {
            x++;
            y--;
            if(inBoarders(x + 1, y - 1) && this.gameBoard[x + 1][y - 1] == EMPTY) break;
            if(inBoarders(x + 1, y - 1) && this.gameBoard[x + 1][y - 1] == value ) {
                islegal = true;
                break;
            }    
        }
        //right down diagonally
        x = row;
        y = col;
        while(inBoarders(x + 1, y + 1) && this.gameBoard[x + 1][y + 1] == opponent) {
            x++;
            y++;
            if(inBoarders(x + 1, y + 1) && this.gameBoard[x + 1][y + 1] == EMPTY) break;
            if(inBoarders(x + 1, y + 1) && this.gameBoard[x + 1][y + 1] == value ) {
                islegal = true;
                break;
            }    
        }
        return islegal;
    }

/*Checks if a move that's trying to be made is inside the boarders of the game board(table from 0 to 7 rows and 0 to 7 columns).                                  */
    Boolean inBoarders(int row, int col) {

        if(row < 0 || row > 7 || col < 0 || col > 7) return false;
        return true;
    }

/*It's changing the board when a valid move so that the enclosing chackers of the opponents flip and change value to the value of the player that made the move.  */
    void flipcheckers(int row, int col, int value){
        int opponent = -1 * value;

        //up vertically
        int x = row - 1; //direction is up so it is previous row
        int y = col;    //direction is up so same column

        while (x > 0 && this.gameBoard[x][y] == opponent) {  //while we are still within the table's bounds and the checker belongs to an opponent
            if (this.gameBoard[x - 1][y] == value){                    //if the checker is one of our own then we hava found the limit where we can flip the checkers
                while(this.gameBoard[x][y] == opponent){               //while there are still checkers of our opponent within our limit
                    this.gameBoard[x][y] = value;                      //flip the checker
                    x++;                                               //go to next row
                }
            }
            x--;
            
        }

        //down vertically
        x = row + 1;  //direction is down so it is next row
        y = col;    //direction is down so same column
        while (x < 7 && this.gameBoard[x][y] == opponent){
            if (this.gameBoard[x+1][y] == value){              //if the checker is one of our own then we hava found the limit where we can flip the checkers
                while(this.gameBoard[x][y] == opponent){
                    this.gameBoard[x][y] = value;
                    x--;
                }
            }
            x++;
        }

        //right horizontally
        x = row; //direction is right so it is same row
        y = col + 1; //direction is right so it is next column
        while (y < 7 && this.gameBoard[x][y] == opponent){
            if (this.gameBoard[x][y+1] == value){
                while(this.gameBoard[x][y] == opponent){
                    this.gameBoard[x][y] = value;
                    y--;
                }
            }
            y++;
        }

        //left horizontally
        x = row; //direction is left so it is same row
        y = col - 1; //direction is left so it is previous column
        while (y > 0 && this.gameBoard[x][y] == opponent){
            if (this.gameBoard[x][y-1] == value){
                while(this.gameBoard[x][y] == opponent){
                    this.gameBoard[x][y] = value;
                    y++;
                }
            }
            y--;
        }

        //left up diagonally
        x = row - 1; //direction is up left so it is previous row
        y = col - 1; //direction is up left so it is previous column
        while(x > 0 && y > 0 && this.gameBoard[x][y] == opponent){
            if (this.gameBoard[x-1][y-1] == value){
                while(this.gameBoard[x][y] == opponent){
                    this.gameBoard[x][y] = value;
                    y++;
                    x++;
                }
            }
            y--;
            x--;
        }

        //right up diagonally
        x = row - 1; //direction is up right so it is previous row
        y = col + 1; //direction is up right so it is next column
        while(x > 0 && y < 7 && this.gameBoard[x][y] == opponent){
            if (this.gameBoard[x-1][y+1] == value){
                while(x > 0 && y > 0 && this.gameBoard[x][y] == opponent){
                    this.gameBoard[x][y] = value;
                    y--;
                    x++;
                }
            }
            y++;
            x--;
        }

        //left down diagonally
        x = row + 1; //direction is down left so it is next row
        y = col - 1; //direction is downt so it is previous column
        while( x < 7 && y > 0 && this.gameBoard[x][y] == opponent){
            if (this.gameBoard[x+1][y-1] == value){
                while(x < 7 && y > 0 && this.gameBoard[x][y] == opponent){
                    this.gameBoard[x][y] = value;
                    y++;
                    x--;
                }
            }
            y--;
            x++;
        }

        //right down horizontally
        x = row + 1; //direction is down right so it is next row
        y = col + 1; //direction is down right so it is next column
        while(x < 7 && y < 7 && this.gameBoard[x][y] == opponent){
            if (this.gameBoard[x+1][y+1] == value){
                while(x < 7 && y < 7 && this.gameBoard[x][y] == opponent){
                    this.gameBoard[x][y] = value;
                    y--;
                    x--;
                }
            }
            y++;
            x++;
        }

    }
	
	void setGameBoard(int[][] gameBoard) {
        for(int i = 0; i < this.dimension; i++) {
            for(int j = 0; j < this.dimension; j++) {
                this.gameBoard[i][j] = gameBoard[i][j];
            }
        }
    }

    void setLastMove(Move lastMove) {
        this.lastMove.setRow(lastMove.getRow());
        this.lastMove.setCol(lastMove.getCol());
        this.lastMove.setValue(lastMove.getValue());
    }

    void setLastPlayer(int lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public Move getLastMove() {
        return this.lastMove;
    }

    public int getLastPlayer() {
        return this.lastPlayer;
    }

    public int[][] getGameBoard() {
        return this.gameBoard;
    }
}