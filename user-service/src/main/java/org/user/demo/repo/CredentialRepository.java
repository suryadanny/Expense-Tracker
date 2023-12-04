package org.user.demo.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CredentialRepository {
	
	private String connString = "jdbc:mysql://ug1iaxxhozdrtr3n:IG8KKZrv343C05y32aWw@bixa0j57zr2btgekt46m-mysql.services.clever-cloud.com:3306/bixa0j57zr2btgekt46m";
    private String dbusername = "ug1iaxxhozdrtr3n";
    private String dbpassword = "IG8KKZrv343C05y32aWw";
    
     
	public void insertOTP(Integer userId, String OTP) throws Exception {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(connString, dbusername, dbpassword);

			String sql = "select * from credential where user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1, userId);
			ResultSet result = stmt.executeQuery();
			if (!result.next()) {
				stmt.close();
				sql = "insert into credential (user_id,otp ) values(?,?)";
				stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				stmt.setInt(1, userId);
				stmt.setString(2, OTP);
				stmt.executeUpdate();
				stmt.close();
			} else {
				stmt.close();
				sql = "update credential set otp = ? where user_id = ?";
				stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				stmt.setInt(2, userId);
				stmt.setString(1, OTP);
				stmt.executeUpdate();
				stmt.close();
			}
		    
		} catch (SQLException e) {
			System.out.println("Exception occurred while storing expense :" + e.getMessage());
			throw e;
		} catch (ClassNotFoundException ex) {
			System.out.println("Exception occurred while storing expense :" + ex.getMessage());
			throw ex;
		}
     }
}
