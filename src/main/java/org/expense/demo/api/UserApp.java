package org.expense.demo.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.soap.AddressingFeature.Responses;

import org.expense.demo.model.Contact;
import org.expense.demo.model.User;
import org.expense.demo.repo.UserRepository;
import org.expense.demo.service.OTPService;
import org.expense.demo.utility.Utility;

@Path("user")
public class UserApp {

	private UserRepository userRepo = new UserRepository();
	private OTPService otpService = new OTPService();
	
	@Path("/register")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response.Status register(User user) {
		try {
			userRepo.Register(user);
		}catch(Exception ex) {
			System.out.println("Exception occurred while registering new user :" + ex.getMessage());
			ex.printStackTrace();
     		return Response.Status.fromStatusCode(500);
		}
		return Response.Status.CREATED;
	}
	
	
	@Path("/reset")
	@GET
	public Response.Status resetUserCredentials(@QueryParam("username") String username) {
		try {
			User user = userRepo.getUser(username);
			if(user != null && user.getEmail() != null) {
				otpService.sendOTPViaEmail(user);
			}
		}catch(Exception ex) {
			System.out.println("Exception occurred while registering new user :" + ex.getMessage());
			ex.printStackTrace();
     		return Response.Status.fromStatusCode(500);
		}
		return Response.Status.CREATED;
	}
	
	@Path("/addFriend")
	@POST
	public Response addUserToNetwork(@Context HttpHeaders headers , @QueryParam("username") String username){
		try {
			Map<String, String> map = Utility.resolveHeaders(headers.getHeaderString("Authorization"));
			userRepo.addFriend(Integer.parseInt(map.get("userId")), username);

		} catch (Exception ex) {
			return Response.serverError().entity(ex.getMessage()).build();
		}
		return Response.ok("CREATED").build();

	}
	
	@Path("/getAllFriends")
	@GET
	public Response getAllFriends(@Context HttpHeaders headers) {
		List<Contact> contactList = new ArrayList<Contact>();
		try {
			Map<String,String> map = Utility.resolveHeaders(headers.getHeaderString("Authorization"));
			contactList = userRepo.getAllFriends(Integer.parseInt(map.get("userId")));
		}catch(Exception ex) {
			return Response.serverError().entity(ex).build();
		}
		return Response.ok(contactList).build();
		
	}
	
}
