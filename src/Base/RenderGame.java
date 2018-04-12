package Base;


import java.awt.Color;
import java.awt.Graphics;

public class RenderGame {

	public static void rend(ContainerGame cont,Graphics g, Game game){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, cont.getSizeX(), cont.getSizeY());
		synchronized(game.units){
			g.setColor(Color.BLACK);
			for(Unit u : game.units){
				g.fillRect(u.x*50, u.y*50, 50, 50);
			}
		}
	}
	
	
}
