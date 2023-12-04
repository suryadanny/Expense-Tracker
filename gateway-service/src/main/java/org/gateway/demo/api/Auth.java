package org.gateway.demo.api;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.gateway.demo.model.Expense;
import org.gateway.demo.model.User;
import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Servlet implementation class Auth
 */

// servlet for login page
@WebServlet("/loginauth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	  /**
     * Default constructor. 
     */
    public Auth() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//
//		try {
//			System.out.println("here");
//			User user = userRepo.getUser(username);
//
//			if (user != null && password.equals(user.getPassword())) {
//				// chain.doFilter(request, response);
//				Cookie User = new Cookie("userId", String.valueOf(user.getId()));
//				Cookie pass = new Cookie("password", password);
//				User.setMaxAge(30);
//				pass.setMaxAge(30);
//				response.addCookie(User);
//				response.addCookie(pass);
//				
//			} else {
//				response.sendRedirect("http://localhost:8888/expense_api/Login.html");
//			}
//		} catch (Exception ex) {
//			System.out.println("Exception occurred while getting user :" + ex.getMessage());
//			ex.printStackTrace();
//			response.setStatus(500);
//		}
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		InputStream inputStream = Auth.class.getResourceAsStream("/application.yml");

		Yaml yaml = new Yaml();
		Map<String, Object> data = yaml.load(inputStream);
		HttpUrl.Builder urlBuilder 
	      = HttpUrl.parse("http://localhost:"+data.get("user-service-port")+"/User-Service/loginauth").newBuilder();
	    urlBuilder.addQueryParameter("username", request.getParameter("username"));
	    urlBuilder.addQueryParameter("password", request.getParameter("password"));
	    
		okhttp3.Request.Builder requestBuilder = new Request.Builder()
			      .url(urlBuilder.build().toString()).post(RequestBody.create(
					      MediaType.parse("application/json"), ""));
		System.out.println("here gateway");
		
		okhttp3.Request httpRequest = requestBuilder.build();
		
		OkHttpClient client = new OkHttpClient();
		Call call = client.newCall(httpRequest);
		okhttp3.Response httpResponse = call.execute();
	    if(httpResponse.isSuccessful()) {
	    	
	    	Cookie User= new Cookie("userId", "");
			Cookie pass= new Cookie("password", ""); 
			for(String cookie : httpResponse.headers().values("Set-Cookie")) {
				if(cookie.contains("userId")) {
				   User =  new Cookie("userId", cookie.split(";")[0].split("=")[1]);
				}else {
					pass =  new Cookie("password", cookie.split(";")[0].split("=")[1]);
				}
			}
			User.setMaxAge(30);
			pass.setMaxAge(30);
			response.addCookie(User);
			response.addCookie(pass);
	    	
	    	System.out.println("users here all cookie set ");
	    }
	}

}
