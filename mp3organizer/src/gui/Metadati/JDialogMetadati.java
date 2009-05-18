package gui.Metadati;

import gui.JPanelLibreria;
import gui.MainFrame;
import gui.Tabella.TabellaMp3;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import database.FileMp3;

/**
 * Classe che definisce tramite anche l'ereditarietà dei metodi delle classi JDialog e ActionListener
 * le impostazioni visive e non della JDialog visualizzare i metadati dei vari FileMp3. 
 * 
 * @author Monduzzi Mattia
 *
 */
@SuppressWarnings("serial")
public class JDialogMetadati extends JDialog implements ActionListener {
	
	/**
	 * Costruttore del JDialog Metadati
	 * @param Pos
	 * 			posizione nella LibreriaView del FileMp3 di cui si vogliono visualizzare i metadati
	 */
    public JDialogMetadati(int Pos) {
    	this.Pos = Pos;
        this.Mp3 = TabellaMp3.getLibreriaView().getFile(Pos);
        jPanel1 	= new JPanel();
        jButton1	= new JButton("Salva");
        jButton2 	= new JButton("Annulla");
        jButton3 	= new JButton("Importa Copertina");
        jButton4 	= new JButton("Mp3 Prec");
        jButton5 	= new JButton("Mp3 Succ");
        jPanel2 	= new JPanel();
        jPanel3 	= new JPanel();
        jPanel4 	= new JPanel();
        jScrollPane1	 = new JScrollPane();
        jTextPaneLyrics  = new JTextPane();
        jPanel6  = new JPanel();
        jLabel1  = new JLabel("Artista:");
        jLabel2  = new JLabel("Titolo:");
        jLabel3  = new JLabel("Album:");
        jLabel4  = new JLabel("Anno:");
        jLabel5  = new JLabel("Traccia:");
        jLabel6  = new JLabel("Genere:");
        jLabel7  = new JLabel("BitRate:");
        jLabel8  = new JLabel("SampleRate:");
        jLabel9  = new JLabel("Channels:");
        jTextArtista	= new JTextField();
        jTextTitolo		= new JTextField();
        jTextAlbum 		= new JTextField();
        jTextAnno 	 	= new JTextField();
        jTextTraccia 	= new JTextField();
        jTextGenere  	= new JTextField();
        jTextBitRate 	= new JTextField();
        jTextSampleRate = new JTextField();
        jTextChannels 	= new JTextField();
        jMenuBar = new JMenuBar();
        jMenu1	 = new JMenu("File");
        jMenu2 	 = new JMenu("Modifica");
        jMenuItem1 = new JMenuItem("Salva ed Esci");
        jMenuItem2 = new JMenuItem("Annulla Modifiche");
        jMenuItem3 = new JMenuItem("Importa Copertina");
        jSeparator = new JSeparator();
        jLabelCopertina = new JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(MainFrame.getMainFrame().getWidth()/3 - 100, MainFrame.getMainFrame().getHeight()/3 - 50);
        setPreferredSize(new Dimension( 600,480));
        setResizable(false);
        setAlwaysOnTop(true);

        
        jPanel1.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.setPreferredSize(new Dimension(100, 35));
        
        jButton1.setAlignmentY(0.0F);
        jButton1.setPreferredSize(new Dimension(85, 23));
        jButton1.addActionListener(this);
        jPanel1.add(jButton1);

        jButton2.setPreferredSize(new Dimension(85, 23));
        jButton2.addActionListener(this);
        jPanel1.add(jButton2);

        jButton3.setPreferredSize(new Dimension(140, 23));
        jButton3.addActionListener(this);
        jPanel1.add(jButton3);

        getContentPane().add(jPanel1, BorderLayout.PAGE_END);

        jPanel2.setLayout(new BorderLayout());
        
        jPanel3.setPreferredSize(new Dimension(200, 100));
        jPanel3.setLayout(new BorderLayout());

        jPanel4.setBorder(BorderFactory.createTitledBorder("Copertina"));
        jPanel4.setPreferredSize(new Dimension(200, 200));
        jPanel4.setLayout(null);        
    	
        
        jLabelCopertina.setHorizontalAlignment(SwingConstants.CENTER);
        jLabelCopertina.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        jPanel4.add(jLabelCopertina);
        jLabelCopertina.setBounds(20, 20, 160, 160);

        jPanel3.add(jPanel4, BorderLayout.PAGE_START);

        jScrollPane1.setBorder(BorderFactory.createTitledBorder("Testo"));
        jScrollPane1.setPreferredSize(new Dimension(100, 205));

        jTextPaneLyrics.setText("jTextPaneLyrics");
        jScrollPane1.setViewportView(jTextPaneLyrics);

        jPanel3.add(jScrollPane1, BorderLayout.CENTER);

        jPanel2.add(jPanel3, BorderLayout.LINE_END);

        jPanel6.setBorder(BorderFactory.createTitledBorder("Tag"));
        jPanel6.setLayout(null);
        
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel6.add(jLabel1);
        jLabel1.setBounds(30, 30, 100, 23);

        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel6.add(jLabel2);
        jLabel2.setBounds(30, 65, 100, 23);

        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel6.add(jLabel3);
        jLabel3.setBounds(30, 100, 100, 23);

        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel6.add(jLabel4);
        jLabel4.setBounds(30, 135, 100, 23);

        jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel6.add(jLabel5);
        jLabel5.setBounds(30, 170, 100, 23);

        jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel6.add(jLabel6);
        jLabel6.setBounds(30, 205, 100, 23);

        jLabel7.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel6.add(jLabel7);
        jLabel7.setBounds(30, 240, 100, 23);

        jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel6.add(jLabel8);
        jLabel8.setBounds(30, 275, 100, 23);

        jLabel9.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel6.add(jLabel9);
        jLabel9.setBounds(30, 310, 100, 23);

        loadMetadati();
        
        jTextArtista.addActionListener(this);
        jPanel6.add(jTextArtista);
        jTextArtista.setBounds(150, 30, 200, 23);

        jTextTitolo.addActionListener(this);
        jPanel6.add(jTextTitolo);        
        jTextTitolo.setBounds(150, 65, 200, 23);


        jTextAlbum.addActionListener(this);
        jPanel6.add(jTextAlbum);
        jTextAlbum.setBounds(150, 100, 200, 23);
        

        jTextAnno.addActionListener(this);
        jPanel6.add(jTextAnno);
        jTextAnno.setBounds(150, 135, 200, 23);

        jTextTraccia.addActionListener(this);
        jPanel6.add(jTextTraccia);
        jTextTraccia.setBounds(150, 170, 200, 23);

        jTextGenere.addActionListener(this);
        jPanel6.add(jTextGenere);
        jTextGenere.setBounds(150, 205, 200, 23);
        
        jTextBitRate.setEditable(false);
        jPanel6.add(jTextBitRate);
        jTextBitRate.setBounds(150, 240, 200, 23);

        jTextSampleRate.setEditable(false);
        jPanel6.add(jTextSampleRate);
        jTextSampleRate.setBounds(150, 275, 200, 23);

        jTextChannels.setEditable(false);
        jPanel6.add(jTextChannels);
        jTextChannels.setBounds(150, 310, 200, 23);
        
        jButton4.addActionListener(this);
        jPanel6.add(jButton4);
        jButton4.setBounds(110, 350, 100, 23);        

        jButton5.addActionListener(this);
        jPanel6.add(jButton5);
        jButton5.setBounds(220, 350, 100, 23);

        jPanel2.add(jPanel6, BorderLayout.CENTER);

        getContentPane().add(jPanel2, BorderLayout.CENTER);
        
        jMenu1.add(jMenuItem1);
        jMenuItem1.addActionListener(this);
        jMenuBar.add(jMenu1);
        
        jMenu2.add(jMenuItem2);
        jMenuItem2.addActionListener(this);
        jMenu2.add(jSeparator);
        jMenu2.add(jMenuItem3);
        jMenuItem3.addActionListener(this);
        jMenuBar.add(jMenu2);
        
        CopertinaCambiata = false;
        
        setJMenuBar(jMenuBar);
        setVisible(true);
        pack();
    }
    
    /**
     * Metodo per caricare i metadati del FileMp3 all'interno dei campi del JDialog Metadati
     */
    public void loadMetadati () {
    	jTextArtista.setText(Mp3.getArtista());
    	jTextTitolo.setText(Mp3.getTitolo());
    	jTextAlbum.setText(Mp3.getAlbum());
    	jTextAnno.setText(Mp3.getAnno());
    	jTextTraccia.setText(Mp3.getTraccia());
    	jTextGenere.setText(Mp3.getGenere());
        jLabelCopertina.setIcon(Mp3.getCopertina());
        jTextBitRate.setText(Mp3.getBitRate());
        jTextSampleRate.setText(Mp3.getSampleRate());
        jTextChannels.setText(Mp3.getChannels());
        jTextPaneLyrics.setText(Mp3.getLyrics());
        setTitle("Proprietà : " + Mp3.getArtista() + " - " + Mp3.getTitolo());
        return;       
    }
    
    /**
     * Metodo per verificare se si sono apportate delle modifiche ai metadati del FileMp3 
     * all'interno dei campi del JDialog Metadati, ed in caso affermativo salva tali modifiche
     * sul FileMp3 stesso.
     */
    public void checkMetadati () {
    	boolean flag = false;
    	String string = null;
    	if (!(string = jTextArtista.getText()).equals(Mp3.getArtista())) {
    		flag = true;
    		Mp3.setArtista(string);
    	}
    	if (!(string = jTextTitolo.getText()).equals(Mp3.getTitolo())) {
    		flag = true;
   	 		Mp3.setTitolo(string);
    	}
   	 	if (!(string = jTextAlbum.getText()).equals(Mp3.getAlbum())) {
    		flag = true;
   	 		Mp3.setAlbum(string);
    	}
   	 	if (!(string = jTextAnno.getText()).equals(Mp3.getAnno())) {
    		flag = true;
   	 		Mp3.setAnno(string);
    	}
   	 	if (!(string = jTextTraccia.getText()).equals((Mp3.getTraccia()))) {
    		flag = true;
   	 		Mp3.setTraccia(string);
    	}
   	 	if (!(string = jTextGenere.getText()).equals(Mp3.getGenere())) {
    		flag = true;
   	 		Mp3.setGenere(string);
    	}
   	 	if (!(string = jTextPaneLyrics.getText()).equals(Mp3.getLyrics())) {
    		flag = true;
   	 		Mp3.setLyrics(string);
    	}
   	 	if (CopertinaCambiata) {
    		flag = true;
   	 		Mp3.setCopertina(PathCopertina);
    	}
   	 	if (flag)
   	 		Mp3.setTag();   	 	
   }
  
    /**
     * Metodo per chiudere il JDialog Metadati e che aggiornare la LibreriaView (e quindi la TabellaMp3)
     * per poter caricare le eventuali modifiche apportate ai metadati del FileMp3 caricato nel JDialog
     * Metadati 
     */
    public void DisposeRefresh () {
    	if (TabellaMp3.getLibreriaView().length() == 0) {
    		JPanelLibreria.UpdateComboBox(TabellaMp3.getLibreriaBack(), false);
    		TabellaMp3.loadLibreriaView();
    	}
    	else
    		JPanelLibreria.UpdateComboBox(TabellaMp3.getLibreriaBack(), true);
    	TabellaMp3.getTabella().updateUI();
    	this.dispose();
    }
    
    /**
     * Metodo per caricare l'immagine scelta tramite il JChooserImage, senza salvarla sul FileMp3 stesso.
     */
    public void loadImage() {
    	Image Copertina = java.awt.Toolkit.getDefaultToolkit().getImage(PathCopertina);
    	jLabelCopertina.setIcon(new ImageIcon(Copertina.getScaledInstance(160, 160,Image.SCALE_SMOOTH)));
    	CopertinaCambiata = true;
    }
    
    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * ActionListener 
     */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Salva ed Esci") || e.getActionCommand().equals("Salva")) {
			checkMetadati();
			DisposeRefresh();
			}
	else if (e.getActionCommand().equals("Annulla Modifiche"))
		loadMetadati();
	else if (e.getActionCommand().equals("Annulla"))
		DisposeRefresh();
	else if (e.getActionCommand().equals("Importa Copertina")) {
		this.setAlwaysOnTop(false);
		PathCopertina = new JChooserImage().ChooseImage();
		if (!PathCopertina.equals(""))
			loadImage();
		this.setAlwaysOnTop(true);
	}

	else if (e.getActionCommand().equals("Mp3 Prec")) {
		if (Pos > 0) {
			checkMetadati();
			Pos--;
			Mp3 = TabellaMp3.getLibreriaView().getFile(Pos);
			TabellaMp3.getTabella().getSelectionModel().setSelectionInterval(Pos,Pos);
			loadMetadati();
			}
	}
	else if (e.getActionCommand().equals("Mp3 Succ")) {
		if (Pos < (TabellaMp3.getLibreriaView().length() - 1)) {
			checkMetadati();
			Pos++;
			Mp3 = TabellaMp3.getLibreriaView().getFile(Pos);
			TabellaMp3.getTabella().getSelectionModel().setSelectionInterval(Pos,Pos);
			loadMetadati();
			}
		}		
	}


    // Variables declaration - do not modify   
	private int Pos;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JLabel jLabelCopertina;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenuBar jMenuBar;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private JMenuItem jMenuItem3;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel6;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator;
    private JTextField jTextArtista;
    private JTextField jTextTitolo;
    private JTextField jTextAlbum;
    private JTextField jTextAnno;
    private JTextField jTextTraccia;
    private JTextField jTextGenere;
    private JTextField jTextBitRate;
    private JTextField jTextSampleRate;
    private JTextField jTextChannels;
    private JTextPane jTextPaneLyrics;
    private FileMp3 Mp3;
    private boolean CopertinaCambiata; 
    private String PathCopertina = "";
    // End of variables declaration               
    
}
