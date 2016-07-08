package com.game.src.main;

import java.awt.Rectangle;

public class GameObject {
	
	public double x, y;
	
	public GameObject(double x, double y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getBounds(int w, int h) 
	{
		return new Rectangle((int)x,(int)y,w,h);	
	}
}
