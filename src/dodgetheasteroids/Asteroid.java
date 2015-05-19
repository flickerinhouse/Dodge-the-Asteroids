package dodgetheasteroids;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.*;

public class Asteroid extends MovingObject {

	private BufferedImage Asteroid;
	private BufferedImage HitMarker;
	private boolean canHit,wasHit,firstUpdateHit;
	private String hitpath = "Assets/HitMarker.png";
	
	public Asteroid(int x, int y,int width,int height){
		super(x,y,width,height);
		try {
			Asteroid = ImageIO.read(new File( "Assets/Meteor2.png"));
			HitMarker = ImageIO.read(new File(hitpath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Couldn't find asteroid picture");
		}
		canHit = true;
		wasHit= false;
		firstUpdateHit = false;
	}
	
	public BufferedImage getHitMarker(){
		return HitMarker;
	}
	
	public BufferedImage getAsteroid(){
		return Asteroid;
	}
	
	public boolean getHitter(){
		return canHit;
	}
	
	public void setHitter(boolean hitter){
		canHit = hitter;
	}
	
	public boolean wasHit(){
		return wasHit;
	}
	
	public void setHit(boolean isHit){
		wasHit = isHit;
	}
	
	public boolean firstHit(){
		return firstUpdateHit;
	}
	
	public void setfirstHit(boolean isHit){
		firstUpdateHit = isHit;
	}
}
