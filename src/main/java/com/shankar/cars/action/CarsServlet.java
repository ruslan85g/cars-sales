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

import com.shankar.cars.data.Car;
import com.shankar.cars.data.CarType;
import com.shankar.cars.data.meta.CarMeta;
import com.shankar.cars.data.meta.CarTypeMeta;
import com.shankar.cars.data.persist.CarDBService;

@Path("/cars")
@Log
public class CarsServlet {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	CarDBService carDBService = new CarDBService();

	@Path("/get/{car_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public CarMeta newApplication(@QueryParam("car_id") Long car_id) {
		log.info("Start newApplication ");

		/*
		 * CarDBService db = new CarDBService(); Car car = db.load(Car.class,
		 * car_id); CarMeta carMeta = new CarMeta();
		 * carMeta.setCar_id(car.getCar_id());
		 */
		CarMeta carMeta = new CarMeta();
		carMeta.setCar_id(1L);
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
	@Produces(MediaType.APPLICATION_JSON)
	public Response newApplication(CarMeta carMeta) {

		log.info("Start newApplication ");

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
			} else {
				car = new Car();
				car.setCreated_time(System.currentTimeMillis());
			}

			car.setCar_model(carMeta.getCar_model());
			car.setCar_name(carMeta.getCar_name());
			car.setCar_url(carMeta.getCar_url());

			carDBService.save(car);

		} catch (Exception e) {
			log.severe("Exception::" + e.getMessage());
			Response.serverError().build();
		}

		log.info("End newApplication");
		return Response.ok().build();
	}

	@Path("/saveCarTypes/{carType}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newApplication(CarType carType) {
		log.info("Start newApplication ");

		/*
		 * CarDBService db = new CarDBService(); Car car = db.load(Car.class,
		 * car_id); CarMeta carMeta = new CarMeta();
		 * carMeta.setCar_id(car.getCar_id());
		 */
		CarTypeMeta carTypeMeta = new CarTypeMeta();
		carTypeMeta.setManufature_id(1l);
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
		return Response.ok().build();
	}

}
