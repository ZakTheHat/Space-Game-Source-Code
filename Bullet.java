package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.src.main.classes.EntityA;

public class Bullet extends GameObject implements EntityA {

	BufferedImage image;
	private Textures tex;
	private Controller c;
	private Game game;
	private boolean forward;
	private int xVal = 0;
	private boolean mY;

	public Bullet(double x, double y, Textures tex, Controller c, Game game, Boolean forward, int xVal, boolean mY) {
		super(x, y);
		this.tex = tex;
		this.c = c;
		this.game = game;
		this.forward = forward;
		this.xVal = xVal;
		this.mY = mY;
	}

	public void tick() {
		if(mY) {
			if(forward)
				y -= 8 + (Game.LEVEL_SPEED * 2);
			else if(!forward)
				y += 8 + (Game.LEVEL_SPEED * 2);
		}

		if (y < 0)
			c.removeEntity(this);
		if (x < 0)
			c.removeEntity(this);

		if (x > Game.WIDTH * Game.SCALE - 32)
			c.removeEntity(this);
		if (y > Game.HEIGHT * Game.SCALE - 32)
			c.removeEntity(this);
		
		x += xVal;

	}

	public void render(Graphics g) {
		g.drawImage(tex.bullet, (int) x, (int) y, null);
	}

	public double getY() {
		return this.y;
	}

	public double getX() {
		return this.x;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}
