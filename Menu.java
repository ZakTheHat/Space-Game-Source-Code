package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
	
	public Rectangle playButton = new Rectangle(Game.WIDTH / 2 + 120, 150, 100, 50);
	public Rectangle helpButton = new Rectangle(Game.WIDTH / 2 + 120, 250, 100, 50);
	public Rectangle quitButton = new Rectangle(Game.WIDTH / 2 + 120, 350, 100, 50);
	
	public void render(Graphics g) {
		
		Font fnt0 = new Font("showcard gothic", Font.BOLD, 50);
		
		Graphics2D g2d = (Graphics2D)g;
		
		g.setFont(fnt0);
		
		g.setColor(Color.WHITE);
		
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		
		g.drawString(" Space Game ", Game.WIDTH / 2, 100);
		
		g.setFont(fnt1);
		
		g.setColor(Color.black);
		
		g.fillRect(playButton.x, playButton.y, 100, 50);
		g.fillRect(helpButton.x, helpButton.y, 100, 50);
		g.fillRect(quitButton.x, quitButton.y, 100, 50);
		
		g.setColor(Color.WHITE);
		
		g.drawString("Play", playButton.x + 19, playButton.y + 30);
		g.drawString("Help", helpButton.x + 19, helpButton.y + 30);
		g.drawString("Quit", quitButton.x + 19, quitButton.y + 30);
		
		g2d.draw(playButton);
		g2d.draw(quitButton);
		g2d.draw(helpButton);
	}
	
}
