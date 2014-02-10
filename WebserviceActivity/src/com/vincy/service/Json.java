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
	private final static String webServiceUrl = "http://10.0.1.116/MobileServices/v2.3.4/IMBillsMobileService.asmx/GetInitialIcdBySearch";

	// Method which invoke web methods
	private static String invokeJsonWebService()
	{

		String jsonRespone = "";
		HttpClient httpClient = new DefaultHttpClient();
		String httpResponse = "";

		try
		{
			HttpPost httpPost = new HttpPost(webServiceUrl);

			Gson gson = new Gson();
			String parameter = gson.toJson(new IcdSearch());

			//Create the json parameter to invoke the web service method
			StringEntity stringEntity = new StringEntity(parameter);
			stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

			httpPost.setEntity(stringEntity);

//			httpPost.setHeader(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			httpPost.setHeader("Content-type", "application/json");
			Log.e("webservice request", "executing");

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			httpResponse = httpClient.execute(httpPost, responseHandler);

			/**Since the Asp.Net web service is wrapping the json string within the [d] object, 
			***remove the wrapper and get the valid json from the response string.
			**/
			JSONObject jsonObject = (JSONObject) new JSONTokener(httpResponse).nextValue();

			String jsonString = jsonObject.getString("d");

			jsonRespone = jsonString;

		} catch (Exception exception)
		{
			exception.printStackTrace();
		}
		return jsonRespone;

	}
	
	public static IcdResult getIcdResultFromWebService()
	{
		String jsonResponse = invokeJsonWebService();
		Gson gson = new Gson();
		
		IcdResult icdResult = gson.fromJson(jsonResponse, IcdResult.class);
		
		return icdResult;
		
	}
}
