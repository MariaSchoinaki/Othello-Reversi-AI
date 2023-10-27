import java.util.ArrayList;

class Board
{
	public static final int W = 1;
    public static final int B = -1;
    public static final int EMPTY = 0;

    private int[][] gameBoard;

    private int lastPlayer;

    private Move lastMove;
	
	private int dimension;
	
	public Board() { 
        this.lastMove = new Move();
        this.lastPlayer = W;
        this.dimension = 8;
        this.gameBoard = new int[dimension][dimension];

        for(int row = 0; row < this.dimension; row++)
        {
            for(int col = 0; col < this.dimension; col++)
            {
                this.gameBoard[row][col] = EMPTY;
            }
        }
        this.gameBoard[3][3] = B;
        this.gameBoard[3][4] = W;
        this.gameBoard[4][3] = W;
        this.gameBoard[4][4] = B;
    }
	
	// copy constructor
    public Board(Board board) {
        this.lastMove = board.lastMove;
        this.lastPlayer = board.lastPlayer;
        this.dimension = board.dimension;
        this.gameBoard = new int[this.dimension][this.dimension];
        for (int i=0;i<this.dimension;i++){
            for (int j=0;j<this.dimension;j++){
                this.gameBoard[i][j]= board.gameBoard[i][j];
            }
        }
    }
	
	public void print() {
        for(int row = 0; row < this.dimension; row++)
        {
            System.out.print("\u001B[42;42m|\u001B[0m");
            for(int col = 0; col < this.dimension; col++)
            {
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
	
	ArrayList<Board> getChildren(int letter) {
        ArrayList<Board> children = new ArrayList<>();
        for(int row = 0; row < this.gameBoard.length; row++)
        {
            for(int col = 0; col < this.gameBoard.length; col++){
                if(this.is_Valid(row, col,letter)){
                    Board child = new Board(this);
                    child.makeMove(row, col, letter);
                    children.add(child);
                }
            }
        }
        return children;
    }
	
	public int evaluate () {return 0;}
	
	public boolean isTerminal() {
        int sumB = 0; //score White
        int sumW = 0;//score Black 
        int sumE = 0;//sum of Empty squares
        for (int i = 0; i<this.gameBoard.length; i++){
            for(int j = 0; j<this.gameBoard.length; j++){
                if(this.gameBoard[i][j] == W){
                    sumW++;
                }else if(this.gameBoard[i][j] == B){
                    sumB++;
                }else{
                    sumE++;
                }
            }
        }
        if(sumW == 0 || sumB == 0){
            return true;
        }
        return false;
    }
	
	public Move getLastMove()
    {
        return this.lastMove;
    }

    public int getLastPlayer()
    {
        return this.lastPlayer;
    }

    public int[][] getGameBoard()
    {
        return this.gameBoard;
    }
	
	void setGameBoard(int[][] gameBoard)
    {
        for(int i = 0; i < this.dimension; i++)
        {
            for(int j = 0; j < this.dimension; j++)
            {
                this.gameBoard[i][j] = gameBoard[i][j];
            }
        }
    }

    void setLastMove(Move lastMove)
    {
        this.lastMove.setRow(lastMove.getRow());
        this.lastMove.setCol(lastMove.getCol());
        this.lastMove.setValue(lastMove.getValue());
    }

    void setLastPlayer(int lastPlayer)
    {
        this.lastPlayer = lastPlayer;
    }

    void makeMove(int row, int col, int letter){
        //if (isvalid(row,col,letter)){
            this.gameBoard[row][col] = letter;  //it is valid and the player makes a move in the spot [row,col]
            this.lastMove = new Move(row,col);  //store last players move
            this.lastPlayer = letter;           //store last players letter
            flipcheckers(row, col, letter);
        //}else{
            //System.out.println("Invalid move");
        //}
    }



    Boolean is_Valid(int row, int col, int value){
        boolean islegal = false;
        int checker = -1 * value;
        int x = row;
        int y = col;
        //first row
        if(row == 0){
            //left up edge
            if(col == 0){
                while(checker == gameBoard[x][y + 1]){
                    y++;
                    if(y == 7) break;
                    if(gameBoard[x][y + 1] == 0) break;
                    if(gameBoard[x][y + 1] == value){
                        islegal = true;
                        break;
                    }
                }
                y = col;
                while(checker == gameBoard[x + 1][y + 1]){
                    y++;
                    x++;
                    if(x == 7 && y == 7)break;
                    if(gameBoard[x + 1][y + 1] == 0) break;
                    if(gameBoard[x + 1][y + 1] == value){
                        islegal = true;
                        break;
                    }  
                }
                x = row; 
                y = col;
                while(checker == gameBoard[x + 1][y]){
                    x++;
                    if(x == 7)break;
                    if(gameBoard[x + 1][y] == 0) break;
                    if(gameBoard[x + 1][y] == value){
                        islegal = true;
                        break;
                    }  
                }
                x = row; 
                //right up edge    
            }else if(col == 7){
                while(checker == gameBoard[x][y - 1]){
                    y--;
                    if(y == 0) break;
                    if(gameBoard[x][y - 1] == 0) break;
                    if(gameBoard[x][y - 1] == value){
                        islegal = true;
                        break;
                    }
                }
                y = col;
                while(checker == gameBoard[x + 1][y - 1]){
                    y--;
                    x++;
                    if(x == 7 && y == 0)break;
                    if(gameBoard[x + 1][y - 1] == 0) break;
                    if(gameBoard[x + 1][y - 1] == value){
                        islegal = true;
                        break;
                    }  
                }
                x = row; 
                y = col;
                while(checker == gameBoard[x + 1][y]){
                    x++;
                    if(x == 7)break;
                    if(gameBoard[x + 1][y] == 0) break;
                    if(gameBoard[x + 1][y] == value){
                        islegal = true;
                        break;
                    }  
                }
                x = row; 
                //middle
            }else{
                while(checker == gameBoard[x][y + 1]){
                    y++;
                    if(y == 7) break;
                    if(gameBoard[x][y + 1] == 0) break;
                    if(gameBoard[x][y + 1] == value){
                        islegal = true;
                        break;
                    }
                }
                y = col;
                while(checker == gameBoard[x][y - 1]){
                    y--;
                    if(y == 0) break;
                    if(gameBoard[x][y - 1] == 0) break;
                    if(gameBoard[x][y - 1] == value){
                        islegal = true;
                        break;
                    }
                }
                y = col;
                while(checker == gameBoard[x + 1][y + 1]){
                    y++;
                    x++;
                    if(x == 7 && y == 7)break;
                    if(gameBoard[x + 1][y + 1] == 0) break;
                    if(gameBoard[x + 1][y + 1] == value){
                        islegal = true;
                        break;
                    }  
                }
                x = row; 
                y = col;
                while(checker == gameBoard[x + 1][y - 1]){
                    y--;
                    x++;
                    if(x == 7 && y == 0)break;
                    if(gameBoard[x + 1][y - 1] == 0) break;
                    if(gameBoard[x + 1][y - 1] == value){
                        islegal = true;
                        break;
                    }  
                }
                x = row; 
                y = col;
                while(checker == gameBoard[x + 1][y]){
                    x++;
                    if(x == 7)break;
                    if(gameBoard[x + 1][y] == 0) break;
                    if(gameBoard[x + 1][y] == value){
                        islegal = true;
                        break;
                    }  
                }
                x = row; 
            }


        }
        return true;
    }

    void flipcheckers(int row, int col, int letter){
        //if a move is made it flips the opponents checkers
        int opponent = ((letter == W) ? B : W);

        //flip up checkers
        int i = row -1; //direction is up so it is previous row
        int j = col;    //direction is up so same column

        while (i > 0 && this.gameBoard[i][j] == opponent){  //while we are still within the table's bounds and the checker belongs to an opponent
            if (this.gameBoard[i-1][j] == letter){          //if the checker is one of our own then we hava found the limit where we can flip the checkers
                while(i>0 && this.gameBoard[i][j] == opponent){ //while there are still checkers of our opponent within our limit
                    this.gameBoard[i][j] = letter;              //flip the checker
                    i++;                                        //go to next row
                }
            }
            i--;                                            //go to previous row to check if there is another opponent
        }

        //flip down checkers

        i = row + 1;  //direction is down so it is next row
        j = col;    //direction is down so same column

        while (i < 7 && this.gameBoard[i][j] == opponent){
            if (this.gameBoard[i+1][j] == letter){              //if the checker is one of our own then we hava found the limit where we can flip the checkers
                while(i>0 && this.gameBoard[i][j] == opponent){
                    this.gameBoard[i][j] = letter;
                    i--;
                }
            }
            i++;
        }

        //flip right checkers

        i = row; //direction is right so it is same row
        j = col + 1; //direction is right so it is next column

        while (j < 7 && this.gameBoard[i][j] == opponent){
            if (this.gameBoard[i][j+1] == letter){
                while(i>0 && this.gameBoard[i][j] == opponent){
                    this.gameBoard[i][j] = letter;
                    j--;
                }
            }
            j++;
        }

        //flip left checkers

        i = row; //direction is left so it is same row
        j = col - 1; //direction is left so it is previous column

        while (j > 0 && this.gameBoard[i][j] == opponent){
            if (this.gameBoard[i][j-1] == letter){
                while(i>0 && this.gameBoard[i][j] == opponent){
                    this.gameBoard[i][j] = letter;
                    j++;
                }
            }
            j--;
        }

        //flip up left

        i = row - 1; //direction is up left so it is previous row
        j = col - 1; //direction is up left so it is previous column

        while( i > 0 && j > 0 && this.gameBoard[i][j] == opponent){
            if (this.gameBoard[i-1][j-1] == letter){
                while(i > 0 && j > 0 && this.gameBoard[i][j] == opponent){
                    this.gameBoard[i][j] = letter;
                    j++;
                    i++;
                }
            }
            j--;
            i--;
        }

        //flip up right

        i = row - 1; //direction is up right so it is previous row
        j = col + 1; //direction is up right so it is next column

        while( i > 0 && j < 7 && this.gameBoard[i][j] == opponent){
            if (this.gameBoard[i-1][j+1] == letter){
                while(i > 0 && j > 0 && this.gameBoard[i][j] == opponent){
                    this.gameBoard[i][j] = letter;
                    j--;
                    i++;
                }
            }
            j++;
            i--;
        }

        //flip down left

        i = row + 1; //direction is down left so it is next row
        j = col - 1; //direction is downt so it is previous column

        while( i < 7 && j > 0 && this.gameBoard[i][j] == opponent){
            if (this.gameBoard[i+1][j-1] == letter){
                while(i < 7 && j > 0 && this.gameBoard[i][j] == opponent){
                    this.gameBoard[i][j] = letter;
                    j++;
                    i--;
                }
            }
            j--;
            i++;
        }

        //flip down right

        i = row + 1; //direction is down right so it is next row
        j = col + 1; //direction is down right so it is next column

        while( i < 7 && j < 7 && this.gameBoard[i][j] == opponent){
            if (this.gameBoard[i+1][j+1] == letter){
                while(i < 7 && j < 7 && this.gameBoard[i][j] == opponent){
                    this.gameBoard[i][j] = letter;
                    j--;
                    i--;
                }
            }
            j++;
            i++;
        }

    }
}