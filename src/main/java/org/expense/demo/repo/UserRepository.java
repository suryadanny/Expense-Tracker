package org.expense.demo.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.expense.demo.model.User;

public class UserRepository {

	public void Register(User user) throws Exception{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
					"lingam1998");
			String sql = "insert into users (username, name , email ,mobile ,password) values(?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1,user.getUsername());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getMobile());
			stmt.setString(5, user.getPassword());
			stmt.executeUpdate();
		    stmt.close();
		    
		} catch (SQLException e) {
			System.out.println("Exception occurred while storing expense :" + e.getMessage());
			throw e;
		} catch (ClassNotFoundException ex) {
			System.out.println("Exception occurred while storing expense :" + ex.getMessage());
			throw ex;
		}
	}
	
	public User getUser(String username) throws Exception{
		User user = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
					"lingam1998");
			String sql = "select * from users where username = ?";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1,username);
			ResultSet result = stmt.executeQuery();
			if(result.next()) {
				user = new User();
				user.setId(result.getInt("id"));
				user.setPassword(result.getString("password"));
				user.setEmail(result.getString("email"));
				user.setUsername(username);
				user.setMobile(result.getString("mobile"));
			}
		    stmt.close();
		    
		} catch (SQLException e) {
			System.out.println("Exception occurred while getting user :" + e.getMessage());
			throw e;
		} catch (ClassNotFoundException ex) {
			System.out.println("Exception occurred while getting user :" + ex.getMessage());
			throw ex;
		}
		
		return user;
	}
}
