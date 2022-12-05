import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import static java.lang.Character.*;

import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.*;

public class OuterSpace extends Canvas implements KeyListener, Runnable {
	private Ship ship;
	private AlienHorde horde;
	private Bullets shots;
	private Bullets alienShots;

	private boolean[] keys;
	private BufferedImage back;

	private Timer alienTimer;

	private int score = 0;
	private int lives = 3;
	private boolean pause = false;
	private boolean lost = false;
	private boolean won = false;

	public OuterSpace() {
		setBackground(Color.black);

		keys = new boolean[6];

		//instantiate other instance variables
		//Ship, Alien
		ship = new Ship(400, 400, 30, 30, 10);

		shots = new Bullets();
		alienShots = new Bullets();

		horde = new AlienHorde(20);

		this.addKeyListener(this);
		new Thread(this).start();

		setVisible(true);
	}

	public void update(Graphics window) {
		Graphics2D twoDGraph = (Graphics2D) window;
		if (back == null)
			back = (BufferedImage) (createImage(getWidth(), getHeight()));
		Graphics graphToBack = back.createGraphics();

		if (lost) {
			graphToBack.setColor(Color.BLACK);
			graphToBack.fillRect(0, 0, 800, 600);
			graphToBack.setColor(Color.RED);
			graphToBack.drawString("You lost!", 400, 230);
			graphToBack.drawString("Your final score was: " + score, 400, 245);
			twoDGraph.drawImage(back, null, 0, 0);
		} else if (won) {
			graphToBack.setColor(Color.BLACK);
			graphToBack.fillRect(0, 0, 800, 600);
			graphToBack.setColor(Color.RED);
			graphToBack.drawString("You Won! Game Over", 400, 230);
			graphToBack.drawString("Your final score was: " + score, 400, 245);
			twoDGraph.drawImage(back, null, 0, 0);
		} else if (pause) {
			graphToBack.setColor(Color.BLACK);
			graphToBack.fillRect(0, 0, 800, 600);
			graphToBack.setColor(Color.RED);
			graphToBack.drawString("Lives: " + lives, 400, 230);
			graphToBack.setColor(Color.GREEN);
			graphToBack.drawString("Press R to resume game", 400, 245);
			twoDGraph.drawImage(back, null, 0, 0);
			ship.setX(450);
			ship.setY(450);
			if (keys[5]) {
				pause = false;
			}
		} else {
			paint(window);
		}
	}

	public void paint(Graphics window) {
		//set up the double buffering to make the game animation nice and smooth
		Graphics2D twoDGraph = (Graphics2D) window;

		//take a snapshot of the current screen and save it as an image
		//that is the exact same width and height as the current screen
		if (back == null)
			back = (BufferedImage) (createImage(getWidth(), getHeight()));

		//create a graphics reference to the background image
		//we will draw all changes on the background image
		Graphics graphToBack = back.createGraphics();

		graphToBack.setColor(Color.BLUE);
		graphToBack.drawString("StarFighter ", 25, 50);
		graphToBack.setColor(Color.BLACK);
		graphToBack.fillRect(0, 0, 800, 600);

		// score and lives
		graphToBack.setColor(Color.WHITE);
		graphToBack.fillRect(30, 30 - 10, 150, 15);
		graphToBack.setColor(Color.BLACK);
		graphToBack.setColor(Color.WHITE);
		graphToBack.fillRect(650, 30 - 10, 130, 15);
		graphToBack.setColor(Color.BLACK);
		graphToBack.drawString("Lives: " + lives, 30, 30);
		graphToBack.drawString("Score: " + score, 650, 30);

		if (keys[0] && ship.getX() > 10) {
			ship.move("LEFT");
		}
		if (keys[1] && ship.getX() < 700) {
			ship.move("RIGHT");
		}
		if (keys[2] && ship.getY() > 10) {
			ship.move("UP");
		}
		if (keys[3] && ship.getY() < 500) {
			ship.move("DOWN");
		}
		if (keys[4]) {
			shots.add(new Ammo(ship.getX() + ship.getWidth() / 2, ship.getY(), 5));
			keys[4] = false;
		}

		shots.move("UP");
		alienShots.move("DOWN");
		horde.move();

		// see if aliens are hit by ship
		for (Alien alien : horde.getAliens()) {
			if (ship.didCollide(alien)) {
				lives--;

				if (lives == 0) {
					lost = true;
				} else {
					alienShots.clear();
					pause = true;
				}
			}
		}

		// check for collision between shots and aliens
		int ihit = horde.calcHits(shots.getList());
		if (ihit != -1) {
			score += 10;
		}

		// check for collision between alien shots and ship
		for (int i = 0; i < alienShots.getList().size(); i++) {
			if (alienShots.getList().get(i).didCollide(ship)) {
				alienShots.remove(i);
				lives--;

				if (lives == 0) {
					lost = true;
				} else {
					alienShots.clear();
					pause = true;
				}
			}
		}

		// check for win or game over
		if (horde.isEmpty()) {
			won = true;
		}

		ship.draw(window);
		horde.draw(window);

		shots.draw(window);
		shots.cleanUpEdges();

		alienShots.draw(window);
		alienShots.cleanUpEdges();

		twoDGraph.drawImage(back, null, 0, 0);
	}


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys[2] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys[3] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			keys[5] = true;
		}
		repaint();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			keys[1] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			keys[2] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			keys[3] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			keys[4] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_R) {
			keys[5] = false;
		}
		repaint();
	}

	public void keyTyped(KeyEvent e) {
		//no code needed here
	}

	public void run() {
		try {
			new Task().run();
			while (true) {
				Thread.currentThread().sleep(5);
				repaint();
			}
		} catch (Exception e) {
		}
	}

	public class Task extends TimerTask {
		Timer timer = new Timer();

		@Override
		public void run() {
			int delay = (new Random().nextInt(2)) * 750;
			timer.schedule(new Task(), delay);
			int ammoSpeed = 2;
			if (alienShots.getList().size() <= 10) {
				try {
					List<Alien> aliens = horde.getAliens();
					int size = aliens.size();
					int randomAlienIndex = (int) (Math.random() * size);

					// spawn ammo
					int alienX = aliens.get(randomAlienIndex).getX();
					int alienY = aliens.get(randomAlienIndex).getY();
					Ammo enemyRound = new Ammo(alienX, alienY, ammoSpeed);

					// add bullet to alienshots
					alienShots.add(enemyRound);
				} catch (Exception ignored) {
				}
			}

		}
	}
}
