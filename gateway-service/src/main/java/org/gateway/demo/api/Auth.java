package org.gateway.demo.api;

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
import javax.ws.rs.core.Response;

import org.gateway.demo.model.User;
import org.gateway.demo.repo.UserRepository;

/**
 * Servlet implementation class Auth
 */

// servlet for login page
@WebServlet("/loginauth")
public class Auth extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserRepository userRepo = new UserRepository();
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
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		try {
			System.out.println("here");
			User user = userRepo.getUser(username);

			if (user != null && password.equals(user.getPassword())) {
				// chain.doFilter(request, response);
				Cookie User = new Cookie("userId", String.valueOf(user.getId()));
				Cookie pass = new Cookie("password", password);
				User.setMaxAge(30);
				pass.setMaxAge(30);
				response.addCookie(User);
				response.addCookie(pass);
				//below link to redirect  to main home screen
				//response.sendRedirect("http://localhost:8888/visitor_api/admin.html");
				// out.println("loggedin");
			} else {
				response.sendRedirect("http://localhost:8888/expense_api/Login.html");
			}
		} catch (Exception ex) {
			System.out.println("Exception occurred while getting user :" + ex.getMessage());
			ex.printStackTrace();
			response.setStatus(500);
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
