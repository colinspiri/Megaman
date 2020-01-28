import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class MegamanMain {
	
	public static void main(String[] args) {
		new MegamanMain();
	}

	@SuppressWarnings("static-access")
	public MegamanMain() {
		GameFrame mainWindow = new GameFrame("Megaman",800,550,false,false,true);
		
		createLevel1();
		
		createMegaman(false);

		mainWindow.addKeyListener((KeyListener)mainWindow.getPrimaryControllableObject());
		mainWindow.setGameBackground(Color.white);
		mainWindow.setFPS(60);
		mainWindow.setVisible(true);
	}

	public void createLevel1() {
		Image blockimg = null, pillarimg = null, backgroundimg = null;
		try
		{
			blockimg = ImageIO.read(this.getClass().getResource("/images/blockregular.png") );
			pillarimg = ImageIO.read(this.getClass().getResource("/images/blockpillar.png") );
			backgroundimg = ImageIO.read(this.getClass().getResource("/images/backgroundhighway.png") );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		createBackground(backgroundimg,2500,600);
		
		createRoller(1000,300);
		createRoller(1300,300);
		
		createLauncher(1500,300);
		
		createDropper(1900,300);
		createDropper(1950,300);
		createDropper(2000,300);
		
		createLauncher(2300,300);
		createDropper(2420,300);
		
		createBee(2700,100);
		
		createGround(0,400,64,2); //ground
		createBlocksRect(0,0,1,8,blockimg); //wall on left
		createBlocksRect(400,300,1,2,pillarimg); //little jumpable wall
		createBlocksRect(300,100,4,1,blockimg); //ceiling
		createBlocksRect(2600,300,1,2,pillarimg); //wall at end near bee
	}

	public void createBackground(Image img, int width, int height) {
		MegamanBackground background = new MegamanBackground(-100,-50,img,width,height);
		GameFrame.addDrawableObject(background);
	}
	public void createMegaman(boolean editor) {
		Image characterimg = null;
		try
		{
			characterimg = ImageIO.read(this.getClass().getResource("/images/megaman.png") );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		MegamanMegaman player = new MegamanMegaman(100,300,characterimg,50,50,editor);
		GameFrame.setPrimaryControllableObject(player);
	}
	
	public void createGround(int x, int y, int blocksX, int blocksY) {
		Image groundimg = null, pillarimg = null;
		try
		{
			groundimg = ImageIO.read(this.getClass().getResource("/images/blockground.png") );
			pillarimg = ImageIO.read(this.getClass().getResource("/images/blockpillar.png") );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//if coordinates are not evenly divisible, throw an exception
		if(x%50 != 0 || y%50 != 0) throw new IllegalArgumentException("x and y parameters for createBlocksRect(x,y,blocksX,blocksY) must be divisible by 50");

		//create rectangles
		for(int i=0; i<blocksX; i++) {
			for(int a=0; a<blocksY; a++) {
				int addedX = 50*i;
				int addedY = 50*a;
				if(addedX%400==0 && addedX!=0) createBlock(x+addedX,y+addedY,pillarimg);
				else createBlock(x+addedX,y+addedY,groundimg);
			}
		}
	}
	public void createBlocksRect(int x, int y, int blocksX, int blocksY, Image img) {
		//if coordinates are not evenly divisible, throw an exception
		if(x%50 != 0 || y%50 != 0) throw new IllegalArgumentException("x and y parameters for createBlocksRect(x,y,blocksX,blocksY) must be divisible by 50");

		//create rectangles
		for(int i=0; i<blocksX; i++) {
			for(int a=0; a<blocksY; a++) {
				int addedX = 50*i;
				int addedY = 50*a;
				createBlock(x+addedX,y+addedY,img);
			}
		}

	}
	public void createBlock(int x, int y, Image img) {
		MegamanBlock b = new MegamanBlock(x,y,img,50,50);
		GameFrame.addDrawableObject(b);
		System.out.println("Created MegamanBlock at (" + x + ", " + y + ")");
	}
	
	public void createRoller(int x, int y) {
		Image rollerimg = null;
		try
		{
			rollerimg = ImageIO.read(this.getClass().getResource("/images/roller.png") );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		MegamanRoller roller = new MegamanRoller(x,y,rollerimg,50,50);
		GameFrame.addDrawableObject(roller);
	}
	public void createLauncher(int x, int y) {
		Image launcherimg = null;
		try
		{
			launcherimg = ImageIO.read(this.getClass().getResource("/images/launcher.png") );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		MegamanLauncher launcher = new MegamanLauncher(x,y,launcherimg,71,100);
		GameFrame.addDrawableObject(launcher);
	}
	public void createDropper(int x, int y) {
		Image dropperimg = null;
		try
		{
			dropperimg = ImageIO.read(this.getClass().getResource("/images/dropper.png") );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		MegamanDropper dropper = new MegamanDropper(x,y,dropperimg,73,63);
		GameFrame.addDrawableObject(dropper);
	}
	public void createBee(int x, int y) {
		Image beeimg = null;
		try
		{
			beeimg = ImageIO.read(this.getClass().getResource("/images/bee.png") );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		MegamanBee bee = new MegamanBee(x,y,beeimg,150,102);
		GameFrame.addDrawableObject(bee);
	}

}
