package com.shankar.cars.action;

import java.util.ArrayList;
import java.util.List;

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

import com.shankar.cars.data.CarModel;
import com.shankar.cars.data.meta.CarModelMeta;
import com.shankar.cars.data.meta.CarTypeMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;

@Path("/carmodels")
@Log
public class CarModelServlet {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	CarDBService carDBService = new CarDBService();
	CarModelsDBService carModelsDBService = new CarModelsDBService();
	CarTypeDBService carTypeDBService = new CarTypeDBService();

	@Path("/get")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<CarModelMeta> getCarModelsByCarTypeId(CarTypeMeta carTypeMeta) {
		log.info("Start getCarModelsByCarTypeId ");
		CarModelsDBService dbModels = new CarModelsDBService();

		List<CarModelMeta> carModelsMeta = new ArrayList<>();
		List<CarModel> models = dbModels.load(CarModel.class, "car_type_id",
				carTypeMeta.getCarType_id());
		for (CarModel carModels : models) {
			CarModelMeta carModelMeta = new CarModelMeta();
			carModelMeta.setCar_model_id(carModels.getCar_model_id());
			carModelMeta.setCar_type_id(carModels.getCar_type_id());
			carModelMeta.setModel_name(carModels.getModel_name());
			carModelsMeta.add(carModelMeta);
		}

		log.info("End getCarModelsByCarTypeId");
		return carModelsMeta;
	}

	@Path("/save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveCarModel(CarModelMeta carModelMeta) {

		log.info("Start saveCar ");

		CarModel carModel = null;

		/*
		 * if(user == null ){
		 * Response.serverError().status(Response.Status.BAD_REQUEST).entity(
		 * "User not found" ).build(); }
		 */

		try {

			if (carModelMeta.getCar_model_id() != null) {
				carModel = carModelsDBService.load(CarModel.class,
						carModelMeta.getCar_model_id());
				carModel.setUpdate_time(System.currentTimeMillis());
			}

			if (carModel == null) {
				carModel = new CarModel();
				carModel.setCreated_time(System.currentTimeMillis());
			}

			carModel.setModel_name(carModelMeta.getModel_name());
			carModel.setCar_type_id(carModelMeta.getCar_type_id());
			carModelsDBService.save(carModel);

		} catch (Exception e) {
			log.severe("Exception::" + e.getMessage());
			Response.serverError().build();
		}

		log.info("End saveCar");
		return Response.ok().build();
	}

}
