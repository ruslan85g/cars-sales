package com.shankar.cars.action;

import java.util.UUID;

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

//import com.shankar.ActivationCodeAlredyExist;
import com.shankar.cars.EmailService;
import com.shankar.cars.PasswordService;
import com.shankar.cars.data.User;
import com.shankar.cars.data.UserActivationCode;
import com.shankar.cars.data.meta.UserAuthentication;
import com.shankar.cars.data.meta.UserMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;
import com.shankar.cars.data.persist.UserActivationCodeDBService;
import com.shankar.cars.data.persist.UserDBService;
//import com.shankar.cars.EmailService ;

//
@Path("/users")
@Log
public class UserServlet {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	//
	// EmailService EmailService
	// EmailService emailService = new EmailService;
	CarDBService carDBService = new CarDBService();
	CarModelsDBService carModelsDBService = new CarModelsDBService();
	CarTypeDBService carTypeDBService = new CarTypeDBService();
	UserDBService userDBService = new UserDBService();
	UserActivationCodeDBService userActivationCodeDBService = new UserActivationCodeDBService();

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

	@Path("/updateUname")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUname(Long user_id, String userName) {
		log.info("Start updateUname ");

		UserDBService db = new UserDBService();
		User user = db.load(User.class, user_id);
		if (user != null) {
			user.setUser_name(userName);
			userDBService.save(user);
		} else {
			log.severe("UserNotFoiundException:");
			Response.serverError().build();
		}
		log.info("End updateUname");
		return Response.ok().build();
	}

	@Path("/updateUphone")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUphone(Long user_id, String userPhone) {
		log.info("Start updateUphone ");

		UserDBService db = new UserDBService();
		User user = db.load(User.class, user_id);
		if (user != null) {
			user.setMobilePhone(userPhone);
			userDBService.save(user);
		} else {
			log.severe("UserNotFoiundException:");
			Response.serverError().build();
		}
		log.info("End updateUphone");
		return Response.ok().build();
	}

	@Path("/updateUmail")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUmail(Long user_id, String userEmail) {
		log.info("Start updateUmail ");
		UserDBService db = new UserDBService();
		User user = db.load(User.class, user_id);
		if (user != null) {
			user.setEmail(userEmail);
			userDBService.save(user);
		} else {
			log.severe("UserNotFoiundException:");
			Response.serverError().build();
		}
		log.info("End updateUmail");
		return Response.ok().build();
	}

	@SuppressWarnings("null")
	@Path("/registration")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrationUser(UserMeta userMeta) {

		log.info("Start registrationUser ");

		User user = null;

		try {

			if (emailExist(userMeta)) {
				Throwable e = null;
				log.severe("EmailExistsException::" + e.getMessage());

			}
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
			// user.setIsActive(true);

			// activation
			boolean valid = this.sendActivationCode(user.getUser_id(),
					user.getEmail(), user.getUser_name());
			if (valid) {
				userDBService.save(user);
			} else {
				Throwable e = null;
				log.severe("ActivationCodeException::" + e.getMessage());
				// new Exception();
			}

			// validateActivationCode(user);

		} catch (Exception e) {
			log.severe("Exception::" + e.getMessage());
			Response.serverError().build();
		}

		log.info("End registrationUser");
		return Response.ok().build();
	}

	@Path("/authentication ")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticationUser(UserAuthentication userAuthentication)
			throws Exception {

		log.info("Start authenticationUser ");

		User user = null;

		try {

			if (userAuthentication.getEmail() != null
					|| userAuthentication.getActivationCode() != null) {
				String user_activation_code = PasswordService
						.encrypt(userAuthentication.getActivationCode());
				UserActivationCode userActivationCode = userActivationCodeDBService
						.loadWithActivationCode(UserActivationCode.class,
								user_activation_code,
								userAuthentication.getEmail());
				if (userActivationCode != null) {
					user = userDBService.load(User.class,
							userActivationCode.getUser_id());
					if (user != null) {
						if (user.getUpdate_time() < System.currentTimeMillis()) {
							user.setUpdate_time(System.currentTimeMillis());
							user.setIsActive(true);
							userDBService.save(user);
						}
					}
				}
			}

		} catch (Exception e) {
			log.severe("AuthenticationException::" + e.getMessage());
			Response.serverError().build();
		}

		log.info("End authenticationUser");
		return Response.ok().build();
	}

	@SuppressWarnings("null")
	private boolean sendActivationCode(Long userId, String email,
			String userName) throws Exception {

		UserActivationCode userActivationCode = userActivationCodeDBService
				.load(UserActivationCode.class, userId, email);// (userId,email);

		int number = 0;
		if (userActivationCode != null) {
			number++;
			// throw new ActivationCodeAlredyExist();
			throw new Exception("ActivationCodeAlredyExist");
		}

		if (number > 1) {
			return false;
		}
		UserActivationCode newUserActivationCode = new UserActivationCode();
		newUserActivationCode.setUser_email(email);
		newUserActivationCode.setUser_id(userId);
		// PasswordService.encrypt("password");
		String user_activation_code = PasswordService.encrypt(UUID.randomUUID()
				.toString());
		newUserActivationCode.setUser_activation_code(user_activation_code);
		userActivationCodeDBService.save(newUserActivationCode);

		try {
			EmailService.sendEmail(email, user_activation_code, userId,
					userName);
		} catch (Exception e) {
			Throwable e1 = null;
			log.severe("SendEmailException::" + e1.getMessage());
		}

		return true;

	}

	@SuppressWarnings("null")
	@Path("/forgotPassword")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void forgotPassword(String email) throws Exception {
		log.info("Start forgotPassword ");

		User user = null;
		user = userDBService.load(User.class, email);
		if (user == null) {
			throw new Exception("UserNotFound");
		}
		UserActivationCode newUserActivationCode = new UserActivationCode();
		newUserActivationCode.setUser_email(email);
		newUserActivationCode.setUser_id(user.getUser_id());
		// PasswordService.encrypt("password");
		String user_activation_code = PasswordService.encrypt(UUID.randomUUID()
				.toString());
		newUserActivationCode.setUser_activation_code(user_activation_code);
		userActivationCodeDBService.save(newUserActivationCode);

		try {
			EmailService.sendEmail(email, user_activation_code,
					user.getUser_id(), user.getUser_name());
		} catch (Exception e) {
			Throwable e1 = null;
			log.severe("SendEmailForForgotPasswordException::"
					+ e1.getMessage());
		}

		log.info("End forgotPassword");
	}

	@SuppressWarnings("null")
	@Path("/changePassword")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void changePassword(String currentPassword, String newPassword)
			throws Exception {
		log.info("Start changePassword ");

		User user = null;
		user = userDBService.load(User.class, currentPassword);
		if (user == null) {
			throw new Exception("UserNotFound");
		}
		UserActivationCode newUserActivationCode = new UserActivationCode();
		newUserActivationCode.setUser_email(user.getEmail());
		newUserActivationCode.setUser_id(user.getUser_id());
		// PasswordService.encrypt("password");
		String user_activation_code = PasswordService.encrypt(newPassword);
		newUserActivationCode.setUser_activation_code(user_activation_code);
		userActivationCodeDBService.save(newUserActivationCode);

		try {
			EmailService.sendChangePasswordEmail(user.getEmail(),
					user_activation_code, user.getUser_id(),
					user.getUser_name());
		} catch (Exception e) {
			Throwable e1 = null;
			log.severe("SendEmailForForgotPasswordException::"
					+ e1.getMessage());
		}

		log.info("End changePassword");
	}

	private boolean emailExist(UserMeta userMeta) {

		User user = null;
		if (userMeta.getEmail() != null) {
			user = userDBService.load(User.class, userMeta.getEmail());
		}
		if (user != null) {
			return true;
		}
		return false;

	}

}
