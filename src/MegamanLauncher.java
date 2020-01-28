import java.awt.Image;

import javax.imageio.ImageIO;

import org.francisparker.mmaunu.gameengine.GameFrame;

public class MegamanLauncher extends MegamanEnemy {
	protected int attackCooldown = 0;

	public MegamanLauncher(int x, int y, Image img, int width, int height) {
		super(x, y, img, width, height, 0, 100, 450);
	}

	public void enemyact() {
		attack();
	}

	private void attack() {
		attackCooldown++;
		if(attackCooldown>=150) {
			Image rocketimg = null;
			try
			{
				rocketimg = ImageIO.read(this.getClass().getResource("/images/rocket.png") );
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			MegamanBullet b = new MegamanBullet(getX(),getY()+getHeight()/2,rocketimg,30,20,6,new Vector(-1,0),20,true);
			GameFrame.addDrawableObject(b);
			attackCooldown=0;
		}

	}

}
