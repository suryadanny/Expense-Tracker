package org.expense.demo.api;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.expense.demo.expense_store.visit_store;
import org.expense.demo.expense_store.visitor;


//api endpoint for visitor entry in json
@Path("/visitor")
public class visitorin {
	
    visit_store d = new visit_store();
	
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public String insert_visitor(visitor v)
	{
	   	d.insert(v);
		return "insert successful";
	}
	
	
	//@SuppressWarnings("rawtypes")
//  @GET
//	@Produces(MediaType.APPLICATION_JSON)
//    public List<visitor> getAll(@QueryParam("name") String name ,@QueryParam("case") int t ) throws SQLException, ParseException
//    {
//    	switch(t) {
////             
//    	case 1:
//    	 return d.view("visitor_name", name,t);	
////    		
//    	default :
//    	 String st = null;
//    	 return d.view("NULL", st,t);
////    	
////    	
//    	}
//   }
	
	
//	//@SuppressWarnings("rawtypes")
//	@GET
//	@Path("/new")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public List<visitor> getqueries( @QueryParam("name") String name ,@QueryParam("day") Date day@) throws SQLException 
//	{
//		//if( name!= null) 
//	    return d.view("visitor_name", name,t);
//		
////		else 
////			return d.view("day",day);
////		
//		
//		
//	}
	
	
	
}
