import java.awt.*;
import java.awt.image.BufferedImage;

public class Calibration {
	private Robot Me;
	private Dimension screenSize;
	private BufferedImage Img;
	private int beginX;
	private int beginY;
	private int cellSize;
	
	public Calibration() throws AWTException, InterruptedException {
		Me = new Robot();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	public boolean isDone() {
		int c = Img.getRGB(screenSize.width/2, screenSize.height/2);
		
		return Color.isEndScreen(c);
	}
	
	public void moveTo(int x, int y) throws InterruptedException {
		Me.mouseMove((beginX + x * cellSize) + cellSize/2 + x/2, (beginY + y * cellSize) + cellSize/2);
		Thread.sleep(100);
	}
	
	public void moveToPos(int x, int y) throws InterruptedException {
		Me.mouseMove(x + beginX, y + beginY);
		Thread.sleep(100);
	}
	
	public void mousePress(int x, int y) throws InterruptedException {
		Me.mouseMove((beginX + x * cellSize) + cellSize/2 + x/2, (beginY + y * cellSize) + cellSize/2);
		Me.mousePress(16);
		Thread.sleep(10);
		Me.mouseRelease(16);
		Thread.sleep(100);
	}
	
	public void getWindow() {
		try {
		      Rectangle captureSize = new Rectangle(screenSize);
		      Img = Me.createScreenCapture(captureSize);
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public int getCell(int x, int y, BufferedImage board) throws InterruptedException {
		int x1 =  x * cellSize;
		int y1 =  y * cellSize;

		return board.getRGB(x1 + cellSize/2 + 8, y1 + cellSize/2);
	}
	
	public BufferedImage getBoard(int width, int height) {
		try {
		      Rectangle captureSize = new Rectangle(beginX, beginY, width * cellSize, height * cellSize);
		      return Me.createScreenCapture(captureSize);
		} catch(Exception e) { e.printStackTrace(); }
		
		return null;
	}
	
	private boolean isTopLeft(int c) {
		int r = (c >> 16) & 0xFF;
	    int g = (c >> 8) & 0xFF;
	    int b = c & 0xFF;

	   return (b > 240 && r < 190 && b > g && b > r); //This  
	}
	
	
	public Board makeBoard() throws InterruptedException {
		int x = -1, y = -1;

		topLeft:
		for(int h = 50; h < Img.getHeight(); h++){
			for(int w= 50; w <  Img.getWidth(); w++){
				int c = Img.getRGB(w, h);
				
		        if(w < 50 || h < 50 || w > Img.getWidth()-50 || h > Img.getHeight()-50) //Ignore Bottom and Top
		            continue;

		          if(isTopLeft(c)) {
		        	  x = w; 
		        	  y = h+20; 
		        	  break topLeft;
	              }
		          
			}
		}
	  
		cellSize = getCellSize(x, y); //Save Cell size to calculate Move
		return new Board(getWidth(x, y)/cellSize, getHeight(x, y)/cellSize, cellSize, this);
	}
	
	private int getCellSize(int x, int y) {
		int width = 0, height = 0;
		
		for(int w= x; w < Img.getWidth(); w++){
			int c = Img.getRGB(w, y);
			
			if(!Color.isDark(c)) {
				width++;
			} else break;
			
		}
		
		for(int w= x; w >  0; w--){
			int c = Img.getRGB(w, y);
			
			if(!Color.isDark(c)) {
				width++;
			} else break;
			
		}
		
		for(int h = y; h < Img.getHeight(); h++){
			int c = Img.getRGB(x, h);
			
			if(!Color.isDark(c)) {
				height++;
			} else break;
		}
		
		for(int h= y; h > 0; h--){
			int c = Img.getRGB(x, h);
			
			if(!Color.isGray(c)) {
				height++;
			} else break;
		}
		
		return (width+height)/2;
	}
	
	private int getWidth(int x, int y) {
		int width = 0;
		
		for(int w= x; w >  0; w--){
			int c = Img.getRGB(w, y);
			
			if(!Color.isGray(c)) {
				width++;
			} else {
				beginX = x - width;
				break;
			}
		}
		
		for(int w= x; w <  Img.getWidth(); w++){
			int c = Img.getRGB(w, y);
			
			if(!Color.isGray(c)) {
				width++;
			} else break;
			
		}
			
		return width;
	}

	private int getHeight(int x, int y) {
		int height = 0;
		
		for(int h = y; h >  0; h--){
			int c = Img.getRGB(x, h);
			
			if(!Color.isGray(c)) {
				height++;
			} else {
				beginY = y - height;
				break;
			}
		}
		
		for(int h= y; h <  Img.getHeight(); h++){
			int c = Img.getRGB(x, h);
			
			if(!Color.isGray(c)) {
				height++;
			} else break;
			
		}
		
		return height;
	}

	public Point getPos(int x, int y) {
		int posX = (x * cellSize) + cellSize/2 + 8;
		int posY = (y * cellSize) + cellSize/2;
		
		return new Point(posX, posY );
	}
}