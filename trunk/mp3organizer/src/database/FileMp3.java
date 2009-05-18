package database;
import java.awt.Image;
import java.io.*;
import javax.swing.ImageIcon;
import org.jaudiotagger.audio.exceptions.*;
import org.jaudiotagger.audio.mp3.*;
import org.jaudiotagger.tag.*;
import org.jaudiotagger.tag.id3.*;
import org.jaudiotagger.tag.id3.framebody.*;

/**
 * Implementazione tramite il concetto di ereditarietà della classe MP3(jaudiotagger)
 * 
 * @author Monduzzi Mattia
 *
 */
public class FileMp3 extends MP3File {

	/**
	 * Costruttore della classe FileMp3, che crea un nuovo file di tipo Mp3 dal file passato come parametro
	 * 
	 * @param file da cui creare il FileMp3
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 */
	public FileMp3 (File file) throws IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		super(file, LOAD_ALL);
		
		//Lettura ed impostazione Metadati
		AbsPath = file.getAbsolutePath();
		Path = file.getParent();
		
		BitRate = getAudioHeader().getBitRate();
		SampleRate = getAudioHeader().getSampleRate();
		Channels= getAudioHeader().getChannels();
		
		if (this.hasID3v2Tag()) {
			tag= this.getID3v2Tag(); 
			
			TagVersion = "ID3v2";
			Artista = 	tag.getFirstArtist();
			Titolo = 	tag.getFirstTitle();
			Album = 	tag.getFirstAlbum();
			Genere = 	tag.getFirstGenre();
			Genere =	infoGenere(tag.getFirstGenre());
			Traccia = 	tag.getFirstTrack();
			Anno = 		tag.getFirstYear();
			
			Copertina = loadCopertina();
			Lyrics = loadLyrics();
			}
		else if (this.hasID3v1Tag()) {
			tag= this.getID3v1Tag();
			
			TagVersion = "ID3v1";
			Artista = 	tag.getFirstArtist();
			Titolo = 	tag.getFirstTitle();
			Album = 	tag.getFirstAlbum();
			Genere =	infoGenere(tag.getFirstGenre());
			Traccia = 	"Sconosciuto";
			Anno = 		tag.getFirstYear();
			
			Copertina = loadCopertina();
			Lyrics = loadLyrics();
			}
		else {
			TagVersion = "NoTag";
			Artista = 	"Sconosciuto";
			Titolo =  	"Sconosciuto";
			Album = 	"Sconosciuto";
			Genere = 	"Sconosciuto";
			Genere = 	"Sconosciuto";
			Traccia = 	"Sconosciuto";
			Anno = 		"Sconosciuto";
		}
	}
		
	/**
	 * Restituisce l'indirizzo assoluto del FileMp3
	 * 
	 * @return l'indirizzo assoluto
	 */
	public String getAbsolutePath() {
		return AbsPath;
	}
	
	/**
	 * Restituisce l'album precedentemente estratto dai metadati del FileMp3
	 * 
	 * @return l'album
	 */
	public String getAlbum() {
		if (Album!= null && !Album.isEmpty())
				return Album;
		return "Sconosciuto";
	}
	
	/**
	 * Restituisce l'anno precedentemente estratto dai metadati del FileMp3
	 * 
	 * @return l'anno
	 */
	public String getAnno() {
		if (Anno!= null && !Anno.isEmpty())
				return Anno;
		return "Sconosciuto";
	}
		
	/**
	 * Restituisce l'artista precedentemente estratto dai metadati del FileMp3
	 * 
	 * @return l'artista
	 */
	public String getArtista() {
		if (Artista!= null && !Artista.isEmpty())
				return Artista;
		return "Sconosciuto";
	}
	
	/**
	 * Restituisce il BitRate precedentemente estratto dai AudioHeader del FileMp3
	 * 
	 * @return il BitRate
	 */
	public String getBitRate() {
		return BitRate;
	}

	/**
	 * Restituisce il Channels precedentemente estratto dai AudioHeader del FileMp3
	 * 
	 * @return il Channels
	 */
	public String getChannels() {
		return Channels;
	}
	
	/**
	 * Restituisce la copertina precedentemente estratta dai AudioHeader del FileMp3
	 * 
	 * @return la copertina
	 */
	public ImageIcon getCopertina () {
		return Copertina;
}
	/**
	 * Restituisce il genere precedentemente estratto dai metadati del FileMp3
	 * 
	 * @return il genere
	 */
	public String getGenere() {
		if (Genere!= null && !Genere.isEmpty())
				return Genere;
		return "Sconosciuto";
	}

	/**
	 * Restituisce la lyrics precedentemente estratta dai metadati del FileMp3
	 * 
	 * @return la lyrics
	 */
	public String getLyrics () {
		return Lyrics;
	}
	/**
	 * Restituisce la directory del FileMp3
	 * 
	 * @return la directory
	 */
	public String getPath() {
		return Path;
	}
	
	/**
	 * Restituisce il SampleRate precedentemente estratto dai AudioHeader del FileMp3
	 * 
	 * @return il SampleRate
	 */
	public String getSampleRate() {
		return SampleRate;
	}
	
	/**
	 * Restituisce la traccia precedentemente estratta dai metadati del FileMp3
	 * 
	 * @return la traccia
	 */
	public String getTraccia() {
		if (Traccia!= null && !Traccia.isEmpty())
				return Traccia;
		return "Sconosciuto";
	}
	
	/**
	 * Restituisce il titolo precedentemente estratto dai metadati del FileMp3
	 * 
	 * @return il titolo
	 */
	public String getTitolo() {
		if (Titolo!= null && !Titolo.isEmpty())
				return Titolo;
		return "Sconosciuto";
	}
	
	/**
	 * Estrae la copertina dai metadati del FileMp3
	 * 
	 * @return la copertina sottoforma di ImageIcon
	 */
	public ImageIcon loadCopertina() {
		Image Copertina;
        try {
        	byte[] artwork = null;
            ID3v23Frame apicframe = (ID3v23Frame) ((ID3v23Tag) tag).getFrame("APIC");
            FrameBodyAPIC cc = (FrameBodyAPIC) apicframe.getBody();
            artwork = (byte[]) cc.getObjectValue(org.jaudiotagger.tag.datatype.DataTypes.OBJ_PICTURE_DATA);
             Copertina = new ImageIcon(artwork).getImage();
            }
        catch (Exception e) {
        	Copertina = java.awt.Toolkit.getDefaultToolkit().getImage("./Immagini/copertina.png");
  			}       
		return new ImageIcon(Copertina.getScaledInstance(160, 160,Image.SCALE_SMOOTH));
	}
	
	/**
	 * Estrae la lyrics dai metadati del FileMp3
	 * 
	 * @return la lyrics
	 */
	public String loadLyrics () {
		String testo;
		try{
			   ID3v23Frame lyricframe = (ID3v23Frame) ((AbstractID3v2Tag) tag).getFrame(ID3v23FieldKey.LYRICS.getFieldName());
			   FrameBodyUSLT temp = (FrameBodyUSLT) lyricframe.getBody();
			   testo = temp.getFirstTextValue();
			}
			catch(Exception e){
			  testo = "Sconosciuto";
			}
		return testo;
	}
	
	/**
	 * Imposta il parametro Album della tag relativa ai metadati del FileMp3
	 * 
	 * @param stringa 
	 * 				la stringa contente l'album da impostare
	 */
	public void setAlbum(String stringa) {
		try {
			tag.setAlbum(stringa);
			Album = stringa;
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Imposta il parametro Anno della tag relativa ai metadati del FileMp3
	 * 
	 * @param stringa 
	 * 				la stringa contente l'Anno da impostare
	 */
	public void setAnno (String stringa) {
		try {
			tag.setYear(stringa);
			Anno = stringa;
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Imposta il parametro Artista della tag relativa ai metadati del FileMp3
	 * 
	 * @param stringa
	 * 				la  contente l'Artista da impostare
	 */
	public void setArtista (String stringa) {
		try {
			tag.setArtist(stringa);
			Artista = stringa;
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Imposta la tag del FileMp3
	 *
	 */
	public void setTag () {
		this.setTag(tag);
		Commit();
	}
	
	/**
	 * Imposta il parametro Copertina della tag relativa ai metadati del FileMp3
	 * 
	 * @param stringa
	 * 				la  contente la Copertina da impostare
	 */
	public void setCopertina (String Path) {
		if (Path.isEmpty())
			return;
		RandomAccessFile imageFile = null;
		String estensione;
		
		if 		(Path.endsWith(".jpg") || Path.endsWith(".JPG"))
			estensione = "image/jpg";
		else if (Path.endsWith(".png") || Path.endsWith(".PNG"))
			estensione = "image/png";
		else
			estensione = "image/bmp";
		
		byte[] imagedata = null;
		
		try { imageFile = new RandomAccessFile(new File(Path), "r"); } 
		catch (FileNotFoundException e) {}
		
		try { imagedata = new byte[(int) imageFile.length()]; } 
		catch (IOException e1) {}
	/*	try { imageFile.read(imagedata); } 
		catch (IOException e) {}
		try {
			if (hasCopertina()){
				tag.deleteTagField(TagFieldKey.COVER_ART);
				tag.add(((AbstractID3v2Tag) tag).createArtworkField(imagedata,estensione));
			}
			else
				tag.add(((AbstractID3v2Tag) tag).createArtworkField(imagedata,estensione));
		} catch (FieldDataInvalidException e) {}  */
		Copertina = loadCopertina();
		setTag(tag);  
	}  
		
	/**
	 * Imposta il parametro Lyrics della tag relativa ai metadati del FileMp3
	 * 
	 * @param stringa 
	 * 				la stringa contente la Lyrics da impostare
	 */
	public void setLyrics (String stringa) {
		ID3v23Frame lyricframe;
		try {
			lyricframe = (ID3v23Frame) ((AbstractID3v2Tag) tag).getFrame(ID3v23FieldKey.LYRICS.getFieldName());
			FrameBodyUSLT tmp = (FrameBodyUSLT) lyricframe.getBody();
			tmp.setLyric(stringa);
			lyricframe.setBody(tmp);
			((AbstractID3v2Tag) tag).setFrame(lyricframe);
		} catch( NullPointerException e) {
		}	
		Lyrics = loadLyrics();
		setTag(tag);
	}
	
	/**
	 * Imposta il parametro Genere della tag relativa ai metadati del FileMp3
	 * 
	 * @param stringa
	 * 				la stringa contente il genere da impostare
	 */
	public void setGenere (String stringa) {
		try {
			tag.setGenre(stringa);
			Genere = stringa;
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		}
		setTag(tag);
	}
	
	/**
	 * Imposta il parametro Traccia della tag relativa ai metadati del FileMp3
	 * 
	 * @param stringa 
	 * 				la stringa contente la Traccia da impostare
	 */
	public void setTraccia (String stringa) {
		try {
			tag.setTrack(stringa);
			Traccia = stringa;
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		}
		setTag(tag);
	}
	
	/**
	 * Imposta il parametro Titolo della tag relativa ai metadati del FileMp3
	 * 
	 * @param stringa
	 * 				la stringa contente il Titolo da impostare
	 */
	public void setTitolo (String stringa) {
		try {
			tag.setTitle(stringa);
			Titolo = stringa;
		} catch (FieldDataInvalidException e) {
			e.printStackTrace();
		}
		setTag(tag);
	}
	
	/**
	 * Metodo per rilevare se un FileMp3 ha la copertina.
	 * @return true se il FileMp3 possiede la copertina
	 * @return false se il FileMp3 non la possiede
	 */
	public boolean hasCopertina() { 
		if (!TagVersion.equals("ID3v2"))
			return false;
		if (tag.getFirst(TagFieldKey.COVER_ART).isEmpty())
			return false;
		return true;
		}
	
	/**
	 * Metodo per rilevare se un FileMp3 ha la Lyrics.
	 * @return true se il FileMp3 possiede la Lyrics
	 * @return false se il FileMp3 non la possiede
	 */
	public boolean hasLyrics() { 
		if (TagVersion.equals("NoTag"))
			return false;
		if (tag.getFirst(TagFieldKey.LYRICS).isEmpty())
			return false;
		return true;
		}
	
	/**
	 * Metodo sfruttato elaborare le informazioni ottenute dal parametro Genere
	 * dei metadati del FileMp3
	 * 
	 * @param genere
	 * 				il metadato Genere da elaborare
	 * @return una stringa rappresentante il genere del FileMp3
	 */
	public String infoGenere (String genere) {
		 if (genere.startsWith("(")) {
			 int index = genere.lastIndexOf(")");
			 genere = genere.substring(0, index);
			 genere = genere.replace("(", "");
			 int i = new Integer(genere);
			 switch (i) {
				case   0 : return "Blues"; 
				case   1 : return "Classic Rock";
				case   2 : return "Country";
				case   3 : return "Dance";
				case   4 : return "Disco";
				case   5 : return "Funk";
				case   6 : return "Grunge";
				case   7 : return "Hip-Hop";
				case   8 : return "Jazz";
				case   9 : return "Metal";
				case  10 : return "New Age";
				case  11 : return "Oldies";
				case  12 : return "Other";
				case  13 : return "Pop";
				case  14 : return "R&B";
				case  15 : return "Rap";
				case  16 : return "Reggae";
				case  17 : return "Rock";
				case  18 : return "Techno";
				case  19 : return "Industrial";
				case  20 : return "Alternative";
				case  21 : return "Ska";
				case  22 : return "Death Metal";
				case  23 : return "Pranks";
				case  24 : return "Soundtrack";
				case  25 : return "Euro-Techno";
				case  26 : return "Ambient";
				case  27 : return "Trip-Hop";
				case  28 : return "Vocal";
				case  29 : return "Jazz Funk";
				case  30 : return "Fusion";
				case  31 : return "Trance";
				case  32 : return "Classical";
				case  33 : return "Instrumental";
				case  34 : return "Acid";
				case  35 : return "House";
				case  36 : return "Game";
				case  37 : return "Sound Clip";
				case  38 : return "Gospel";
				case  39 : return "Noise";
				case  40 : return "AlternRock";
				case  41 : return "Bass";
				case  42 : return "Soul";
				case  43 : return "Punk";
				case  44 : return "Space";
				case  45 : return "Meditative";
				case  46 : return "Instrumental Pop";
				case  47 : return "Instrumental Rock";
				case  48 : return "Ethnic";
				case  49 : return "Gothic";
				case  50 : return "Darkwave";
				case  51 : return "Techno-Industrial";
				case  52 : return "Electronic";
				case  53 : return "Pop-Folk";
				case  54 : return "Eurodance";
				case  55 : return "Dream";
				case  56 : return "Southern Rock";
				case  57 : return "Comedy";
				case  58 : return "Cult";
				case  59 : return "Gangsta";
				case  60 : return "Top 40";
				case  61 : return "Christian Rap";
				case  62 : return "Pop/Funk";
				case  63 : return "Jungle";
				case  64 : return "Native American";
				case  65 : return "Cabaret";
				case  66 : return "New Wave";
				case  67 : return "Psychadelic";
				case  68 : return "Rave";
				case  69 : return "Showtunes";
				case  70 : return "Trailer";
				case  71 : return "Lo-Fi";
				case  72 : return "Tribal";
				case  73 : return "Acid Punk";
				case  74 : return "Acid Jazz";
				case  75 : return "Polka";
				case  76 : return "Retro";
				case  77 : return "Musical";
				case  78 : return "Rock & Roll";
				case  79 : return "Hard Rock";
				case  80 : return "Folk";
				case  81 : return "Folk-Rock";
				case  82 : return "National Folk";
				case  83 : return "Swing";
				case  84 : return "Fast Fusion";
				case  85 : return "Bebob";
				case  86 : return "Latin";
				case  87 : return "Revival";
				case  88 : return "Celtic";
				case  89 : return "Bluegrass";
				case  90 : return "Avantgarde";
				case  91 : return "Gothic Rock";
				case  92 : return "Progressive Rock";
				case  93 : return "Psychedelic Rock";
				case  94 : return "Symphonic Rock";
				case  95 : return "Slow Rock";
				case  96 : return "Big Band";
				case  97 : return "Chorus";
				case  98 : return "Easy Listening";
				case  99 : return "Acoustic";
				case 100 : return "Humour";
				case 101 : return "Speech";
				case 102 : return "Chanson";
				case 103 : return "Opera";
				case 104 : return "Chamber Music";
				case 105 : return "Sonata";
				case 106 : return "Symphony";
				case 107 : return "Booty Bass";
				case 108 : return "Primus";
				case 109 : return "Porn Groove";
				case 110 : return "Satire";
				case 111 : return "Slow Jam";
				case 112 : return "Club";
				case 113 : return "Tango";
				case 114 : return "Samba";
				case 115 : return "Folklore";
				case 116 : return "Ballad";
				case 117 : return "Power Ballad";
				case 118 : return "Rhythmic Soul";
				case 119 : return "Freestyle";
				case 120 : return "Duet";
				case 121 : return "Punk Rock";
				case 122 : return "Drum Solo";
				case 123 : return "A capella";
				case 124 : return "Euro-House";
				case 125 : return "Dance Hall";
				
				default: return genere;
				}
		 }
		 return genere;
	}	
	
	/**
	 * Metodo per scrivere le modifiche apportate dal programma al FileMp3 sul file stesso
	 * nella sua locazione originale.
	 */
	public void Commit() {
		try { this.commit(); }
		catch (CannotWriteException e) {e.printStackTrace();
		}
	}
	
	// Dichiarazione variabili
	private String TagVersion;
	private String Artista;
	private String Titolo;
	private String Album;
	private String Anno;
	private String Genere;
	private String Traccia;
	private String BitRate;
	private String SampleRate;
	private String Channels;
	private String AbsPath;
	private String Path;
	private String Lyrics;
	private ImageIcon Copertina;
	private Tag tag;
}

