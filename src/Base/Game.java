package Base;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeSet;

public class Game implements MouseListener, MouseMotionListener {

	public TreeSet<Unit> units = new TreeSet<Unit>();
	private String ip = "";
	private SocketThread st;

	public void load() {

		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez l'ip : ");
		ip = "25.63.216.240";//sc.nextLine();
		st = new SocketThread(ip, this);

		Thread t1 = new Thread(st);
		t1.start();

		units.add(new Unit(3, 3));

	}

	public void ticks(ContainerGame container, boolean[] keys) {
		if (container.getTime() % 100 == 0) {
			Unit u = units.floor(new Unit(0));
			int x = u.x;
			int y = u.y;
			if (st != null) {
				if (keys[KeyEvent.VK_UP]) {
					st.sendMsg("moveu " + x + " " + (y - 1) + " 0");
				} else if (keys[KeyEvent.VK_DOWN]) {
					st.sendMsg("moveu " + x + " " + (y + 1) + " 0");
				} else if (keys[KeyEvent.VK_LEFT]) {
					st.sendMsg("moveu " + (x - 1) + " " + (y) + " 0");
				} else if (keys[KeyEvent.VK_RIGHT]) {
					st.sendMsg("moveu " + (x + 1) + " " + (y) + " 0");
				}
			}
		}
	}

	public void unload() {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		// TODO Auto-generated method stub

	}

	public void moveUnit(int x, int y, int id) {
		Unit check = new Unit(id);
		synchronized (units) {
			Unit u = units.floor(check);
			u.x = x;
			u.y = y;
		}
	}

}

class SocketThread implements Runnable {

	Socket soc;
	BufferedReader bf;
	PrintWriter printer;// = new PrintWriter(soc.getOutputStream());

	private Game game;
	boolean ok = true;
	private Queue<String> msgToServer = new LinkedList<String>();

	public SocketThread(String ip, Game game) {
		try {
			soc = new Socket(ip, 3232);
		} catch (UnknownHostException e) {
			ok = false;
			e.printStackTrace();
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		}
		try {
			bf = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		}
		try {
			printer = new PrintWriter(soc.getOutputStream());
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		}

		this.game = game;

	}

	@Override
	public void run() {
		if (ok) {
			while (true) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				try {
					while (bf.ready()) {
						String s = null;
						s = bf.readLine();
						this.getMsgFromServer(s);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				synchronized (msgToServer) {
					while (!msgToServer.isEmpty()) {
						String ss = msgToServer.poll();
						System.out.println("MSG TO SERVER : " + ss);

						printer.println(ss);
					}
					printer.flush();
				}
			}

		} else {
			System.out.println("Oups Problem :(");
		}

	}

	public void sendMsg(String s) {
		synchronized (msgToServer) {
			msgToServer.add(s);
		}
	}

	public void getMsgFromServer(String msg) {
		if (msg != null) {
			System.out.println("MSG FROM SERVER : " + msg);
			String parsed[] = msg.split(" ");
			if (parsed.length > 0) {
				int hash = parsed[0].hashCode();
				if (hash == "unit".hashCode()) {
					if (parsed.length >= 3) {
						try {
							int x = Integer.parseInt(parsed[1]);
							int y = Integer.parseInt(parsed[2]);
							int id = 0;
							if (parsed.length >= 4) {
								id = Integer.parseInt(parsed[3]);
							}
							game.moveUnit(x, y, id);

						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			} else {
					


			}

		}

	}

}
