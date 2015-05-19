package dodgetheasteroids;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.*;

import java.io.*;
import java.text.AttributedString;
import java.util.Random;
import java.util.ArrayList;

/* todo make it so that it always shows hit marker for hit asteroids*/


public class GameWindow extends JPanel implements ActionListener,KeyListener{
	
	public static final int WIDTH = 240;
	public static final int HEIGHT = 320;
	public static final int SCALE = 2;
	
	private int xpos,ypos,meteorhits,playerscore;
	private boolean goDown,isGameOver;
	
	private Timer timer;
	
	private ScoreBoard ScoreKeeper;
	private SpaceShip Player;
	private ArrayList<Asteroid> Asteroids;
	private Laser Laser;
	private Graphics2D g2d;
	private BufferedImage background;
	public Random randint;
	
	
	
	public GameWindow(){
		randint = new Random();
		/* creates players character, object for laser, and array for asteroids*/
		Player= new SpaceShip(WIDTH-30,HEIGHT*SCALE -60,WIDTH/4,HEIGHT/5);
		setSurfaceSize();
		Laser = new Laser(Player.getX()+(Player.getWidth() *5/12), Player.getY()-Player.getHeight()*3/4, Player.getHeight()*3/4, Player.getWidth()/5);
		Asteroids = new ArrayList<Asteroid>();
		setBackground();
		/* lets you have key listeners and creates one*/
		setFocusable(true);
		addKeyListener(this);
		/* starts game*/
		isGameOver = false;
		meteorhits = 0;
		playerscore= 0;
		initTimer();
		
		
		for (int i = 0;i > -5;i--){
			xpos = randint.nextInt(SCALE*WIDTH);
			ypos = i * HEIGHT;
			Asteroids.add(new Asteroid(xpos,ypos,WIDTH/4,HEIGHT/5));
			}
		}
	
	private void initTimer(){
		timer = new Timer(100, this);
	    timer.start();
	}
	
	private void stopTimer(){
		timer.stop();
	}
	
	/* if ship is moving, draws it with tail flame, otherwise draws it stationary*/
	private void doDrawShip(Graphics g){
		g2d = (Graphics2D) g.create();
		
		if (Player.getState()){
			g2d.drawImage(Player.getStopped(),Player.getX(),Player.getY(),Player.getWidth(),Player.getHeight(),null);
		}else{
			g2d.drawImage(Player.getMoving(),Player.getX(),Player.getY(),Player.getWidth(),Player.getHeight(),null); 
		}
		g2d.dispose();
	}
	
	/* draws all the asteroids, if an asteroid is hit, it draws it with a hit marker over top of it*/
	private void doDrawAsteroids(Graphics g){
		g2d = (Graphics2D) g.create();
		
		for (Asteroid Asteroid: Asteroids){
			if(!Asteroid.wasHit()){
				g2d.drawImage(Asteroid.getAsteroid(),Asteroid.getX(),Asteroid.getY(),Asteroid.getWidth(),Asteroid.getHeight(),null); 
			}else{
				g2d.drawImage(Asteroid.getAsteroid(),Asteroid.getX(),Asteroid.getY(),Asteroid.getWidth(),Asteroid.getHeight(),null); 
				g2d.drawImage(Asteroid.getHitMarker(), Asteroid.getX(), Asteroid.getY(), Asteroid.getWidth(), Asteroid.getHeight(), null);
			}
		}
		
		g2d.dispose();
	}
	
	/* draws the background*/
	private void doDrawBackground(Graphics g){
		g2d = (Graphics2D) g.create();
		g2d.drawImage(background,0,0,WIDTH*SCALE+Player.getWidth(),HEIGHT*SCALE,null);
		g2d.setFont(new Font("Helvetica", Font.BOLD, 20));
		g2d.setColor(Color.white);
		g2d.drawString("Score: " + Integer.toString(playerscore), 0,20);
		g2d.dispose();
	}
	
	/* draws lives, ships hit marker, and laser*/
	private void doDrawLives(Graphics g){
		g2d = (Graphics2D) g.create();
		for (int i = 0; i < Player.getLives(); i ++){
			g2d.drawImage(Player.getLife(),WIDTH*SCALE + WIDTH/9,i*HEIGHT/8, HEIGHT/10,WIDTH/8, null);
		}
		if (Player.wasHit()){
			g2d.drawImage(Player.getHitMarker(), Player.getX(), Player.getY(), Player.getWidth(), Player.getHeight(), null);
		}
		if (Laser.isFired()){
			g2d.drawImage(Laser.getLaser(), Laser.getX(), Laser.getY(), Laser.getWidth(), Laser.getHeight(), null);
		}
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		doDrawBackground(g);
		if (!isGameOver){
			doDrawShip(g);
			doDrawAsteroids(g);
			doDrawLives(g);
		}else{
			g.setFont(new Font("Helvetica", Font.BOLD, 20));
			g.setColor(Color.white);
			JFrame screen = new JFrame();
		    screen.setContentPane(new ScoreBoard(meteorhits,playerscore));
		    screen.pack();
		    screen.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		    screen.setVisible(true);
		    SwingUtilities.getWindowAncestor(this).setVisible(false); 
		}
	}
	
	private void setSurfaceSize(){
		Dimension d = new Dimension();
        d.width = WIDTH*SCALE+Player.getWidth();
        d.height = HEIGHT*SCALE;
        setPreferredSize(d);        
	    }
	
	private void setBackground(){
		try {
			background = ImageIO.read(new File("Assets/background.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not find picture of background");
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/* if you have no lives, game ends*/
		if (Player.getLives() > 0) {
			step();
			repaint();
		}else{
			stopTimer();
			isGameOver = true;
			repaint();
		}
	}

	
	public void step(){
		playerscore += 5;
		/* if hit before, this sets you as not hit so you can be hit now and hit marker goes away*/
		Player.setHit();
		/* if laser is fired, it moves it up */
		if (Laser.isFired()){
			Laser.moveY(-HEIGHT/6);
			Laser.updateHitBox();
			/* If the laser is off the map, it lets you fire again */
			if (Laser.getY() < 0){
				Laser.setY(Player.getY()-Player.getHeight()*3/4);
				Laser.setX(Player.getX()+(Player.getWidth() *5/12));
				Laser.shotFired(false);
			}
		}
		for (Asteroid asteroid: Asteroids){
			asteroid.setfirstHit(false);
			/* If the Laser hits an asteroid, the asteroid will display hit market and then go to the top */
			if (Laser.getHitBox().intersects(asteroid.getHitBox()) && Laser.isFired()){
				asteroid.setHitter(false);
				asteroid.setfirstHit(true);
			}
			
			if ((asteroid.getY() > HEIGHT*SCALE || asteroid.wasHit())){
				asteroid.setHit(false);
				asteroid.setY(0);
				asteroid.setX(randint.nextInt(WIDTH*SCALE));
				asteroid.updateHitBox();
				asteroid.setHitter(true);
			
			}else{
				asteroid.moveY(HEIGHT/6);
				asteroid.updateHitBox();
			}
			
			if (Player.getHitBox().intersects(asteroid.getHitBox()) && asteroid.getHitter()){
				Player.getHit();
				asteroid.setHitter(false);
			}
			
			if ((Laser.getHitBox().intersects(asteroid.getHitBox()) && Laser.isFired()) || asteroid.firstHit()){
				asteroid.setHit(true);
				asteroid.setHitter(false);
				Laser.shotFired(false);
				Laser.setY(Player.getY()-Player.getHeight()*3/4);
				Laser.setX(Player.getX()+(Player.getWidth() *5/12));
				meteorhits += 1;
			}
		if (Player.getY() < HEIGHT*SCALE-60 && goDown ){
			Player.moveY(15);
			Player.updateHitBox();
		}
	}
}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT && Player.getX() > 0) {
			Player.setMoving();
	    	Player.moveX(-30);
	    	Player.updateHitBox();
	    	if (!Laser.isFired()){
	    		Laser.moveX(-30);
	    		Laser.updateHitBox();
	    	}
	    }

	    if (key == KeyEvent.VK_RIGHT && Player.getX() < SCALE*WIDTH) {
	    	Player.setMoving();
	    	Player.moveX(30);
	    	Player.updateHitBox();
	    	if (!Laser.isFired()){
	    		Laser.moveX(30);
	    		Laser.updateHitBox();
	    	}

	    }

	    if (key == KeyEvent.VK_UP && Player.getY() > 0) {
	        Player.setMoving();
	    	Player.moveY(-30);
	    	Player.updateHitBox();
	    	goDown = false;
	    	if (!Laser.isFired()){
	    		Laser.moveY(-30);
	    		Laser.updateHitBox();
	    	}
	    }

	    if (key == KeyEvent.VK_DOWN && Player.getY() < SCALE*WIDTH) {
	        Player.setMoving();
	    	Player.moveY(30);
	    	Player.updateHitBox();
	    	if (!Laser.isFired()){
	    		Laser.moveY(30);
	    		Laser.updateHitBox();
	    	}
	    }
	  
	    if (key == KeyEvent.VK_SPACE){
	    	Laser.shotFired(true);
	    }
	    repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		Player.setIdle();
		if (Player.getY() < HEIGHT*SCALE){
			goDown = true;
		}
		repaint();
	}
	
}
	
	
