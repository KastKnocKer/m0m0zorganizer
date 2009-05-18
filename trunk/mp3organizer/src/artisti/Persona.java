package artisti;

/**
 * Interfaccia base per la gestione avanzata degli artisti, che definisce i metodi
 * base che devono avere le classi che implementeranno questa interfaccia
 * 
 * @author Monduzzi Mattia
 */
public interface Persona{

	/**
	 * Restituisce il nome della Persona
	 * 
	 * @return il nome 
	 */
	public String getNome();
	
	/**
	 * Restituisce il cognome della Persona
	 * 
	 * @return il cognome
	 */
	public String getCognome();
	
	/**
	 * Restituisce la data di nascita della Persona
	 * 
	 * @return la data di nascita
	 */
	public String getDataNascita();
	
	/**
	 * Restituisce la data di morte della Persona
	 * 
	 * @return data di morte
	 */
	public String getDataMorte();
	
	/**
	 * Restituisce la nazionalitàdella Persona
	 * 
	 * @return la nazionalità
	 */
	public String getNazionalità();	
	

	
	/**
	 * Imposta il nome della persona secondo il parametro passato
	 * 
	 * @param  il nome da impostare
	 */
	public void setNome(String nome);
	
	/**
	 * Imposta il cognome della persona secondo il parametro passato
	 * 
	 * @param il cognome da impostare
	 */
	public void setCognome(String cognome);
	
	/**
	 * Imposta la data di nascita della persona secondo il parametro passato
	 * 
	 * @param la data di nascita da impostare
	 */
	public void setDataNascita(String dataNscita);
	
	/**
	 * Imposta la data di morte della persona secondo il parametro passato
	 * 
	 * @param data di morte da impostare
	 */
	public void setDataMorte(String dataMorte);
	
	/**
	 * Imposta la nazionalità della persona secondo il parametro passato
	 * 
	 * @param  la nazionalità da impostare
	 */
	public void setNazionalità(String Nazionalità);	
}