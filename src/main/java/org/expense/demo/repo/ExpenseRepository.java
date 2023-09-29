package org.expense.demo.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.expense.demo.model.Expense;

public class ExpenseRepository {

	public void addExpense(Expense expense) throws Exception {
		try
		{
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop","E088679","Welcome@123");
		    String st = "insert into expense values(null,?,?,?,?,now(),?)";
		    PreparedStatement stmt = conn.prepareStatement(st , ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		    
		    stmt.setString(1, expense.getTitle());
		    stmt.setString(2, expense.getNote());
		    stmt.setString(3,expense.getCategory());
		    stmt.setDouble(4, expense.getAmount());
		    stmt.setString(5, expense.getCurrency());
		    stmt.setString(6, expense.getPaymentMode());
		    stmt.setInt(7, expense.getUserId());
		    stmt.setInt(8, expense.getOwedUserId());
		    stmt.setInt(8, expense.getGroupId());
		    stmt.executeUpdate();
		    
		}
		catch(SQLException e)
		{
			System.out.println("Exception occurred while storing expense :" + e.getMessage());
			throw e;
		}
		catch(ClassNotFoundException ex)
		{
			System.out.println("Exception occurred while storing expense :" + ex.getMessage());
			throw ex;
		}
		
		
	}
	
	
	public List<Expense> getAllExpenses(int userId) throws Exception {
		List<Expense> expenses = new ArrayList<Expense>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/expense_tracker", "surya",
					"lingam1998");
			String sql = "select * from expense where user_id = ? ";
			PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stmt.setInt(1, userId);
			ResultSet result = stmt.executeQuery();
			
			while (result != null) {
				Expense expense = new Expense();
				expense.setExpenseId(result.getInt("id"));
                expense.setTitle(result.getString("title"));
                expense.setNote(result.getString("notes"));
                expense.setCategory(result.getString("category"));
                expense.setAmount(result.getDouble("amount"));
                expense.setCurrency(result.getString("currency"));
                expense.setTrans_dttm(result.getTimestamp("txn_dttm"));
                expense.setPaymentMode(result.getString("payment_mode"));
                expense.setUserId(result.getInt("user_id"));
                expense.setOwedUserId(result.getInt("owing_user_id"));
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
