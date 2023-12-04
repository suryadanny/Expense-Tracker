package org.gateway.demo.api;


import java.io.InputStream;
import java.lang.reflect.Type;
// api administrator endpoint for searching visitor entry
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.gateway.demo.model.Contact;
import org.gateway.demo.model.Expense;
import org.gateway.demo.model.Group;
import org.gateway.demo.model.User;
import org.gateway.demo.utility.Utility;
import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

@Path("gateway")
public class GatewayApp {
	
	
	@GET
	@Path("/expense/all")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@Context HttpHeaders headers) {
		List<Expense> expenses = new ArrayList<Expense>(); 
    	try {
    		
    		Gson gson = new Gson();
			InputStream inputStream = GatewayApp.class.getResourceAsStream("/application.yml");

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			
			okhttp3.Request.Builder requestBuilder = new Request.Builder()
				      .url("http://localhost:"+data.get("expense-service-port")+"/Expense-Service/app/expense" + "/all");
				      
			for(Entry<String, List<String>> entry : headers.getRequestHeaders().entrySet()) {
				requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue().stream()
	                       .map(Object::toString)
	                       .collect(Collectors.joining(", ")));
			}
			
			okhttp3.Request request = requestBuilder.build();
			Type typeMyType = new TypeToken<List<Expense>>() {
			}.getType();
			OkHttpClient client = new OkHttpClient();
				    Call call = client.newCall(request);
			okhttp3.Response response = call.execute();
		    if(response.isSuccessful()) {
		    	
		    	expenses = 	gson.fromJson(response.body().string(), typeMyType);
		    	
		    	System.out.println("users here all expense size : "+expenses.size());
		    }
    	  
     	}catch (Exception ex) {
     		System.out.println("Exception occurred while getting all expenses :" + ex.getMessage());
     		return Response.serverError().entity(ex.getMessage()).build();
     	}
     	return Response.ok(expenses).build();
    }
    
    
    @SuppressWarnings("deprecation")
	@POST
    @Path("/postExpense")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response addExpense(@Context HttpHeaders headers, Expense expense) {
    	try {
    		
    		Gson gson = new Gson();
			InputStream inputStream = GatewayApp.class.getResourceAsStream("/application.yml");

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			
			RequestBody body = RequestBody.create(
					okhttp3.MediaType.parse("application/json"), gson.toJson(expense));
			
			okhttp3.Request.Builder requestBuilder = new Request.Builder()
				      .url("http://localhost:"+data.get("expense-service-port")+"/Expense-Service/app/expense" + "/postExpense");
				      
			for(Entry<String, List<String>> entry : headers.getRequestHeaders().entrySet()) {
				requestBuilder = 	requestBuilder.addHeader(entry.getKey(), entry.getValue().stream()
	                       .map(Object::toString)
	                       .collect(Collectors.joining(", ")));
			}
			
			okhttp3.Request request = requestBuilder.post(body).build();
			
			OkHttpClient client = new OkHttpClient();
				    Call call = client.newCall(request);
			okhttp3.Response response = call.execute();
		    if(!response.isSuccessful()) {
		  
		    	throw new Exception(response.body().string());
		    }
    		
    	}catch (Exception ex) {
    		System.out.println("Exception occurred while posting expense :" + ex.getMessage());
    		return Response.serverError().entity(ex.getMessage()).build();
    	}
    	return Response.ok("EXPENSE POSTED").build();
    }
    
    @SuppressWarnings("deprecation")
	@POST
    @Path("/postSplitExpense")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response postExpenseSplit(@Context HttpHeaders headers, Expense expense) {
    	try {
    		
    		Gson gson = new Gson();
			InputStream inputStream = GatewayApp.class.getResourceAsStream("/application.yml");

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			
			RequestBody body = RequestBody.create(
					okhttp3.MediaType.parse("application/json"), gson.toJson(expense));
			
			okhttp3.Request.Builder requestBuilder = new Request.Builder()
				      .url("http://localhost:"+data.get("expense-service-port")+"/Expense-Service/app/expense" + "/postSplitExpense");
				      
			for(Entry<String, List<String>> entry : headers.getRequestHeaders().entrySet()) {
				requestBuilder = 	requestBuilder.addHeader(entry.getKey(), entry.getValue().stream()
	                       .map(Object::toString)
	                       .collect(Collectors.joining(", ")));
			}
			
			okhttp3.Request request = requestBuilder.post(body).build();
			
			OkHttpClient client = new OkHttpClient();
				    Call call = client.newCall(request);
			okhttp3.Response response = call.execute();
		    if(!response.isSuccessful()) {
		  
		    	throw new Exception(response.body().string());
		    }
		    
    	}catch (Exception ex) {
    		System.out.println("Exception occurred while posting expense :" + ex.getMessage());
    		return Response.serverError().entity(ex.getMessage()).build();
    	}
    	return Response.ok("SPLIT EXPENSE POSTED").build();
    }
    
    
    @SuppressWarnings("deprecation")
	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(@Context HttpHeaders headers ,User user) {
		try {
			
			Gson gson = new Gson();
			InputStream inputStream = GatewayApp.class.getResourceAsStream("/application.yml");

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			
			RequestBody body = RequestBody.create(
					okhttp3.MediaType.parse("application/json"), gson.toJson(user));
			
			okhttp3.Request.Builder requestBuilder = new Request.Builder()
				      .url("http://localhost:"+data.get("user-service-port")+"/User-Service/app/user" + "/register");
				      
			for(Entry<String, List<String>> entry : headers.getRequestHeaders().entrySet()) {
				requestBuilder = 	requestBuilder.addHeader(entry.getKey(), entry.getValue().stream()
	                       .map(Object::toString)
	                       .collect(Collectors.joining(", ")));
			}
			
			okhttp3.Request request = requestBuilder.post(body).build();
			
			OkHttpClient client = new OkHttpClient();
				    Call call = client.newCall(request);
			okhttp3.Response response = call.execute();
		    if(!response.isSuccessful()) {
		  
		    	throw new Exception(response.body().string());
		    }
			
		}catch(Exception ex) {
			System.out.println("Exception occurred while registering new user :" + ex.getMessage());
			ex.printStackTrace();
     		return Response.serverError().entity(ex.getMessage()).build();
		}
		return Response.ok("USER CREATED").build();
	}
	
	
	@SuppressWarnings("deprecation")
	@Path("/reset")
	@GET
	public Response resetUserCredentials(@QueryParam("username") String username) {
		try {
			InputStream inputStream = Auth.class.getResourceAsStream("/application.yml");

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			HttpUrl.Builder urlBuilder 
		      = HttpUrl.parse("http://localhost:"+data.get("user-service-port")+"/User-Service/app/user/reset").newBuilder();
		    urlBuilder.addQueryParameter("username", username);
		    
			okhttp3.Request.Builder requestBuilder = new Request.Builder()
				      .url(urlBuilder.build().toString());
			System.out.println("here gateway");
			
			okhttp3.Request httpRequest = requestBuilder.build();
			
			OkHttpClient client = new OkHttpClient();
			Call call = client.newCall(httpRequest);
			okhttp3.Response response = call.execute();
			if(!response.isSuccessful()) {
				throw new Exception(response.body().string());
			}
		}catch(Exception ex) {
			System.out.println("Exception occurred while registering new user :" + ex.getMessage());
			ex.printStackTrace();
     		return Response.serverError().entity(ex.getMessage()).build();
		}
		return Response.ok("OTP SENT").build();
	}
	
	@SuppressWarnings("deprecation")
	@Path("/addFriend")
	@POST
	public Response addUserToNetwork(@Context HttpHeaders headers , @QueryParam("username") String username){
		try {
			
			
			Gson gson = new Gson();
			InputStream inputStream = GatewayApp.class.getResourceAsStream("/application.yml");

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			HttpUrl.Builder urlBuilder 
		      = HttpUrl.parse("http://localhost:"+data.get("user-service-port")+"/User-Service/app/user/addFriend").newBuilder();
		    urlBuilder.addQueryParameter("username", username);
			
			RequestBody body = RequestBody.create(
					okhttp3.MediaType.parse("application/json"), "");
			
			okhttp3.Request.Builder requestBuilder = new Request.Builder()
				      .url(urlBuilder.build().toString());
				      
			for(Entry<String, List<String>> entry : headers.getRequestHeaders().entrySet()) {
				requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue().stream()
	                       .map(Object::toString)
	                       .collect(Collectors.joining(", ")));
			}
			
			okhttp3.Request request = requestBuilder.post(body).build();
			
			OkHttpClient client = new OkHttpClient();
				    Call call = client.newCall(request);
			okhttp3.Response response = call.execute();
		    if(!response.isSuccessful()) {
		  
		    	throw new Exception(response.body().string());
		    }

		} catch (Exception ex) {
			return Response.serverError().entity(ex.getMessage()).build();
		}
		return Response.ok("FRIEND ADDED").build();

	}
	
	@Path("/getAllFriends")
	@GET
	public Response getAllFriends(@Context HttpHeaders headers) {
		List<Contact> contactList = new ArrayList<Contact>();
		try {
			
			Gson gson = new Gson();
			InputStream inputStream = GatewayApp.class.getResourceAsStream("/application.yml");

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			
			okhttp3.Request.Builder requestBuilder = new Request.Builder()
				      .url("http://localhost:"+data.get("user-service-port")+"/User-Service/app/user" + "/getAllFriends");
				      
			for(Entry<String, List<String>> entry : headers.getRequestHeaders().entrySet()) {
				requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue().stream()
	                       .map(Object::toString)
	                       .collect(Collectors.joining(", ")));
			}
			
			okhttp3.Request request = requestBuilder.build();
			Type typeMyType = new TypeToken<List<Contact>>() {
			}.getType();
			OkHttpClient client = new OkHttpClient();
				    Call call = client.newCall(request);
			okhttp3.Response response = call.execute();
		    if(response.isSuccessful()) {
		    	
		    	contactList = 	gson.fromJson(response.body().string(), typeMyType);
		    	
		    	System.out.println("users here all contact size : "+contactList.size());
		    }
			
		}catch(Exception ex) {
			return Response.serverError().entity(ex.getMessage()).build();
		}
		return Response.ok(contactList).build();
		
	}

	@Path("/userProfile")
	@GET
	public Response getUserProfile(@Context HttpHeaders headers) {
		Map<String, String> userInfo = new HashMap<>();
		try {
			Gson gson = new Gson();
			InputStream inputStream = GatewayApp.class.getResourceAsStream("/application.yml");

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			
			okhttp3.Request.Builder requestBuilder = new Request.Builder()
				      .url("http://localhost:"+data.get("user-service-port")+"/User-Service/app/user" + "/userProfile");
				      
			for(Entry<String, List<String>> entry : headers.getRequestHeaders().entrySet()) {
				requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue().stream()
	                       .map(Object::toString)
	                       .collect(Collectors.joining(", ")));
			}
			
			okhttp3.Request request = requestBuilder.build();
			Type typeMyType = new TypeToken<Map<String, String>>() {
			}.getType();
			OkHttpClient client = new OkHttpClient();
				    Call call = client.newCall(request);
			okhttp3.Response response = call.execute();
		    if(response.isSuccessful()) {
		    	
		    	userInfo = 	gson.fromJson(response.body().string(), typeMyType);
		    	
		    	System.out.println("users here all user profile size : "+userInfo.size());
		    }
			
			
		}
		catch (Exception ex) {
			return Response.serverError().entity(ex.getMessage()).build();
		}
		return Response.ok(userInfo).build();
	}
	
	@SuppressWarnings("deprecation")
	@Path("/group/create")
	@POST
	public Response createGroup(@Context HttpHeaders headers,Group group) {
		try {
			Gson gson = new Gson();
			InputStream inputStream = GatewayApp.class.getResourceAsStream("/application.yml");

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			
			RequestBody body = RequestBody.create(
					okhttp3.MediaType.parse("application/json"), gson.toJson(group));
			
			okhttp3.Request.Builder requestBuilder = new Request.Builder()
				      .url("http://localhost:"+data.get("user-service-port")+"/User-Service/app/user" + "/group/create");
				      
			for(Entry<String, List<String>> entry : headers.getRequestHeaders().entrySet()) {
				requestBuilder = 	requestBuilder.addHeader(entry.getKey(), entry.getValue().stream()
	                       .map(Object::toString)
	                       .collect(Collectors.joining(", ")));
			}
			
			okhttp3.Request request = requestBuilder.post(body).build();
			
			OkHttpClient client = new OkHttpClient();
				    Call call = client.newCall(request);
			okhttp3.Response response = call.execute();
		    if(!response.isSuccessful()) {
		  
		    	throw new Exception(response.body().string());
		    }
			
		}catch(Exception ex){
			return Response.serverError().entity(ex.getMessage()).build();
		}
		return Response.ok("Group Created").build();
	}
	
	@Path("/group/all")
	@GET
	public Response getAllGroups(@Context HttpHeaders headers) {
		List<Group> groupList = new ArrayList<Group>();
		try {
			Gson gson = new Gson();
			InputStream inputStream = GatewayApp.class.getResourceAsStream("/application.yml");

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			
			okhttp3.Request.Builder requestBuilder = new Request.Builder()
				      .url("http://localhost:"+data.get("user-service-port")+"/User-Service/app/user" + "/group/all");
				      
			for(Entry<String, List<String>> entry : headers.getRequestHeaders().entrySet()) {
				requestBuilder = requestBuilder.addHeader(entry.getKey(), entry.getValue().stream()
	                       .map(Object::toString)
	                       .collect(Collectors.joining(", ")));
			}
			
			okhttp3.Request request = requestBuilder.build();
			Type typeMyType = new TypeToken<List<Group>>() {
			}.getType();
			OkHttpClient client = new OkHttpClient();
				    Call call = client.newCall(request);
			okhttp3.Response response = call.execute();
		    if(response.isSuccessful()) {
		    	
		    	groupList = 	gson.fromJson(response.body().string(), typeMyType);
		    	
		    	System.out.println("users here all group size : "+groupList.size());
		    }
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
			return Response.serverError().entity(ex.getMessage()).build();
		}
		return Response.ok(groupList).build();
	}
	

}
