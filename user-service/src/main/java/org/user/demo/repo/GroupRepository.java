package org.user.demo.repo;

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
import org.user.demo.utility.Utility;

import okhttp3.Call;
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
			Map<Integer,Double> spendPerGrp = totalSpendPerGrp(userId);
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
				
				
				Request request = new Request.Builder()
					      .url("http://localhost:8080/Expense-Sevice/app" + "/amountOwedPerGrp")
					      .addHeader("Authorization", Utility.getBase64Encoded(userId.toString()+":"))
					      .addHeader("Accept", "*/*")
					      .addHeader("Content-Type", "application/json")
					      .build();
				OkHttpClient client = new OkHttpClient();
					    Call call = client.newCall(request);
			    Response response = call.execute();
			    if(response.isSuccessful()) {
			    	response.body().string();
			    }
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
						"lingam1998");
				
				String amountCalcSql = "select group_id as group_id, sum(amount) as owed from (\r\n"
						+ "select  group_id , sum(amount) as amount from expenses where user_id = ? and group_id is not null group by group_id\r\n"
						+ "union  \r\n"
						+ "select  group_id , sum(-amount) as amount from expenses where owing_user_id = ? and group_id is not null group by group_id) agg group by group_id\r\n"
						+ ""; 
				
				PreparedStatement amtCal = conn.prepareStatement(amountCalcSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				amtCal.setInt(1, userId);
				amtCal.setInt(2, userId);
				ResultSet result = amtCal.executeQuery();
				
				while(result.next()) {
					amtPerGrp.put(result.getInt(1), result.getDouble(2));
					
				}
				result.close();
				amtCal.close();
				
			}catch(Exception ex) {
				System.out.println("Error occurred while calculating per user expense split");
			}
			return amtPerGrp;
		}
	

		public Map<Integer, Double> totalSpendPerGrp(Integer userId) {
			Map<Integer, Double> spendPerGrp = new HashMap<Integer, Double>();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
						"lingam1998");

				String amountCalcSql = "select group_id , sum(amount) from expenses where group_id in (select group_id from split_group where user_id = ?) group by group_id";

				PreparedStatement amtCal = conn.prepareStatement(amountCalcSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				amtCal.setInt(1, userId);
				ResultSet result = amtCal.executeQuery();

				while (result.next()) {
					spendPerGrp.put(result.getInt(1), result.getDouble(2));

				}
				result.close();
				amtCal.close();

			} catch (Exception ex) {
				System.out.println("Error occurred while calculating per user expense split");
			}
			return spendPerGrp;
		}
}



