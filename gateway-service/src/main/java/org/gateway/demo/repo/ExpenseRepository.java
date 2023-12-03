package org.gateway.demo.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.gateway.demo.model.Expense;

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
}
