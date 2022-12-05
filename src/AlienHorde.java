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

	public List<Alien> getAliens() {
		return aliens;
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

	public boolean isEmpty() {
		return aliens.isEmpty();
	}

	// calculate if Aliens are hit by shots, if so remove the shot and alien and return the number of hits
	public int calcHits(List<Ammo> shots) {
		int i;
		for (Alien alien : aliens) {
			i = 0;
			for (Ammo shot : shots) {
				if (alien.didCollide(shot)) {
					aliens.remove(alien);
					shots.remove(shot);
					return i;
				}

				i++;
			}
		}

		return -1;
	}

	public String toString() {
		return "";
	}
}
