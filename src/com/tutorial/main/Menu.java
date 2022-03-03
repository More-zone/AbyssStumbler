package com.tutorial.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.tutorial.main.Game.STATE;

public class Menu extends MouseAdapter {
	
	private Game game;
	private Handler handler;
	private HUD hud;
	
	public Menu(Game game, Handler handler, HUD hud) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
	}

	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if(mouseOver(mx, my, 450, 350, 400, 100)) {
			game.gameState = STATE.Game;

            handler.addObject(new Player(100, 100, ID.Player, handler));
		}else if(mouseOver(mx, my, 450, 500, 400, 100)) {
			game.gameState = STATE.Help;
		}else if(mouseOver(mx, my, 450, 650, 400, 100)) {
			System.exit(1);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			}else return false;
		}else return false;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(game.gameState == STATE.Menu){
			Font fnt = new Font("arial", 1, 200);
			Font fnt2 = new Font("arial", 1, 80);
			g.setColor(Color.white);
			g.setFont(fnt);
			g.drawString("Menu", 350, 200);
			
			g.drawRect(450, 350, 400, 100);
			g.setFont(fnt2);;
			g.drawString("Play", 560, 425);
			
			g.drawRect(450, 500, 400, 100);
			g.drawString("Help", 560, 575);
			
			g.drawRect(450, 650, 400, 100);
			g.drawString("Quit", 560, 725);
		}else if(game.gameState == STATE.Help) {
			Font fnt = new Font("arial", 1, 200);
			g.setColor(Color.white);
			g.setFont(fnt);
			g.drawString("HELP", 350, 200);
		}else if(game.gameState == STATE.End) {
			Font fnt = new Font("arial", 1, 200);
			g.setColor(Color.white);
			g.setFont(fnt);
			g.drawString("Game", 350, 200);
			g.drawString("Over", 350, 300);
			g.drawString("Game", 350, 200);
		
		}
	}
	
	
}
