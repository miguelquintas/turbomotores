package com.turbo.turbomotores;

import com.turbo.turbomotores.library.GetNews;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	public GetNews getNews;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void start(){
    	
    	getNews = new GetNews();
    	getNews.getNewsFromUrl();
    }
}
