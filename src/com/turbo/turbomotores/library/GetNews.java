package com.turbo.turbomotores.library;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class GetNews {
	
	public String url = "http://turbomotores.com/feed/json";
	public List<News> news;
	
	public void getNewsFromUrl()
	{
		String page = "";
		BufferedReader in = null;
		
		try{
			HttpClient client = new DefaultHttpClient();
	        client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "android");
	        HttpGet request = new HttpGet();
	        request.setURI(new URI(url));
	        HttpResponse response = client.execute(request);
	        in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	        StringBuffer sb = new StringBuffer("");
	        String line = "";

	        String NL = System.getProperty("line.separator");
	        while ((line = in.readLine()) != null) 
	        {
	            sb.append(line + NL);
	        }
	        in.close();
	        page = sb.toString();
	        //System.out.println(page);

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
		}
		
		try{
			JSONArray jsonArray = new JSONArray(page);
			for (int i = 0 ; i < jsonArray.length(); i++ ) {
			  JSONObject entry = (JSONObject) jsonArray.get(i);
			  // now get the data from each entry
			  System.out.println(entry);
			}
		}
		catch(Exception e){
			Log.e("log_tag", "Error converting to Json "+e.toString());
		}
		
	}
	
	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}
	
}
