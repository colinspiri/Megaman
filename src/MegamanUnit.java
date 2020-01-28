import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.francisparker.mmaunu.gameengine.ControllableObject;
import org.francisparker.mmaunu.gameengine.GameFrame;

public abstract class MegamanUnit extends ControllableObject {
	protected Vector pos = new Vector(0,0);
	protected Vector velocity = new Vector(0,0);
	protected Vector acceleration = new Vector(0,0);
	protected Vector direction = new Vector(1,0);
	
	protected final double ogweight = 5;
	protected double weight = ogweight;
	protected boolean onPlatform = false;
	protected int landingTime = 0;
	protected boolean canJump = false;

	protected int invincibleFrames = 0;
	protected boolean invincible = false;

	protected double maxHealth;
	protected double currHealth;

	protected boolean collideLeft = false;
	protected boolean collideRight = false;
	protected boolean collideTop = false;
	protected boolean collideBottom = false;

	protected boolean translatingRight = false;
	protected boolean translatingUp = false;
	protected static int time = 0;

	public MegamanUnit(int x, int y, Image img, int width, int height, int speed, double health) {
		super(x, y, img, width, height, speed);
		pos.setComponents(getX(), getY());
		maxHealth=health;
		currHealth=maxHealth;
	}

	public void act() {
		pos.setComponents(getX(), getY());
		time++;

		invincible();
		onGround();
		
		if(time>=60) {
			translate();
			subact();
		}
		
		move();
		

		collideLeft=false;
		collideRight=false;
		collideTop=false;
		collideBottom=false;

		setX((int)Math.round(pos.getX()));
		setY((int)Math.round(pos.getY()));
	}
	
	private void invincible() {
		invincibleFrames--;
		if(invincibleFrames<=0) {
			invincibleFrames=0;
			invincible=false;
		}
	}
	private void onGround() {
		if(collideBottom) {
			weight=0;
			canJump=true;
			landingTime++;

			if(landingTime==1) {
				velocity.setY(0);
			}
		}
		else {
			weight=ogweight;
			landingTime=0;
		}
	}
	private void move() {
		acceleration.setComponents(0, 0.05*(weight));
		velocity.add(acceleration);
		if(translatingRight) {
			pos.add(0, velocity.getY());
		}
		else if(translatingUp) {
			pos.add(velocity.getY(),0);
		}
		else pos.add(velocity);
	}

	protected abstract void subact();
	protected abstract void translate();

	public void collideLeft() { collideLeft=true; }
	public void collideRight() { collideRight=true; }
	public void collideTop() { collideTop=true; }
	public void collideBottom() { collideBottom=true; }

	public Vector getPos() {
		return pos;
	}
	public Vector getVelocity() {
		return velocity;
	}

	public void takeDamage(int damage) {
		if(invincible==false){
			currHealth-=damage;
			if(this instanceof MegamanMegaman) {
				invincibleFrames=60;
				invincible=true;
			}
			if(currHealth<=0) {
				if(this instanceof MegamanMegaman) {
					JOptionPane.showMessageDialog(null, "GAME OVER");
					GameFrame.closeWindow();
				}
				if(this instanceof MegamanEnemy) {
					GameFrame.removeDrawableObject(this);
				}
			}
		}
	}

}
