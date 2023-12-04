package org.expense.demo.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.expense.demo.model.Expense;

public class ExpenseRepository {

	public void addExpense(Expense expense) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
				"lingam1998");
		conn.setAutoCommit(false);
		try
		{
			
			String st = "insert into expenses"
					+ " (title, notes,category, amount,currency,txn_dttm,payment_mode,user_id, owing_user_id,group_id)"
					+ "values(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(st, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			int splitSize = 1;
			if(expense.getOwingUserId() != null && expense.getOwingUserId().size() >0) {
				splitSize = expense.getOwingUserId().size();
			}
		if(expense.getOwingUserId() != null && !expense.getOwingUserId().isEmpty() ) {		
          for(Integer owingUserId : expense.getOwingUserId()) {
			stmt.setString(1, expense.getTitle());
			stmt.setString(2, expense.getNote());
			stmt.setString(3, expense.getCategory());
			stmt.setDouble(4, expense.getAmount()/splitSize);
			stmt.setString(5, expense.getCurrency());
			stmt.setTimestamp(6, expense.getTrans_dttm());
			stmt.setString(7, expense.getPaymentMode());
			stmt.setInt(8, expense.getOwedUserId());
			
			stmt.setObject(9, owingUserId);
			stmt.setObject(10, expense.getGroupId());
			stmt.executeUpdate();
		   }
		}else {
			stmt.setString(1, expense.getTitle());
			stmt.setString(2, expense.getNote());
			stmt.setString(3, expense.getCategory());
			stmt.setDouble(4, expense.getAmount()/splitSize);
			stmt.setString(5, expense.getCurrency());
			stmt.setTimestamp(6, expense.getTrans_dttm());
			stmt.setString(7, expense.getPaymentMode());
			stmt.setInt(8, expense.getOwedUserId());
			stmt.setObject(9, null);
			stmt.setObject(10, expense.getGroupId());
			stmt.executeUpdate();
		}
          conn.commit();

		}
		catch(SQLException e)
		{
			System.out.println("Exception occurred while storing expense :" + e.getMessage());
			conn.rollback();
			throw e;
		}
		
		
		
	}
	
	
	public List<Expense> getAllExpenses(int userId) throws Exception {
		List<Expense> expenses = new ArrayList<Expense>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
					"lingam1998");
			String sql = "select * from expenses where user_id = ? ";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1, userId);
			ResultSet result = stmt.executeQuery();
			
			while (result.next()) {
				Expense expense = new Expense();
				expense.setExpenseId(result.getInt("id"));
                expense.setTitle(result.getString("title"));
                expense.setNote(result.getString("notes"));
                expense.setCategory(result.getString("category"));
                expense.setAmount(result.getDouble("amount"));
                expense.setCurrency(result.getString("currency"));
                expense.setTrans_dttm(result.getTimestamp("txn_dttm"));
                expense.setPaymentMode(result.getString("payment_mode"));
                expense.setOwedUserId(result.getInt("user_id"));
                List<Integer> userIdList = new ArrayList<Integer>();
                userIdList.add(result.getInt("owing_user_id"));
                expense.setOwingUserId(userIdList);
                expense.setGroupId(result.getInt("group_id"));
                expenses.add(expense);
			}
		} catch(SQLException e)
		{
			System.out.println("Exception occurred while storing expense :" + e.getMessage());
			throw e;
		}
		catch(ClassNotFoundException ex)
		{
			System.out.println("Exception occurred while storing expense :" + ex.getMessage());
			throw ex;
		}
		return expenses;

	}
	
	

	public Map<Integer,Double> amountOwedPerGrp(Integer userId){
			Map<Integer,Double> amtPerGrp = new HashMap<Integer,Double>();
			try {
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
	
	public Map<Integer,Double> amountOwedByUser(Integer userId){
		Map<Integer,Double> amtPerUser = new HashMap<Integer,Double>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
					"lingam1998");
			
			String amountCalcSql = "select owing_user_id as id, sum(amount) as owed from (\r\n"
					+ "select  owing_user_id , sum(amount) as amount from expenses where user_id = ? and owing_user_id is not null group by Owing_User_id\r\n"
					+ "union  \r\n"
					+ "select  user_id , sum(-amount) as amount from expenses where owing_user_id = ? and owing_user_id is not null group by user_id) agg group by owing_user_id\r\n"
					+ ""; 
			
			PreparedStatement amtCal = conn.prepareStatement(amountCalcSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			amtCal.setInt(1, userId);
			amtCal.setInt(2, userId);
			ResultSet result = amtCal.executeQuery();
			
			while(result.next()) {
				amtPerUser.put(result.getInt(1), result.getDouble(2));
				
			}
			result.close();
			amtCal.close();
			
		}catch(Exception ex) {
			System.out.println("Error occurred while calculating per user expense split");
		}
		return amtPerUser;
	}
	

		public Map<Integer, Double> totalSpendPerGrp(List<Integer> userIds) {
			Map<Integer, Double> spendPerGrp = new HashMap<Integer, Double>();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
						"lingam1998");

				String amountCalcSql = "select group_id , sum(amount) from expenses where group_id in (";
				for(int i = 0; i< userIds.size()-1;i++) {
					amountCalcSql += "?, ";
				}
				
				amountCalcSql += "? ) group by group_id";

				PreparedStatement amtCal = conn.prepareStatement(amountCalcSql, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				for(int i = 0; i < userIds.size();i++) {
				    amtCal.setInt(i+1, userIds.get(i));
				}
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
