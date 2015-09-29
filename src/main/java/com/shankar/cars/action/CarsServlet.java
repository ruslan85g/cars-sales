package com.shankar.cars.action;

import java.util.ArrayList;
import java.util.List;

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

import com.google.appengine.api.search.DateUtil;
import com.shankar.cars.data.Car;
import com.shankar.cars.data.User;
import com.shankar.cars.data.meta.CarMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarMetaDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;
import com.shankar.cars.data.persist.UserDBService;

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
	public Response saveCar(CarMeta carMeta) {

		log.info("Start saveCar ");

		Car car = null;

		User user = userDBService.load(User.class, carMeta.getUser_id());
		if (user == null) {
			Response.serverError().status(Response.Status.BAD_REQUEST)
					.entity("User not found").build();

			try {

				if (carMeta.getCar_id() != null) {
					car = carDBService.load(Car.class, carMeta.getCar_id());
					car.setUpdate_time(System.currentTimeMillis());
				}

				if (car == null) {
					car = new Car();
					car.setCreated_time(System.currentTimeMillis());
				}

				car.setCar_model_id(carMeta.getCar_model_id());
				car.setCar_name(carMeta.getCar_name());
				car.setCar_url(carMeta.getCar_url());
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
				carDBService.save(car);

			} catch (Exception e) {
				log.severe("Exception::" + e.getMessage());
				Response.serverError().build();
			}
		}

		log.info("End saveCar");
		return Response.ok().build();
	}

	@SuppressWarnings("unchecked")
	@Path("/getCarsByUserId")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Car> getCarsByUserId(Long user_id) throws Exception {
		log.info("Start newApplication ");

		List<Car> carMetas = new ArrayList<>();
		CarDBService db = new CarDBService();
		carMetas = (List<Car>) db.loadCarsByUserId(Car.class, user_id);
		if (carMetas == null) {
			throw new Exception("CarsNotFouds");
		}
		// Car carMeta = new Car();
		// carMeta.setCar_id(car.getCar_id());

		log.info("End newApplication");
		return carMetas;
	}

	@Path("/deleteCar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CarMeta deleteCar(@QueryParam("car_id") Long car_id) {
		log.info("Start deleteCar ");

		CarDBService db = new CarDBService();
		CarMetaDBService carmetaDB = new CarMetaDBService();
		Car car = db.load(Car.class, car_id);
		if (car != null) {
			db.deleteCarPerId(Car.class, car_id);
		} else {
			log.severe("CarNotFoundException:");
			Response.serverError().build();
		}
		CarMeta carMeta = new CarMeta();
		carMeta = carmetaDB.load(CarMeta.class, car_id);
		if (carMeta != null) {
			carmetaDB.deleteCarPerId(CarMeta.class, car_id);
		} else {
			log.severe("CarMetaNotFoundException:");
			Response.serverError().build();
		}
		log.info("End deleteCar");
		return carMeta;
	}

	@Path("/deleteCarPerUserId")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CarMeta deleteCarPerUserId(@QueryParam("user_id") Long user_id) {
		log.info("Start deleteCarPerUserId ");

		CarDBService db = new CarDBService();
		CarMetaDBService carmetaDB = new CarMetaDBService();
		Car car = db.load(Car.class, user_id);
		if (car != null) {
			db.deleteCarPerId(Car.class, user_id);
		} else {
			log.severe("CarNotFoundException:");
			Response.serverError().build();
		}
		CarMeta carMeta = new CarMeta();
		carMeta = carmetaDB.load(CarMeta.class, user_id);
		if (carMeta != null) {
			carmetaDB.deleteCarPerId(CarMeta.class, user_id);
		} else {
			log.severe("CarMetaNotFoundException:");
			Response.serverError().build();
		}
		log.info("End deleteCarPerUserId");
		return carMeta;
	}

	@Path("/updateCar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCar(CarMeta carMeta) {

		log.info("Start updateCar ");

		Car car = null;
		User user = userDBService.load(User.class, carMeta.getUser_id());
		if (user == null) {
			Response.serverError().status(Response.Status.BAD_REQUEST)
					.entity("User not found").build();
		}

		try {
			car = carDBService.load(Car.class, carMeta.getCar_id());
			if (car != null) {
				car.setUpdate_time(System.currentTimeMillis());
				car.setCar_model_id(carMeta.getCar_model_id());
				car.setCar_name(carMeta.getCar_name());
				car.setCar_url(carMeta.getCar_url());
			} else {
				Response.serverError().status(Response.Status.BAD_REQUEST)
						.entity("Car not found").build();
			}

			carDBService.save(car);

		} catch (Exception e) {
			log.severe("UpdateCarException::" + e.getMessage());
			Response.serverError().build();
		}
		log.info("End updateCar");
		return Response.ok().build();
	}
}