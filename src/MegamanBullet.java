import java.awt.Image;
import javax.swing.JOptionPane;
import org.francisparker.mmaunu.gameengine.AIObject;
import org.francisparker.mmaunu.gameengine.CollisionDetector;
import org.francisparker.mmaunu.gameengine.ControllableObject;
import org.francisparker.mmaunu.gameengine.Drawable;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class MegamanBullet extends AIObject {
	protected Vector pos = new Vector(0,0);
	protected Vector velocity = new Vector(0,0);

	protected int damage = 0;
	
	protected boolean fromEnemy = false;

	public MegamanBullet(int x, int y, Image img, int w, int h, int s, Vector dir, int tempdamage, boolean tempfromEnemy) {
		super(x,y,img,w,h,s);
		velocity.set(dir);
		velocity.mult(getSpeed());
		damage=tempdamage;
		fromEnemy=tempfromEnemy;
	}

	public void act() {
		pos.setComponents(getX(),getY());

		collisionDetection();

		pos.add(velocity);

		setX((int)Math.round(pos.getX()));
		setY((int)Math.round(pos.getY()));
	}

	private void collisionDetection() {
		//collision detection with edge of screen
		if(getX()+getWidth()<=0 || getX()>=GameFrame.getWidthOfGameArea()) {
			GameFrame.removeDrawableObject(this);
		}
		
		for(int i=0; i<GameFrame.getDrawableList().size(); i++) {
			Drawable d = GameFrame.getDrawableList().get(i);
			
			//collision detection with enemies
			if(d instanceof MegamanEnemy && fromEnemy==false) {
				MegamanEnemy enemy = (MegamanEnemy) d;
				MegamanBullet bullet = this;

				if(bullet.getX()+bullet.getWidth()>=enemy.getX() && bullet.getX()<=enemy.getX()+enemy.getWidth() 
				&& bullet.getY()+bullet.getHeight()>=enemy.getY() && bullet.getY()<=enemy.getY()+enemy.getHeight()){
					enemy.takeDamage(bullet.damage);
					GameFrame.removeDrawableObject(this);

				}
			}
			
			//collision detection with MegamanMegaman
			if(d instanceof MegamanMegaman && fromEnemy==true) {
				MegamanMegaman megaman = (MegamanMegaman) d;
				MegamanBullet bullet = this;

				if(bullet.getX()+bullet.getWidth()>=megaman.getX() && bullet.getX()<=megaman.getX()+megaman.getWidth() 
				&& bullet.getY()+bullet.getHeight()>=megaman.getY() && bullet.getY()<=megaman.getY()+megaman.getHeight()){
					megaman.takeDamage(bullet.damage);
					GameFrame.removeDrawableObject(this);

				}
			}
			

			//collision detection with blocks
			if(d instanceof MegamanObstacle) {
				MegamanObstacle obstacle = (MegamanObstacle) d;
				MegamanBullet bullet = this;

				if(bullet.getX()+bullet.getWidth()>=obstacle.getX() && bullet.getX()<=obstacle.getX()+obstacle.getWidth() 
				&& bullet.getY()+bullet.getHeight()>=obstacle.getY() && bullet.getY()<=obstacle.getY()+obstacle.getHeight()){
					GameFrame.removeDrawableObject(this);

				}
			}
		}
	}


}
