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
import com.shankar.cars.data.CarModel;
import com.shankar.cars.data.CarType;
import com.shankar.cars.data.User;
import com.shankar.cars.data.meta.CarMeta;
import com.shankar.cars.data.meta.SearchResponseMeta;
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> saveCar(CarMeta carMeta) {

		log.info("Start saveCar ");
		Map<String, String> resp = new HashMap<String, String>();
		Car car = null;
		String car_url = carMeta.getUser_id().toString()
				+ carMeta.getCar_model_id().toString()
				+ carMeta.getCar_type_id().toString();
		User user = userDBService.load(User.class, carMeta.getUser_id());
		if (user == null) {
			Response.serverError().status(Response.Status.BAD_REQUEST)
					.entity("User not found").build();
			log.info("userNotfound");
		}
		try {
			log.info("start try");
			if (carMeta.getCar_id() != null) {
				log.info("update Car");
				car = carDBService.load(Car.class, carMeta.getCar_id());
				car.setUpdate_time(System.currentTimeMillis());
				car.setCar_id(carMeta.getCar_id());
				// car.setImage(carMeta.getFile());
			}
			if (car == null) {
				car = new Car();
				car.setCreated_time(System.currentTimeMillis());
				log.info("Create New Car");
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
			car.setUser_id(carMeta.getUser_id());
			car.setYear(carMeta.getYear());
			car.setVolume(carMeta.getVolume());
			car.setCar_url(car_url);
			car.setImage(carMeta.getFile());
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

	@Path("/uploadFile")
	@GET
	public Boolean uploadFile(@QueryParam("car_id") Long car_id)
			throws ServletException, IOException {
		CarDBService db = new CarDBService();
		if (car_id != null) {
			Car car = db.load(Car.class, car_id);
			request.setAttribute("car", car);
		}
		CarTypeDBService dbType = new CarTypeDBService();
		List<CarType> types = dbType.loadAll(CarType.class);
		request.setAttribute("types", types);
		request.getRequestDispatcher("/car_form.jsp")
				.forward(request, response);
		return true;
	}

	@Path("/getAllCars")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<SearchResponseMeta>  getAllCars() throws Exception {
		log.info("Start newApplication ");
		CarDBService cardb = new CarDBService();
		List<SearchResponseMeta> searchResponseMetaList = new ArrayList<>();
		List<Car> cars = cardb.loadAll(Car.class);
		log.info("find : " + cars.size() + "cars");
		UserDBService db = new UserDBService();
		CarModel carModel = null;
		for (Car car : cars) {
			SearchResponseMeta searchResponseMeta = new SearchResponseMeta();
			if (car.getCar_id() != null) {
				searchResponseMeta.setCar_id(car.getCar_id());
				log.info("Start setCar_id ");
			}
			if (car.getCar_model_id() != null) {

				carModel = carModelsDBService.load(CarModel.class,
						car.getCar_model_id());
				if (carModel != null) {
					searchResponseMeta.setCar_model_name(carModel
							.getModel_name());
				}
			}
			if (car.getCar_type_id() != null) {
				CarType carType = carTypeDBService.load(CarType.class,
						car.getCar_type_id());
				if (carType != null) {
					searchResponseMeta.setCar_type_name(carType
							.getCar_type_name());
				}

			}
			if (car.getCar_id() != null) {
				searchResponseMeta.setCar_id(car.getCar_id());
			} else {
				String car_url = car.getUser_id().toString()
						+ carModel.getCar_model_id().toString()
						+ car.getCar_type_id().toString();
				log.info("try find Car per Car_id ");
				Car newCar = carDBService
						.loadOne(Car.class, "car_url", car_url);
				if (newCar.getCar_id() != null) {
					log.info(" find Car SUCSESS");
					searchResponseMeta.setCar_id(newCar.getCar_id());
					searchResponseMeta.setImage(newCar.getImage());
					searchResponseMeta.setCar(newCar);
					log.info("newCar.getCar_id() = " + newCar.getCar_id());
				} else {
					log.info(" find Car FAILED");
				}
			}
			searchResponseMeta.setColor(car.getColor());
			searchResponseMeta.setCar_id(car.getCar_id());
			searchResponseMeta.setYear(car.getYear());
			searchResponseMeta.setKm(car.getKm());
			searchResponseMeta.setPrice(car.getPrice());
			searchResponseMeta.setType_geare(car.getType_geare());
			searchResponseMeta.setVolume(car.getVolume());
			User user = db.load(User.class, car.getUser_id());
			searchResponseMeta.setUser(user);
			searchResponseMeta.setCar(car);
			if (car.getImage() != null) {
				log.info("Start set Car Photo");
				searchResponseMeta.setImage(car.getImage());
				log.info(" set Car Photo SUCSESS");
			}
			searchResponseMetaList.add(searchResponseMeta);
		}
		for (SearchResponseMeta car2 : searchResponseMetaList) {
			log.info("Car_id() = " + car2.getCar_id());
		}
		return searchResponseMetaList;
	}

}