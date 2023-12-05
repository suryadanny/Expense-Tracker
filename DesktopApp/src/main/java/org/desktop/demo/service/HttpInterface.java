package org.desktop.demo.service;





import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpInterface {

    @SuppressWarnings("deprecation")
	public static Response POST(String url, String payload) throws Exception
    {
    	Gson gson = new Gson();
        String auth = Common_var.btoa();
        RequestBody body = RequestBody.create(
				okhttp3.MediaType.parse("application/json"),payload);
        okhttp3.Request.Builder requestBuilder = new Request.Builder()
			      .url(url)
			      .post(body)
	              .addHeader("Authorization", auth)
	              .addHeader("Content-Type", "application/json");
			      
		
		
		okhttp3.Request request = requestBuilder.build();
		
		OkHttpClient client = new OkHttpClient();
		Call call = client.newCall(request);
		okhttp3.Response response = call.execute();

        

        return response;
    }

    public static Response GET(String url) throws Exception
    {
        String auth = Common_var.btoa();
        
        
        
        okhttp3.Request.Builder requestBuilder = new Request.Builder()
			      .url(url)
			      .addHeader("Authorization", auth)
	              .addHeader("Content-Type", "application/json");
			      
		
		
		okhttp3.Request request = requestBuilder.build();
		
		OkHttpClient client = new OkHttpClient();
		Call call = client.newCall(request);
		okhttp3.Response response = call.execute();

        

        return response;
    }
}
