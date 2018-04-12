package Base;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	
	
	public static BufferedImage loadImg(String name){
		
		
		try {
			// Ressource/Images est l'endroit ou l'on met toutes les images (à changer a volonté)
			return ImageIO.read(new File("Ressource/Images/" +name + ".png"));
		} catch (IOException e) {
			System.out.print("Erreur lors du chargement de l'image " + name + " : " );
			e.printStackTrace();
			return null;
		}
	}

	
}
