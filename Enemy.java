package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Enemy extends GameObject implements EntityB {

	private Random r;
	private int speed;

	private int health = 5;

	public static int total = 1;

	private Controller c;
	private Game game;// if null pointer put game before controller c

	private Animation anim;

	public Enemy(double x, double y, Textures tex, Game game, Controller c) {
		super(x, y);
		r = new Random();
		speed = (r.nextInt(3) + 1);
		anim = new Animation(10, tex.enemy[0], tex.enemy[1], tex.enemy[2]);
		this.c = c;
		this.game = game;
	}

	private int right = 1;

	public void tick() {
		y += speed;

		if (Game.WAVE >= 4) {
			if (right == 1)
				if ((r.nextInt(2) + 1) == 1) {
					right = 10;
				} else {
					right = 20;
				}

			if (right == 10) {
				x += (r.nextInt(2) + 1);
			}

			if (right == 20) {
				x -= (r.nextInt(2) + 1);
			}

			if (x > Game.WIDTH * Game.SCALE - 32) {
				x -= 600;
			}

			if (x < 0) {
				x += Game.WIDTH * Game.SCALE - 32;
			}

		}

		anim.runAnimation();

		if (y > Game.HEIGHT * Game.SCALE) {
			y = -8;
			x = r.nextInt(Game.WIDTH * Game.SCALE - 32);
		}

		for (int i = 0; i < game.ea.size(); i++) {
			EntityA tempEnt = game.ea.get(i);

			if (Physics.Collision(this, tempEnt)) {
				health--;

				c.removeEntity(tempEnt);
				if (health == 0) {
					c.removeEntity(this);
					game.setEnemy_killed(game.getEnemy_killed() + 1);
					Game.POINTS++;
				}
			}

		}

	}

	public void render(Graphics g) {
		anim.drawAnimation(g, x, y, 0);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}
