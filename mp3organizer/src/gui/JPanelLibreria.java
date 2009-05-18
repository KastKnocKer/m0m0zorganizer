package gui;

import gui.Tabella.TabellaMp3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import database.ListaMp3;

/**
 * Classe che definisce il Pannello Libreria dell'interfaccia grafica, tramite anche i 
 * metodi ereditati della classe JPanel e ActionListener
 * 
 * @author Administrator
 */

@SuppressWarnings("serial")
public class JPanelLibreria extends JPanel implements ActionListener{

	/**
	 * Costruttore del Pannello Libreria
	 */
    public JPanelLibreria () {
    	LinkjPanelLibreria = this;

        setLayout(new BorderLayout());        
    
        Tabella = new TabellaMp3();
        jScrollPane = new JScrollPane(Tabella);
        jScrollPane.setBorder(BorderFactory.createTitledBorder("Libreria Globale"));

        jPanelFiltro = new JPanel();
        jLabel1 = new JLabel("Filtri:", SwingConstants.CENTER);
        jLabel2 = new JLabel("Monduzzi Matr.25505 v 1.0", SwingConstants.CENTER);
        

        jPanelFiltro.setBorder(BorderFactory.createTitledBorder(""));
        jPanelFiltro.setPreferredSize(new Dimension(800, 40));
        jPanelFiltro.setLayout(new GridLayout());
        
        jComboArtista = new JComboBox();
        jComboAlbum = new JComboBox();
        jComboAnno = new JComboBox();
        jComboGenere = new JComboBox();
        
        UpdateComboBox(TabellaMp3.getLibreriaBack(), false);
        
        
        jComboArtista.addActionListener(this);
        jComboAlbum.addActionListener(this);
        jComboAnno.addActionListener(this);
        jComboGenere.addActionListener(this);
        
        jPanelFiltro.add(jLabel1);
        jPanelFiltro.add(jComboArtista);
        jPanelFiltro.add(jComboAlbum);
        jPanelFiltro.add(jComboAnno);
        jPanelFiltro.add(jComboGenere);
        jPanelFiltro.add(jLabel2);
        
        ViewLibreria = true;
        jPanelArtisti = new JPanelArtisti();
        
        add(jPanelFiltro, BorderLayout.PAGE_START);
		add(jScrollPane, BorderLayout.CENTER);                
	}
	
    /**
     * Metodo per visualizzare all'interno del Pannello Libreria, il jScrollPanel
     * contenente la Tabella degli Mp3
     */
	public static void ViewLibreria() {
		if (ViewLibreria) {
			TabellaMp3.loadLibreriaView();
			TabellaMp3.getTabella().updateUI();
		}
		else {
			LinkjPanelLibreria.remove(jPanelArtisti);
			LinkjPanelLibreria.add(jScrollPane, BorderLayout.CENTER);
			LinkjPanelLibreria.updateUI();
		}
		ViewLibreria = true;
	}
	
    /**
     * Metodo per visualizzare all'interno del Pannello Libreria, il pannello
     * Artisti, contenente le informazioni dell'artista/gruppo musicale.
     */
	public static void ViewArtisti() {
		LinkjPanelLibreria.remove(jScrollPane);
		LinkjPanelLibreria.add(jPanelArtisti, BorderLayout.CENTER);
		LinkjPanelLibreria.updateUI();
		ViewLibreria = false;
	}
	
    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * ActionListener 
     */
	public void actionPerformed(ActionEvent e) {
		if 		(e.getSource() == jComboArtista) {
			if (ViewLibreria)
				TabellaMp3.FiltraLista();
			else {
				if(TabellaMp3.getTabella().getRowCount()!=0) {
					JPanelArtisti.salva();
					JPanelArtisti.loadArtisti(JPanelLibreria.getJComboFiltro("Artista").getSelectedItem().toString());
				}
			}
		}
		else if (e.getSource() == jComboAlbum)
			TabellaMp3.FiltraLista();	
		else if (e.getSource() == jComboAnno)
			TabellaMp3.FiltraLista();
		else if (e.getSource() == jComboGenere)
			TabellaMp3.FiltraLista();
	}
	
	/**
	 * Metodo per aggiornare il contenuto dei JComboBox utilizzati per filtrare le liste
	 * @param Libreria
	 * 			la libreria da cui ricavare i valori da inserire nei vari JComboBox
	 * @param flag
	 * 			parametro che indica se mantenere il parametro del JComboBox selezionato o 
	 * 			se impostare quello	di default
	 */
	public static void UpdateComboBox(ListaMp3 Libreria, boolean flag) {
		UpdateComboArtista(Libreria, flag);
		UpdateComboAlbum(Libreria, flag);
		UpdateComboAnno(Libreria, flag);
		UpdateComboGenere(Libreria, flag);
	}
	
	/**
	 * Metodo per impostare il parametro di default dei JComboBox senza ricaricare le liste
	 * dei vari componenti
	 */
	public static void ResetComboBox() {
		jComboArtista.setSelectedItem("Seleziona Artista");
		jComboAlbum.setSelectedItem("Seleziona Album");
		jComboAnno.setSelectedItem("Seleziona Anno");
		jComboGenere.setSelectedItem("Seleziona Genere");
	}
	
	/**
	 *	Aggiorno il ComboBox Artisti in modo controllato.
	 * @param flag
	 * 			parametro di controllo:
	 * 			- true: mantengo il valore selezionato dal ComboBox
	 * 			- false: imposto il valore di Default	 * 
	 */
	public static void UpdateComboArtista (ListaMp3 Libreria, boolean flag) {
		String tmp = (String) jComboArtista.getSelectedItem();
        jComboArtista.setModel(new DefaultComboBoxModel(TabellaMp3.makeListaComboBox(Libreria, "Artista")));
        if (!flag)
        	jComboArtista.setSelectedItem("Seleziona Artista");
        else 
        	jComboArtista.setSelectedItem(tmp);
        }
	
	/**
	 *	Aggiorno il ComboBox Album in modo controllato.
	 * @param flag
	 * 			parametro di controllo:
	 * 			- true: mantengo il valore selezionato dal ComboBox
	 * 			- false: imposto il valore di Default	 * 
	 */
    public static void UpdateComboAlbum (ListaMp3 Libreria, boolean flag) {
		String tmp = (String) jComboAlbum.getSelectedItem();
		jComboAlbum.setModel(new DefaultComboBoxModel(TabellaMp3.makeListaComboBox(Libreria, "Album")));
        if (!flag)
        	jComboAlbum.setSelectedItem("Seleziona Album");
        else 
        	jComboAlbum.setSelectedItem(tmp);
    }
    
	/**
	 *	Aggiorno il ComboBox Anno in modo controllato.
	 * @param flag
	 * 			parametro di controllo:
	 * 			- true: mantengo il valore selezionato dal ComboBox
	 * 			- false: imposto il valore di Default	 * 
	 */
    public static void UpdateComboAnno (ListaMp3 Libreria, boolean flag) {
		String tmp = (String) jComboAnno.getSelectedItem();
		jComboAnno.setModel(new DefaultComboBoxModel(TabellaMp3.makeListaComboBox(Libreria, "Anno")));
        if (!flag)
        	jComboAnno.setSelectedItem("Seleziona Anno");
        else 
        	jComboAnno.setSelectedItem(tmp);
    }
    
	/**
	 *	Aggiorno il ComboBox Genere in modo controllato.
	 * @param flag
	 * 			parametro di controllo:
	 * 			- true: mantengo il valore selezionato dal ComboBox
	 * 			- false: imposto il valore di Default	 * 
	 */
    public static void UpdateComboGenere (ListaMp3 Libreria, boolean flag) {
		String tmp = (String) jComboGenere.getSelectedItem();
		jComboGenere.setModel(new DefaultComboBoxModel(TabellaMp3.makeListaComboBox(Libreria, "Genere")));
        if (!flag)
        	jComboGenere.setSelectedItem("Seleziona Genere");
        else 
        	jComboGenere.setSelectedItem(tmp);	
	}
	
    /**
     * Metodo che restituisce il JComboBox relativo al parametro passato
     * @param Colonna
     * 			l'identificatore dei JComboBox
     * @return	il JComboBox relativo al parametro Colonna
     */
	public static JComboBox getJComboFiltro (String Colonna) {
		if 		(Colonna.equals("Artista"))
			return jComboArtista;
		else if (Colonna.equals("Album"))
			return jComboAlbum;
		else if (Colonna.equals("Anno"))
			return jComboAnno;
		else if (Colonna.equals("Genere"))
			return jComboGenere;
		return null;
	}
	
	private static JComboBox jComboArtista;
    private static JComboBox jComboAlbum;
	private static JComboBox jComboAnno;
    private static JComboBox jComboGenere;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanelFiltro;
    private static JScrollPane jScrollPane;
    private static TabellaMp3 Tabella; 
    private static JPanelLibreria LinkjPanelLibreria;
    private static JPanelArtisti jPanelArtisti;
    private static boolean ViewLibreria;

}
