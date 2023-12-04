package org.user.demo.repo;

import java.io.File;
import java.io.FileInputStream;
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
import org.user.demo.model.Group;
import org.user.demo.model.GroupId;
import org.user.demo.utility.Utility;
import org.yaml.snakeyaml.Yaml;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GroupRepository {
	
	
	public void createGroup(Group group) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
				"lingam1998");
		conn.setAutoCommit(false);
		try {
			
			String sql = "select max(group_id) from split_group";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet result = stmt.executeQuery();
			if(result.next()) {
				int groupId = result.getInt(1);
				groupId+= groupId<0? 2:1;
				
				if(group.getGroupId() != null ) {
					groupId = group.getGroupId();
				}
				sql = "Insert into split_group(group_id,group_name,user_id) values (?,?,?)";
				PreparedStatement insertToNetwork = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				
				for(Integer userId : group.getUserIdList()) {
				   insertToNetwork.setInt(1,groupId);
				   insertToNetwork.setString(2,group.getGroupName());
				   insertToNetwork.setInt(3, userId);
				   int rowsAffected = insertToNetwork.executeUpdate();
				}
				conn.commit();
				 stmt.close();
				 insertToNetwork.close();
			}
		}catch (SQLException e) {
			System.out.println("Exception occurred while getting user :" + e.getMessage());
			conn.rollback();
			throw e;
		}
	}
	
	
	public List<Group> getAllGroups(Integer userId)throws Exception{
		Map<Integer,Group> groupMap = new HashMap<Integer,Group>();
		List<Group> groupList = new ArrayList<Group>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "root",
					"lingam1998");
			String sql = "select *  from split_group where group_id in ( select group_id from split_group where user_id = ?)";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1,userId);
			ResultSet result = stmt.executeQuery();
			int groupId =-1;
			
			Map<Integer,Double> amountPerUserSplit = amountOwedPerGrp(userId);
			Map<Integer,Double> spendPerGrp = totalSpendPerGrp(getGroupIdsForUser(userId),userId);
			while(result.next()) {
				
				 groupId = result.getInt("group_id");
				 if(groupMap.containsKey(groupId)) {
					 groupMap.get(groupId).getUserIdList().add(result.getInt("user_id"));
				 }else {
					 Group group = new Group();
					 group.setGroupId(groupId);
					 List<Integer> userIdList = new ArrayList<Integer>();
					 group.setGroupName(result.getString("group_name"));
					 userIdList.add(result.getInt("user_id"));
					 group.setAmount(amountPerUserSplit.getOrDefault(groupId, 0.0));
					 group.setTotalGroupSpend(spendPerGrp.getOrDefault(groupId, 0.0));
					 group.setUserIdList(userIdList);
					 groupMap.put(groupId, group);
				 }
				
			}
		}catch (SQLException e) {
			System.out.println("Exception occurred while getting user :" + e.getMessage());
			throw e;
		} catch (ClassNotFoundException ex) {
			System.out.println("Exception occurred while getting user :" + ex.getMessage());
			throw ex;
		}
		groupList.addAll(groupMap.values());
		return groupList ;
		
		
	}
	
	public List<Integer> getGroupIdsForUser(Integer userId){
		List<Integer> groupIds = new ArrayList<Integer>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
					"lingam1998");

			String groupIdForUser = "select group_id from split_group where user_id = ?";

			PreparedStatement groupIdQuery = conn.prepareStatement(groupIdForUser, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			groupIdQuery.setInt(1, userId);
			ResultSet result = groupIdQuery.executeQuery();

			while (result.next()) {
				groupIds.add(result.getInt(1));

			}
			result.close();
			groupIdQuery.close();

		} catch (Exception ex) {
			System.out.println("Error occurred while calculating per user expense split");
		}
		return groupIds;
	}
	
	public Map<Integer,Double> amountOwedPerGrp(Integer userId){
			Map<Integer,Double> amtPerGrp = new HashMap<Integer,Double>();
			try {
				Gson gson = new Gson();
				InputStream inputStream = GroupRepository.class.getResourceAsStream("/application.yml");

				Yaml yaml = new Yaml();
				Map<String, Object> data = yaml.load(inputStream);
				
				Request request = new Request.Builder()
					      .url("http://localhost:"+data.get("expense-service-port")+"/Expense-Service/app/expense" + "/amountOwedPerGrp")
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
			    	amtPerGrp = 	gson.fromJson(response.body().string(), typeMyType);
			    	System.out.println("users here amtPerGrp size : "+amtPerGrp.size());
			    }
				
				
			}catch(Exception ex) {
				System.out.println("Error occurred while calculating per user expense split");
			}
			return amtPerGrp;
		}
	

		@SuppressWarnings("deprecation")
		public Map<Integer, Double> totalSpendPerGrp(List<Integer> groupIds, Integer userId) {
			Map<Integer, Double> spendPerGrp = new HashMap<Integer, Double>();
			try {
				//System.out.println(environment.get("spring.application.name"));
				InputStream inputStream = GroupRepository.class.getResourceAsStream("/application.yml");

				Yaml yaml = new Yaml();
				Map<String, Object> data = yaml.load(inputStream);
				
				System.out.println("expense port : "+data.get("expense-service-port"));
				Gson gson = new Gson();
				GroupId groupIdList = new GroupId();
				groupIdList.setGroupIds(groupIds);
				RequestBody body = RequestBody.create(
					      MediaType.parse("application/json"), gson.toJson(groupIdList));
				
				Request request = new Request.Builder()
					      .url("http://localhost:"+data.get("expense-service-port")+"/Expense-Service/app/expense" + "/totalSpendPerGrp")
					      .addHeader("Authorization", Utility.getBase64Encoded(userId.toString()+":"))
					      .addHeader("Accept", "*/*")
					      .addHeader("Content-Type", "application/json")
					      .post(body)
					      .build();
				Type typeMyType = new TypeToken<HashMap<Integer, Double>>() {
				}.getType();
				OkHttpClient client = new OkHttpClient();
			    Call call = client.newCall(request);
			    Response response = call.execute();
			    if(response.isSuccessful()) {
			    	spendPerGrp = 	gson.fromJson(response.body().string(), typeMyType);
			        System.out.println("users here totalSpendPerGrp size : "+spendPerGrp.size());
			    }
				
				
			} catch (Exception ex) {
				System.out.println("Error occurred while calculating per user expense split"+ ex.getMessage());
			}
			return spendPerGrp;
		}
}



