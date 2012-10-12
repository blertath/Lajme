package com.milot.lajme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

public class NewsReadingActivity extends Activity {
	TextView txtDetailedTitle;
	WebView txtDetailedBody;
	final int SHARE_MENU = Menu.FIRST + 1;
	String content;
	String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.newsreading);
		
		txtDetailedBody = (WebView) findViewById(R.id.txtDetailedBody);
		
		
		final Intent intent = getIntent();
		
		content = intent.getStringExtra("newsBody").toString();		
		
		txtDetailedBody.loadDataWithBaseURL(null, "<HTML>" + content + "</HTML>", "text/html", "utf-8", "about:blank");
			
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case SHARE_MENU:
				Intent m_Intent = getIntent();
				Intent shareIntent = new Intent(Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(Intent.EXTRA_SUBJECT, m_Intent.getStringExtra("newsTitle").toString());
				shareIntent.putExtra(Intent.EXTRA_TEXT,  m_Intent.getStringExtra("newsLink").toString());
				startActivity(Intent.createChooser(shareIntent, "Ndaj� lajmin me t� tjer�t"));
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, SHARE_MENU, 0, "Ndaj� lajmin me t� tjer�t").setIcon(android.R.drawable.ic_menu_share);
		return super.onCreateOptionsMenu(menu);
	}
}
