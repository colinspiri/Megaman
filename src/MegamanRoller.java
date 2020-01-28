import java.awt.Image;
import org.francisparker.mmaunu.gameengine.Drawable;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class MegamanRoller extends MegamanEnemy {
	protected int momentum = 0;
	protected double driftSpeed = 0;

	public MegamanRoller(int x, int y, Image img, int width, int height) {
		super(x, y, img, width, height, 4, 30, 500);
	}

	public void enemyact() {
		
		momentum();
		move();
		
		if((collideLeft && velocity.getX()!=0) || (collideRight && velocity.getX()!=0)) {
			GameFrame.removeDrawableObject(this);
		}
	}

	private void momentum() {
		driftSpeed = (double)getSpeed()*(momentum*0.02);
		if(momentum>=60) momentum=60;
	}
	private void move() {
		MegamanMegaman megaman = (MegamanMegaman) GameFrame.getPrimaryControllableObject();
		if(megaman.getX()<pos.getX()) {
			if(momentum>0 && velocity.getX()>0) {
				momentum--;
			}
			else {
				velocity.setX(-1*driftSpeed);
				momentum++;
			}
		}
		else if(megaman.getX()>pos.getX()) {
			if(momentum>0 && velocity.getX()<0) {
				momentum--;
			}
			else {
				velocity.setX(driftSpeed);
				momentum++;
			}
		}
	}
}
