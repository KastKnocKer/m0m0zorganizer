package gui;
import gui.Metadati.JDialogArtista;
import io.TextFileHandler;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

import gui.Tabella.*;
import database.ListaMp3;
import artisti.*;

/**
 * Classe che definisce il Pannello Artisti dell'interfaccia grafica, tramite anche i 
 * metodi ereditati della classe JPanel e ActionListener
 * 
 * @author Administrator
 */

@SuppressWarnings("serial")
public class JPanelArtisti extends JPanel implements ActionListener {

	/**
	 * Costruttore del Pannello Artisti
	 */
    public JPanelArtisti() {
    	super();
    	
        jLabel1 = new JLabel();
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jLabel7 = new JLabel();
        jTextField6 = new JTextField();
        jLabel8 = new JLabel();
        jLabel16 = new JLabel();
        jTextField7 = new JTextField();
        jSplitPane1 = new JSplitPane();
        jPanel3 = new JPanel();
        jLabel2 = new JLabel();
        jTextField1 = new JTextField();
        jLabel3 = new JLabel();
        jTextField2 = new JTextField();
        jLabel4 = new JLabel();
        jTextField3 = new JTextField();
        jLabel5 = new JLabel();
        jTextField4 = new JTextField();
        jLabel6 = new JLabel();
        jTextField5 = new JTextField();
        jPanel4 = new JPanel();
        jLabel9 = new JLabel();
        jTextField8 = new JTextField();
        jLabel10 = new JLabel();
        jTextField9 = new JTextField();
        jLabel11 = new JLabel();
        jTextField10 = new JTextField();
        jLabel12 = new JLabel();
        jTextField11 = new JTextField();
        jLabel13 = new JLabel();
        jScrollPane1 = new JScrollPane();
        jTextPane1 = new JTextPane();
        jLabel14 = new JLabel();
        jScrollPane2 = new JScrollPane();
        jTextPane2 = new JTextPane();
        jLabel15 = new JLabel();
        jScrollPane3 = new JScrollPane();
        jTextPane3 = new JTextPane();
        jPanel5 = new JPanel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jButton4 = new JButton();   
        jButton5 = new JButton();


        setLayout(new BorderLayout());

        jLabel1.setFont(new Font("Tahoma", 0, 14)); 
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setText("Artista Musicale: ");
        jLabel1.setPreferredSize(new Dimension(34, 30));
        add(jLabel1, BorderLayout.PAGE_START);

        jPanel1.setLayout(new BorderLayout());

        jPanel2.setBorder(BorderFactory.createTitledBorder("Informazioni Gruppo"));
        jPanel2.setPreferredSize(new Dimension(100, 60));

        jLabel7.setText("Data di Formazione");
        jLabel7.setPreferredSize(new Dimension(100, 25));
        jPanel2.add(jLabel7);

        jTextField6.setPreferredSize(new Dimension(250, 25));
        jPanel2.add(jTextField6);

        jLabel8.setPreferredSize(new Dimension(200, 25));
        jPanel2.add(jLabel8);

        jLabel16.setText("Data di Scioglimento");
        jLabel16.setPreferredSize(new Dimension(100, 25));
        jPanel2.add(jLabel16);

        jTextField7.setPreferredSize(new Dimension(250, 25));
        jPanel2.add(jTextField7);

        jPanel1.add(jPanel2, BorderLayout.PAGE_START);

        jSplitPane1.setDividerLocation(300);

        jPanel3.setBorder(BorderFactory.createTitledBorder("Dati Anagrafici"));
        jPanel3.setMinimumSize(new Dimension(300, 100));
        jPanel3.setLayout(null);

        jLabel2.setText("Nome:");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(20, 35, 150, 25);

        jPanel3.add(jTextField1);
        jTextField1.setBounds(20, 60, 260, 25);

        jLabel3.setText("Cognome:");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(20, 95, 150, 25);

        jPanel3.add(jTextField2);
        jTextField2.setBounds(20, 120, 260, 25);

        jLabel4.setText("Data di Nascita:");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(20, 155, 150, 25);

        jPanel3.add(jTextField3);
        jTextField3.setBounds(20, 180, 260, 25);

        jLabel5.setText("Data decesso:");
        jPanel3.add(jLabel5);
        jLabel5.setBounds(20, 215, 150, 25);

        jPanel3.add(jTextField4);
        jTextField4.setBounds(20, 240, 260, 25);

        jLabel6.setText("Nazionalità");
        jPanel3.add(jLabel6);
        jLabel6.setBounds(20, 275, 150, 25);

        jPanel3.add(jTextField5);
        jTextField5.setBounds(20, 300, 260, 25);

        jSplitPane1.setLeftComponent(jPanel3);
        jSplitPane1.setBorder(null);

        jPanel4.setLayout(null);

        jPanel4.setBorder(BorderFactory.createTitledBorder("Informazioni Musicali"));

        jLabel9.setText("Nome d'Arte:");
        jPanel4.add(jLabel9);
        jLabel9.setBounds(20, 35, 100, 25);

        jPanel4.add(jTextField8);
        jTextField8.setBounds(20, 60, 300, 25);

        jLabel10.setText("Data d'esordio:");
        jPanel4.add(jLabel10);
        jLabel10.setBounds(388, 35, 100, 25);

        jPanel4.add(jTextField9);
        jTextField9.setBounds(388, 60, 300, 25);

        jLabel11.setText("FanPage:");
        jPanel4.add(jLabel11);
        jLabel11.setBounds(20, 95, 100, 25);

        jPanel4.add(jTextField10);
        jTextField10.setBounds(20, 120, 300, 25);

        jLabel12.setText("Casa Discografica");
        jPanel4.add(jLabel12);
        jLabel12.setBounds(388, 95, 100, 25);

        jPanel4.add(jTextField11);
        jTextField11.setBounds(388, 120, 300, 25);

        jLabel13.setText("DiscoGrafia");
        jPanel4.add(jLabel13);
        jLabel13.setBounds(20, 155, 100, 25);

        jScrollPane1.setViewportView(jTextPane1);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(20, 180, 668, 25);

        jLabel14.setText("Generi Musicali");
        jPanel4.add(jLabel14);
        jLabel14.setBounds(20, 215, 100, 25);

        jScrollPane2.setViewportView(jTextPane2);
        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(20, 240, 668, 25);

        jLabel15.setText("Bibliografia");
        jPanel4.add(jLabel15);
        jLabel15.setBounds(20, 275, 100, 25);
        
        jScrollPane3.setViewportView(jTextPane3);

        jPanel4.add(jScrollPane3);
        jScrollPane3.setBounds(20, 300, 668, 160);

        jSplitPane1.setRightComponent(jPanel4);

        jPanel1.add(jSplitPane1, BorderLayout.CENTER);

        jButton1.setText("Componente Precedente");
        jButton1.setPreferredSize(new Dimension(155, 23));
        jPanel5.add(jButton1);

        jButton3.setText("Salva");
        jButton3.setPreferredSize(new Dimension(155, 23));
        jPanel5.add(jButton3);

        jButton4.setText("Annulla");
        jButton4.setPreferredSize(new Dimension(155, 23));
        jPanel5.add(jButton4);
        
        jButton5.setText("Rimuovi Scheda");
        jButton5.setPreferredSize(new Dimension(155, 23));
        jPanel5.add(jButton5);

        jButton2.setText("Componente Successivo");
        jButton2.setPreferredSize(new Dimension(155, 23));
        jPanel5.add(jButton2);
        


        jPanel1.add(jPanel5, BorderLayout.PAGE_END);
        
        jButton1.addActionListener(this);
        jButton2.addActionListener(this);
        jButton3.addActionListener(this);
        jButton4.addActionListener(this);
        jButton5.addActionListener(this);

        add(jPanel1, BorderLayout.CENTER);
    }            
    
    /**
     * Metodo per caricare le informazioni relative all'artista o al gruppo musicale avete come nome
     * il parametro nomeArt
     * @param nomeArt
     * 				il nome dell'artista di cui si vogliono visualizzare le informazioni
     */
    public static void loadArtisti(String nomeArt) {
    	nome = nomeArt;
    	Artista = new TextFileHandler().readArtista();
    	Gruppo = new TextFileHandler().readGruppo();
	  	    	
    	if (nome.equals("Seleziona Artista")) {
    		setInfo(new SoloMusicArtist("","","","","","","","","","","",""));
    		jLabel1.setText("Seleziona un Artista o un Gruppo dall'elenco");    		
    		return;
    		}
    	else {
       		
    		for (int i = 0; i < Artista.size(); i++)
    			if (Artista.elementAt(i).getNomeArte().equals(nome)) {
    				setInfo(Artista.elementAt(i));
    				return;
    			}
    		for (int i = 0; i < Gruppo.size(); i++)
    			if (Gruppo.elementAt(i).getNomeGruppo().equals(nome)) {
    				setInfo(Gruppo.elementAt(i), 0);
    				return;
    			} 
    		new JDialogArtista(nome);
    		}
    	}
    	
    /**
     * Metodo per impostare le informazioni di un artista musicale all'interno dei campi del
     * Pannello Artisti
     * @param artista
     * 				l'artista di cui si vogliono visualizzare le informazioni
     */
    public static void setInfo  (SoloMusicArtist artista) {
    	jLabel7.setVisible(false);
    	jLabel16.setVisible(false);
		jTextField6.setVisible(false);
		jTextField7.setVisible(false);
		jTextField6.setText("");
		jTextField7.setText("");
		jButton1.setVisible(false);
		jButton2.setVisible(false);
		
    	ArtistaSet = artista;
    	jLabel1.setText("Artista selezionato: " + artista.getNomeArte());
    	jTextField1.setText(artista.getNome());
    	jTextField2.setText(artista.getCognome());
    	jTextField3.setText(artista.getDataNascita());
    	jTextField4.setText(artista.getDataMorte());
    	jTextField5.setText(artista.getNazionalità());
    	jTextField8.setText(artista.getNomeArte());
    	jTextField9.setText(artista.getDataEsordio());
    	jTextField10.setText(artista.getFanPage());
    	jTextField11.setText(artista.getCasaDisco());    
    	jTextPane1.setText(artista.getDiscografia());
    	jTextPane2.setText(artista.getGeneri());
    	jTextPane3.setText(artista.getBiografia());    	
    }
    
    /**
     * Metodo per impostare le informazioni di un componente di un gruppo musicale all'interno 
     * dei campi del Pannello Artisti
     * 
     * @param gruppo
     * 				il gruppo di cui si vogliono visualizzare le informazioni relative ad un componente
     * @param num
     * 				l'indice del componente del gruppo
     */
    public static void setInfo  (MusicGroup gruppo, int num) {
    	jLabel7.setVisible(true);
    	jLabel16.setVisible(true);
		jTextField6.setVisible(true);
		jTextField7.setVisible(true);
		jButton1.setVisible(true);
		jButton2.setVisible(true);
		
    	ComponenteSel = num;
    	GruppoSet = gruppo;
    	jLabel1.setText("Gruppo: " + gruppo.getNomeGruppo() + "                   Artista selezionato: " 
    			+ gruppo.getArtista(num).getNomeArte() + "                   Componente " + (num+1) + " di " 
    			+ gruppo.getNumeroArtisti());
    	jTextField1.setText(gruppo.getArtista(num).getNome());
    	jTextField2.setText(gruppo.getArtista(num).getCognome());
    	jTextField3.setText(gruppo.getArtista(num).getDataNascita());
    	jTextField4.setText(gruppo.getArtista(num).getDataMorte());
    	jTextField5.setText(gruppo.getArtista(num).getNazionalità());
    	jTextField8.setText(gruppo.getArtista(num).getNomeArte());
    	jTextField9.setText(gruppo.getArtista(num).getDataEsordio());
    	jTextField10.setText(gruppo.getArtista(num).getFanPage());
    	jTextField11.setText(gruppo.getArtista(num).getCasaDisco());    
    	jTextPane1.setText(gruppo.getArtista(num).getDiscografia());
    	jTextPane2.setText(gruppo.getArtista(num).getGeneri());
    	jTextPane3.setText(gruppo.getArtista(num).getBiografia());   
    	
    	jTextField6.setText(gruppo.getDataFormazione());
    	jTextField7.setText(gruppo.getDataScioglimento());
    }
    
    /**
     * Metodo per costruire le informazioni relative ad un nuovo artista musicale, 
     * non presente nel database 
     */
    public static void makeArtista  () { 
    	SoloMusicArtist tmp = new SoloMusicArtist();
    	Vector<String> temp1  = new Vector<String>();
    	Vector<String> temp2 = new Vector<String>();
    	String temp3 = "";
    	tmp.setNomeArte(nome);
    	Artista.add(tmp);    	
    	ListaMp3 Libreria = TabellaMp3.getLibreriaBack();
    	for (int i = 0; i < Libreria.length(); i++) {
    		if (Libreria.getFile(i).getArtista().equals(nome)) {
    			if(!temp1.contains(Libreria.getFile(i).getAlbum()))
    				if (!Libreria.getFile(i).getAlbum().equals("Sconosciuto"))
    					temp1.add(Libreria.getFile(i).getAlbum());
    			if(!temp2.contains(Libreria.getFile(i).getGenere()))
    				if (!Libreria.getFile(i).getGenere().equals("Sconosciuto"))
    					temp2.add(Libreria.getFile(i).getGenere());
    		}
    	}
    	for (int i = 0; i < temp1.size(); i++) {
    		temp3 = temp3.concat(temp1.elementAt(i));
    		if (i < temp1.size()-1)
    			temp3 = temp3.concat(" - ");
    	}
    	Artista.elementAt(Artista.size()-1).setDiscografia(temp3);
    	temp3 = "";
    	for (int i = 0; i < temp2.size(); i++) {
    		temp3 = temp3.concat(temp2.elementAt(i));
    		if (i < temp2.size()-1)
    			temp3 = temp3.concat(" - ");
    	}   	    	
    	Artista.elementAt(Artista.size()-1).setGeneri(temp3);
    	System.out.println("PROVA: " + temp3);
    	new TextFileHandler().writeArtista(Artista);
    	setInfo(tmp);    	
    	}
    
    /**
     * Metodo per costruire le informazioni relative ad un nuovo gruppo musicale, 
     * non presente nel database 
     */
    public static void makeGruppo (int numeroArtisti) { 
    	MusicGroup tmp = new MusicGroup(numeroArtisti);
    	Vector<String> temp1  = new Vector<String>();
    	Vector<String> temp2 = new Vector<String>();
    	String temp3 = "";
    	String temp4 = "";
    	tmp.setNomeGruppo(nome);
    	Gruppo.add(tmp);
    	ListaMp3 Libreria = TabellaMp3.getLibreriaBack();
    	for (int i = 0; i < Libreria.length(); i++) {
    		if (Libreria.getFile(i).getArtista().equals(nome)) {
    			if(!temp1.contains(Libreria.getFile(i).getAlbum()))
    				if (!Libreria.getFile(i).getAlbum().equals("Sconosciuto"))
    					temp1.add(Libreria.getFile(i).getAlbum());
    			if(!temp2.contains(Libreria.getFile(i).getGenere()))
    				if (!Libreria.getFile(i).getGenere().equals("Sconosciuto"))
    					temp2.add(Libreria.getFile(i).getGenere());
    		}
    	}
    	for (int i = 0; i < temp1.size(); i++) {
    		temp3 = temp3.concat(temp1.elementAt(i));
    		if (i < temp1.size()-1)
    			temp3 = temp3.concat(" - ");
    	}
    	for (int i = 0; i < temp2.size(); i++) {
    		temp4 = temp4.concat(temp2.elementAt(i));
    		if (i < temp2.size()-1)
    			temp4 = temp4.concat(" - ");
    	}
    	for (int i = 0; i < Gruppo.elementAt(Gruppo.size()-1).getNumeroArtisti(); i++) {
    		Gruppo.elementAt(Gruppo.size()-1).getArtista(i).setDiscografia(temp3);
    		Gruppo.elementAt(Gruppo.size()-1).getArtista(i).setGeneri(temp4);
    	}
    	new TextFileHandler().writeGruppo(Gruppo);
    	setInfo(tmp, 0);   
    }
    
    /**
     * Metodo per salvare le informazioni relative ad un artista/gruppo musicale
     */
    public static void salva() {
		if (jTextField6.isVisible()) {
			GruppoSet.setDataFormazione(jTextField6.getText());
	    	GruppoSet.setDataScioglimento(jTextField7.getText());
	    	GruppoSet.setArtista(ComponenteSel,
	    			jTextField1.getText(), 
	    			jTextField2.getText(), 
	    			jTextField3.getText(), 
	    			jTextField4.getText(), 
	    			jTextField5.getText(), 
	    			jTextField8.getText(),
	    			jTextPane3.getText(),
	    			jTextField9.getText(),
	    			jTextPane2.getText(),
	    			jTextPane1.getText(),
	    			jTextField10.getText(),
	    			jTextField11.getText());
	  	    for (int i = 0; i < Gruppo.size(); i++)
	  	    	if (Gruppo.elementAt(i).getNomeGruppo().equals(nome))
	  	    		Gruppo.removeElementAt(i);
	  	    Gruppo.add(GruppoSet);	  	   
	    	new TextFileHandler().writeGruppo(Gruppo);
		}
		else {
			ArtistaSet.SetSoloMusicArtist(
				jTextField1.getText(), 
    			jTextField2.getText(), 
    			jTextField3.getText(), 
    			jTextField4.getText(), 
    			jTextField5.getText(), 
    			jTextField8.getText(),
    			jTextPane3.getText(),
    			jTextField9.getText(),
    			jTextPane2.getText(),
    			jTextPane1.getText(),
    			jTextField10.getText(),
    			jTextField11.getText());
	    	for (int i = 0; i < Artista.size(); i++)
	    		if (Artista.elementAt(i).getNomeArte().equals(nome))
	    			Artista.removeElementAt(i);
	    	Artista.add(ArtistaSet);
	    
	    	new TextFileHandler().writeArtista(Artista);
		}
    }

    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * ActionListener
     * 
     */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jButton3 )
			salva();
		else if (e.getSource() == jButton4) {
			if (jTextField6.isVisible())
				setInfo(GruppoSet, 0);
			else 
				setInfo(ArtistaSet);
		}
		else if (e.getSource() == jButton1) {
			salva();
			if(ComponenteSel == 0)
				ComponenteSel = GruppoSet.getNumeroArtisti();
			setInfo(GruppoSet, --ComponenteSel);
		}			
		else if (e.getSource() == jButton2) {	
			salva();
			if(ComponenteSel == (GruppoSet.getNumeroArtisti() - 1))
				ComponenteSel = - 1;
			setInfo(GruppoSet, ++ComponenteSel);
			}
		else if	(e.getSource() == jButton5) {
			if (jTextField6.isVisible()) {
				Object[] options = {"Rimuovi", "Annulla",};
				int n = JOptionPane.showOptionDialog(MainFrame.getMainFrame(),
						"Vuoi veramente rimuovere la scheda del gruppo?",
					    "Rimozione scheda gruppo",
					    JOptionPane.OK_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    options,
					    options[1]);
				if (n == JOptionPane.OK_OPTION ) {
					for (int i = 0; i < Gruppo.size(); i++)
						if (Gruppo.elementAt(i).getNomeGruppo().equals(nome))
							Gruppo.removeElementAt(i);
					new TextFileHandler().writeGruppo(Gruppo);
					JPanelLibreria.getJComboFiltro("Artista").setSelectedIndex(0);
				}
			}
			else {
				Object[] options = {"Rimuovi", "Annulla",};
				int n = JOptionPane.showOptionDialog(MainFrame.getMainFrame(),
						"Vuoi veramente rimuovere la scheda dell' artista?",
					    "Rimozione scheda artista",
					    JOptionPane.OK_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    options,
					    options[1]);
				if (n == JOptionPane.OK_OPTION ) {
					for (int i = 0; i < Artista.size(); i++)
						if (Artista.elementAt(i).getNomeArte().equals(nome))
							Artista.removeElementAt(i);
					new TextFileHandler().writeArtista(Artista);
					JPanelLibreria.getJComboFiltro("Artista").setSelectedIndex(0);
				}
			}

		}
	}

    // Variables declaration - do not modify
    private static JButton jButton1;
    private static JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private static JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private static JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private static JLabel jLabel16;
    private JPanel jPanel1;
    private static JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JSplitPane jSplitPane1;
    private static JTextField jTextField1;
    private static JTextField jTextField2;
    private static JTextField jTextField3;
    private static JTextField jTextField4;
    private static JTextField jTextField5;
    private static JTextField jTextField6;
    private static JTextField jTextField7;
    private static JTextField jTextField8;
    private static JTextField jTextField9;
    private static JTextField jTextField10;
    private static JTextField jTextField11;
    private static JTextPane jTextPane1;
    private static JTextPane jTextPane2;
    private static JTextPane jTextPane3;
    private static Vector<SoloMusicArtist> Artista; 
    private static Vector<MusicGroup> Gruppo;
    private static String nome;
    private static SoloMusicArtist ArtistaSet;
    private static MusicGroup GruppoSet;
    private static int ComponenteSel;
    // End of variables declaration
}
