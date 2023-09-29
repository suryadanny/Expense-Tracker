package org.expense.demo.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.expense.demo.model.User;
import org.expense.demo.repo.UserRepository;
import org.expense.demo.service.OTPService;

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
	
}
