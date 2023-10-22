class Player 
{
	private int maxDepth;
    private int playerLetter;
	
	public Player() {}

    public Player(int maxDepth, int playerLetter)
    {
        this.maxDepth = maxDepth;
        this.playerLetter = playerLetter;
    }
	
	public Move MiniMax(Board board) {
        if (this.playerLetter == Board.B){
            return max(new Board(board), 0);
        }else {
            return min(new Board(board), 0);
        }
    }
	
	public Move max(Board board, int depth) {
        return null;
    }
	
	public Move min(Board board, int depth) {return null;}
}
