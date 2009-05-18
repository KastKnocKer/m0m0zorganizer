package gui.Metadati;

import gui.JPanelArtisti;
import gui.MainFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Classe che definisce tramite anche l'ereditarietà dei metodi delle classi JDialog e ActionListener
 * le impostazioni visive e non della JDialog per creare le schede dei vari artisti. 
 * 
 * @author Monduzzi Mattia
 *
 */

@SuppressWarnings("serial")
public class JDialogArtista extends JDialog implements ActionListener{
	
	/**
	 * Costruttore del JDialog Artista
	 * @param nome
	 * 			nome del artista/gruppo musicale di cui si vuol creare la scheda
	 */
	public JDialogArtista(String nome) {		
		super();	
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jRadioButton1 = new JRadioButton();
		jRadioButton2 = new JRadioButton();
		jSpinner1 = new JSpinner();
		jLabel3 = new JLabel();
		jLabel4 = new JLabel();
		jButton1 = new JButton();
		jButton2 = new JButton();
		
		setPreferredSize(new Dimension(400,285));
		setTitle("Informazioni su " + nome + " non trovate.");
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(MainFrame.getMainFrame().getWidth()/3, MainFrame.getMainFrame().getHeight()/3);
		getContentPane().setLayout(null);
		
		jLabel1.setText("Le informazioni relative all'artista/gruppo selezionato, non sono presenti.");
		getContentPane().add(jLabel1);
		jLabel1.setBounds(20, 20, 360, 25);
		
		jLabel2.setText("Selezionare le seguenti opzioni per impostarne la creazione:");
		getContentPane().add(jLabel2);
		jLabel2.setBounds(20, 40, 360, 25);
		
		jRadioButton1.setText("Artista Musicale");
		getContentPane().add(jRadioButton1);
		jRadioButton1.setBounds(50, 120, 130, 23);
		
		jRadioButton2.setText("Gruppo Musicale");
		getContentPane().add(jRadioButton2);
		jRadioButton2.setBounds(210, 120, 130, 23);
		
		jSpinner1.setModel(new SpinnerNumberModel(Integer.valueOf(2), Integer.valueOf(2), null, Integer.valueOf(1)));
		getContentPane().add(jSpinner1);
		jSpinner1.setBounds(250, 162, 60, 20);
		
		jLabel3.setText("Numero Componenti del Gruppo:");
		getContentPane().add(jLabel3);
		jLabel3.setBounds(50, 160, 200, 25);
		
		jLabel4.setText("Nome: " + nome);
		getContentPane().add(jLabel4);
		jLabel4.setBounds(50, 83, 300, 25);
		
		jButton1.setText("Crea");
		getContentPane().add(jButton1);
		jButton1.setBounds(90, 220, 100, 23);
		
		jButton2.setText("Annulla");
		getContentPane().add(jButton2);
		jButton2.setBounds(200, 220, 100, 23);
		
		jButton1.addActionListener(this);
		jButton2.addActionListener(this);
		jRadioButton1.addActionListener(this);
		jRadioButton2.addActionListener(this);
		
		setVisible(true);
		pack();
	    }

    /**
     * Metodo per eseguire le diverse operazioni in base al generatore dell'evento
     * ActionListener 
     */
	public void actionPerformed(ActionEvent e) {
		if 		(e.getSource() == jButton1) {
			if (jRadioButton1.isSelected())
				JPanelArtisti.makeArtista();
			else if (jRadioButton2.isSelected())
				JPanelArtisti.makeGruppo(new Integer(jSpinner1.getValue().toString()));
			this.dispose();
		}
		else if (e.getSource() == jButton2)
			this.dispose();
			else if (e.getSource() == jRadioButton1) {
				jSpinner1.setEnabled(false);
				jRadioButton2.setSelected(false);
			}
		else if (e.getSource() == jRadioButton2) {
			jSpinner1.setEnabled(true);
			jRadioButton1.setSelected(false);
		}
	}
	
	    // Variables declaration - do not modify
	    private JButton jButton1;
	    private JButton jButton2;
	    private JLabel jLabel1;
	    private JLabel jLabel2;
	    private JLabel jLabel3;
	    private JLabel jLabel4;
	    private JRadioButton jRadioButton1;
	    private JRadioButton jRadioButton2;
	    private JSpinner jSpinner1;
	    // End of variables declaration

}

