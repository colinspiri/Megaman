import java.awt.Image;
import java.util.ArrayList;
import org.francisparker.mmaunu.gameengine.GameFrame;

public abstract class MegamanEnemy extends MegamanUnit {
	protected static ArrayList<MegamanEnemy> enemies = new ArrayList<MegamanEnemy>();

	protected int range;
	protected int collideDamage;

	protected boolean triggered = false;

	public MegamanEnemy(int x, int y, Image img, int width, int height, int speed, double health, int temprange) {
		super(x, y, img, width, height, speed, health);
		enemies.add(this);

		range=temprange;
		collideDamage=20;
	}

	public void subact() {
		testIfTriggered();
		if(triggered) {
			collideCollisionDetection();
			enemyact();
		}
	}
	
	public void translate() {
		MegamanMegaman megaman = (MegamanMegaman) GameFrame.getPrimaryControllableObject();

		if(megaman.getX()>=400 && megaman.collideLeft==false && megaman.velocity.getX()>0) {
			pos.setX((int)(pos.getX()-megaman.getVelocity().getX()));
		}
	}
	
	protected void testIfTriggered() {
		MegamanMegaman player = (MegamanMegaman)GameFrame.getPrimaryControllableObject();
		if(Logic.distanceFrom(player.getX(), player.getY(), getX(), getY())<=range) {
			triggered=true;
		}
		else triggered=false;
	}
	protected void collideCollisionDetection() {
		MegamanMegaman m = (MegamanMegaman) GameFrame.getPrimaryControllableObject();
		if(m.getX()+m.getWidth()>=this.getX() && m.getX()<=this.getX()+this.getWidth() &&
				m.getY()+m.getHeight()>=this.getY() && m.getY()<=this.getY()+this.getHeight()) {
			m.takeDamage(collideDamage);
			if(this instanceof MegamanRoller) {
				GameFrame.removeDrawableObject(this);
			}
			if(this instanceof MegamanDropper) {
				MegamanDropper dropper = (MegamanDropper) this;
				dropper.rise();
			}
		}
	}
	
	public abstract void enemyact();

}
