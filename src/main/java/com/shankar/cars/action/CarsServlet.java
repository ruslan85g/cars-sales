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

import com.shankar.cars.data.Car;
import com.shankar.cars.data.meta.CarMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;

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

		/*
		 * if(user == null ){
		 * Response.serverError().status(Response.Status.BAD_REQUEST).entity(
		 * "User not found" ).build(); }
		 */

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

			carDBService.save(car);

		} catch (Exception e) {
			log.severe("Exception::" + e.getMessage());
			Response.serverError().build();
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
}