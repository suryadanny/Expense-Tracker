package org.expense.demo.expense_store;
import java.sql.*;
import java.util.*;
import java.util.Date;

import org.expense.demo.expense_store.visitor;

import java.text.*;

public class visit_store {
	
	public int flag;
	
	
	// for inserting a particular visitor entry
	public int insert(visitor v)
	{
		try
		{
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop","E088679","Welcome@123");
		    String st = "insert into visitor values(null,?,?,?,?,now(),?)";
		    PreparedStatement stmt = conn.prepareStatement(st , ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
		    
		    stmt.setString(1, v.getName());
		    stmt.setString(2, v.getOrganisation());
		    stmt.setString(3,v.getIdtype());
		    stmt.setString(4, v.getId_number());
		    stmt.setString(5, v.getContact());
		    
		    flag= stmt.executeUpdate();
		    
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		catch(ClassNotFoundException e1)
		{
			e1.printStackTrace();
		}
		
		return flag;
	}
	
	
	
	// for view in accordance with query parameters
	public<T> List<visitor> view(String s1 ,String s2, String s3 ,int type) throws SQLException, ParseException
	{ 
		   SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		   String st = null;
		   String s4 = " 23:59:59";
		   ResultSet s = null;
		       try
				{
				    Class.forName("com.mysql.cj.jdbc.Driver");
				    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookshop","E088679","Welcome@123");
				     
				    switch(type){
				    case 1:
				    	st = "select * from visitor where "; 
				    	st =st.concat(s1 + "= ?"); 
				    	
				    	break;
				    	
				    case 2:
				    	st="select * from visitor where entry_time between ? and ?" ;				    	 
				    	break;
				    
				    case 3:
				    	st="select * from visitor where  visitor_name = ? and entry_time >= ? and entry_time <= ?" ;				    	 
				    	break;
				    	
				    default:
				    	st = "select * from visitor";
				    }
				    
				    
				    
				    
				    	
				    PreparedStatement stmt = conn.prepareStatement(st, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
				    if(type ==1)
				     {
				    	 stmt.setString(1, s2);
				     }
				    
				    else if(type == 2)
				    {
				    	s2=s2.concat(s4);
				    	stmt.setString(1, s1);
				    	stmt.setString(2, s2);
				    }
				    
				    else if(type== 3)
				    {
				    	s3 = s3.concat(s4);
				    	stmt.setString(1,s1);
				    	stmt.setString(2, s2);
				    	stmt.setString(3, s3);
				    }
				    
				     s = stmt.executeQuery();
				     
				    
				}
				catch(SQLException e)
			{
					e.printStackTrace();
				}
				catch(ClassNotFoundException e1)
				{
					e1.printStackTrace();
				}
				
			   List<visitor> list = new LinkedList<visitor>();	
		     //  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		       SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");

			   while(s.next())
			   {		   
		         visitor v = new visitor();
		         String  d = sdf.format(s.getDate("entry_time"));
		         d= d.concat(" " + sd.format(s.getTime("entry_time")));
		         v.set_visitor(s.getString("visitor_name"),s.getString("organisation"),s.getString("id") , s.getString("id_number"),d,s.getString("contact_number"));
			     list.add(v); 
			   
			   }
		       return list;
	}

}
