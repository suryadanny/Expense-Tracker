package org.expense.demo.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.expense.demo.model.Group;

public class GroupRepository {
	
	public void createGroup(Group group) throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
					"lingam1998");
			String sql = "select max(group_id) from split_group";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet result = stmt.executeQuery();
			if(result.next()) {
				int groupId = result.getInt(1);
				groupId+= groupId<0? 2:1;
				sql = "Insert into split_group(group_id,group_name,user_id) values (?,?,?)";
				PreparedStatement insertToNetwork = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				
				for(Integer userId : group.getUserIdList()) {
				   insertToNetwork.setInt(1,groupId);
				   insertToNetwork.setString(2,group.getGroupName());
				   insertToNetwork.setInt(3, userId);
				   int rowsAffected = insertToNetwork.executeUpdate();
				}
				 stmt.close();
				 insertToNetwork.close();
			}
		}catch (SQLException e) {
			System.out.println("Exception occurred while getting user :" + e.getMessage());
			throw e;
		} catch (ClassNotFoundException ex) {
			System.out.println("Exception occurred while getting user :" + ex.getMessage());
			throw ex;
		}
	}

}
