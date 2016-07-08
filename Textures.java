package com.game.src.main;

import java.awt.image.BufferedImage;

public class Textures {
	
	private SpriteSheet ss = null;
	
	public BufferedImage rock, planet1;
	public BufferedImage[] player = new BufferedImage[3];
	public BufferedImage[] enemy = new BufferedImage[3];
	public BufferedImage bullet;
	
	public Textures(Game game) 
	{
		ss = new SpriteSheet(game.getSpriteSheet());
		
		getTextures();
	}
	
	private void getTextures() {
		
		player[0] = ss.grabImage(1, 1, 32, 32);
		player[1] = ss.grabImage(1, 2, 32, 32);
		player[2] = ss.grabImage(1, 3, 32, 32);
		
		enemy[0] = ss.grabImage(3, 1, 32, 32);
		enemy[1] = ss.grabImage(3, 2, 32, 32);
		enemy[2] = ss.grabImage(3, 3, 32, 32);
		
		bullet = ss.grabImage(2, 1, 32, 32);
		rock = ss.grabImage(4, 1, 32, 32);
		planet1 = ss.grabImage(6, 1, 32, 32);
		
	}
	
}
