package com.turbo.turbomotores.library;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class GetNews {
	
	public String url = "http://turbomotores.com/feed/json";
	public List<Article> news;
	public DBManager dbManager;
	
	public void getNewsFromUrl(Context context)
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
			dbManager = new DBManager(context);
			dbManager.open();
			
			ArrayList<Category> categories = dbManager.getAllCategories();
			ArrayList<Tag> tags = dbManager.getAllTags();
			ArrayList<Article> articles = dbManager.getAllArticles();
			
			JSONArray jsonArticles = new JSONArray(page);
			for (int i = 0 ; i < jsonArticles.length(); i++ ) {
				
				Boolean isArticleAlreadyInDB = false;
				Article savedArticle = null;
				Category savedCategory = null;
				Tag savedTag = null;
				
				JSONObject jsonArticle = (JSONObject) jsonArticles.get(i);
				// now get the data from each entry
				
				for (Article art : articles)
				{
					if (art.getTitle().equals(jsonArticle.getString("title")))
					{
						isArticleAlreadyInDB = true;
					}
				}
				
				if (!isArticleAlreadyInDB)
				{
					String imageUrl;
					String content = jsonArticle.getString("content");
					try
					{
						String content1 = content.substring(content.indexOf("<img src="));
						imageUrl = content1.substring(10, content1.indexOf("jpg") + 3);
					}
					catch(Exception e)
					{
						imageUrl = "";
					}

					// save article to DB
					savedArticle = dbManager.createArticle(jsonArticle.getString("title"), 
							jsonArticle.getString("permalink"), 
							jsonArticle.getString("excerpt"), 
							jsonArticle.getString("content"), 
							jsonArticle.getString("author"), 
							imageUrl, 
							jsonArticle.getString("date"));

					// save category do DB
					JSONArray jsonCategories = new JSONArray(jsonArticle.getString("categories"));
					for (int j = 0 ; j < jsonCategories.length() ; j++)
					{
						String categoryString = jsonCategories.getString(j);
						Boolean isAlreadyInDB = false;
						// if there's no this category in DB, add it
						for (Category cat : categories)
						{
							if (cat.getName().equals(categoryString))
							{
								isAlreadyInDB = true;
							}
						}

						if (!isAlreadyInDB)
						{
							savedCategory = dbManager.createCategory(categoryString);
							
							// save Article Category to DB
							dbManager.createArticleCategory(savedArticle.getId(), savedCategory.getId());
							
							Category category = new Category();
							category.setName(categoryString);
							categories.add(category);
						}
					}

					// save tag to DB
					JSONArray jsonTags = new JSONArray(jsonArticle.getString("tags"));
					for (int k = 0 ; k < jsonTags.length() ; k++)
					{
						String tagString = jsonTags.getString(k);
						Boolean isAlreadyInDB = false;
						// if there's no this tag in DB, add it
						for (Tag tag : tags)
						{
							if (tag.getName().equals(tagString))
							{
								isAlreadyInDB = true;
							}
						}

						if (!isAlreadyInDB)
						{
							savedTag = dbManager.createTag(tagString);
							
							// save Article Tag to DB
							dbManager.createArticleTag(savedArticle.getId(), savedTag.getId());
							
							Tag tag = new Tag();
							tag.setName(tagString);
							tags.add(tag);
						}
					}
				}
			}
			
			System.out.println("finish importing JSON to DB");
		}
		catch(Exception e){
			Log.e("log_tag", "Error converting to Json "+e.toString());
		}
		
	}
	
	public List<Article> getNews() {
		return news;
	}

	public void setNews(List<Article> news) {
		this.news = news;
	}
	
}
