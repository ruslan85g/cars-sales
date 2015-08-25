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
import com.shankar.cars.data.CarModels;
import com.shankar.cars.data.CarType;
import com.shankar.cars.data.meta.CarMeta;
import com.shankar.cars.data.meta.CarModelMeta;
import com.shankar.cars.data.meta.CarTypeMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;
import com.shankar.cars.data.persist.DBService;

@Path("/cars")
@Log
public class CarTypeServlet {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	CarDBService carDBService = new CarDBService();
	CarModelsDBService carModelsDBService = new CarModelsDBService();
	CarTypeDBService carTypeDBService = new CarTypeDBService();

	@Path("/get/{car_type_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<CarModels> /*carmodelsMeta*/newApplication(@QueryParam("car_type_id") Long car_type_id) {
		log.info("Start newApplication ");
		CarTypeDBService dbType = new CarTypeDBService();
		CarModelsDBService dbModels = new CarModelsDBService();
//		Long car_type_id = 1L;
//		List<CarType>  types = dbType.load(CarType.class, "is_active", true);
//		for (CarType carType : types) {
//			List<CarModels> models = dbModels.load(CarModels.class,"car_type_id",car_type_id);
//			carType.setCar_models(models);
//		}
		List<CarModelMeta> carModelsMeta = new ArrayList<>();
		List<CarModels> models = dbModels.load(CarModels.class,"car_type_id",car_type_id);
		int count=0;
//		for (CarModels carModels : models) {
//			
//			count++;
//			carModelsMeta.set(count, carModels);
//		}
//		carModelMeta = models ;

		log.info("End newApplication");
		return models;
	}

	// opa
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
	public Response newApplication(CarTypeMeta carTypeMeta) {
		log.info("Start newApplication ");

		/*
		 * CarDBService db = new CarDBService(); Car car = db.load(Car.class,
		 * car_id); CarMeta carMeta = new CarMeta();
		 * carMeta.setCar_id(car.getCar_id());
		 */
		CarType carType = null;
		if (carTypeMeta.getCarType_id() != null) {
			carType = carTypeDBService.load(CarType.class, carTypeMeta.getCarType_id());
			carType.setUpdate_time(System.currentTimeMillis());
		} else {
			carType = new CarType();
			carType.setCreated_time(System.currentTimeMillis());
		}

		carType.setCar_type_id(carTypeMeta.getCarType_id());
		carType.setCar_type_name(carTypeMeta.getCarType_Name());
//		carType.setCar_url(carMeta.getCar_url());
		carType.setIs_active(true);
		carTypeDBService.save(carType);
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
