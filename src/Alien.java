import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;

public class Alien extends MovingThing {
	private int speed;
	private Image image;
	private String direction = "RIGHT";

	public Alien() {
		this(0, 0, 30, 30, 0);
	}

	public Alien(int x, int y) {
		this(x, y, 30, 30, 0);
	}

	public Alien(int x, int y, int s) {
		this(x, y, 30, 30, s);
	}

	// all ctors call this ctor
	public Alien(int x, int y, int w, int h, int s) {
		super(x, y, w, h);
		speed = s;
		try {
			URL url = getClass().getResource("alien.jpg");
			image = ImageIO.read(url);
		} catch (Exception e) {
			System.out.println("Error loading image");
		}
	}

	public void setSpeed(int s) {
		speed = s;
	}

	public int getSpeed() {
		return speed;
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

//	move horizontally until it hits the edge of the screen, then move down and change direction
	public void move() {
		move(direction);
		if (getX() < 0) {
			setX(0);
			setY(getY() + getHeight());
			direction = "RIGHT";
		}
		if (getX() > 800) {
			setX(800);
			setY(getY() + getHeight());
			direction = "LEFT";
		}
	}

	public void draw(Graphics window) {
		window.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}

	public String toString() {
		return super.toString() + " " + getSpeed();
	}
}
