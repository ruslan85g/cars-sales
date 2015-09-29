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

import lombok.extern.java.Log;

import com.shankar.cars.data.Car;
import com.shankar.cars.data.CarModel;
import com.shankar.cars.data.CarType;
import com.shankar.cars.data.meta.SearchMeta;
import com.shankar.cars.data.meta.SearchResponseMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;
import com.shankar.cars.data.persist.SearchDBService;

@Path("/search")
@Log
public class SearchServlet {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	CarDBService carDBService = new CarDBService();
	CarModelsDBService carModelsDBService = new CarModelsDBService();
	CarTypeDBService carTypeDBService = new CarTypeDBService();
	SearchDBService searchDBService = new SearchDBService();

	@Path("/searchResult")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<SearchResponseMeta> search(SearchMeta searchMeta) {
		log.info("Start getCarModelsByCarTypeId ");

		List<SearchResponseMeta> searchResponseMetaList = new ArrayList<>();
		List<Car> cars = searchDBService.load(Car.class, searchMeta);
		for (Car car : cars) {

			SearchResponseMeta searchResponseMeta = new SearchResponseMeta();
			searchResponseMeta.setCar_id(car.getCar_id());
			CarModel carModel = carModelsDBService.load(CarModel.class,
					car.getCar_model_id());
			if (carModel != null) {
				searchResponseMeta.setCar_model_name(carModel.getModel_name());
			}
			CarType carType = carTypeDBService.load(CarType.class,
					carModel.getCar_type_id());
			if (carType != null) {
				searchResponseMeta.setCar_type_name(carType.getCar_type_name());
			}
			searchResponseMeta.setColor(car.getColor());
			searchResponseMeta.setYear(car.getYear());
			searchResponseMeta.setKm(car.getKm());
			searchResponseMeta.setPrice(car.getPrice());
			searchResponseMeta.setType_geare(car.getType_geare());
			searchResponseMeta.setVolume(car.getVolume());
			searchResponseMetaList.add(searchResponseMeta);
		}

		log.info("End getCarModelsByCarTypeId");
		return searchResponseMetaList;
	}

	// @Path("/save")
	// @POST
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// public Response saveCarModel(CarModelMeta carModelMeta) {
	//
	// log.info("Start saveCar ");
	//
	// CarModel carModel = null;
	//
	// /*
	// * if(user == null ){
	// * Response.serverError().status(Response.Status.BAD_REQUEST).entity(
	// * "User not found" ).build(); }
	// */
	//
	// try {
	//
	// if (carModelMeta.getCar_model_id() != null) {
	// carModel = carModelsDBService.load(CarModel.class,
	// carModelMeta.getCar_model_id());
	// carModel.setUpdate_time(System.currentTimeMillis());
	// }
	//
	// if (carModel == null) {
	// carModel = new CarModel();
	// carModel.setCreated_time(System.currentTimeMillis());
	// }
	//
	// carModel.setModel_name(carModelMeta.getModel_name());
	// carModel.setCar_type_id(carModelMeta.getCar_type_id());
	// carModelsDBService.save(carModel);
	//
	// } catch (Exception e) {
	// log.severe("Exception::" + e.getMessage());
	// Response.serverError().build();
	// }
	//
	// log.info("End saveCar");
	// return Response.ok().build();
	// }

}
