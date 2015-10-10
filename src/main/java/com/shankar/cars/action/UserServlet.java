package com.shankar.cars.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.java.Log;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
//import com.shankar.ActivationCodeAlredyExist;
import com.shankar.cars.EmailService;
import com.shankar.cars.data.User;
import com.shankar.cars.data.UserActivationCode;
import com.shankar.cars.data.meta.ChangePassMeta;
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
	CarDBService carDBService = new CarDBService();
	CarModelsDBService carModelsDBService = new CarModelsDBService();
	CarTypeDBService carTypeDBService = new CarTypeDBService();
	UserDBService userDBService = new UserDBService();
	UserActivationCodeDBService userActivationCodeDBService = new UserActivationCodeDBService();

	@Path("/get")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public UserMeta getUserById(UserMeta userInf) {
		log.info("Start newApplication ");
		UserDBService db = new UserDBService();
		User user = db.load(User.class, userInf.getUser_id());
		UserMeta userMeta = new UserMeta();
		userMeta.setUser_id(user.getUser_id());
		userMeta.setEmail(user.getEmail());
		userMeta.setMobilePhone(user.getMobilePhone());
		userMeta.setUser_name(user.getUser_name());

		log.info("End newApplication");
		return userMeta;
	}

	@Path("/updateName")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<String, String> updateName(UserMeta userMeta) {
		log.info("Start updateUname ");
		Map<String, String> resp = new HashMap<String, String>();
		try {
			UserDBService db = new UserDBService();
			User user = db.load(User.class, userMeta.getUser_id());
			if (user != null) {
				user.setUser_name(userMeta.getUser_name());
				user.setUser_id(user.getUser_id());
				userDBService.save(user);
			}
		} catch (Exception e) {
			resp.put("status", "fail");
			resp.put("error_text", e.getMessage());
		}
		log.info("End updateUname");
		resp.put("status", "success");
		return resp;
	}

	@Path("/updatePhone")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<String, String> updatePhone(UserMeta userMeta) {
		log.info("Start updateUphone ");
		Map<String, String> resp = new HashMap<String, String>();
		try {
			UserDBService db = new UserDBService();
			User user = db.load(User.class, userMeta.getUser_id());
			if (user != null) {
				user.setMobilePhone(userMeta.getMobilePhone());
				user.setUser_id(user.getUser_id());
				userDBService.save(user);
			}
		} catch (Exception e) {
			resp.put("status", "fail");
			resp.put("error_text", e.getMessage());
		}

		log.info("End updateUphone");
		resp.put("status", "success");
		return resp;
	}

	@Path("/updateEmail")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<String, String> updateUmail(UserMeta userMeta) {
		log.info("Start updateUmail ");
		Map<String, String> resp = new HashMap<String, String>();
		try {
			UserDBService db = new UserDBService();
			User user = db.load(User.class, userMeta.getUser_id());
			if (user != null) {
				user.setEmail(userMeta.getEmail());
				user.setUser_id(user.getUser_id());
				userDBService.save(user);
			} else {
				log.severe("UserNotFoiundException:");
				Response.serverError().build();
			}

		} catch (Exception e) {
			resp.put("status", "fail");
			resp.put("error_text", e.getMessage());
		}

		log.info("End updateUmail");
		resp.put("status", "success");
		return resp;

	}

	@Path("/registration")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> registrationUser(UserMeta userMeta) {

		log.info("Start registrationUser ");

		Map<String, String> resp = new HashMap<String, String>();
		try {

			if (emailExist(userMeta)) {
				log.severe("EmailExistsException email exist::");
				resp.put("status", "fail");
				resp.put("error_text", "email exists");
				return resp;
			}

			User user = new User();
			user.setCreated_time(System.currentTimeMillis());
			user.setEmail(userMeta.getEmail());
			user.setMobilePhone(userMeta.getMobilePhone());
			user.setUser_name(userMeta.getUser_name());
			user.setIsActive(false);

			// activation
			userDBService.save(user);
			log.info("Start registrationUser ");
			boolean valid = this.sendActivationCode(user.getUser_id(),
					user.getEmail(), user.getUser_name());

			if (valid) {
				log.info("sendActivationCode + ");
				resp.put("status", "success");
			} else {
				userDBService.deleteUserPerId(User.class, user.getUser_id());
				resp.put("status", "fail");
				resp.put("error_text",
						"Email not sends with activation code , Try again with another email ");
			}

			// validateActivationCode(user);

		} catch (Exception e) {
			log.severe("Exception::" + e.getMessage());
			resp.put("status", "fail");
			resp.put("error_text", "Regestration Failed");

		}
		log.info("End registrationUser");
		return resp;
	}

	@Path("/authentication")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> authenticationUser(
			UserAuthentication userAuthentication) throws Exception {

		log.info("Start authenticationUser ");
		Map<String, String> resp = new HashMap<String, String>();
		// try {

		User user = null;

		try {

			if (userAuthentication.getEmail() != null
					|| userAuthentication.getActivationCode() != null) {
				// String user_activation_code =
				// PasswordService.encrypt(userAuthentication.getActivationCode());

				log.info("Start loadWithActivationCode ");

				UserActivationCode userActivationCode = userActivationCodeDBService
						.loadOne(UserActivationCode.class, "user_email",
								userAuthentication.getEmail());
				log.info("End loadWithActivationCode ");
				if (userActivationCode == null) {
					log.info(" userActivationCode not Exists");
					resp.put("status", "fail");
					resp.put("error_text", "userActivationCode not Exists");
					return resp;
				}
				String codeFromTable = userActivationCode
						.getUser_activation_code();
				String user_activation_code = userAuthentication
						.getActivationCode();

				// Decode data on other side, by processing encoded data
				byte[] valueDecoded = Base64.decodeBase64(codeFromTable
						.getBytes());
				String pass = new String(valueDecoded);

				if (!pass.equals(user_activation_code)) {
					log.info(" codeFromTableAfterDecode" + valueDecoded);
					log.info(" user_activation_code" + user_activation_code);
					log.info(" userActivationCode not Valid");
					resp.put("status", "fail");
					resp.put("error_text", "userActivationCode not Valid");
					return resp;
				}
				log.info(" same user_activation_code ");
				if (userAuthentication.getEmail().equals(
						userActivationCode.getUser_email())) {
					log.info(" same Emails ");
					user = userDBService.load(User.class,
							userActivationCode.getUser_id());
					log.info(" load user ");
					if (user == null) {
						log.info(" user == null ");
						resp.put("status", "fail");
						resp.put("error_text", "user not find");
						return resp;
					}

					user.setUpdate_time(System.currentTimeMillis());
					log.info(" update user ");
					user.setIsActive(true);
					userDBService.save(user);
				}
			}
		} catch (Exception e) {
			log.severe("AuthenticationException::" + e.getMessage());
			Response.serverError().build();
		}
		log.info("End authenticationUser");
		resp.put("status", "success");
		resp.put("userId", user.getUser_id().toString());
		return resp;

	}

	private boolean sendActivationCode(Long userId, String email,
			String userName) throws Exception {

		UserActivationCode userActivationCode = userActivationCodeDBService
				.loadOne(UserActivationCode.class, "user_email", email);// (userId,email);
		if (userActivationCode != null) {
			log.info(" userActivationCode Exists");
			return false;
		}
		try {
			log.info("start try");
			UserActivationCode newUserActivationCode = new UserActivationCode();
			newUserActivationCode.setUser_email(email);
			newUserActivationCode.setUser_id(userId);
			// PasswordService.encrypt("password");

			byte[] bytesEncoded = Base64.encodeBase64(String.valueOf(userId)
					.getBytes());
			String user_activation_code = new String(bytesEncoded);

			log.info("hashing");
			newUserActivationCode.setUser_activation_code(user_activation_code);
			userActivationCodeDBService.save(newUserActivationCode);
			log.info("save userActivationCode");

			EmailService.sendEmail(email, user_activation_code, userId,
					userName);
			log.info("sendEmail");
		} catch (Exception e) {

			log.severe("SendEmailException::" + e.getMessage());
		}

		return true;

	}

	@Path("/forgotPassword")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> forgotPassword(UserMeta userMeta)
			throws Exception {
		log.info("Start forgotPassword ");
		Map<String, String> resp = new HashMap<String, String>();
		User user = null;
		user = userDBService.loadOne(User.class, "email", userMeta.getEmail());
		// user = userDBService.loadOne(User.class, userMeta.getEmail());
		if (user == null) {
			resp.put("status", "failed");
			resp.put("error_text", "UserNotFound");
			return resp;
		}
		UserActivationCode newUserActivationCode = new UserActivationCode();
		newUserActivationCode.setUser_email(userMeta.getEmail());
		newUserActivationCode.setUser_id(user.getUser_id());

		byte[] bytesEncoded = Base64.encodeBase64(String.valueOf(
				user.getUser_id()).getBytes());
		String user_activation_code = new String(bytesEncoded);
		newUserActivationCode.setUser_activation_code(user_activation_code);
		userActivationCodeDBService.save(newUserActivationCode);
		try {
			EmailService.sendEmail(userMeta.getEmail(), user_activation_code,
					user.getUser_id(), user.getUser_name());
		} catch (Exception e) {
			log.severe("SendEmailForForgotPasswordException::" + e.getMessage());
			resp.put("status", "failed");
			resp.put("error_text", e.getMessage());
		}

		log.info("End forgotPassword");
		resp.put("status", "success");
		return resp;
	}

	@Path("/changePassword")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<String, String> changePassword(ChangePassMeta changePassMeta)
			throws Exception {
		log.info("Start changePassword ");
		Map<String, String> resp = new HashMap<String, String>();
		// User user = null;
		// user = userDBService.load(User.class, changePassMeta.getUser_id());
		UserDBService db = new UserDBService();
		User user = db.load(User.class, changePassMeta.getUser_id());
		if (user == null) {
			throw new Exception("UserNotFound");
		}
		UserActivationCode newUserActivationCode = new UserActivationCode();
		newUserActivationCode.setUser_email(user.getEmail());
		newUserActivationCode.setUser_id(user.getUser_id());
		byte[] bytesEncoded = Base64.encodeBase64(String.valueOf(
				changePassMeta.getNewPassword()).getBytes());
		String user_activation_code = new String(bytesEncoded);
		newUserActivationCode.setUser_activation_code(user_activation_code);
		userActivationCodeDBService.save(newUserActivationCode);
		try {
			EmailService.sendChangePasswordEmail(user.getEmail(),
					user_activation_code, user.getUser_id(),
					user.getUser_name());
		} catch (Exception e) {
			log.severe("SendEmailForForgotPasswordException::" + e.getMessage());
			resp.put("status", "failed");
			resp.put("error_text", e.getMessage());
		}

		log.info("End changePassword");
		resp.put("status", "success");
		return resp;
	}

	private boolean emailExist(UserMeta userMeta) {

		User user = null;
		if (userMeta.getEmail() != null) {
			user = userDBService.loadOne(User.class, "email",
					userMeta.getEmail());
		}
		if (user != null) {
			return true;
		}
		return false;

	}

}
