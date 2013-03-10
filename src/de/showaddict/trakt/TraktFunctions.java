package de.showaddict.trakt;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.showaddict.entity.Show;

public class TraktFunctions {
	
	public static final String API_KEY = "4bde279d0fe5826ab48e2f0f91117474";
	public static final String USERNAME = "leche43";
	public static final String LIBRARY_URL = "http://api.trakt.tv/user/library/shows/all.json/";
	
	public static List<Show> getAllShowsFromLibrary() {
		List<Show> list = new ArrayList<Show>();

		String url = LIBRARY_URL + API_KEY + "/" + USERNAME;
		Type type = new TypeToken<ArrayList<Show>>(){}.getType();
		list = doRequest(url, type);
		return list;
	}
	
	public static List<Show> doRequest(String url, Type type) {
		List<Show> list = Collections.emptyList();
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
	
	public static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
}
