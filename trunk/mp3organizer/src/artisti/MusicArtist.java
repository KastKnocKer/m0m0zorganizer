package artisti;

/**
 * Implementazione concreta di un artist.
 * @author Monduzzi Mattia
 *
 */
public class MusicArtist implements Artist {
	
	/**
	 * Costruttore di questa classe, necessita dei dati di inizializzazione dell'interfaccia Artist e
     * dei dati di inizializzazione di questa classe.
     * 
	 * @param nomeArte
	 * 				il nomeArte dell'artista
	 * @param bibliografia
	 * 				la bibliografia dell'artista
	 * @param dataEsordio
	 * 				la dataEsordio dell'artista
	 * @param generi
	 * 				i generi musicali dell'artista
	 * @param discografia
	 * 				la discografia dell'artista
	 * @param fanPage
	 * 				la fanpage dell'artista
	 * @param casaDiscografica
	 * 				la casa discografica dell'artista
	 */
	public MusicArtist (String nomeArte, String bibliografia, String dataEsordio, String generi,
			String discografia, String fanPage, String casaDiscografica ) {
		this.nomeArte = nomeArte;
		this.bibliografia = bibliografia;
		this.dataEsordio = dataEsordio;
		this.generi = generi;
		this.discografia = discografia;
		this.fanPage = fanPage;
		this.casaDiscografica = casaDiscografica;
	}

	/**
	 * Restituisce il nome d'arte dell'artista
	 * 
	 * @return il nome d'arte
	 */
	public String getNomeArte() 	{ return nomeArte;}

	/**
	 * Restituisce la biografia dell'artista
	 * 
	 * @return la biografia
	 */
	public String getBiografia()	{ return bibliografia; }

	/**
	 * Restituisce la data d'esordio dell'artista
	 * 
	 * @return la data d'esorsio
	 */
	public String getDataEsordio()  { return dataEsordio;}
 	
	/**
	 * Restituisce i generi musicali dell'artista
	 * 
	 * @return i generei musicali dell'artista
	 */
	public String getGeneri() 		{ return generi;}
	
	/**
	 * Restituisce la discografia dell'artista
	 * 
	 * @return la discografia dell'artista
	 */
	public String getDiscografia() 	{ return discografia;}
	
	/**
	 * Restituisce la fanpage dell'artista
	 * 
	 * @return la fanpage dell'artista
	 */
	public String getFanPage() 		{ return fanPage;}
	
	/**
	 * Restituisce la casa discografica dell'artista
	 * 
	 * @return la casa discografica dell'artista
	 */
	public String getCasaDisco() 	{ return casaDiscografica;}
	
	
	
	/**
	 * Imposta il nome d'arte dell'Artista secondo il parametro passato
	 * 
	 * @param nomeArte
	 * 					il nome d'arte da impostare
	 */
	public void setNomeArte(String nomeArte) 			{ this.nomeArte = nomeArte;}

	/**
	 * Imposta la biografia dell'Artista secondo il parametro passato
	 * 
	 * @param bibliografia
	 * 					la biografia da impostare
	 */
	public void setBiografia(String bibliografia)		{ this.bibliografia = bibliografia; }

	/**
	 * Imposta la data d'esordio dell'Artista secondo il parametro passato
	 * 
	 * @param dataEsordio 
	 * 					la data d'esordio da impostare
	 */
	public void setDataEsordio(String dataEsordio )  	{ this.dataEsordio = dataEsordio;}
	
	/**
	 * Imposta i generi musicali dell'Artista secondo il parametro passato
	 * 
	 * @param generi
	 * 					i generi musicali da impostare
	 */
	public void setGeneri(String generi ) 				{ this.generi = generi;}
	
	/**
	 * Imposta la discografia dell'Artista secondo il parametro passato
	 * 
	 * @param discografia
	 * 					 la discografia da impostare
	 */
	public void setDiscografia(String discografia ) 	{ this.discografia = discografia;}
	
	/**
	 * Imposta la fanpage dell'Artista secondo il parametro passato
	 * 
	 * @param fanPage
	 * 					 la fanpage da impostare
	 */
	public void setFanPage(String fanPage ) 			{ this.fanPage = fanPage;}
	
	/**
	 * Imposta la casa discografica dell'Artista secondo il parametro passato
	 * 
	 * @param casaDiscografica
	 * 					la casa discografica da impostare
	 */
	public void setCasaDisco(String casaDiscografica ) 	{ this.casaDiscografica = casaDiscografica;}
	
	
	// attributi interni
	private String nomeArte;
	private String bibliografia;
	private String dataEsordio;
	private String generi;
	private String discografia;
	private String fanPage;
	private String casaDiscografica;
}
