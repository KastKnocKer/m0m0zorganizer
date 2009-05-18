package artisti;

/**
 * Classe che definisce un artista solita tramite l'implementazione concreta di un artist e tramite l'ereditarietà
 * dei metodi della classe MusicArtist.
 * @author Monduzzi Mattia
 *
 */
public class SoloMusicArtist extends MusicArtist implements Persona {
	/**
	 * Costruttore senza parametri della classe. Definisce un artista solista con tutti gli attributi settati
	 * come "Sconosciuto"
	 * 
	 */
	public SoloMusicArtist() {
		super("Sconosciuto" , "Sconosciuto","Sconosciuto",  "Sconosciuto", "Sconosciuto", "Sconosciuto", "Sconosciuto" );
		setAnagrafe ("Sconosciuto", "Sconosciuto", "Sconosciuto", "Sconosciuto", "Sconosciuto");
	}
	
	/**
	 * Costruttore con parametri della classe. Definisce tutti gli attributi di un artista secondo i parametri passati
	 * 
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
	public SoloMusicArtist(String nome, String cognome, String dataNascita, String dataMorte, String nazionalità,
			String nomeArte, String biografia, String dataEsordio, String generi, String discografia,
			String fanPage, String casaDiscografica) {
		super(nomeArte , biografia, dataEsordio, generi, discografia, fanPage, casaDiscografica );
		setAnagrafe (nome, cognome, dataNascita, dataMorte, nazionalità);
	}
	
	/**
	 * Metodo per impostare tutti gli attributi della Classe SoloMusicArtist secondo i parametri passati.
	 * 
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
	public void SetSoloMusicArtist(String nome, String cognome, String dataNascita, String dataMorte,
			String nazionalità, String nomeArte, String bibliografia, String dataEsordio, String generi,
			String discografia, String fanPage, String casaDiscografica) {
		
		setMusicArtist(nomeArte, bibliografia, dataEsordio, generi, discografia, fanPage, casaDiscografica);
		setAnagrafe (nome, cognome, dataNascita, dataMorte, nazionalità);
	}
	
	/**
	 * Metodo per impostare tutti gli attributi della Classe MusicArtist secondo i parametri passati.
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
	public void setMusicArtist (String nomeArte, String bibliografia, String dataEsordio,String generi,
			String discografia, String fanPage, String casaDiscografica) {
		this.setNomeArte(nomeArte);
		this.setBiografia(bibliografia);
		this.setDataEsordio(dataEsordio);
		this.setGeneri(generi);
		this.setDiscografia(discografia);
		this.setFanPage(fanPage);
		this.setCasaDisco(casaDiscografica);
	}	
	
	/**
	 * Metodo per impostare tutti gli attributi ereditati dall'interfaccia persona, secondo i parametri passati
	 * 
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
	 */
	public void setAnagrafe (String nome, String cognome, String dataNascita, String dataMorte, String nazionalità) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.dataMorte = dataMorte;
		this.nazionalità = nazionalità;
	}

	/**
	 * Restituisce il nome della Persona
	 * 
	 * @return il nome 
	 */
	public String getNome() 		{ return nome; }

	/**
	 * Restituisce il cognome della Persona
	 * 
	 * @return il cognome
	 */
	public String getCognome()  	{ return cognome; }
	
	/**
	 * Restituisce la data di nascita della Persona
	 * 
	 * @return la data di nascita
	 */
	public String getDataNascita()  { return dataNascita; }
	
	/**
	 * Restituisce la data di morte della Persona
	 * 
	 * @return data di morte
	 */
	public String getDataMorte() 	{ return dataMorte; }
	
	/**
	 * Restituisce la nazionalitàdella Persona
	 * 
	 * @return la nazionalità
	 */
	public String getNazionalità()  { return nazionalità; }
	

	
	/**
	 * Imposta il nome della persona secondo il parametro passato
	 * 
	 * @param  nome
	 * 				il nome da impostare
	 */
	public void setNome(String nome) { this.nome = nome;	}

	/**
	 * Imposta il cognome della persona secondo il parametro passato
	 * 
	 * @param cognome
	 * 				il cognome da impostare
	 */
	public void setCognome(String cognome) {this.cognome = cognome;}
	
	/**
	 * Imposta la data di nascita della persona secondo il parametro passato
	 * 
	 * @param dataNascita
	 * 				la data di nascita da impostare
	 */
	public void setDataNascita(String dataNascita) {this.dataNascita = dataNascita;}

	/**
	 * Imposta la data di morte della persona secondo il parametro passato
	 * 
	 * @param dataMorte
	 * 				la data di morte da impostare
	 */
	public void setDataMorte(String dataMorte) {this.dataMorte = dataMorte;}
	
	/**
	 * Imposta la nazionalità della persona secondo il parametro passato
	 * 
	 * @param Nazionalità
	 * 				la nazionalità da impostare
	 */
	public void setNazionalità(String Nazionalità) {this.nazionalità = Nazionalità;}
	
	
	// attributi interni
	private String nome;
	private String cognome;
	private String dataNascita;
	private String dataMorte;
	private String nazionalità;

}
