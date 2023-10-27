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
	
	ArrayList<Board> getChildren(int letter) {return null;}
	
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
            //method that flips the opponents checkers
        //}else{
            //System.out.println("Invalid move");
        //}
    }
}