import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Window extends JPanel implements MouseListener {
    
    Player player = new Player();
    Player AI = new Player();
    int playerTurn;
    Board board = new Board();
    int[][] boardData;

    JFrame frame = new JFrame();
    JPanel startingScreen = new JPanel();
    JPanel panel = new JPanel();
    JPanel endScreen = new JPanel();

    // JLabel label = new JLabel("Enter your name:");
    // JTextField textField = new JTextField();
    // JButton button = new JButton("Say Hello!");
    //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    public Window(){

        //Showing window attributes
        frame.setTitle("Othello");
        frame.setLocationRelativeTo(null);
        frame.setLocation(450,150);
        frame.setPreferredSize(new Dimension(490,600));

        //Panels Dimensions
        startingScreen.setPreferredSize(new Dimension(490,510));
        panel.setPreferredSize(new Dimension(490,510));
        endScreen.setPreferredSize(new Dimension(490,510));
        frame.setResizable(false);      //to not resize window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(45,78,90));  //Window background color


        //Buttons

        JButton startgame = new JButton("Start");
        startgame.setBackground(new Color(240,255,240));
        startgame.setForeground(Color.black);
        startgame.setFont(new Font("Georgia", Font.BOLD,24));
        startgame.setBorder(BorderFactory.createBevelBorder(1, new Color(205,239,171), Color.white));
        

        ButtonGroup difficulty = new ButtonGroup();

        JRadioButtonMenuItem one = new JRadioButtonMenuItem("1");
        one.setBackground(new Color(240,255,240));
        one.setForeground(Color.black);
        one.setFont(new Font("Georgia", Font.BOLD,14));
        one.setBorder(BorderFactory.createBevelBorder(1, new Color(205,239,171), Color.white));
        
        JRadioButtonMenuItem two = new JRadioButtonMenuItem("2");
        two.setBackground(new Color(240,255,240));
        two.setForeground(Color.black);
        two.setFont(new Font("Georgia", Font.BOLD,14));
        two.setBorder(BorderFactory.createBevelBorder(1, new Color(205,239,171), Color.white));
        
        JRadioButtonMenuItem three = new JRadioButtonMenuItem("3");
        three.setBackground(new Color(240,255,240));
        three.setForeground(Color.black);
        three.setFont(new Font("Georgia", Font.BOLD,14));
        three.setBorder(BorderFactory.createBevelBorder(1, new Color(205,239,171), Color.white));
        
        JRadioButtonMenuItem four = new JRadioButtonMenuItem("4");
        four.setBackground(new Color(240,255,240));
        four.setForeground(Color.black);
        four.setFont(new Font("Georgia", Font.BOLD,14));
        four.setBorder(BorderFactory.createBevelBorder(1, new Color(205,239,171), Color.white));
        
        JRadioButtonMenuItem five = new JRadioButtonMenuItem("5");
        five.setBackground(new Color(240,255,240));
        five.setForeground(Color.black);
        five.setFont(new Font("Georgia", Font.BOLD,14));
        five.setBorder(BorderFactory.createBevelBorder(1, new Color(205,239,171), Color.white));
        
        difficulty.add(one); difficulty.add(two); difficulty.add(three); difficulty.add(four); difficulty.add(five);


        ButtonGroup turn = new ButtonGroup();

        JRadioButtonMenuItem first = new JRadioButtonMenuItem(" 1st");
        first.setBackground(new Color(240,255,240));
        first.setForeground(Color.black);
        first.setFont(new Font ("Georgia", Font.BOLD, 14));
        first.setBorder(BorderFactory.createBevelBorder(1, new Color(205,239,171), Color.white));
        
        //Button Second of Starting Panel
        JRadioButtonMenuItem second = new JRadioButtonMenuItem(" 2nd");
        second.setBackground(new Color(240,255,240));
        second.setForeground(Color.black);
        second.setFont(new Font ("Georgia", Font.BOLD, 14));
        second.setBorder(BorderFactory.createBevelBorder(1, new Color(205,239,171), Color.white));
        
        turn.add(first); turn.add(second);

        JButton exit = new JButton("Exit");
        exit.setBackground(new Color(240,255,240));
        exit.setForeground(Color.black);
        exit.setFont(new Font ("Georgia", Font.BOLD, 16));
        exit.setBorder(BorderFactory.createBevelBorder(1, new Color(205,239,171), Color.white));
        
        //Button newGame of Ending Panel
        JButton newGame = new JButton("New Game");
        newGame.setBackground(new Color(240,255,240));
        newGame.setForeground(Color.black);
        newGame.setFont(new Font ("Georgia", Font.BOLD, 16));
        newGame.setBorder(BorderFactory.createBevelBorder(1, new Color(205,239,171), Color.white));
    
        //--------------------------------------------------------------------------------------------------------------
        // Action Listeners


        //when user clicks exit, the window closes
        exit.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                System.exit(0);
            }
        });

        //Difficulty buttons action listeners
        one.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                player.setMaxDepth(1);
                AI.setMaxDepth(1);
            }
        });

        two.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                player.setMaxDepth(2);
                AI.setMaxDepth(2);
            }
        });

        three.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                player.setMaxDepth(3);
                AI.setMaxDepth(3);
            }
        });

        four.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                player.setMaxDepth(4);
                AI.setMaxDepth(4);
            }
        });

        five.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                player.setMaxDepth(5);
                AI.setMaxDepth(5);
            }
        });

        //Turn buttons action listener

        first.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                player.setPlayerLetter(Board.B);
                playerTurn = 1;
                AI.setPlayerLetter(Board.W);
            }
        });

        second.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                player.setPlayerLetter(Board.W);
                playerTurn = 2;
                AI.setPlayerLetter(Board.B);
            }
        });

        //when player wants to start their game, the starting screen with all
        //the player's choises are removed and the main screen (panel) is added
        startgame.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                frame.remove(startingScreen);
                frame.add(panel);
                frame.pack();
                frame.repaint();
            }
        });

        //when player wants to start a new game, we restart Window, meaning
        //we initialize again all data, remove ending screen and add staring screen
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                board = new Board();
                player = new Player();
                AI = new Player();
                //scores to be added
                frame.remove(endScreen);
                frame.add(startingScreen);
                frame.pack();
                frame.repaint();
            }
        });


        //----------------------------------------------------------------------------------
        //starting screen panel formating

        startingScreen = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);

                g.setColor(new Color(230,230,250));
                g.fillRect(0, 0, 1000, 1000);
                
                //add image
                // try{
                //     g.drawImage(ImageIO.read(new File("browndaisy.jpg")),0,0,null);
                // } catch (IOException e){
                //     e.printStackTrace();
                // }

                startgame.setBounds(190,100,100,45);

                g.setFont(new Font("Georgia", Font.BOLD, 17));
                g.setColor(Color.black);

                // Button group Title
                g.drawString("Difficulty" , 105, 215);
                // Button locations and size
                one.setBounds(114, 230, 80, 30);
                two.setBounds(114, 265, 80, 30);
                three.setBounds(114, 300, 80, 30);
                four.setBounds(114, 335, 80, 30);
                five.setBounds(114, 370, 80, 30);
                g.drawString("Turn" , 310, 215);
                first.setBounds(290, 230, 80, 30);
                second.setBounds(290, 265, 80, 30);

                //space to add preselected buttons if we want

                //add buttons to starting screen panel
                startingScreen.add(startgame); 
                startingScreen.add(one); startingScreen.add(two); startingScreen.add(three); startingScreen.add(four); startingScreen.add(five);
                startingScreen.add(first); startingScreen.add(second);
            }

            @Override
            public Dimension getPreferredSize() {            	
                  return new Dimension(481, 505);   
            }
            
        };


        //action listener to starting screen

        startingScreen.addMouseListener(this);

        //game scree panel formating
        panel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);

                //empty board
                for(int i = 0; i < board.getGameDimension(); i++){
                    for(int j = 0; j < board.getGameDimension(); j++){
                        g.setColor(new Color(11,102,35));
                        g.fillRect(i * 60, j * 60, 60, 60);
                        g.setColor(Color.black);
                        g.drawRect(i*60, j*60, 60, 60); //draw black square border
                    }
                }

                boardData = board.getGameBoard();
                for(int i = 0; i < board.getGameDimension(); i++){
                    for(int j = 0; j < board.getGameDimension(); j++){
                        switch(boardData[i][j]){
                            case Board.EMPTY: break;
                            case Board.W:
                                g.setColor(Color.white);
                                g.fillOval(10 + i*60, 10+j*60, 40, 40);
                                g.setColor(Color.GRAY);
                    	        g.drawOval(10+i * 60, 10+j * 60, 40, 40);
                                break;
                            case Board.B:
                                g.setColor(Color.black);
                                g.fillOval(10 + i*60, 10+j*60, 40, 40);
                                g.setColor(Color.GRAY);
                    	        g.drawOval(10+i * 60, 10+j * 60, 40, 40);
                                break;
                        }
                    }
                }

                //scores to be added
                //if player cant play, display message
                //display move messages

                g.setColor(Color.black);
                g.setFont(new Font("Georgia", Font.PLAIN, 15));
                if(playerTurn == 1){
                    if(board.getLastPlayer() == Board.B){
                        g.drawString("AI's Turn! Click to continue.", 30, 500);
                    }else{
                        g.drawString("Your turn!", 30, 500);
                    }
                }else{
                    if(board.getLastPlayer() == Board.W){
                        g.drawString("AI's Turn! Click to continue.", 30, 500);
                    }else{
                        g.drawString("Your turn!", 30, 500);
                    }
                }


            }

            @Override
            public Dimension getPreferredSize() {            	
                return new Dimension(481, 505);   
            }
        };

        // Adding action listener to panel
        panel.addMouseListener(this);


        //end screen panel formating

        endScreen = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);

                // Setting window background color
                g.setColor(new Color(230,230,250));
                g.fillRect(  0,   0, 1000, 1000);

                // try {                    
                //     g.drawImage(ImageIO.read(new File("browndaisy.jpg")),  0,  0,  null);
                // } catch (IOException e) {
                //     e.printStackTrace();
                // }

                //scores to be added

                //text to be added

                endScreen.add(newGame);
                endScreen.add(exit);
            }

            @Override 										// No more dimensions needed
            public Dimension getPreferredSize() {            	
                return new Dimension(481, 505);   
            }
        };

        endScreen.addMouseListener(this);
        frame.add(startingScreen);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent arg0){
        int x,y, i = 0, j = 0;
        x =arg0.getX();         //mouse coordinates
        y = arg0.getY();

        j = y/60;
        i = x/60;

        frame.repaint();

        if(board.isTerminal()){
            frame.remove(panel);
            frame.add(endScreen);
            frame.pack();
            frame.repaint();
        }

        switch(board.getLastPlayer()){
            case Board.B:
                if(playerTurn == 2){   //player plays 
                    //if he can play
                    Move moveP = new Move(i,j, Board.W);
                    board.makeMove(moveP.getRow(), moveP.getCol(), Board.W);
                }else{                  //AI plays
                    Move moveAI = AI.MiniMax(board);
                    board.makeMove(moveAI.getRow(), moveAI.getCol(), Board.W);
                }
                panel.repaint();
                break;
            case Board.W:
                if(playerTurn == 1){   //player plays 
                    //if he can play
                    Move moveP = new Move(i,j, Board.B);
                    board.makeMove(moveP.getRow(), moveP.getCol(), Board.B);
                }else{                  //AI plays
                    Move moveAI = AI.MiniMax(board);
                    board.makeMove(moveAI.getRow(), moveAI.getCol(), Board.B);
                }
                panel.repaint();
                break;
        }
    }

    @Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new Window(); // Creates and opens new Window
            }
        });
    }

}