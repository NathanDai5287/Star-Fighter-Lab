import java.awt.*;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

public class Ammo extends MovingThing {
	private int speed;
	private Image image;

	public Ammo() {
		this(0, 0, 0);
	}

	public Ammo(int x, int y) {
		this(x, y, 0);
	}

	public Ammo(int x, int y, int s) {
		super(x, y, 15, 15);
		speed = s;

		try {
			URL url = getClass().getResource("bullet.png");
			image = ImageIO.read(url);
		} catch (Exception e) {
			System.out.println("Error loading bullet.png");
		}
	}

	public void setSpeed(int s) {
		speed = s;
	}

	public int getSpeed() {
		return speed;
	}

	public void draw(Graphics window) {
		window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}

	public void move() {
		move("UP");
	}

	public void move(String direction) {
		switch (direction) {
			case "LEFT":
				setX(getX() - speed);
				break;
			case "RIGHT":
				setX(getX() + speed);
				break;
			case "UP":
				setY(getY() - speed);
				break;
			case "DOWN":
				setY(getY() + speed);
				break;
		}
	}

	public String toString() {
		return super.toString() + getSpeed();
	}
}
