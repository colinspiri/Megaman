import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.francisparker.mmaunu.gameengine.ControllableObject;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class MegamanMegaman extends MegamanUnit implements KeyListener {
	protected boolean editor = false;

	public MegamanMegaman(int x, int y, Image img, int width, int height, boolean tempeditor) {
		super(x, y, img, width, height, 3, 100);
		editor=tempeditor;
	}
	
	protected void subact() {
		if(editor) {
			this.setSpeed(10);
			currHealth+=100;
		}
		else this.setSpeed(3);
		GameFrame.setMessage("Health: " + currHealth + " out of " + maxHealth);
		if(isMovingLeft()) {
			velocity.setX(-1*getSpeed());
			direction.setComponents(-1, 0);
		}
		if(isMovingRight()) {
			velocity.setX(getSpeed());
			direction.setComponents(1, 0);
		}
		else if(isMovingLeft()==false && isMovingRight()==false) velocity.setX(0);
	}

	protected void translate() {
		if(pos.getX()>=GameFrame.getWidthOfGameArea()/2 && velocity.getX()>0) {
			translatingRight=true;
		}
		else translatingRight=false;
//		if(pos.getY()<=GameFrame.getHeightOfGameArea()/3 && velocity.getY()<0) {
//			translatingUp=true;
//		}
//		else translatingUp=false;
	}
	
	public void keyPressed(KeyEvent evt){
		int key = evt.getKeyCode();
		if(key == KeyEvent.VK_SPACE && canJump) {
			velocity.setY(-8);
			canJump=false;
		}
		if(key == KeyEvent.VK_DOWN) {
			Image bulletimg = null;
			try
			{
				bulletimg = ImageIO.read(this.getClass().getResource("/images/bullet.png") );
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			MegamanBullet b = new MegamanBullet(getX(),getY()+getHeight()/2-10,bulletimg,18,16,6,direction,10,false);
			GameFrame.addDrawableObject(b);
		}
		if(key == KeyEvent.VK_E) {
			if(editor==true) editor=false;
			else editor=true;
		}
	}
	public void keyReleased(KeyEvent evt){} //use key released for charge shot
	public void keyTyped(KeyEvent evt){}
}
