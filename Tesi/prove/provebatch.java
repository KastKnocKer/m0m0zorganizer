package prove;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.batch.*;
import com.google.gdata.data.youtube.*;
import com.google.gdata.util.ServiceException;

public class provebatch {

	public static void main(String[] args) {
		YouTubeService service = new YouTubeService("Tesi", developerKey); 
		VideoFeed feed;
		try {
			feed = service.getFeed(new URL("http://gdata.youtube.com/feeds/api/users/animegamer3/uploads?max-results=50&start-index=51"), VideoFeed.class);
			URL batchUrl = new URL(feed.getFeedBatchLink().getHref());
			System.out.println(batchUrl);
			VideoFeed batchFeed = new VideoFeed();
			VideoFeed batchResponse = service.batch(batchUrl, batchFeed);
			for(VideoEntry entry : batchResponse.getEntries()) {
				 if(BatchUtils.isSuccess(entry)) {
				   System.out.println("Operation " + BatchUtils.getBatchId(entry) + " succeeded!");
				   String operation = BatchUtils.getBatchOperationType(entry).getName();
				   System.out.println("The " + operation + " worked!");
				 }
				 else {
				   System.out.println("Operation " + BatchUtils.getBatchId(entry) + "failed!");
				   BatchStatus status = BatchUtils.getBatchStatus(entry);
				   System.out.println(status.getCode() + " - " + status.getContent());
				 }
				}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		
	}	
	private final static String developerKey = "AI39si6Eq4oBSKdw1KHpCX9rhwVpdsxO04VqiFyB13xRa37gbQR3D0i-PBiSqLAi8vfaEya3w95AZFq8T6qbIwQwxVuyaADJsQ";
}
