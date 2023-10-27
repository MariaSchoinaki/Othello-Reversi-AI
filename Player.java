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
            //If the Black plays then it wants to maximize the heuristics value
            return max(new Board(board), 0);
        }else {
            //If the White plays then it wants to minimize the heuristics value
            return min(new Board(board), 0);
        }
    }
	
	public Move max(Board board, int depth) {
        return null;
    }
	
	public Move min(Board board, int depth) {return null;}
}
