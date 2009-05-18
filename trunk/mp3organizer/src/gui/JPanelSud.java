/**
 * 
 */
package gui;

import gui.Tabella.TabellaMp3;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import player.PlayerMp3;
import database.*;

/**
 * Classe che definisce il Pannello Sud dell'interfaccia grafica, tramite anche i 
 * metodi ereditati della classe JPanel e ActionListener
 * 
 * @author Administrator
 */

@SuppressWarnings("serial")
public class JPanelSud extends JPanel implements ActionListener {
	
	/**
	 * Costruttore del Pannello Sud
	 */	
	public JPanelSud () {
        setPreferredSize(new Dimension(100, 36));
        
        jButton1 = new JButton("<<");
        jButton2 = new JButton("Play");
        jButton3 = new JButton(">>");
        jButton4 = new JButton("Stop");
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        
  
        jLabel1.setPreferredSize(new Dimension(250, 23));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        add(jLabel1);
        
        jButton1.addActionListener(this);
        jButton1.setPreferredSize(new Dimension(75, 23));
        add(jButton1);

        jButton2.addActionListener(this);
        jButton2.setPreferredSize(new Dimension(100,23));
        add(jButton2);

        jButton3.addActionListener(this);
        jButton3.setPreferredSize(new Dimension(75, 23));
        add(jButton3);

        jButton4.addActionListener(this);
        jButton4.setPreferredSize(new Dimension(75, 23));
        add(jButton4);
        
        jLabel2.setPreferredSize(new Dimension (250 , 23));
        jLabel2.setHorizontalAlignment(SwingConstants.LEFT);
        add(jLabel2);
        
        pause = false;
	}

    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * ActionListener 
     */
	public void actionPerformed(ActionEvent e) {		
		if (e.getActionCommand() == "Play") {
			setMp3toPlay("Play", pause);
		}
		else if (e.getActionCommand() == "Pausa") {
			jButton2.setText("Play");
			if (Player.Pause().equals("OK"))
					pause = true;
		}
		else if (e.getActionCommand() == ">>") 	
			setMp3toPlay(">>", false);
		
		else if (e.getActionCommand() == "<<") 
			setMp3toPlay("<<", false);

		else if (e.getActionCommand() == "Stop") { 
			Player.Stop();
			Player.Deallocate();
			jButton2.setText ("Play");
			jLabel1.setText("");
			jLabel2.setText("");
			pause = false;
			Player = new PlayerMp3();
		}
	}
	
	/**
	 * Metodo per impostare i campi del pannello se si verifica un errore nella riproduzione del FileMp3
	 * selezionato e per visualizzare un JDialog di errore
	 */
	public static void ErrorePlay() {
		JOptionPane.showMessageDialog(MainFrame.getMainFrame(),
				"Errore nella riproduzione del file.",
				"File Mp3 non supportato",
				JOptionPane.ERROR_MESSAGE);
		jButton2.setText("Play");
		jLabel1.setText("");
		jLabel2.setText("");
	}
	
	/**
	 * Metodo per impostare il FileMp3 da riprodurre in base ai parametri passati 
	 * @param stringa
	 * 			stringa per identificare quale tasto è stato premuto
	 * @param pause
	 * 			flag che indica se il player è in pausa o no 
	 */
	public static void setMp3toPlay (String stringa, boolean pause) {
		if (Player == null)
			Player = new PlayerMp3();
		else 
			Player.Stop();
		if (TabellaMp3.getTabella().getRowCount() == 0) {
			jButton2.setText("Play");
			jLabel1.setText("Nessun file nella Libreria filtrata/normale.");
			jLabel2.setText("");
		}
		FileMp3 Mp3Play = null;
		int pos = 0;
		int[] file = TabellaMp3.getTabella().getSelectedRows();

		TabellaMp3.getLibreriaBack();
		LibreriaView = TabellaMp3.getLibreriaView();
		if (stringa.equals("Play")) {
			if (file.length != 0)
				pos = file[0];
			Mp3Play = LibreriaView.getFile(pos);
		}
		else if (stringa.equals("<<")) {
			Mp3Play = Player.getMp3();
			pos = LibreriaView.getPos(Mp3Play);
			if (pos == 0)
				pos = LibreriaView.length()-1;
			else 
				pos--;
			Mp3Play = LibreriaView.getFile(pos);
		}
		else if (stringa.equals(">>")) {
			Mp3Play = Player.getMp3();
			pos = LibreriaView.getPos(Mp3Play);
			if ((pos == LibreriaView.length()-1) || (pos > (LibreriaView.length()-1)))
				pos = 0;
			else 
				pos++;
			Mp3Play = LibreriaView.getFile(pos);
		}
		TabellaMp3.getTabella().getSelectionModel().setSelectionInterval(pos,pos);
		if (pause) {
			if (Player.Resume().equals("Errore"))
				ErrorePlay();
			else 
				jButton2.setText("Pausa");
		}			
		else {
			if (Player.Play(Mp3Play).equals("Errore"))
				ErrorePlay();
			else {
				jLabel1.setText("In Esecuzione: "+ Mp3Play.getArtista() + " - " + Mp3Play.getTitolo() + "   ");
				jLabel2.setText(" Album: " + Mp3Play.getAlbum() + "      Anno: " + Mp3Play.getAnno());
				jButton2.setText("Pausa");
			}
		}
		pause = false;
	}
	

	private static JButton jButton1;
    private static JButton jButton2;
    private static JButton jButton3;
    private static JButton jButton4;
    private static JLabel jLabel1;
    private static JLabel jLabel2;
    private static ListaMp3 LibreriaView;
    private static PlayerMp3 Player;    
    private boolean pause;	
}