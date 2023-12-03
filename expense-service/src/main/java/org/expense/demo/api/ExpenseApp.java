package org.expense.demo.api;


// api administrator endpoint for searching visitor entry
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.expense.demo.model.Expense;
import org.expense.demo.model.GroupId;
import org.expense.demo.repo.ExpenseRepository;
import org.expense.demo.utility.Utility;

@Path("expense")
public class ExpenseApp {
	
	ExpenseRepository expenseRepo = new ExpenseRepository();
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@Context HttpHeaders headers) {
		List<Expense> expenses ; 
    	try {
    	  Map<String,String> map = Utility.resolveHeaders(headers.getHeaderString("Authorization"));
     	 expenses =   expenseRepo.getAllExpenses(Integer.parseInt(map.get("userId")));
     	}catch (Exception ex) {
     		System.out.println("Exception occurred while getting all expenses :" + ex.getMessage());
     		return Response.serverError().entity(ex.getMessage()).build();
     	}
     	return Response.ok(expenses).build();
    }
    
    
    @POST
    @Path("/postExpense")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response addExpense(@Context HttpHeaders headers, Expense expense) {
    	try {
    		Map<String,String> map = Utility.resolveHeaders(headers.getHeaderString("Authorization"));
    		
			if (map.containsKey("userId")) {
				System.out.println("user id  : "+ map.get("userId"));
				expense.setOwedUserId(Integer.parseInt(map.get("userId")));
				expense.setTrans_dttm(Timestamp.from(Instant.now()));
				expenseRepo.addExpense(expense);

			}
    	}catch (Exception ex) {
    		System.out.println("Exception occurred while posting expense :" + ex.getMessage());
    		return Response.serverError().entity(ex.getMessage()).build();
    	}
    	return Response.ok("EXPENSE POSTED").build();
    }
    
    @POST
    @Path("/postSplitExpense")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response postExpenseSplit(@Context HttpHeaders headers, Expense expense) {
    	try {
    		Map<String,String> map = Utility.resolveHeaders(headers.getHeaderString("Authorization"));
    		
			if (map.containsKey("userId")) {
				System.out.println("user id  : "+ map.get("userId"));
				//expense.setOwedUserId(Integer.parseInt(map.get("userId")));
				expense.setTrans_dttm(Timestamp.from(Instant.now()));
				expenseRepo.addExpense(expense);

			}
    	}catch (Exception ex) {
    		System.out.println("Exception occurred while posting expense :" + ex.getMessage());
    		return Response.serverError().entity(ex.getMessage()).build();
    	}
    	return Response.ok("SPLIT EXPENSE POSTED").build();
    }
    
    
    @GET
    @Path("/amountOwedPerGrp")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response amountOwedPerGrp(@Context HttpHeaders headers) {
    	Map<Integer,Double> amtOwedPerGrp = new HashMap<Integer,Double>();
    	try {
    		Map<String,String> map = Utility.resolveHeaders(headers.getHeaderString("Authorization"));
    		
			if (map.containsKey("userId")) {
				System.out.println("user id  : "+ map.get("userId"));
				//expense.setOwedUserId(Integer.parseInt(map.get("userId")));
				amtOwedPerGrp =  expenseRepo.amountOwedPerGrp(Integer.parseInt(map.get("userId")));

			}
    	}catch (Exception ex) {
    		System.out.println("Exception occurred while posting expense :" + ex.getMessage());
    		return Response.serverError().entity(ex.getMessage()).build();
    	}
    	return Response.ok(amtOwedPerGrp).build();
    }
	
    @POST
    @Path("/totalSpendPerGrp")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response totalSpendPerGrp(@Context HttpHeaders headers, GroupId groupIds) {
    	Map<Integer,Double> totalSpendPerGrp = new HashMap<Integer,Double>();
    	try {
    		Map<String,String> map = Utility.resolveHeaders(headers.getHeaderString("Authorization"));
    		
			if (map.containsKey("userId")) {
				System.out.println("user id  : "+ map.get("userId"));
				//expense.setOwedUserId(Integer.parseInt(map.get("userId")));
				totalSpendPerGrp =  expenseRepo.totalSpendPerGrp(groupIds.getGroupIds());

			}
    	}catch (Exception ex) {
    		System.out.println("Exception occurred while posting expense :" + ex.getMessage());
    		return Response.serverError().entity(ex.getMessage()).build();
    	}
    	return Response.ok(totalSpendPerGrp).build();
    }

}
