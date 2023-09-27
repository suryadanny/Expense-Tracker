package org.expense.demo.api;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
        String username = request.getParameter("usern");
        String password = request.getParameter("passwd");
        
        try {
       	 Class.forName("com.mysql.cj.jdbc.Driver");
       	 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC","E088679","Welcome@123");
         String st = "select passwd from administrator where username = ?";
         PreparedStatement stmt = conn.prepareStatement(st,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        stmt.setString(1, username);	
         
         
         ResultSet re = stmt.executeQuery();
         
       	 if( re.next() && password.equals(re.getString("passwd")) )
       	 {
       		// chain.doFilter(request, response);
       		 Cookie User = new Cookie("username",username);
       		 Cookie pass = new Cookie("password",password);
       		 User.setMaxAge(30);
       		 pass.setMaxAge(30);
       		 response.addCookie(User);
       		 response.addCookie(pass);
       		 response.sendRedirect("http://localhost:8888/visitor_api/admin.html");
       		// out.println("loggedin");
       	 }
       	 else 
       	 {
       		 if(re!= null )
       		 {
       			 re.last();
       			 
       		 }
       		 response.sendRedirect("http://localhost:8888/visitor_api/Login.html");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
