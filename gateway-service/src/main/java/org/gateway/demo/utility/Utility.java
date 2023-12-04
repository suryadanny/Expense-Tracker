package org.gateway.demo.utility;

import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
import java.util.StringTokenizer;

import javax.ws.rs.core.HttpHeaders;

import org.gateway.demo.api.GatewayApp;
import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;

import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;
import okhttp3.Request.Builder;

public class Utility {

	public static Map<String,String> resolveHeaders(String token) throws Exception {
		String au = token;
		au= au.replaceFirst("Basic","");
		Decoder d = Base64.getMimeDecoder();
		byte[]  au1 = d.decode(au);
		String au2 =new String(au1);
		
		Map<String,String> map = new HashMap<String,String>();
		try {
			StringTokenizer st = new StringTokenizer(au2, ":");
			map.put("userId", st.nextToken());
		} catch (Exception ex) {
			throw new Exception("Authorization Not Found");
		}
		return map;
	}
	
	public String getBase64Encoded(String token) throws Exception{
		Encoder en = Base64.getMimeEncoder();
		byte[] encodedToken = en.encode(token.getBytes());
		return new String(encodedToken);
	}
	
	
	public Builder getRequestBuilder(HttpHeaders headers , String service,String urlPath){
		Gson gson = new Gson();
		InputStream inputStream = Utility.class.getResourceAsStream("/application.yml");

		Yaml yaml = new Yaml();
		Map<String, Object> data = yaml.load(inputStream);
		String portService= "";
		if(service.toLowerCase().contains("expense")) {
			portService = "expense-service-port";
		}else {
			portService = "user-service-port";
		}
		
		okhttp3.Request.Builder requestBuilder = new Request.Builder()
			      .url("http://localhost:"+data.get("portService")+"/"+service+"/app/expense" + "/all");
			      
		for(Entry<String, List<String>> entry : headers.getRequestHeaders().entrySet()) {
			requestBuilder.addHeader(entry.getKey(), entry.getValue().stream()
                       .map(Object::toString)
                       .collect(Collectors.joining(", ")));
		}

		return null;
	}
}
