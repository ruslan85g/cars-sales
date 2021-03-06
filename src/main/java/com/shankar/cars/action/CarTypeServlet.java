package com.shankar.cars.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import lombok.extern.java.Log;

import com.shankar.cars.data.CarType;
import com.shankar.cars.data.meta.CarTypeMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;

@Path("/cartypes")
@Log
public class CarTypeServlet {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	CarDBService carDBService = new CarDBService();
	CarModelsDBService carModelsDBService = new CarModelsDBService();
	CarTypeDBService carTypeDBService = new CarTypeDBService();

	@Path("/getalltypes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CarType> get_types() {
		log.info("Start get_types ");
		CarTypeDBService dbType = new CarTypeDBService();
		List<CarType> types = dbType.loadAll(CarType.class);
		log.info("End get_types ");
		return types;
	}

	@Path("/saveCarTypes")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newApplication(CarTypeMeta carTypeMeta) {
		log.info("Start newApplication ");

		CarType carType = null;
		if (carTypeMeta.getCarType_id() != null) {
			log.info("update ");
			carType = carTypeDBService.load(CarType.class,
					carTypeMeta.getCarType_id());
			carType.setUpdate_time(System.currentTimeMillis());
		} else {
			log.info("create ");
			carType = new CarType();
			carType.setCreated_time(System.currentTimeMillis());
		}
		log.info("Start set ");
		carType.setCreated_time(System.currentTimeMillis());
		carType.setCar_type_id(carTypeMeta.getCarType_id());
		carType.setCar_type_name(carTypeMeta.getCarType_Name());
		// carType.setCar_url(carMeta.getCar_url());
		carType.setIs_active(true);
		log.info("save ");
		carTypeDBService.save(carType);
		log.info("End newApplication");
		return Response.ok().build();
	}

}
