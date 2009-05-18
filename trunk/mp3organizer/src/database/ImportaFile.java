package database;
/**
 * Classe che imposta e definisce il JChooser per caricare i FileMp3 e le directory di FileMp3
 * 
 * @author Monduzzi Mattia
 */
import gui.JPanelLibreria;
import gui.Tabella.TabellaMp3;

import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.filechooser.*;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

public class ImportaFile {
	
	/**
	 * Costruttore della classe per importare i file  
	 * @param string
	 * 			parametro che indica se caricare bisogna caricare un FileMp3 o una directory
	 */
	public ImportaFile(String string) {
		jChooser = new JFileChooser();
		LibreriaView = TabellaMp3.getLibreriaView();
		LibreriaBack = TabellaMp3.getLibreriaBack();
		
		
		if		(string.equals("mp3")) {
			jChooser.setDialogTitle("Seleziona il file mp3 da importare");
			jChooser.addChoosableFileFilter(new FileNameExtensionFilter  ("Mp3 File", "mp3"));
			jChooser.setAcceptAllFileFilterUsed(false);
			jChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jChooser.setMultiSelectionEnabled(true);
			returnVal = jChooser.showOpenDialog(null);
			if 	(returnVal == JFileChooser.APPROVE_OPTION ) {
				File files[] = jChooser.getSelectedFiles();
				for (int i=0; i < files.length; i++)
					seekFile(files[i]);
				}
		}
		
		else {
			jChooser.setDialogTitle("Seleziona la directory da importare");	
			jChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			returnVal = jChooser.showOpenDialog(null);
			if 	(returnVal == JFileChooser.APPROVE_OPTION )
					seekFile(jChooser.getSelectedFile());
				}
		
		if (LibreriaBack.length() != 0) {
			TabellaMp3.saveLibreriaBack("./Salvataggi/LibreriaGlobale.dat");
			JPanelLibreria.UpdateComboBox(LibreriaBack , false);
		}
		JPanelLibreria.ViewLibreria();
	}
	
	/**
	 * Metodo ricorsivo per andare controllare il file selezionato o la directory selezionata per caricare
	 * i file FileMp3 
	 *   
	 * @param Fileseek
	 * 				il file/directory da analizzare
	 */
	public void seekFile (File Fileseek) {		
		if (Fileseek.isDirectory()) {
			File[] folderFiles = Fileseek.listFiles(); 
			for (int i=0; i < folderFiles.length ; i++)
				seekFile(folderFiles[i]);
		}
		else if (Fileseek.getName().endsWith(".mp3")) {
			try { 
				Mp3 = new FileMp3 (Fileseek);	
				System.out.println("Inserito: " + Mp3.getAbsolutePath());
			} 
			catch (IOException e) {System.out.println("Errore 1");}
			catch (TagException e) {System.out.println("Errore 2");}
			catch (ReadOnlyFileException e) {System.out.println("Errore 3");}
			catch (InvalidAudioFrameException e) {System.out.println("Errore 4");}
			
			LibreriaView.insert(Mp3);
			LibreriaBack.insert(Mp3);
			}
		}
	// Dichiarazione Variabili
	private ListaMp3 LibreriaView;
	private ListaMp3 LibreriaBack;
	private FileMp3 Mp3;
	private JFileChooser jChooser; 
	private int returnVal;
}
	


