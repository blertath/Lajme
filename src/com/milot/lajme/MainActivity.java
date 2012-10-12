package com.milot.lajme;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	
	HttpClient client = null;
	List<News> list = new ArrayList<News>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                
        client = new DefaultHttpClient();
        
        new AsyncNewsUpdate().execute("http://zeri.info/rss-all.php?category=1");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    class AsyncNewsUpdate extends AsyncTask<String, Void, Void> {

		Parser p = new Parser();
		ProgressDialog loadingProgress;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			loadingProgress = new ProgressDialog(MainActivity.this);
			loadingProgress.setMessage("Duke i shkarkuar lajmet...");			
			list.clear();		    
			loadingProgress.show();


		}

		@Override
		protected Void doInBackground(String... params) {
			try{
			updateNews(params[0]);
			}catch(Exception e){}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			setListAdapter(new NewsAdapter());			
			loadingProgress.dismiss();
		}

	}

	private void updateNews(String url) {
		Parser parser = new Parser();
		HttpGet getMethod = new HttpGet(url);

		try {
			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = client.execute(getMethod, responseHandler);
			list = parser.fillList(responseBody, 0);

		} catch (Throwable t) {
			Log.e("updateNews Error", t.getMessage());
		}

	}
    
    class NewsAdapter extends ArrayAdapter<News>
    {

		public NewsAdapter() {
			super(MainActivity.this, R.layout.list_row, list);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			View row = convertView;
			final int _position = position;
			
			if (row == null) {
				LayoutInflater inflater = getLayoutInflater();
				row = inflater.inflate(R.layout.list_row, parent, false);
				holder = new ViewHolder(row);
				row.setTag(holder);
			} else {
				holder = (ViewHolder) row.getTag();
			}
			holder.getTitleView().setText(Html.fromHtml(list.get(position).title()));
			
			row.setBackgroundResource(R.drawable.selected_state);
						
			row.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intentNewsDetails = new Intent(MainActivity.this,
							NewsReadingActivity.class);
					intentNewsDetails.putExtra("newsTitle",
							list.get(_position).title());
					intentNewsDetails.putExtra("newsBody",
							list.get(_position).description());
					intentNewsDetails.putExtra("newsLink",
							list.get(_position).link());
					startActivity(intentNewsDetails);
					
				}
			});
			
			return row;
		}
    			
    }
}
