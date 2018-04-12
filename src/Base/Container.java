package Base;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class Container extends JPanel implements Runnable, ContainerGame, KeyListener {

	private Thread thread;
	private static final long serialVersionUID = 1L;
	protected long time = 0;
	public boolean[] keys = new boolean[65536];
	Game game = new Game();



	public Container(){
		addKeyListener(this);
		addMouseListener(game);
		addMouseMotionListener(game);
		setFocusable(true);
		requestFocus();
		
	}
	
	
	
	@Override
	public void run() {
		game.load();
		while (running) {
			time ++;
			game.ticks(this, keys);
			repaint();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		game.unload();
		System.exit(0);

	}

	public void start() {
		thread = new Thread(this);
		running = true;
		thread.start();
		

	}

	public void paintComponent(Graphics g){
		render(g);
	}
	
	public void render(Graphics g){
		RenderGame.rend(this, g, game);
		
	}

	
	public boolean running = false;

	@Override
	public int getSizeX() {
		// TODO Auto-generated method stub
		return this.getWidth();
	}

	@Override
	public int getSizeY() {
		// TODO Auto-generated method stub
		return this.getHeight();
	}

	@Override
	public long getTime() {
		// TODO Auto-generated method stub
		return time;
	}

	@Override
	public void stop() {
		running = false;
		
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); 
		if (code>0 && code<keys.length) {
			keys[code] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode(); 
		if (code>0 && code<keys.length) {
			keys[code] = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}






}
