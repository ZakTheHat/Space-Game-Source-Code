package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Player extends GameObject implements EntityA {

	Animation anim;
	private double velX = 0, velY = 0;
	private Textures tex;
	private Game game;
	private Controller c;

	public double getVelX() {
		return velX;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public double getVelY() {
		return velY;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	public Player(double x, double y, Textures tex, Game game, Controller c) {
		super(x, y);
		this.tex = tex;
		this.game = game;
		this.c = c;

		anim = new Animation(10, tex.player[0], tex.player[1], tex.player[2]);
	}

	public void tick() {

		x += velX;
		y += velY;

		anim.runAnimation();

		if (x <= 0)
			x = 0;
		if (y <= 0)
			y = 0;

		if (x >= Game.WIDTH * Game.SCALE - 32)
			x = Game.WIDTH * Game.SCALE - 32;
		if (y >= Game.HEIGHT * Game.SCALE - 32)
			y = Game.HEIGHT * Game.SCALE - 32;
		
		for(int i = 0; i < game.eb.size(); i++) 
		{
			
			EntityB tempEnt = game.eb.get(i);
			
			if(Physics.Collision(this, tempEnt))
			{
				c.removeEntity(tempEnt);
				Game.HEALTH -= 10;
				game.setEnemy_killed(game.getEnemy_killed() + 1);
			}
			
		}

	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void render(Graphics g) {

		anim.drawAnimation(g, x, y, 0);

	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}
