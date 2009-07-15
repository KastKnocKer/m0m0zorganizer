package prove;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import database.*;
import download.*;

public class provevideo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
    	new Scan();
    	
        Socket soc = new java.net.Socket();
        try {
            soc.connect(new InetSocketAddress("37.237.2.161", 80));
            System.out.println(API.getUser("active", "Staffgrillo"));
        } catch (IOException e) {
            e.printStackTrace();
        }     	    	
        try {
			soc.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
