package com.shankar.cars.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.java.Log;

import com.shankar.cars.data.User;
import com.shankar.cars.data.meta.UserMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;
import com.shankar.cars.data.persist.UserDBService;

//
@Path("/users")
@Log
public class UserServlet {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	CarDBService carDBService = new CarDBService();
	CarModelsDBService carModelsDBService = new CarModelsDBService();
	CarTypeDBService carTypeDBService = new CarTypeDBService();
	UserDBService userDBService = new UserDBService();

	@Path("/get/{user_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UserMeta getUserById(@QueryParam("user_id") Long user_id) {
		log.info("Start newApplication ");

		UserDBService db = new UserDBService();
		User user = db.load(User.class, user_id);
		UserMeta userMeta = new UserMeta();
		userMeta.setUser_id(user.getUser_id());
		userMeta.setEmail(user.getEmail());
		userMeta.setMobilePhone(user.getMobilePhone());
		userMeta.setUser_name(user.getUser_name());
		/*
		 * Response resp = null;
		 * 
		 * try{ ApplicationLogic al = new ApplicationLogic(); Boolean flag =
		 * al.saveApplication( appMeta );
		 * 
		 * if(flag){ resp = Response.ok().build(); }else{ resp =
		 * Response.notModified().build(); }
		 * 
		 * }catch(Exception e){ log.info("Exception::" + e.getMessage()); resp =
		 * Response.serverError().build(); }
		 */

		log.info("End newApplication");
		return userMeta;
	}

	@Path("/registration")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrationUser(UserMeta userMeta) {

		log.info("Start registrationUser ");

		User user = null;
		try {

			if (userMeta.getUser_id() != null) {
				user = userDBService.load(User.class, userMeta.getUser_id());
				user.setUpdate_time(System.currentTimeMillis());
			}

			if (user == null) {
				user = new User();
				user.setCreated_time(System.currentTimeMillis());
			}

			user.setEmail(userMeta.getEmail());
			user.setMobilePhone(userMeta.getMobilePhone());
			user.setUser_name(userMeta.getUser_name());
//			user.setIsActive(true);

			userDBService.save(user);

		} catch (Exception e) {
			log.severe("Exception::" + e.getMessage());
			Response.serverError().build();
		}

		log.info("End registrationUser");
		return Response.ok().build();
	}

}
