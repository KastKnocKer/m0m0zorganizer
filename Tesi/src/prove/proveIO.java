package prove;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
public class proveIO {

	public static void main(String[] args) {
		URL metafeedUrl;
		try {
			metafeedUrl = new URL("http://gdata.youtube.com/feeds/api/users/staffgrillo/favorites");
			HttpURLConnection connection = (HttpURLConnection) metafeedUrl.openConnection();
			int resp = connection.getResponseCode();
			System.out.println(resp);
			System.out.println(connection.getInputStream());
		} 
		catch (IOException e) {
				e.printStackTrace();
		}
		
	}
	}
