package artisti;

/**
 * Classe che definisce un gruppo musicale tramite più oggetti della SoloMusicArtist e altre variabili.
 * 
 * @author Monduzzi Mattia
 *
 */
public class MusicGroup {
	
	/**
	 * Costruttore della classe. Crea tanti SoloMusicArtist in base al parametro passato
	 * 
	 * @param numeroArtisti che compongono il gruppo
	 */
	public MusicGroup (int numeroArtisti ) {
		this.numeroArtisti = numeroArtisti;
		Artisti = new SoloMusicArtist[this.numeroArtisti];
		dataFormazione = "Sconosciuto";
		dataScioglimento = "Sconosciuto";
		for (int i = 0; i < numeroArtisti; i++)
			Artisti[i] = new SoloMusicArtist();
	}
	
	/**
	 * Metodo per impostare tutti gli attributi dell' oggetto SoloMusicArtist di indice num secondo i valori 
	 * passati come parametri
	 * 
	 * @param num
	 * 				l' indice dell'artista da impostare
	 * @param nome
	 * 				il nome dell'artista
	 * @param cognome
	 * 				il cognome dell'artista
	 * @param dataNasciata
	 * 				la data di nascita dell'artista
	 * @param dataMorte
	 * 				la data di morte dell'artista
	 * @param nazionalità
	 * 				la nazionalità dell'artista
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
	public void setArtista (int num, String nome, String cognome, String dataNascita, String dataMorte, String nazionalità, 
			String nomeArte, String bibliografia, String dataEsordio, String generi, 
			String discografia, String fanPage, String casaDiscografica) {
		
		Artisti[num].SetSoloMusicArtist(nome, cognome, dataNascita, dataMorte, nazionalità, nomeArte, 
				bibliografia, dataEsordio, generi, discografia, fanPage, casaDiscografica);
	}
	
	/**
	 * Metodo che restituisce l'artista del gruppo con indice relativo al numero passato come parametro
	 * 
	 * @param numero
	 * 				il numero dell'artista da restituire
	 * @return l'artista (componente) del gruppo di indice numero
	 */
	public SoloMusicArtist getArtista (int numero) {
		if (numero > Artisti.length)
			return null;
		return Artisti[numero];
	}	
	
	
	
	/**
	 * Restituisce il nome del Gruppo Musicale
	 * 
	 * @return il nome del gruppo
	 */
	public String getNomeGruppo ()			{ return nomeGruppo; }
	
	/**
	 * Restituisce il numero di componenti del Gruppo Musicale
	 * 
	 * @return il numero di componenti del gruppo come intero
	 */
	public int  getNumeroArtisti ()			{ return numeroArtisti; }
	
	/**
	 * Restituisce il numero di componenti del Gruppo Musicale
	 * 
	 * @return il numero di componenti del gruppo come stringa
	 */
	public String getNumeroArtistiString ()	{ return new Integer(numeroArtisti).toString(); }
	
	/**
	 * Restituisce la data di formazione del Gruppo Musicale
	 * 
	 * @return la data di formazione del gruppo
	 */
	public String getDataFormazione ()		{ return dataFormazione; }
	
	/**
	 * Restituisce la data di formazione del Gruppo Musicale
	 * 
	 * @return la data di formazione del gruppo
	 */
	public String getDataScioglimento () 	{ return dataScioglimento; }
	

	/**
	 * Imposta il nome del Gruppo musicale secondo il parametro passato
	 * 
	 * @param  nomeGruppo
	 * 				il nome del gruppo da impostare
	 */
	public void setNomeGruppo (String nomeGruppo)				{ this.nomeGruppo = nomeGruppo; }
	
	/**
	 * Imposta la data di formazione del Gruppo musicale secondo il parametro passato
	 * 
	 * @param dataFormazione
	 * 				 la data di formazione del gruppo da impostare
	 */
	public void setDataFormazione (String dataFormazione)		{ this.dataFormazione = dataFormazione; }
	
	/**
	 * Imposta la data di scioglimento del Gruppo musicale secondo il parametro passato
	 * 
	 * @param  dataScioglimento
	 * 				la data di scioglimento del gruppo da impostare
	 */
	public void setDataScioglimento (String dataScioglimento) 	{ this.dataScioglimento = dataScioglimento; }
	
	
	// Attributi interni
	private SoloMusicArtist[] Artisti;
	private String nomeGruppo;
	private String dataFormazione;
	private String dataScioglimento;
	private int numeroArtisti;

}
