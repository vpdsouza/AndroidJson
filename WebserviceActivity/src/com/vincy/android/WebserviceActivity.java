package com.vincy.android;

import com.vincy.android.R;
import com.vincy.data.*;
import com.vincy.service.Json;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class WebserviceActivity extends Activity
{
	private String TAG = "VincyJson";

	ProgressBar progressBar;
	IcdResult icdResult;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		// AysnTask class to handle Json Web Service call as separate Thread
		AsyncJsonCall task = new AsyncJsonCall();
		// Execute the task get the data from web service and display it in the ListView 
		task.execute();

	}

	// AysnTask class to handle Json Web Service call as separate Thread
	private class AsyncJsonCall extends AsyncTask<String, Void, Void>
	{
	
		@Override
		protected Void doInBackground(String... params)
		{
			Log.i(TAG, "doInBackground");
			icdResult = Json.getIcdResultFromWebService();
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			Log.i(TAG, "onPostExecute");


			ListView listView1 = (ListView) findViewById(R.id.ListView1);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(WebserviceActivity.this, android.R.layout.simple_list_item_1, icdResult.IcdItems());

			listView1.setAdapter(adapter);

			// Make the progress bar invisible
			progressBar.setVisibility(View.INVISIBLE);
		}

		@Override
		protected void onPreExecute()
		{
			Log.i(TAG, "onPreExecute");
			// Display progress bar
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Void... values)
		{
			Log.i(TAG, "onProgressUpdate");
		}

	}


}