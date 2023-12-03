package org.user.demo.api;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.StringTokenizer;
//import org.glassfish.jersey.internal.util.Base64;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/*
 * filter for administration class(which used for search )
 */

@Provider
public class Authentication implements ContainerRequestFilter{

	public void filter(ContainerRequestContext request) throws IOException {
		// TODO Auto-generated method stub
		
		if(request.getUriInfo().getPath().contains("admin")) {
			
			List<String> auth = request.getHeaders().get("Authorization");
			
			Response AuthStatus = Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized to login").build();
			if(auth!=null) {
				String au = auth.get(0);
				au= au.replaceFirst("Basic","");
				Decoder d = Base64.getMimeDecoder();
				byte[]  au1 = d.decode(au);
				String au2 =new String(au1);
				
				
				StringTokenizer st = new StringTokenizer(au2, ":");
				String userId = st.nextToken();
				String password =st.nextToken();
				
				try {
			       	 Class.forName("com.mysql.cj.jdbc.Driver");
			       	 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC","surya","lingam1998");
			         String line = "select passwd from user where username = ?";
			         PreparedStatement stmt = conn.prepareStatement(line,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			        stmt.setString(1, userId);	
			         
			         
			         ResultSet re = stmt.executeQuery();
			         
			       	 
			       	 if( re.next() &&  password.equals(re.getString("passwd")))
			       	 {
			       		return;
			       	 }
			       	 else 
			       	 {
			       			request.abortWith(AuthStatus);
			       			 	       		 
			       		
			       	 }
			        }
			        
			        catch(ClassNotFoundException e)
			        {
			       	 e.printStackTrace();
			        }
			        catch(SQLException e1)
			        {
			       	 e1.printStackTrace();
			        }
			
			  	
			}
			else
			request.abortWith(AuthStatus);
			
			
			
		}
		
		
	}	

}
