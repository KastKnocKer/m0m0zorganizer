package artisti;
/**
 * Interfaccia base per la gestione avanzata degli artisti, che definisce i metodi
 * base che devono avere le classi che implementeranno questa interfaccia
 * 
 * @author Monduzzi Mattia
 */
public interface Artist {

	// metodi getter
	/**
	 * Restituisce il nome d'arte dell'Artista
	 * 
	 * @return il nome d'arte
	 */
	public String getNomeArte();

	/**
	 * Restituisce la biografia dell'Artista
	 * 
	 * @return la biografia
	 */
	public String getBiografia();

	/**
	 * Restituisce la data d'esordio dell'Artista
	 * 
	 * @return la data d'esorsio
	 */
	public String getDataEsordio();
	
	// metodi set	
	/**
	 * Imposta il nome d'arte dell'Artista secondo il parametro passato
	 * 
	 * @param il nome d'arte da impostare
	 */
	public void setNomeArte		(String nomeArte);

	/**
	 * Imposta la biografia dell'Artista secondo il parametro passato
	 * 
	 * @param la biografia da impostare
	 */
	public void setBiografia	(String Biografia);

	/**
	 * Imposta la data d'esordio dell'Artista secondo il parametro passato
	 * 
	 * @param la data d'esordio da impostare
	 */
	public void setDataEsordio	(String DataEsordio);

	
}
