import java.awt.Image;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import org.francisparker.mmaunu.gameengine.Drawable;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class MegamanBee extends MegamanEnemy {
	protected int attackCooldown = 0;
	protected Vector target = new Vector(0,0);
	protected int directionY = 1;
	protected int up = 1, down = 2;

	public MegamanBee(int x, int y, Image img, int width, int height) {
		super(x, y, img, width, height, 0, 200, 500);
	}

	public void enemyact() {
		velocity.setY(0); //to take out gravity

		move();
		attackCooldown();
		if(attackCooldown==0) attack();

	}

	private void move() {
		//move x
		MegamanMegaman megaman = (MegamanMegaman)GameFrame.getPrimaryControllableObject();
		if(megaman.getX()+megaman.getWidth()<getX()) velocity.setX(-1*getSpeed());
		else if(megaman.getX()>getX()+getWidth()) velocity.setX(getSpeed());
		
		//move y
		if(directionY==up) {
			velocity.setY(-1);
			if(getY()<100) {
				directionY=down;
			}
		}
		else if(directionY==down) {
			velocity.setY(1);
			if(getY()>110) {
				directionY=up;
			}
		}
	}
	private void attackCooldown() {
		attackCooldown--;
		if(attackCooldown<=0) {
			attackCooldown=0;
		}
		else if(attackCooldown>0) {
			MegamanMegaman megaman = (MegamanMegaman)GameFrame.getPrimaryControllableObject();
			target.setComponents(megaman.getX(), megaman.getY());
		}
	}
	private void attack() {
		//fire bullets
		Image bulletimg = null;
		try
		{
			bulletimg = ImageIO.read(this.getClass().getResource("/images/beebullet.png") );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		for(int i=0; i<5; i++) {
			double speedOfBullet = 10;
			MegamanBullet b = new MegamanBullet(getX()+(i*10),getY()+getHeight()-20,bulletimg,30,20,1,new Vector(-1*Math.sin(45)*speedOfBullet,Math.sin(45)*speedOfBullet),20,true);
			GameFrame.addDrawableObject(b);
		}

		attackCooldown=180;

	}

	protected void collideCollisionDetection() {
		MegamanMegaman m = (MegamanMegaman) GameFrame.getPrimaryControllableObject();

		if(m.getX()+m.getWidth()>=this.getX() && m.getX()<=this.getX()+this.getWidth() &&
				m.getY()+m.getHeight()>=this.getY() && m.getY()<=this.getY()+this.getHeight()) {
			m.takeDamage(collideDamage);
		}
	}
}
