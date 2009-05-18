import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import gui.MainFrame;

/**
 * 
 * @author Monduzzi Mattia Matricola 25505
 * @version 1.0 
 *
 */

public class MainOrganizer {
	
	public static void main(String[] args) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} 
			catch (ClassNotFoundException e) {} 
			catch (InstantiationException e) {} 
			catch (IllegalAccessException e) {} 
			catch (UnsupportedLookAndFeelException e) {}

	new MainFrame().setVisible(true);
	
	}
}