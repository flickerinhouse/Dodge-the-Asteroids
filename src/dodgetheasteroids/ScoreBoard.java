package dodgetheasteroids;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class ScoreBoard extends JPanel implements ActionListener{

	private String[] playerInfo;
	private int playerscore,finalscore;
    private int j = 0;
	private String line,line2;
	String[] columns = {"Name", "Score"};
	ArrayList<Integer> Scores = new ArrayList<Integer>();
	ArrayList<String> Names = new ArrayList<String>();
	
	private JLabel Name,Score,background;
	private JTextField enterNameField;
	private JButton confirmName;
	
	private File scoreSheet;
	private FileReader fr;
	private BufferedReader br;
	
	public ScoreBoard(int playerhits, int playerscore){
		setSize(WIDTH*2,HEIGHT*2);
		background = new JLabel(new ImageIcon("Assets/background.png"));
		background.setLayout(new GridLayout(0,2,10,5));
		background.setBorder(null);
		add(background);
		scoreSheet = new File("HighScores.data");
		this.playerscore = playerscore;
		finalscore = (playerhits*25) + playerscore;
		
		setUpTable();
		
		enterNameField = new JTextField();
		confirmName = new JButton();
		confirmName.setText("Enter Your Name");
		confirmName.setActionCommand("Enter Score");
		confirmName.addActionListener(this);

		background.add(enterNameField);
		background.add(confirmName);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String Action = e.getActionCommand();
		Names.add(enterNameField.getText());
		Scores.add(finalscore);
		sortScores();
		System.out.println(Scores.get(10));
		Scores.remove(10);
		Names.remove(10);
		
		if (Action.equals("Enter Score") && enterNameField != null){
			try {
				if (!scoreSheet.exists()){
					scoreSheet.createNewFile();
				}
				
				if (j < 10) {
					FileWriter fw = new FileWriter(scoreSheet.getAbsoluteFile(),true);
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(enterNameField.getText());
					bw.newLine();
					bw.write(Integer.toString(finalscore));
					bw.newLine();
					
					bw.close();
					fw.close();
				}else{
					FileWriter fw = new FileWriter(scoreSheet.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					for (int i = 0; i < 10;i++){
						bw.write(Names.get(i));
						bw.newLine();
						bw.write(Integer.toString(Scores.get(i)));
						bw.newLine();
					}
					bw.close();
					fw.close();
				}
				
				confirmName.setText("Play Again");
				confirmName.setActionCommand("Play Again");
			
			}catch (IOException e1){
				System.out.println("Could not write to file");
			}
		}else{
			JFrame screen = new JFrame();
		    screen.setContentPane(new GameWindow());
		    screen.pack();
		    screen.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		    screen.setVisible(true);
		    SwingUtilities.getWindowAncestor(this).setVisible(false); 
		}
	}
	
	private void setUpTable(){
		Name = new JLabel();
		Name.setText(columns[0]);
		Name.setHorizontalAlignment(JLabel.CENTER);
		
		Score = new JLabel();
		Score.setHorizontalAlignment(JLabel.CENTER);
		Score.setText(columns[1]);
		
		Name = setUpText(Name);
		Score = setUpText(Score);
		
		Name.setFont(Name.getFont().deriveFont(Font.BOLD, 32));
		Name.setForeground(Color.WHITE);
		Name.setHorizontalAlignment(JLabel.CENTER);
		
		Score.setFont(Score.getFont().deriveFont(Font.BOLD, 32));
		Score.setForeground(Color.WHITE);
		Score.setHorizontalAlignment(JLabel.CENTER);
		
		background.add(Name);
		background.add(Score);
		
		if (scoreSheet.exists()){
			/* trys to create file, error if it can't*/
			try {
				fr = new FileReader("HighScores.data");
			    br = new BufferedReader(fr);
			    
				while(((line = br.readLine())!= null && (line2 = br.readLine()) != null) && j < 10){
					Names.add(line);
					Scores.add(Integer.parseInt(line2));
					j++;
				}
				
				JLabel currentName;
				JLabel currentScore;
				sortScores();
				
				for (int i1 = 0; i1 < (Names.size() >10 ? 10: Names.size()); i1++){
					currentName = new JLabel(Names.get(i1));
					currentName = setUpText(currentName);
					currentName.setHorizontalAlignment(JLabel.CENTER);
					currentScore = new JLabel(Integer.toString(Scores.get(i1)));
					currentScore = setUpText(currentScore);
					currentScore.setHorizontalAlignment(JLabel.CENTER);
					background.add(currentName);
					background.add(currentScore);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Could not read from file");
			}
		}
	}
	
	private JLabel setUpText(JLabel label){
		label.setFont(label.getFont().deriveFont(Font.BOLD, 12));
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
	
	public void sortScores() {
        String tempName;
        int tempScore;
		
		int n =Scores.size();
        int k;
        
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++) {
                k = i + 1;
                if (Scores.get(i) < Scores.get(k)) {
                	tempName = Names.get(i);
                    tempScore = Scores.get(i);
                    Names.set(i, Names.get(k));
                    Scores.set(i, Scores.get(k));
                    Names.set(k,tempName);
                    Scores.set(k,tempScore);
                }
            }
        }
    }
}