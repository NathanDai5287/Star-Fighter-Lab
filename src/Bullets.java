import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Bullets {
	private List<Ammo> ammo;

	public Bullets() {
		ammo = new ArrayList<Ammo>();
	}

	public void add(Ammo al) {
		ammo.add(al);
	}

	public void clear() {
		ammo.clear();
	}

	//post - draw each Ammo
	public void draw(Graphics window) {
		try {
			ammo.forEach(ammo -> ammo.draw(window));
		} catch (Exception ignored) {
		}
	}

	public void move(String direction) {
		ammo.forEach(ammo -> ammo.move(direction));
	}

	public void remove(int i) {
		if (i >= 0 && i < ammo.size()) {
			ammo.remove(i);
		}
	}

	// remove any Ammo which has reached the edge of the screen
	public void cleanUpEdges() {
		ammo.removeIf(ammo -> ammo.getY() < 0 || ammo.getY() > 600);
	}

	public List<Ammo> getList() {
		return ammo;
	}

	public String toString() {
		return super.toString() + " " + ammo;
	}
}
