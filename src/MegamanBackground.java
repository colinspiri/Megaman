import java.awt.Image;
import org.francisparker.mmaunu.gameengine.AIObject;
import org.francisparker.mmaunu.gameengine.DrawableGameObject;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class MegamanBackground extends AIObject {

	public MegamanBackground(int x, int y, Image img, int width, int height) {
		super(x, y, img, width, height, 0);
	}

	public void act() {
		if(MegamanMegaman.time>60) {
			MegamanMegaman megaman = (MegamanMegaman) GameFrame.getPrimaryControllableObject();

			if(megaman.getX()>=400 && megaman.collideLeft==false && megaman.velocity.getX()>0) {
				setX((int)(getX()-megaman.getVelocity().getX()/3));
			}
		}
	}

}
