package com.tutorial.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1329557117279555041L;

	public static final int WIDTH = 1280, HEIGHT = WIDTH / 12 * 9;
    
    private Thread thread;
    private boolean running = false;
    
    private Handler handler;
    private HUD hud;
    private Spawn spawner;
    private Menu menu;
    private Image background1, background2;
    
    public enum STATE {
    	Menu,
    	Help,
    	Game,
    	End;
    }
    
    public STATE gameState = STATE.Menu;
    
    public Game(){
    	handler = new Handler();
        menu = new Menu(this, handler, hud);
    	this.addKeyListener(new KeyInput(handler));
    	this.addMouseListener(menu);
    	background1 =  createBackground(WIDTH, HEIGHT, Color.black);
    	background2 = createBackground(WIDTH, HEIGHT, 100, 100, Color.cyan, Color.black);
    	
    	new Window(WIDTH, HEIGHT, "LET'S BUILD A GAME", this);
    	
    	hud = new HUD();
    	spawner = new Spawn(handler, hud);
        
        //if(gameState == STATE.Game){
        //}
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
	public void run() {
		this.requestFocus();
		// Game Loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;
            
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
	}

	private void tick(){
        handler.tick();
        if(gameState == STATE.Game) {
        	hud.tick();
        	spawner.tick();
        }else if(gameState == STATE.Menu || gameState == STATE.Help) {
        	menu.tick();
        }
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null){
            // not recommended to go over 3 buffers
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        
        if(gameState == STATE.Game){
            g.drawImage(background2, 0, 0, null);
        }else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End) {
            g.drawImage(background1, 0, 0, null);
        }
        
        handler.render(g);
        
        if(gameState == STATE.Game){
        	hud.render(g);
        }else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End) {
        	menu.render(g);
        }
        
        
        g.dispose();
        bs.show();
    }
    
    public static int clampInt(int var, int min, int max) {
    	if(var >= max)
    		return var = max;
    	else if(var <= min)
    		return var = min;
    	else
    		return var;
    }
    
    public static float clamp(float var, float min, float max) {
    	if(var >= max)
    		return var = max;
    	else if(var <= min)
    		return var = min;
    	else
    		return var;
    }
    
    public Image createBackground(int x, int y, Color color) {
    	BufferedImage img = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
		Graphics g = img.createGraphics();
		g.setColor(color);
		g.fillRect(0, 0, x, y);
		return (Image) img;
    }
    
    public Image createBackground(int x, int y, int tileX, int tileY, Color color1, Color color2) {
		//this one is the checkered background constructor
		// it makes a box from 0, 0 to x, y with tiles of size tileX by tileY alternating between 2 colors to an image
    		BufferedImage img = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
			Graphics g = img.createGraphics();
			boolean count = true;
		    int tileColor;
		    for (int col = 0; col <= x; col = col + tileX) {
		    	if(count) {
		    		tileColor = 0;
		    		count = false;
		    	}
		    	else {
		    		tileColor = 1;
		    		count = true;
		    	}
		    	for(int row = 0; row <= y; row = row + tileY) {
			    	if(tileColor % 2 == 0) {
			    		g.setColor(color1);
			    	}else {
			    		g.setColor(color2);
			    	}
			    	g.fillRect(col, row, tileX, tileY);
			    	tileColor++;
		    	}
		    }
		    return (Image) img;
	   }
    
    public static void main(String args[]){
        new Game();
    }
}
