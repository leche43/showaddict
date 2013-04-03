package de.showaddict.trakt;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import de.showaddict.entity.Episode;
import de.showaddict.entity.Season;
import de.showaddict.entity.Show;
import de.showaddict.entity.ShowInfo;

public class TraktFunctions {
	
	public static Logger LOGGER = Logger.getLogger(TraktFunctions.class.getName());
	
	public static final String API_KEY = "4bde279d0fe5826ab48e2f0f91117474";
	public static final String USERNAME = "JaxxTe";
	public static final String LIBRARY_URL = "http://api.trakt.tv/user/library/shows/all.json/";
	public static final String USER_PROGRESS_WATCHED_URL = "http://api.trakt.tv/user/progress/watched.json/";
	
	public static final String NO_NEXT_EPISODE = "\"next_episode\":false,";
	
	public static List<ShowInfo> getAllShowsDetails() {
		List<ShowInfo> list = new ArrayList<ShowInfo>();

		String url = LIBRARY_URL + API_KEY + "/" + USERNAME;
		Type type = new TypeToken<ArrayList<ShowInfo>>(){}.getType();
		list = doRequest(url, type);
		return list;
	}
	
	/**
	 * laedt den aktuellen stand der angeschauten shows herunter
	 * 
	 * 
	 * @see <a href="http://trakt.tv/api-docs/user-progress-watched">http://trakt.tv/api-docs/user-progress-watched</a>
	 * 
	 * @return
	 */
	public static List<Show> getAllWatchedShowsFromLibrary(Context context) {
		List<Show> shows = new ArrayList<Show>();
		String url = USER_PROGRESS_WATCHED_URL + API_KEY + "/"+ USERNAME;
		
		String json = doRequest(url);
		
		shows = parseAllWatchedShowsJson(json);
		//hole mehr Informationen ueber eine show
		List<ShowInfo> showInfos = getAllShowsDetails();
		//informationen zu einer show hinzufuegen und bild herunterladen
		for(Show show : shows) {
			for(ShowInfo showInfo : showInfos) {
				if(show.getShowInfo().getTitle().equals(showInfo.getTitle())) {
					show.setShowInfo(showInfo);
					show = downloadPicture(show, context);
					continue;
				}
				
			}
		}
		
		return shows;
	}
	
	/**
	 * Diese Methode ist speziell für den Rest Aufruf siehe link.
	 * 
	 * Sollte vor verwendung genau verstanden sein
	 * @see <a href="http://trakt.tv/api-docs/user-progress-watched">http://trakt.tv/api-docs/user-progress-watched</a>
	 * @param json
	 * @return liste von shows
	 */
	private static List<Show> parseAllWatchedShowsJson(String json) {
		
		Gson gson = new Gson();
		Type listType = new TypeToken<List<Show>>(){}.getType();
		
		json = checkIfNextEpisodeFalse(json);
		
		List<Show> showList = gson.fromJson(json, listType);
		
		//da die Episoden nicht automatisch geparst werden koennen, muessen die Informationen
		//hier von Hand aus dem json gezogen werden
		
		JsonElement element = new JsonParser().parse(json);
		JsonArray showsArray = element.getAsJsonArray();
		int showIndex = 0;
		for(JsonElement shows : showsArray) {
			JsonArray seasonArray = shows.getAsJsonObject().get("seasons").getAsJsonArray();
			Integer episodeCounter = 1;
			Show showWrapper = showList.get(showIndex);
			List<Season> seasons = showWrapper.getSeasons();
			int seasonIndex = 0;
			for(JsonElement seasonElement : seasonArray) {
				Season season = seasons.get(seasonIndex);
				JsonObject episodesObject = seasonElement.getAsJsonObject().get("episodes").getAsJsonObject();
				while(episodesObject.has(episodeCounter.toString())){
					Boolean watched = episodesObject.get(episodeCounter.toString()).getAsBoolean();
					//setze hier episode und watched
					Episode episode = new Episode(episodeCounter, watched);
					season.addEpisode(episode);
					++episodeCounter;
				}
				++seasonIndex;
				episodeCounter = 1;
			}
			
			++showIndex;
		}
		
		return showList;
	}
	
	private static String checkIfNextEpisodeFalse(String json) {
		if(json.contains(NO_NEXT_EPISODE)) {
			json = json.replaceAll(NO_NEXT_EPISODE, " ");
		}
		
		return json;
	}

	/**
	 * request on the given url
	 * 
	 * 
	 * @param url
	 * @return json as a String
	 */
	public static String doRequest(String url) {
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		InputStream content = null;
		try {
			HttpResponse response = client.execute(request);
			
			content = response.getEntity().getContent();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String json = convertStreamToString(content);
		
		return json;
	}
	
	public static List<ShowInfo> doRequest(String url, Type type) {
		List<ShowInfo> list = Collections.emptyList();
		Gson gson = new Gson();
		
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
        try {
			HttpResponse response = client.execute(request);
			InputStream content = response.getEntity().getContent();
			
			String json = convertStreamToString(content);
			list = gson.fromJson(json, type);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
		return list;		
	}
	
	private static Show downloadPicture(Show show, Context context) {
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(show.getShowInfo().getImages().getPoster());
		HttpResponse response;
		String fileName = show.getShowInfo().getTitle() + "_banner.jpg";

		try {
			response = client.execute(request);
			InputStream is = response.getEntity().getContent();
			
			Bitmap image = BitmapFactory.decodeStream(is);
			LOGGER.info("BITMAP BYTES; " + image.getByteCount());
			
			
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			image.compress(CompressFormat.JPEG, 30, fos);
			LOGGER.info("STORED IMAGE: " + fileName + " BYTES: " + image.getByteCount());
			fos.close();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		show.setBannerUri(fileName);
		return show;
	}
	
	public static String convertStreamToString(InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}
