package com.vincy.service;

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

import android.util.Log;

import com.google.gson.Gson;
import com.vincy.data.IcdResult;
import com.vincy.parameters.IcdSearch;

public class Json
{
	// Change IP to your machine IP
	private final static String URL = "http://10.0.1.116/MobileServices/v2.3.4/IMBillsMobileService.asmx/GetInitialIcdBySearch";

	// Method which invoke web methods
	private static String invokeJSONWS()
	{

		String jsonRespone = "";
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
		return jsonRespone;

	}
	
	public static IcdResult getIcdResultFromWebService()
	{
		String jsonResponse = invokeJSONWS();
		Gson gson = new Gson();
		
		IcdResult icdResult = gson.fromJson(jsonResponse, IcdResult.class);
		
		return icdResult;
		
	}
}
