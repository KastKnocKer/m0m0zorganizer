package gui;

import gui.Tabella.TabellaMp3;
import java.awt.event.*;
import javax.swing.*;
import java.awt.GridLayout;

/**
 * Classe che definisce il Pannello Nord dell'interfaccia grafica, tramite anche i 
 * metodi ereditati della classe JPanel e ActionListener
 * 
 * @author Administrator
 */

@SuppressWarnings("serial")
public class JPanelNord extends JPanel implements ActionListener {
    
	/**
	 * Costruttore del Pannello Nord
	 */
    public JPanelNord () {
    	jButton1 = new JButton("Libreria Globale");
        jButton2 = new JButton("Artisti"); 
        
        /* Definisco le impostazioni basi del pannello */
        setMinimumSize(new java.awt.Dimension(100, 25));
        setPreferredSize(new java.awt.Dimension(100, 25));
        setLayout(new GridLayout(1, 4));
        
        /* Definisco l'ascoltatore dei bottoni */ 
        jButton1.addActionListener(this);
        jButton2.addActionListener(this);
        
        /* Inserisco i bottoni nel pannello */
        add(jButton1);
        add(jButton2);
    }    

    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * ActionListener 
     */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Libreria Globale") {
			JPanelLibreria.ViewLibreria();
			JPanelLibreria.UpdateComboBox(TabellaMp3.getLibreriaBack(), false);
		}
		else if (e.getActionCommand() == "Artisti") {
			JPanelLibreria.ViewArtisti();
			if (TabellaMp3.getTabella().getRowCount() == 0) {
				JPanelLibreria.getJComboFiltro("Artista").setSelectedIndex(0);
				JPanelArtisti.loadArtisti(JPanelLibreria.getJComboFiltro("Artista").getSelectedItem().toString());
			}
			if(TabellaMp3.getTabella().getRowCount() == 0 )
				return;
			int[] x = TabellaMp3.getTabella().getSelectedRows();
			if	(x.length != 0)
				JPanelArtisti.loadArtisti(TabellaMp3.getLibreriaView().getFile(x[0]).getArtista());
			else
				JPanelArtisti.loadArtisti(JPanelLibreria.getJComboFiltro("Artista").getSelectedItem().toString());			
		}
	}
	
    private JButton jButton1;
    private JButton jButton2;
}