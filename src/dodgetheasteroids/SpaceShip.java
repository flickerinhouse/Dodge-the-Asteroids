package dodgetheasteroids;

import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class SpaceShip extends MovingObject{
	
	private BufferedImage MovingSpaceship;
	private BufferedImage StoppedSpaceShip;
	private BufferedImage LifeImage;
	private BufferedImage HitMarker;
	private BufferedImage LaserShot;
	
	private String ship1path = "Assets/rocketcut.png";
	private String ship2path = "Assets/rocketMove.png";
	private String lifepath = "Assets/rocketLives.png";
	private String hitpath = "Assets/HitMarker.png";
	
	private boolean isIdle,isRotating;
	private int lives;
	private boolean wasHit;

	public SpaceShip(int x, int y,int width, int height){
		super(x,y,width,height);
		try {
		    MovingSpaceship = ImageIO.read(new File(ship2path));
		    StoppedSpaceShip = ImageIO.read(new File(ship1path));
		    LifeImage = ImageIO.read(new File(lifepath));
		    HitMarker = ImageIO.read(new File(hitpath));
		} catch (IOException e) {
			System.out.println("Couldn't find file");
		}
		isIdle = true;
		lives = 3;
	}

	public BufferedImage getStopped(){
		return StoppedSpaceShip;
	}
	
	public BufferedImage getMoving(){
		return MovingSpaceship;
	}
	
	public BufferedImage getLife(){
		return LifeImage;
	}
	
	public BufferedImage getHitMarker(){
		return HitMarker;
	}
	
	public void setMoving(){
		isIdle = false;
	}
	
	public void setIdle(){
		isIdle = true;
	}
	
	public boolean getState(){
		return isIdle;
	}
	
	public void setRotating(){
		isRotating = true;
	}
	
	public void setNotRotating(){
		isRotating = false;
	}
	
	public boolean getRotating(){
		return isRotating;
	}
	
	public int getLives(){
		return lives;
	}
	
	public void getHit(){
		lives -= 1;
		wasHit = true;
	}
	
	public boolean wasHit(){
		return wasHit;
	}
	
	public void setHit(){
		wasHit = false;
	}
}