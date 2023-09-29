package org.expense.demo.api;


// api administrator endpoint for searching visitor entry
import java.sql.SQLException;
import java.text.ParseException;
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
import org.expense.demo.repo.ExpenseRepository;
import org.expense.demo.utility.Utility;

@Path("expense/app")
public class UserApp {
	
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
     		return Response.serverError().build();
     	}
     	return Response.ok(expenses).build();
    }
    
    
    @POST
    @Path("/postExpense")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response.Status addExpense(Expense expense) {
    	try {
    	   expenseRepo.addExpense(expense);
    	}catch (Exception ex) {
    		System.out.println("Exception occurred while posting expense :" + ex.getMessage());
    		return Response.Status.BAD_REQUEST;
    	}
    	return Response.Status.CREATED;
    }
	

}
