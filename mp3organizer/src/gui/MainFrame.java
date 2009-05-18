package gui;

import gui.Metadati.JDialogRicerca;
import gui.Tabella.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import database.ImportaFile;
import database.ListaMp3;

/**
 * Classe che definisce il JFrame MainFrame dell'interfaccia grafica, tramite anche i 
 * metodi ereditati della classe JFrame e ActionListener
 * 
 * @author Administrator
 */

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	/**
	 * Costruttore del JFrame MainFrame 
	 */
	public MainFrame() {
    		super();	
    		LinkMainFrame = this;
	        setTitle("m0m0z Organizer");
	        setMinimumSize(new Dimension(1024, 768));
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        
	        jMenuBar = new JMenuBar();
	        jMenu1 = new JMenu("File");
	        jMenu2 = new JMenu("Strumenti");
	        jMenu3 = new JMenu("Visualizza");
	        jMenu4 = new JMenu("?");
	        jMenuItem1  = new JMenuItem("Importa Mp3");
	        jMenuItem2  = new JMenuItem("Importa Directory");
	        jMenuItem3  = new JMenuItem("Esci");
	        jMenuItem4  = new JMenuItem("Ricerca Elenco Filtrato");
	        jMenuItem5  = new JMenuItem("Ricerca Elenco Totale");
	        jMenuItem6  = new JMenuItem("Svuota Libreria");
	        jMenuItem7  = new JMenuItem("Rimuovi Filtri");
	        jMenuItem8  = new JMenuItem("Rimuovi Mp3");
	        jMenuItem9  = new JMenuItem("Proprietà");
	        jMenuItem10 = new JMenuItem("Visualizza Libreria");
	        jMenuItem11 = new JMenuItem("Visualizza Artisti");
	        jMenuItem12 = new JMenuItem("About");
	        
	        jMenuItem1.addActionListener(this);
	        jMenuItem2.addActionListener(this);
	        jMenuItem3.addActionListener(this);
	        jMenuItem4.addActionListener(this);
	        jMenuItem5.addActionListener(this);
	        jMenuItem6.addActionListener(this);
	        jMenuItem7.addActionListener(this);
	        jMenuItem8.addActionListener(this);
	        jMenuItem9.addActionListener(this);
	        jMenuItem10.addActionListener(this);
	        jMenuItem11.addActionListener(this);
	        jMenuItem12.addActionListener(this);
	        
	        jMenu1.add(jMenuItem1);
	        jMenu1.add(jMenuItem2);
	        jMenu1.add(new JSeparator());
	        jMenu1.add(jMenuItem3);
	        jMenuBar.add(jMenu1);

	        jMenu2.add(jMenuItem4);
	        jMenu2.add(jMenuItem5);
	        jMenu2.add(new JSeparator());
	        jMenu2.add(jMenuItem6);
	        jMenu2.add(jMenuItem7);
	        jMenu2.add(jMenuItem8);
	        jMenu2.add(new JSeparator());
	        jMenu2.add(jMenuItem9);
	        jMenuBar.add(jMenu2);
	    
	        jMenu3.add(jMenuItem10);
	        jMenu3.add(jMenuItem11);
	        jMenuBar.add(jMenu3);
	        
	        jMenu4.add(jMenuItem12);
	        jMenuBar.add(jMenu4);
	        
	        setJMenuBar(jMenuBar);

	        jPanelLibreria = new JPanelLibreria();
	        jPanelNord = new JPanelNord();
	        jPanelSud = new JPanelSud();
	        getContentPane().add(jPanelNord, BorderLayout.PAGE_START);
	        getContentPane().add(jPanelLibreria, BorderLayout.CENTER);
	        getContentPane().add(jPanelSud, BorderLayout.PAGE_END);
	        pack();
	        }
	
	/**
	 * Metodo che restituisce il JFrame MainFrame, in modo da poter sfruttare tale metodo
	 * come un link "dinamico"
	 * @return il JFrame MainFrame
	 */
	public static JFrame getMainFrame() {
		return LinkMainFrame;
	}
	
    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * ActionListener
     * 
     */
	public void actionPerformed(ActionEvent e) {		
		if 		((e.getActionCommand() == "Esci") ) {
			ListaMp3.saveLista("./Salvataggi/LibreriaGlobale.dat");
			System.exit(0);
		}
		
		else if (e.getActionCommand() == "Importa Mp3") {
			new ImportaFile("mp3");
		}
		
		else if (e.getActionCommand() == "Importa Directory") { 
			new ImportaFile("dir");
		}
		
		else if (e.getActionCommand() == "Ricerca Elenco Filtrato")
			new JDialogRicerca(true);
		
		else if (e.getActionCommand() == "Ricerca Elenco Totale")
			new JDialogRicerca(false);
		
		else if (e.getActionCommand() == "Rimuovi Filtri")
			JPanelLibreria.ResetComboBox();
		
		else if (e.getActionCommand() == "Rimuovi Mp3")
			PopupMenuTabella.Rimuovi();	
		
		else if (e.getActionCommand() == "Svuota Libreria") {
			Object[] options = {"Svuota", "Annulla",};
			int n = JOptionPane.showOptionDialog(MainFrame.getMainFrame(),
					"Vuoi veramente la libreria globale?",
				    "Svuotare la libreria",
				    JOptionPane.OK_OPTION,
				    JOptionPane.QUESTION_MESSAGE,
				    null,
				    options,
				    options[1]);
			if (n == JOptionPane.OK_OPTION ) {
				TabellaMp3.getLibreriaView().Svuota();
				TabellaMp3.getLibreriaBack().Svuota();
				ListaMp3.saveLista("./Salvataggi/LibreriaGlobale.dat");	
				JPanelLibreria.UpdateComboBox(TabellaMp3.getLibreriaBack(), false);
				TabellaMp3.getTabella().updateUI();
			}
			}
		else if (e.getActionCommand() == "Proprietà")
			PopupMenuTabella.Proprietà();
		
		else if (e.getActionCommand() == "Visualizza Libreria"){
			JPanelLibreria.ViewLibreria();
			JPanelLibreria.UpdateComboBox(TabellaMp3.getLibreriaBack(), false);
		}
		else if (e.getActionCommand() == "Visualizza Artisti") {
			JPanelLibreria.ViewArtisti();
			JPanelArtisti.loadArtisti(JPanelLibreria.getJComboFiltro("Artista").getSelectedItem().toString());
		}		
		else if (e.getActionCommand() == "About")
			JOptionPane.showMessageDialog(MainFrame.getMainFrame(),
				      "Monduzzi Mattia \n"
					+ "Matricola 25505 \n"
					+ "Mp3-Organizer v1.0",
				    "About",
				    JOptionPane.INFORMATION_MESSAGE
				    );

	}
	
	// Dichiarazione Variabili
	private static JPanelLibreria jPanelLibreria;
	private JPanelNord jPanelNord;
	private JPanelSud jPanelSud;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenu jMenu3;
    private JMenu jMenu4;
    private JMenuBar jMenuBar;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private JMenuItem jMenuItem3;
    private JMenuItem jMenuItem4;
    private JMenuItem jMenuItem5;
    private JMenuItem jMenuItem6;
    private JMenuItem jMenuItem7;
    private JMenuItem jMenuItem8;
    private JMenuItem jMenuItem9;
    private JMenuItem jMenuItem10;
    private JMenuItem jMenuItem11;
    private JMenuItem jMenuItem12;
    private static JFrame LinkMainFrame;
}




