package dodgetheasteroids;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

public class Laser extends MovingObject {

	private String laserpath = "Assets/Laser.png";
	private BufferedImage Laser;
	private boolean shotFired;
	

	public Laser(int x, int y, int height, int width) {
		super(x, y, height,width);
		hitbox = new Rectangle(x,y,width,height);
		// TODO Auto-generated constructor stub
		try {
			Laser = ImageIO.read(new File(laserpath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Couldn't find file");
		}
	}
	
	public BufferedImage getLaser(){
		return Laser;
	}
	
	public void shotFired(boolean isFired){
		shotFired = isFired;
	}
	
	public boolean isFired(){
		return shotFired;
	}
	
}
