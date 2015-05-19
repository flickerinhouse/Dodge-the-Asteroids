package dodgetheasteroids;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*; 

public class DodgeTheAsteroids extends JFrame {
	GameWindow Win;
	StartUpScreen Startup;
	JLabel StartUpMessage;
	JLabel StartUpBackground;
	JButton StartGame;
	
	public DodgeTheAsteroids(){
		Startup = new StartUpScreen();
		add(Startup);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	private static void runGui(){
		DodgeTheAsteroids newGame = new DodgeTheAsteroids();
		newGame.setVisible(true);
	}
	
	
	private class StartUpScreen extends JPanel implements ActionListener{
		private StartUpScreen(){
			setSize(WIDTH*2,HEIGHT*2);
			StartUpBackground = new JLabel(new ImageIcon("Assets/background.png"));
			StartUpBackground.setLayout(new BoxLayout(StartUpBackground, BoxLayout.Y_AXIS));
			StartUpBackground.setBorder(null);
			add(StartUpBackground);
			
			StartUpMessage= new JLabel("Dodge The Asteroids");
			StartUpMessage.setFont(StartUpMessage.getFont().deriveFont(Font.BOLD, 28));
			StartUpMessage.setForeground(Color.WHITE);
			StartUpMessage.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			
			StartGame = new JButton();
			StartGame.addActionListener(this);
			StartGame.setText("Start Game");
			StartGame.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
			StartUpBackground.add(StartUpMessage);
			StartUpBackground.add(StartGame);
			pack();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Win = new GameWindow();
			JFrame newScreen = new JFrame();
			newScreen.setContentPane(Win);
			newScreen.pack();
		    newScreen.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		    newScreen.setVisible(true);
		    SwingUtilities.getWindowAncestor(this).setVisible(false); 
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				runGui();
			}
		});
	}
}
