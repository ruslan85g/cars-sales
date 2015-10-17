package com.shankar.cars.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.java.Log;

import com.shankar.cars.data.Car;
import com.shankar.cars.data.User;
import com.shankar.cars.data.meta.CarMeta;
import com.shankar.cars.data.meta.UserMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;
import com.shankar.cars.data.persist.UserDBService;
//import com.sun.jersey.multipart.FormDataParam;

//
@Path("/cars")
@Log
public class CarsServlet {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	CarDBService carDBService = new CarDBService();
	CarModelsDBService carModelsDBService = new CarModelsDBService();
	CarTypeDBService carTypeDBService = new CarTypeDBService();
	UserDBService userDBService = new UserDBService();

	@Path("/get/{car_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CarMeta getCarById(@QueryParam("car_id") Long car_id) {
		log.info("Start newApplication ");

		CarDBService db = new CarDBService();
		Car car = db.load(Car.class, car_id);
		CarMeta carMeta = new CarMeta();
		carMeta.setCar_id(car.getCar_id());

		// CarMeta carMeta = new CarMeta();
		// carMeta.setCar_id(car_id);
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
		return carMeta;
	}

    @Path("/save")
    @POST
    @Consumes("application/x-www-form-urlencoded")
	public  Boolean saveCar( 
			@DefaultValue("") @FormParam("user_id") Long  user_id,
            @DefaultValue("") @FormParam("filename") String  file) {

		log.info("Start saveCar ");
		Map<String, String> resp = new HashMap<String, String>();
		Car car = null;
		String car_url = carMeta.getUser_id().toString()
				+ carMeta.getCar_model();
		User user = userDBService.load(User.class, carMeta.getUser_id());
		if (user == null) {
			Response.serverError().status(Response.Status.BAD_REQUEST)
					.entity("User not found").build();
			log.info("userNotfound");
		}
		try {
			log.info("start try");
			if (carMeta.getCar_id() != null) {
				car = carDBService.load(Car.class, carMeta.getCar_id());
				car.setUpdate_time(System.currentTimeMillis());
				car.setCar_id(carMeta.getCar_id());
			}
			if (car == null) {
				car = new Car();
				car.setCreated_time(System.currentTimeMillis());
				log.info("setCreated_time");
			}
			log.info("start sets");
			car.setCar_model_id(carMeta.getCar_model_id());
			log.info("setCar_model_id save car in db");
			car.setCar_type_id(carMeta.getCar_type_id());
			car.setColor(carMeta.getColor());
			car.setCreated_time(System.currentTimeMillis());
			car.setKm(carMeta.getKm());
			car.setPrice(carMeta.getPrice());
			car.setType_geare(carMeta.getType_geare());
			car.setUpdate_time(System.currentTimeMillis());
			car.setUser_id(user_id);
			car.setYear(carMeta.getYear());
			car.setVolume(carMeta.getVolume());
			car.setCar_url(car_url);
			car.setImage(file);
			log.info("pre save car in db");
			carDBService.save(car);
			log.info("save car in db");
		} catch (Exception e) {
			log.severe("Exception::" + e.getMessage());
			resp.put("status", "failed");
			resp.put("error_text", e.getMessage());
		}
		log.info("find new car per url");
		// Car newCar = null;
		// newCar = carDBService.loadOne(Car.class, "car_url", car_url);
		// // user = userDBService.loadOne(User.class, "email",
		// // userMeta.getEmail());
		// if (newCar.getCar_id() != null) {
		// log.info("finded new car per url");
		resp.put("status", "success");
		resp.put("car_id", car.getCar_id().toString());
		// }
		log.info("End saveCar");
		return resp;
	}

	@Path("/getCarsByUserId")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Car> getCarsByUserId(UserMeta userMeta) throws Exception {
		log.info("Start newApplication ");
		UserDBService db = new UserDBService();
		List<Car> carMetas = new ArrayList<>();
		CarDBService cardb = new CarDBService();
		if (userMeta.getUser_id() != null) {
			User user = db.load(User.class, userMeta.getUser_id());
			if (user != null) {
				carMetas = cardb.load(Car.class, "user_id", user.getUser_id());
			} else {
				log.info("UserNotFouds ");
				throw new Exception("UserNotFouds");
			}
		} else {
			log.info("UserId Null");
			throw new Exception("UserId Null");
		}
		if (carMetas == null) {
			log.info("CarsNotFouds ");
			throw new Exception("CarsNotFouds");

		}
		// Car carMeta = new Car();
		// carMeta.setCar_id(car.getCar_id());

		log.info("End newApplication");
		return carMetas;
	}

	@Path("/deleteCar")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Map<String, String> deleteCar(CarMeta carMeta) {
		log.info("Start deleteCar ");
		Map<String, String> resp = new HashMap<String, String>();
		Long car_id = carMeta.getCar_id();
		if (car_id != null) {
			log.info("car_id not NULL");
			CarDBService db = new CarDBService();
			Car car = db.load(Car.class, car_id);
			log.severe("load Car");
			if (car != null) {
				try {
					log.severe("try DELETE Car");
					db.deleteCarPerId(Car.class, car_id);
					log.severe("Car DELETED SUCSESS ");
					resp.put("status", "success");
				} catch (Exception e) {
					log.severe("Car DELETE FAILED " + e.getMessage());
					resp.put("status", "failed");
					resp.put("error_text", e.getMessage());
				}
			}
		}
		log.info("End deleteCar");

		return resp;
	}

	@Path("/e")
	@GET
	public Boolean uploadFile(@QueryParam("car_id") Long car_id) throws ServletException, IOException {
		CarDBService db = new CarDBService();
		Car car = db.load(Car.class, car_id);
		request.setAttribute("car", car);
		request.getRequestDispatcher("/car_form.jsp").forward(request, response);
        return true;
	}
	
	// @Path("/deleteCarPerUserId")
	// @GET
	// @Produces(MediaType.APPLICATION_JSON)
	// @Consumes(MediaType.APPLICATION_JSON)
	// public CarMeta deleteCarPerUserId(@QueryParam("user_id") Long user_id) {
	// log.info("Start deleteCarPerUserId ");
	//
	// CarDBService db = new CarDBService();
	// CarMetaDBService carmetaDB = new CarMetaDBService();
	// Car car = db.load(Car.class, user_id);
	// if (car != null) {
	// db.deleteCarPerId(Car.class, user_id);
	// } else {
	// log.severe("CarNotFoundException:");
	// Response.serverError().build();
	// }
	// CarMeta carMeta = new CarMeta();
	// carMeta = carmetaDB.load(CarMeta.class, user_id);
	// if (carMeta != null) {
	// carmetaDB.deleteCarPerId(CarMeta.class, user_id);
	// } else {
	// log.severe("CarMetaNotFoundException:");
	// Response.serverError().build();
	// }
	// log.info("End deleteCarPerUserId");
	// return carMeta;
	// }
	//
	
}