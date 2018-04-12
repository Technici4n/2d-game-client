package Base;

import javax.swing.JFrame;

public class Fenetre extends JFrame {

	private Container container = new Container();


	public Fenetre() {

		this.setContentPane(container);
		this.setVisible(true);
		this.setSize(500, 500);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Game");
		container.start();
	}

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

	
	
			@SuppressWarnings("unused")
			Fenetre fen = new Fenetre();
		
	}

}
