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
import com.shankar.cars.data.User;
import com.shankar.cars.data.meta.SearchMeta;
import com.shankar.cars.data.meta.SearchResponseMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;
import com.shankar.cars.data.persist.SearchDBService;
import com.shankar.cars.data.persist.UserDBService;

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
		log.info("Start search ");

		List<SearchResponseMeta> searchResponseMetaList = new ArrayList<>();
//		if (searchMeta.getCar_model_id() == null
//				&& searchMeta.getCar_type_id() == null
//				&& searchMeta.getPriceF() == null
//				&& searchMeta.getPriceT() == null
//				&& searchMeta.getType_geare() == null
//				&& searchMeta.getVolume() == null
//				&& searchMeta.getYearF() == null
//				&& searchMeta.getYearT() != null) {
//			log.info("Start search per YearT");
//
//			List<Car> cars = searchDBService
//					.loadPerYearT(Car.class, searchMeta);
//			log.info("find : " + cars.size() + "cars");
//			UserDBService db = new UserDBService();
//			CarModel carModel = null;
//			for (Car car : cars) {
//				SearchResponseMeta searchResponseMeta = new SearchResponseMeta();
//				if (car.getCar_id() != null) {
//					searchResponseMeta.setCar_id(car.getCar_id());
//					log.info("Start setCar_id ");
//				}
//				if (car.getCar_model_id() != null) {
//
//					carModel = carModelsDBService.load(CarModel.class,
//							car.getCar_model_id());
//					if (carModel != null) {
//						searchResponseMeta.setCar_model_name(carModel
//								.getModel_name());
//					}
//				}
//				if (car.getCar_type_id() != null) {
//					CarType carType = carTypeDBService.load(CarType.class,
//							car.getCar_type_id());
//					if (carType != null) {
//						searchResponseMeta.setCar_type_name(carType
//								.getCar_type_name());
//					}
//
//				}
//				if (car.getCar_id() != null) {
//					searchResponseMeta.setCar_id(car.getCar_id());
//				} else {
//					String car_url = car.getUser_id().toString()
//							+ carModel.getModel_name();
//					log.info("try find Car per Car_id ");
//					Car newCar = db.loadOne(Car.class, "car_url", car_url);
//					if (newCar != null) {
//						log.info(" find Car SUCSESS");
//						searchResponseMeta.setCar_id(newCar.getCar_id());
//						searchResponseMeta.setImage(newCar.getImage());
//					} else {
//						log.info(" find Car FAILED");
//					}
//				}
//				searchResponseMeta.setColor(car.getColor());
//				// searchResponseMeta.setCar_id(car.getCar_id());
//				searchResponseMeta.setYear(car.getYear());
//				searchResponseMeta.setKm(car.getKm());
//				searchResponseMeta.setPrice(car.getPrice());
//				searchResponseMeta.setType_geare(car.getType_geare());
//				searchResponseMeta.setVolume(car.getVolume());
//				User user = db.load(User.class, car.getUser_id());
//				searchResponseMeta.setUser(user);
//				if (car.getImage() != null) {
//					log.info("Start set Car Photo");
//					searchResponseMeta.setImage(car.getImage());
//					log.info(" set Car Photo SUCSESS");
//				}
//				searchResponseMetaList.add(searchResponseMeta);
//			}
//
//			log.info("End search Per YearT");
//			return searchResponseMetaList;
//
//		}
		List<Car> cars = searchDBService.load(Car.class, searchMeta);
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
				// String car_url = car.getUser_id().toString()
				// + carModel.getModel_name();
				String car_url = car.getUser_id().toString()
						+ carModel.getCar_model_id().toString() + car.getCar_type_id().toString();
				log.info("try find Car per Car_id ");
				Car newCar = carDBService.loadOne(Car.class, "car_url", car_url);
				if (newCar != null) {
					log.info(" find Car SUCSESS");
					searchResponseMeta.setCar_id(newCar.getCar_id());
					searchResponseMeta.setImage(newCar.getImage());
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
			if (car.getImage() != null) {
				log.info("Start set Car Photo");
				searchResponseMeta.setImage(car.getImage());
				log.info(" set Car Photo SUCSESS");
			}
			searchResponseMetaList.add(searchResponseMeta);
		}

		log.info("End search");
		return searchResponseMetaList;
	}

}