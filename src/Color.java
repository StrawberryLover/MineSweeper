import java.awt.Point;
import java.awt.image.BufferedImage;

public class Color {
	
	public static boolean isOne(Point point, BufferedImage myBoard) {
		int length = 1, c;
		
		do {
			c = myBoard.getRGB(point.x + length, point.y);
			length++;
		} while(!isGray(c) && length < 10);
		
		return length < 8;
	}
	
	public static boolean isTwo(Point point, BufferedImage myBoard) {
		int length = 1, c;
		
		do {
			c = myBoard.getRGB(point.x + length, point.y);
			length++;
		} while(!isGreen(c) && length < 10);
		
		return length < 8;
	}
	
	public static boolean isThree(Point point, BufferedImage myBoard) {
		int length = 1, c;
		
		do {
			c = myBoard.getRGB(point.x + length, point.y);
			length++;
		} while(!isRed(c) && length < 10);
		
		return length < 8;
	}
	
	public static boolean isFour(Point point, BufferedImage myBoard) {
		int length = 1, c;
		
		do {
			c = myBoard.getRGB(point.x + length, point.y);
			length++;
		} while(!isDarkBlue(c) && length < 10);
		
		return length < 8;
	}
	
	private static boolean isRed(int c) {
		int r = (c >> 16) & 0xFF;
	    int g = (c >> 8) & 0xFF;
	    int b = c & 0xFF;
	    
		return (b + g)*2 < r;
	}

	public static boolean isGreen(int c) {
		int r = (c >> 16) & 0xFF;
	    int g = (c >> 8) & 0xFF;
	    int b = c & 0xFF;
	    
		return (r + b)*2 < g;
	}

	public static boolean isBlue(int c) {
		int r = (c >> 16) & 0xFF;
	    int g = (c >> 8) & 0xFF;
	    int b = c & 0xFF;
	    
	    
		return (r < 100 && r > 60) && (g < 115 && g > 80) && (b < 215 && b > 190);
	}
	
	public static boolean isDarkBlue(int c) {
		int r = (c >> 16) & 0xFF;
	    int g = (c >> 8) & 0xFF;
	    int b = c & 0xFF;
	    
	    
		return (r + g) * 3 < b;
	}
	
	public static boolean isDark(int c) {
		int r = (c >> 16) & 0xFF;
	    int g = (c >> 8) & 0xFF;
	    int b = c & 0xFF;
	    
	    return r + g + b < 120;
	}

	public static boolean isGray(int c) {
		int r = (c >> 16) & 0xFF;
	    int g = (c >> 8) & 0xFF;
	    int b = c & 0xFF;
	    
	    if(r < 50 || g < 50 || b < 50)
	    	return false;
	    
	    if(r > 240 || g > 240 || b > 240)
	    	return false;
	    
	    int diffR = Math.max(Math.abs(r -g), Math.abs(r- b));
	    int diffG = Math.abs(g - b);

	    
	    if(diffR < 40 && diffG < 40)
	    	return true;
	    
	    return false;
	}
	
	public static boolean isEndScreen(int c) {
		int r = (c >> 16) & 0xFF;
	    int g = (c >> 8) & 0xFF;
	    int b = c & 0xFF;
	    
	    return r == 240 && b == 240 && c == 240;
	}
	
	public static void toString(int c) { 
		int r = (c >> 16) & 0xFF;
	    int g = (c >> 8) & 0xFF;
	    int b = c & 0xFF;
	    
	    System.out.println("("+r+", "+g+", "+b+")");
	}
}
