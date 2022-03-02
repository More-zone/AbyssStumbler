package com.tutorial.main;

import java.util.Random;

public class Spawn {
	
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	
	private int scoreKeep = 0;
	private int levelKeep = 0;
	
	public Spawn(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
	}

	public void tick() {
		scoreKeep++;
		
		if(levelKeep < hud.getLevel()) {
			if(levelKeep % 2 == 0) {
				handler.addObject(new SlowEnemy(r.nextInt(Game.WIDTH - 100), r.nextInt(Game.HEIGHT -100), ID.SlowEnemy, handler));
				handler.addObject(new SlowEnemy(r.nextInt(Game.WIDTH - 100), r.nextInt(Game.HEIGHT -100), ID.SlowEnemy, handler));
			}
			else {
				handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 100), r.nextInt(Game.HEIGHT -100), ID.BasicEnemy, handler));
			}
			levelKeep++;
		}
		if(scoreKeep >= 500) {
			handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 100), r.nextInt(Game.HEIGHT -100), ID.SmartEnemy, handler));

			scoreKeep = 0;
			hud.setLevel(hud.getLevel() + 1);
			if(hud.getLevel() == 5) {
				handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 100), r.nextInt(Game.HEIGHT -100), ID.SmartEnemy, handler));

			}
		}
	}
}
