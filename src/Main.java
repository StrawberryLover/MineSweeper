import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Main {
	
	/*
	 * Let's play a game...
	 */
	public static void main(String[] args) throws AWTException, IOException, InterruptedException {
		Calibration window = new Calibration();	
		Process mineSweeper = setup(window);
		Board game =  window.makeBoard();
		
		game.info();		
		game.touch();
	}
	
	public static Process setup(Calibration window) throws IOException, InterruptedException {
		String[] path = new String[] {"C:" + File.separator + "Program Files" + File.separator + "Microsoft Games" + File.separator + "Minesweeper" + File.separator +"MineSweeper.exe"};
		Process p = Runtime.getRuntime().exec(path);	//Run Minesweeper
		Thread.sleep(2000);	//Wait for the program to load
		window.getWindow();	//Save the Image
		
		return p;
	}
}
