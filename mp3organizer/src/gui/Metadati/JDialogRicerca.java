package gui.Metadati;

import gui.MainFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Classe che definisce tramite anche l'ereditarietà dei metodi delle classi JDialog, ActionListener e KeyListener
 * le impostazioni visive e non della JDialog effettuare la ricerca all'interno della Libreria Globale. 
 * 
 * @author Monduzzi Mattia
 *
 */

@SuppressWarnings("serial")
public class JDialogRicerca extends JDialog implements ActionListener, KeyListener {

    /**
     * Costruttore del JDialog Ricerca
     * @param Elenco
     * 			parametro che indica se la ricerca andrà effettuata sull'elenco filtrato(true) o sull'elenco non
     * 			filtrato(false)
     */
    public JDialogRicerca(boolean Elenco) {
    	
    	this.Elenco = Elenco;
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jButton1 = new JButton("Ricerca");
        jButton2 = new JButton("Chiudi");
        jLabel1 = new JLabel("Artista");
        jLabel2 = new JLabel("Titolo");
        jLabel3 = new JLabel("Album");
        jLabel4 = new JLabel("Traccia");
        jLabel5 = new JLabel("Anno");
        jLabel6 = new JLabel("Genere");
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        jTextArtista = new JTextField();
        jTextTitolo = new JTextField();
        jTextAlbum = new JTextField();
        jTextAnno = new JTextField();
        jTextTraccia = new JTextField();
        jTextGenere = new JTextField();
        jComboBox1 = new JComboBox();
        jComboBox2 = new JComboBox();
        jMenuBar = new JMenuBar();
        jMenu = new JMenu("File");
        jMenuItem1 = new JMenuItem("Ricerca");
        jMenuItem2 = new JMenuItem("Esci");

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension (420,420));
        setLocation(MainFrame.getMainFrame().getWidth()/3, MainFrame.getMainFrame().getHeight()/3);
        setResizable(false);
        setAlwaysOnTop(true);
        setTitle("Ricerca Mp3");

        jPanel1.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.setPreferredSize(new Dimension(100, 35));

        jButton1.setAlignmentY(0.0F);
        jButton1.addActionListener(this);
        jButton1.setPreferredSize(new Dimension(100, 23));
        jPanel1.add(jButton1);

        jButton2.setPreferredSize(new Dimension(100, 23));
        jButton2.addActionListener(this);
        jPanel1.add(jButton2);

        getContentPane().add(jPanel1, BorderLayout.PAGE_END);

        jPanel2.setBorder(BorderFactory.createTitledBorder("Filtri di Ricerca"));
        jPanel2.setLayout(null);

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel2.add(jLabel1);
        jLabel1.setBounds(50, 40, 100, 23);

        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel2.add(jLabel2);
        jLabel2.setBounds(50, 75, 100, 23);

        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel2.add(jLabel3);
        jLabel3.setBounds(50, 110, 100, 23);

        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel2.add(jLabel4);
        jLabel4.setBounds(50, 145, 100, 23);

        jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel2.add(jLabel5);
        jLabel5.setBounds(50, 180, 100, 23);

        jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
        jPanel2.add(jLabel6);
        jLabel6.setBounds(50, 215, 100, 23);
        
        jTextArtista.addKeyListener(this);
        jPanel2.add(jTextArtista);
        jTextArtista.setBounds(180, 40, 150, 23);

        jTextTitolo.addKeyListener(this);
        jPanel2.add(jTextTitolo);
        jTextTitolo.setBounds(180, 75, 150, 23);

        jTextAlbum.addKeyListener(this);
        jPanel2.add(jTextAlbum);
        jTextAlbum.setBounds(180, 110, 150, 23);

        jTextTraccia.addKeyListener(this);
        jPanel2.add(jTextTraccia);
        jTextTraccia.setBounds(180, 145, 150, 23);
        
        jTextAnno.addKeyListener(this);
        jPanel2.add(jTextAnno);
        jTextAnno.setBounds(180, 180, 150, 23);

        jTextGenere.addKeyListener(this);
        jPanel2.add(jTextGenere);
        jTextGenere.setBounds(180, 215, 150, 23);

        jLabel7.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel7.setText("Copertina:");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(50, 250, 100, 23);

        jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel8.setText("Testo:");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(50, 285, 100, 23);

        jComboBox1.setModel(new DefaultComboBoxModel(new String[] { "Indifferente", "Con Copertina", "Senza Copertina"}));
        jPanel2.add(jComboBox1);
        jComboBox1.setBounds(180, 250, 150, 23);

        jComboBox2.setModel(new DefaultComboBoxModel(new String[] { "Indifferente", "Con Testo", "Senza Testo"}));
        jPanel2.add(jComboBox2);
        jComboBox2.setBounds(180, 285, 150, 23);

        getContentPane().add(jPanel2, BorderLayout.CENTER);

        jMenuItem1.addActionListener(this);
        jMenuItem2.addActionListener(this);
        
        jMenu.add(jMenuItem1);
        jMenu.add(new JSeparator());
        jMenu.add(jMenuItem2);
        jMenuBar.add(jMenu);
        setJMenuBar(jMenuBar);
          
        setVisible(true);
        pack();
        }              
           
    /**
     * Metodo per chiudere il JDialog in seguito ad una ricerca
     */
    public void Dispose () {
    	this.dispose();
    }
    
    /**
     * Metodo per ricavare i valori per effettuare la ricerca
     *   
     * @return un array di stringhe contente i valori per effettuare la ricerca		
     */
	public static String[] getRicerca () {
		String[] tmp = new String[8]; 
		tmp[0] = jTextTitolo.getText();
		tmp[1] = jTextArtista.getText();
		tmp[2] = jTextAlbum.getText();
		tmp[3] = jTextTraccia.getText();
		tmp[4] = jTextAnno.getText();
		tmp[5] = jTextGenere.getText();
		tmp[6] = (String) jComboBox1.getSelectedItem();
		tmp[7] = (String) jComboBox2.getSelectedItem();
		return tmp;		
	}
	
    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * ActionListener 
     */
	public void actionPerformed(ActionEvent e) {
		if 		((e.getSource() == jButton1) || (e.getSource() == jMenuItem1)) {
			new Ricerca(Elenco);
		}
		else if ((e.getSource() == jButton2) || (e.getSource() == jMenuItem2))
			this.dispose();
	}
	
    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * KeyListener 
     */
	public void keyPressed(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_ENTER)		
			new Ricerca(Elenco);
		}
	public void keyReleased(KeyEvent k) {}	
	public void keyTyped(KeyEvent k) 	{}
		
    // Variables declaration - do not modify                     
    private JButton jButton1;
    private JButton jButton2;
    private static JComboBox jComboBox1;
    private static JComboBox jComboBox2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JMenu jMenu;
    private JMenuBar jMenuBar;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private static JTextField jTextArtista;
    private static JTextField jTextTitolo;
    private static JTextField jTextAlbum;
    private static JTextField jTextAnno;
    private static JTextField jTextTraccia;
    private static JTextField jTextGenere;
    private boolean Elenco;
    // End of variables declaration                


}
