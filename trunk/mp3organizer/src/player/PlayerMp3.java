package player;

import gui.JPanelSud;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.media.*;

import database.FileMp3;

/**
 * Classe che sfrutta la libreria JMF per poter creare un riproduttore di file Mp3
 * 
 * @author Monduzzi Mattia
 *
 */
public class PlayerMp3  {

	private  FileMp3 Mp3 = null;
	private  Player player;
	
	/**
	 * Metodo che crea il riproduttore stesso e in più inizia subito la riproduzione del FileMp3
	 * passato come parametro
	 * @param Mp3
	 * 			il FileMp3 da riprodurre
	 * @return una stringa che indica se il riproduttore riesce a riprodurre il FileMp3 o no
	 * 
	 */
	public String Play(FileMp3 Mp3){
		if(Mp3 == null)
			return "Errore";
		
		this.Mp3 = Mp3;
		File file = new File(Mp3.getAbsolutePath());
		if (!file.exists()) {
			{ JPanelSud.ErrorePlay(); return "Errore";}
		}
		try { player = Manager.createRealizedPlayer(file.toURI().toURL()); 
		} 
		catch (NoPlayerException e) { return "Errore";}
		catch (CannotRealizeException e) { return "Errore";}
		catch (MalformedURLException e) { return "Errore";}
		catch (IOException e) { return "Errore";}
		player.start();
		return "OK";
	}

	/**
	 * Metodo per mettere in pausa il riproduttore del FileMp3
	 * 
	 * @return una stringa che indica se il metodo è andato a buon fine o se vi sono stati errori
	 * 
	 */
	public String Pause(){
		if (player == null) return "Errore";;
		player.stop();
		return "OK";
	}
		
	/**
	 * Metodo per riprendere la riproduzione di un FileMp3 precedentemente messo nello stato di pausa
	 * 
	 * @return una stringa che indica se il metodo è andato a buon fine o se vi sono stati errori
	 * 
	 */
	public String Resume () {
		if (player == null) return "Errore";;
		player.syncStart(player.getMediaTime());
		return "OK";
	}

	/**
	 * Metodo per fermare la riproduzione di un FileMp3
	 * 
	 */
	public void Stop(){
		if (player == null) return;
		player.stop();
		}
	
	/**
	 * Metodo per deallocare il riproduttore del FileMp3
	 * 
	 */
	public void Deallocate() {
		if (player == null) return;
		player.deallocate();
	}
	
	/**
	 * Metodo che restituisce il FileMp3 in riproduzione/pausa.
	 * 
	 * @return il FileMp3 in riproduzione/pausa.
	 */
	public  FileMp3 getMp3 () { return Mp3; }
}