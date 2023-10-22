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
        this.lastPlayer = B;
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
    public Board(Board board) {}
	
	public void print() {
        for(int row = 0; row < this.dimension; row++)
        {
            for(int col = 0; col < this.dimension; col++)
            {
                switch (this.gameBoard[row][col]) {
                case B:
                    System.out.print("\u001B[30;47m 1 \u001B[0m");
                    break; 
                case W:
                    System.out.print("\u001B[97;40m 2 \u001B[0m");
                    break;
                case EMPTY:
                System.out.print("\u001B[32;45m G \u001B[0m"); 
                }
            }
            System.out.println();
        }
    }
	
	ArrayList<Board> getChildren(int letter) {return null;}
	
	public int evaluate () {return 0;}
	
	public boolean isTerminal() {return true;}
	
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
}