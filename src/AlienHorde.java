import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class AlienHorde {
	private List<Alien> aliens;
	public static final int SPACE = 50;

	public AlienHorde(int size) {
		aliens = new ArrayList<Alien>();
		for (int i = 0; i < size; i++) {
			aliens.add(new Alien(10 + SPACE * (i % 10), 30 + 2 * SPACE * (i / 10), SPACE, SPACE, 1));
		}
	}

	public void add(Alien al) {
		aliens.add(al);
	}

	public void draw(Graphics window) {
		aliens.forEach(alien -> alien.draw(window));
	}

	public void move() {
		aliens.forEach(alien -> alien.move());
	}

	// calculate if Aliens are hit by shots, if so remove the shot and alien and return the number of hits
	public int calcHits(List<Ammo> shots) {
		int hits = 0;
		for (Ammo shot : shots) {
			for (Alien alien : aliens) {
				if (shot.getX() >= alien.getX() && shot.getX() <= alien.getX() + alien.getWidth() && shot.getY() >= alien.getY() && shot.getY() <= alien.getY() + alien.getHeight()) {
					aliens.remove(alien);
					shots.remove(shot);
					hits++;
					break;
				}
			}
		}
		return hits;
	}

	public String toString() {
		return "";
	}
}
