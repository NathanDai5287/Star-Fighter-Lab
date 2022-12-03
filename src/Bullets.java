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

	//post - draw each Ammo
	public void draw(Graphics window) {
		ammo.forEach(ammo -> ammo.draw(window));
	}

	public void move() {
		ammo.forEach(ammo -> ammo.move());
	}

	// remove any Ammo which has reached the edge of the screen
	public void cleanUpEdges() {
		ammo.removeIf(ammo -> ammo.getY() < 0);
	}

	public List<Ammo> getList() {
		return ammo;
	}

	public String toString() {
		return super.toString() + " " + ammo;
	}
}
