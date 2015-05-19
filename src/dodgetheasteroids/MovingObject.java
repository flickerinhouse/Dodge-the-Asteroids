package dodgetheasteroids;

import java.awt.*;

abstract class MovingObject {
	
	private int x,y;
	private int height,width;
	protected Rectangle hitbox;
	
	public MovingObject(int x, int y, int height, int width){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		hitbox = new Rectangle(x,y,width*8/10,height*8/10);
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void moveX(int x){
		this.x += x;
	}
	
	public void moveY(int y){
		this.y += y;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public Rectangle getHitBox(){
		return hitbox;
	}
	
	public void updateHitBox(){
		hitbox.setLocation(x, y);
	}
}
