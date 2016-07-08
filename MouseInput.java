package com.game.src.main;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		
		// quit button
				if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220 && Game.state == Game.STATE.MENU) {
					if (my >= 350 && my <= 400)
						System.exit(0);
				}
		
	}

	public void mouseReleased(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		// play button
		if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220 && Game.state == Game.STATE.MENU) {
			if (my >= 150 && my <= 200) {
				Game.state = Game.STATE.GAME;
			}
		}

		// Stores

		if (mx > 285 && mx <= 530 && Game.state == Game.STATE.SHOP && my >= 60 && my <= 95) {
			if (Game.POINTS >= 12) {
				Game.POINTS -= 12;
				Game.LEVEL_SPEED++;
			}
		}

		if (mx > 285 && mx < 530 && my >= 110 && my <= 133 && Game.state == Game.STATE.SHOP
				&& Game.LEVEL_HEALTHR <= 6) {
			if (Game.POINTS >= 14) {
				Game.POINTS -= 14;
				Game.LEVEL_HEALTHR++;
			}
		}

		if (Game.state == Game.STATE.SHOP && mx > 285 && mx <= 530 && my <= 200 && my >= 190) {
			if (Game.level5gun == true && !(Game.level5gunB) && !(Game.level10gunB)) {
				if (Game.POINTS >= 24) {
					Game.POINTS -= 24;
					Game.level5gunB = true;
				}
				
			}
		}

		if (mx > 285 && mx <= 530 && Game.state == Game.STATE.SHOP && my <= 181 && my >= 165) {
			if (Game.POINTS >= 20) {
				Game.POINTS -= 20;
				Game.HEALTH = 100;
			}
		}
		
		if(Game.state == Game.STATE.SHOP && mx > 285 && mx <= 530 && my <= 241 && my >= 231 && !(Game.level10gunB)) {
			if(Game.POINTS >= 30) {
				Game.POINTS -= 30;
				Game.level10gunB = true;
				Game.level5gunB = false;
			}
		}
		
		if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220 && Game.state == Game.STATE.HELP) {
			if (my >= 350 && my <= 400)
				Game.state = Game.STATE.MENU;
		}

		if (mx >= Game.WIDTH / 2 + 120 && mx <= Game.WIDTH / 2 + 220 && Game.state == Game.STATE.MENU) {
			if (my >= 250 && my <= 300)
				Game.state = Game.STATE.HELP;
		}
	}

}
