package org.user.demo.repo;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.user.demo.model.Contact;
import org.user.demo.model.User;
import org.user.demo.utility.Utility;
import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserRepository {

	public void Register(User user) throws Exception{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "root",
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
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "root",
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

	public User getUser(int userID) throws Exception{
		User user = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "root",
					"lingam1998");
			String sql = "select * from users where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1,userID);
			ResultSet result = stmt.executeQuery();
			if(result.next()) {
				user = new User();
				user.setId(userID);
				user.setPassword(result.getString("password"));
				user.setEmail(result.getString("email"));
				user.setUsername(result.getString("username"));
				user.setMobile(result.getString("mobile"));
				user.setName(result.getString("name"));
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
	
	public void addFriend(Integer userId , String friendUsername)throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "root",
					"lingam1998");
			String sql = "select * from users where username = ?";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1,friendUsername);
			ResultSet result = stmt.executeQuery();
			if(result.next()) {
				int friendUserId = result.getInt("id");
				sql = "Insert into network(user_id,conn_user_id) values (?,?)";
				PreparedStatement insertToNetwork = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				insertToNetwork.setInt(1,userId);
				insertToNetwork.setInt(2,friendUserId);
				 int rowsAffected = insertToNetwork.executeUpdate();
				 stmt.close();
				 insertToNetwork.close();
			}else {
				throw new Exception("Contact is not found");
			}
		}catch (SQLException e) {
			System.out.println("Exception occurred while getting user :" + e.getMessage());
			throw e;
		} catch (ClassNotFoundException ex) {
			System.out.println("Exception occurred while getting user :" + ex.getMessage());
			throw ex;
		}
	}
	
	
	public List<Contact> getAllFriends(Integer userId)throws Exception {
		List<Contact> contactList = new ArrayList<Contact>();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "root",
					"lingam1998");
			String sql = "select * from network where user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1,userId);
			ResultSet result = stmt.executeQuery();
			int friendUserId =-1;
			Map<Integer,Double> amountPerUserSplit = amountOwedByUser(userId);
			while(result.next()) {
				 friendUserId = result.getInt("conn_user_id");
				sql = "select * from users where id = ?";
				
				
				PreparedStatement getUser = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				//insetToNetwork.setInt(1,userId);
				getUser.setInt(1,friendUserId);
				ResultSet friend = getUser.executeQuery();
			    if(friend.next()) {
					Contact contact  = new Contact();
					contact.setId(friend.getInt("id")).setUsername(friend.getString("username"))
							.setName(friend.getString("name")).setEmail(friend.getString("email"))
							.setMobile(friend.getString("mobile")).setAmountOwed(amountPerUserSplit.getOrDefault(contact.getId(), 0.0));
					contactList.add(contact);
					
				}
			}
		}catch (SQLException e) {
			System.out.println("Exception occurred while getting user :" + e.getMessage());
			throw e;
		} catch (ClassNotFoundException ex) {
			System.out.println("Exception occurred while getting user :" + ex.getMessage());
			throw ex;
		}
		return contactList;
	}
	
	
	public Map<Integer,Double> amountOwedByUser(Integer userId){
		Map<Integer,Double> amtPerUser = new HashMap<Integer,Double>();
		try {
			Gson gson = new Gson();
			InputStream inputStream = GroupRepository.class.getResourceAsStream("/application.yml");

			Yaml yaml = new Yaml();
			Map<String, Object> data = yaml.load(inputStream);
			
			Request request = new Request.Builder()
				      .url("http://localhost:"+data.get("expense-service-port")+"/Expense-Service/app/expense" + "/amountOwedByUser")
				      .addHeader("Authorization", Utility.getBase64Encoded(userId.toString()+":"))
				      .addHeader("Accept", "*/*")
				      .addHeader("Content-Type", "application/json")
				      .build();
			Type typeMyType = new TypeToken<HashMap<Integer, Double>>() {
			}.getType();
			OkHttpClient client = new OkHttpClient();
				    Call call = client.newCall(request);
		    Response response = call.execute();
		    if(response.isSuccessful()) {
		    	amtPerUser = 	gson.fromJson(response.body().string(), typeMyType);
		    	System.out.println("users here amtPerUser size : "+amtPerUser.size());
		    }
			
			
		}catch(Exception ex) {
			System.out.println("Error occurred while calculating per user expense split");
		}
		return amtPerUser;
	}
}
