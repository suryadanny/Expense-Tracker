package org.expense.demo.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.expense.demo.model.User;
import org.expense.demo.repo.UserRepository;

@Path("user")
public class UserApp {

	UserRepository userRepo = new UserRepository();
	
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
}
