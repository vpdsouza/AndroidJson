package com.prgguru.android;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.json.JSONTokener;

import parameters.IcdSearch;
import com.google.gson.Gson;
import data.*;

import android.app.Activity;
import android.content.ClipData.Item;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class WebserviceActivity extends Activity
{
	// Change IP to your machine IP
	private final String URL = "https://secure.imbills.com/MobileService/Production/v2.3.4/IMBillsMobileService.asmx/GetInitialIcdBySearch";

	private String TAG = "PGGURU";
	private static String jsonRespone;

	// Country Spinner Control
	Spinner countrySpinnerCtrl;
	// City Spinner Control
	Spinner citySpinnerCtrl;
	ProgressBar pg;
	IcdResult icdResult;
	// GSON object
	Gson gson = new Gson();

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		pg = (ProgressBar) findViewById(R.id.progressBar1);

		// AysnTask class to handle Country WS call as separate UI Thread
		AsyncCountryWSCall task = new AsyncCountryWSCall();
		// Execute the task to set Country list in Country Spinner Control
		task.execute();

	}

	// AysnTask class to handle Country WS call as separate UI Thread
	private class AsyncCountryWSCall extends AsyncTask<String, Void, Void>
	{
		@Override
		protected Void doInBackground(String... params)
		{
			Log.i(TAG, "doInBackground");
			// Invoke web method 'PopulateCountries' with dummy value
			invokeJSONWS();
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			Log.i(TAG, "onPostExecute");

			icdResult = gson.fromJson(jsonRespone, IcdResult.class);

			ListView listView1 = (ListView) findViewById(R.id.ListView1);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(WebserviceActivity.this, android.R.layout.simple_list_item_1, icdResult.IcdItems());

			listView1.setAdapter(adapter);

			// Make the progress bar invisible
			pg.setVisibility(View.INVISIBLE);
		}

		@Override
		protected void onPreExecute()
		{
			Log.i(TAG, "onPreExecute");
			// Display progress bar
			pg.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Void... values)
		{
			Log.i(TAG, "onProgressUpdate");
		}

	}

	// Method which invoke web methods
	public void invokeJSONWS()
	{

		HttpClient client = new DefaultHttpClient();
		String responseBody = "";
		new JSONObject();

		try
		{
			HttpPost post = new HttpPost(URL);

			Gson gson = new Gson();
			String parameter = gson.toJson(new IcdSearch());

			StringEntity se = new StringEntity(parameter);
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

			post.setEntity(se);
			post.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setHeader("Content-type", "application/json");
			Log.e("webservice request", "executing");

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = client.execute(post, responseHandler);

			JSONObject jsonObject = (JSONObject) new JSONTokener(responseBody).nextValue();

			String jsonString = jsonObject.getString("d");

			jsonRespone = jsonString;

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}