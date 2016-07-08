package com.game.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static void main(String args[]) {

		Game game = new Game();

		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(game.TITLE);

		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		game.start();
	}

	public static void infoBox(String infoMessage, String titleBar) {
		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
	}

	public boolean confirm(String msg, String title) {

		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog(this, msg, title, dialogButton);
		if (dialogResult == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	public final String TITLE = "2D Space Game";
	private boolean running = false;
	private Thread thread;
	private BufferedImage spriteSheet = null;
	private Player p;
	private Controller c;
	private boolean is_shooting = false;
	private Textures tex;
	public static int enemy_count = 1;
	public static int enemy_killed = 0;
	public static LinkedList<EntityA> ea;
	public static LinkedList<EntityB> eb;
	public static STATE state = STATE.MENU;
	private Menu menu;
	private int CFPS = 0;
	private int CUPS = 0;
	public static int WAVE = 1;
	public static int HEALTH = 100;
	public static int POINTS = 0;
	public static int LEVEL_SPEED = 1;
	public static int LEVEL_HEALTHR = 0;
	public static boolean level5gun = false;
	public static boolean level5gunB = false;
	public static boolean level10gun = false;
	public static boolean level10gunB = false;
	public int tempRegen = 0;
	private Random r;
	private int loca[];

	public static enum STATE {

		MENU, GAME, PAUSE, SHOP, HELP

	};

	public int getEnemy_count() {
		return enemy_count;
	}

	public void setEnemy_count(int enemy_count) {
		Game.enemy_count = enemy_count;
	}

	public int getEnemy_killed() {
		return enemy_killed;
	}

	public void setEnemy_killed(int enemy_killed) {
		Game.enemy_killed = enemy_killed;
	}

	public void init() {
		r = new Random();
		
		for(int i = 0; i < 230; i++) {
			//loca[]
		}
		
		BufferedImageLoader loader = new BufferedImageLoader();

		try {
			spriteSheet = loader.loadImage("/sprite_sheet.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput());

		tex = new Textures(this);

		c = new Controller(tex, this);

		p = new Player(WIDTH * SCALE / 2, HEIGHT * SCALE / 2, tex, this, c);

		c.createEnemy(enemy_count);

		ea = c.getEntityA();
		eb = c.getEntityB();

		menu = new Menu();
		
	}

	private synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public void run() {

		this.requestFocus();
		init();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				CFPS = frames;
				CUPS = updates;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
				if (!(HEALTH == 100) && (check) )
					HEALTH += LEVEL_HEALTHR;
			}
		}stop();

	}
	
	private boolean check = true;

	private void tick() {
		
		if(state == STATE.SHOP) {
			check = false;
		}
		
		if (state == STATE.GAME) {

			p.tick();

			c.tick();
			
			check = true;

			if (WAVE >= 5) {
				level5gun = true;
			}

			if (WAVE >= 10) {
				level10gun = true;
			}

			if (HEALTH > 100) {
				int differ = HEALTH - 100;
				HEALTH -= differ;
			}

			if (WAVE == 26) {
				reset();
				infoBox("You have won!", "Game Info - WIN");
				reset();
				state = STATE.MENU;
			}

			if (HEALTH == 0 || HEALTH <= 0) {
				infoBox("You Lose.\n Top Wave:  " + WAVE, "Game Info");
				reset();
				state = STATE.MENU;
			}

			if (enemy_killed >= enemy_count) {
				enemy_count += 2;
				enemy_killed = 0;
				c.createEnemy(enemy_count);
				WAVE++;
				POINTS++;
			}
		}
	}
	

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		////////////////////////////////
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,WIDTH * SCALE, HEIGHT * SCALE);
		
		
		g.drawImage(tex.planet1, 32, 42, null);
		g.setColor(Color.WHITE);
		g.drawString("FPS:  " + CFPS + " ,  UPDATES:  " + CUPS, 12, 120);

		if (state == STATE.GAME) {
			p.render(g);
			c.render(g);
			g.setColor(Color.WHITE);
			g.drawString("CURRENT WAVE:  " + WAVE, 12, 140);
			g.drawString("POINTS:  " + POINTS, 12, 160);

			g.setColor(Color.GRAY);
			g.fillRect(WIDTH + 115, 5, 200, 40);

			g.setColor(Color.green);
			g.fillRect(WIDTH + 115, 5, HEALTH * 2, 40);

			g.setColor(Color.white);
			g.drawRect(WIDTH + 115, 5, 200, 40);

		} else if (state == STATE.MENU) {

			menu.render(g);

		} else if (state == STATE.PAUSE) {

			g.setColor(Color.WHITE);
			g.drawString("PAUSED", WIDTH * SCALE / 2 - 16, HEIGHT * SCALE / 2 - 16);

		} else if (state == STATE.SHOP) {

			Font fnt0 = new Font("arial", Font.BOLD, 10);

			Graphics2D g2d = (Graphics2D) g;

			g.setFont(fnt0);

			g2d.draw(new Rectangle(275, 40, 260, 300));

			g.drawString("Bullet Speed upgrade \n Requires 12 points.", 285, 80);
			g.drawString("You have " + POINTS + " points. Bullet Speed Level " + LEVEL_SPEED, 285, 90);

			if (LEVEL_HEALTHR <= 6) {

				g.drawString("Upgrade Health regen. \n Requires 14 points", 285, 121);
				g.drawString("You have " + POINTS + " POINTS \n Health regen power Level " + (LEVEL_HEALTHR + 1), 285,
						131);

			} else {
				g.drawString("Upgrade Health regen. \n Requires 14 points", 285, 121);
				g.drawString("You have " + POINTS + " POINTS \n Health regen power Level " + (LEVEL_HEALTHR + 1), 285,
						131);
				g.setColor(Color.red);
				g.drawString("MAXED OUT", 285, 141);
				g.setColor(Color.white);
			}

			if (level5gun == true) {
				if (!level5gunB)
					g.drawString("Buy Level 5 Gun Requires 24 points", 285, 201);
				else if(level5gunB){
					g.drawString("Buy Level 5 Gun Requires 24 points", 285, 201);
					g.setColor(Color.RED);
					g.drawString("BOUGHT", 475, 201);
					g.setColor(Color.WHITE);
				}

			}
			
			if (level10gun) {
				if (!level10gunB)
					g.drawString("Buy Level 10 Gun Requires 30 points", 285, 240);
				else if(level10gunB){
					g.drawString("Buy Level 10 Gun Requires 30 points", 285, 240);
					g.setColor(Color.RED);
					g.drawString("BOUGHT", 475, 240);
					g.setColor(Color.WHITE);
				}

			}

			g.drawString("Fill up health \n Requires 20 points", 285, 171);
			g.drawString("You have " + POINTS + " POINTS", 285, 181);
		}
		if(state == STATE.HELP) 
		{
			
			Font fnt2 = new Font("alien encounters", Font.BOLD, 50);
			
			g.setFont(fnt2);
			
			g.drawString("Space Game", WIDTH / 2, 100);
			g.drawString("Help", WIDTH / 2 + 110, 140);
			
			Rectangle backButton = new Rectangle((Game.WIDTH / 2 + 120), 350, 100, 50);
			
			Graphics2D g2d = (Graphics2D)g;
			
			Font fnt1 = new Font("arial", Font.BOLD, 30);
			
			g.setFont(fnt1);
			
			g2d.draw(backButton);
			
			g.drawString("Back", backButton.x + 19, backButton.y + 30);
			
			Font fnt4 = new Font("arial", Font.BOLD, 10);
			
			g.setFont(fnt4);
			
			g.setColor(Color.BLACK);
			g.fillRect(15, 160, 250, 60);
			
			g.setColor(Color.WHITE);
			g.drawString("Enter key = shop, Backspace key = exit shop", 15, 160);
			g.drawString("P key = pause, U key = unpause", 15, 175);
			g.drawString("Spacebar key = shoot", 15, 190);
			g.drawString("Move with WASD or Arrow Keys", 15, 205);
			Font fnt5 = new Font("cracked johnnie", Font.BOLD, 25);
			g.setFont(fnt5);
			g.drawString("Created by Zak Venter", HEIGHT * SCALE / 2 - 64, HEIGHT * SCALE / 2 + 64);
			
		}
		
		///////////////////////////////
		g.dispose();
		bs.show();
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public void setSpriteSheet(BufferedImage spriteSheet) {
		this.spriteSheet = spriteSheet;
	}
	
	public static void reset() {
		Game.HEALTH = 100;
		Game.WAVE = 1;
		Game.enemy_count = 1;
		Game.enemy_killed = 0;
		for(int i = 0; i < ea.size(); i++) {
			ea.remove(i);
		}
		for(int i = 0; i < eb.size(); i++) {
			eb.remove(i);
		}
	}

	@SuppressWarnings("static-access")
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (state == STATE.PAUSE) {

			if (key == e.VK_U) {
				state = STATE.GAME;
			}

		}

		if (state == STATE.SHOP) {

			if (key == e.VK_BACK_SPACE) {
				state = STATE.GAME;
			}

			if (key == e.VK_ESCAPE) {
				if (confirm("Are you want to quit?", "CONFIRMATION - exit")) {
					System.exit(0);
				} else
					return;
			}

		}

		if (state == STATE.GAME) {
			if (key == e.VK_ESCAPE) {
				if (confirm("Are you sure you want to quit?", "Confirmation to quit"))
					state = STATE.MENU;
			} else if (key == e.VK_RIGHT || key == e.VK_D) {
				p.setVelX(+5);

			} else if (key == e.VK_LEFT || key == e.VK_A) {
				p.setVelX(-5);

			} else if (key == e.VK_UP || key == e.VK_W) {
				p.setVelY(-5);

			} else if (key == e.VK_DOWN || key == e.VK_S) {
				p.setVelY(+5);
			} else if (key == e.VK_SPACE && !is_shooting) {
				if (!level5gunB && !level10gunB) {
					c.addEntity(new Bullet(p.getX(), p.getY() - 32, tex, c, this, true, 0, true));
				} else if (level5gunB && !level10gun) {
					c.addEntity(new Bullet(p.getX() + 8, p.getY() - 32, tex, c, this, true, 1, true));
					c.addEntity(new Bullet(p.getX() - 8, p.getY() - 32, tex, c, this, true, -1, true));
				} else if(level10gun) {
					c.addEntity(new Bullet(p.getX(), p.getY() - 32, tex, c, this, true, 0, true));
					c.addEntity(new Bullet(p.getX() + 8, p.getY() - 32, tex, c, this, true, 3, true));
					c.addEntity(new Bullet(p.getX() - 8, p.getY() - 32, tex, c, this, true, -3, true));
				}
				
				is_shooting = true;
			} else if (key == e.VK_P) {
				state = STATE.PAUSE;
			} else if (key == e.VK_U) {
				state = STATE.GAME;
			} else if (key == e.VK_ENTER) {
				state = STATE.SHOP;
			} else if (key == e.VK_BACK_SPACE) {
				state = STATE.GAME;
			}

		}

	}

	@SuppressWarnings("static-access")
	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == e.VK_ESCAPE) {
			System.exit(0);
		}

		if (key == e.VK_RIGHT || key == e.VK_D) {
			p.setVelX(0);

		} else if (key == e.VK_LEFT || key == e.VK_A) {
			p.setVelX(0);

		} else if (key == e.VK_UP || key == e.VK_W) {
			p.setVelY(0);

		} else if (key == e.VK_DOWN || key == e.VK_S) {
			p.setVelY(0);
		} else if (key == e.VK_SPACE) {
			is_shooting = false;
		}

	}

}
