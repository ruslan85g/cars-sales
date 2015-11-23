package com.shankar.cars.data.persist;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import lombok.extern.java.Log;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.shankar.cars.data.Car;
import com.shankar.cars.data.meta.SearchMeta;

@Log
public class SearchDBService extends DBService {

	public List<Car> load(Class<Car> class1, SearchMeta searchMeta) {

		// Get the Datastore Service
		log.info(" Start SearchDBService.load");
		Query q_year = new Query("Car");
		Query q_price = new Query("Car");
		Query q = new Query("Car");
		List<Entity> results = new ArrayList<Entity>();
		List<Entity> result_year = new ArrayList<Entity>();
		List<Entity> result_price = new ArrayList<Entity>();
		Collection<Filter> subFilters = new ArrayList<Filter>();
		Collection<Filter> subFiltersPrice = new ArrayList<Filter>();
		Collection<Filter> subFiltersYear = new ArrayList<Filter>();

		List<Car> carsList = new ArrayList<Car>();

		if (searchMeta == null) {
			throw new RuntimeException("searchMeta cannot be null");
		}

		setSubFiltersYear(searchMeta, subFiltersYear);
		setSubFiltersPrice(searchMeta, subFiltersPrice);

		if (subFiltersYear.size() == 0 && subFiltersPrice.size() == 0) {
			log.info("Start loadCars(q, subFilters): ");
			setSubFiltersCommon(searchMeta, subFilters);
			results = loadCars(q, subFilters);
			buildCarsList(results, carsList);
			
		}else{
			
			if (subFiltersYear.size() > 0) {
				log.info("Start loadCars(q_year, subFiltersYear): ");
				result_year = loadCars(q_year, subFiltersYear);
			}

			if (subFiltersPrice.size() > 0) {
				log.info("Start loadCars(q_price, subFiltersPrice): ");
				result_price = loadCars(q_price, subFiltersPrice);
			}
			
			if(!result_year.isEmpty() && !result_price.isEmpty()){
				log.info("Start buildCarsList(result_year, result_price, carsList,searchMeta)");
				buildCarsList(result_year, result_price, carsList,searchMeta);	
			}else{
				log.info("Start buildCarsList(result_year, carsList)");
				buildCarsList(result_year, carsList);	
				log.info("Start buildCarsList(result_price, carsList)");
				buildCarsList(result_price, carsList);
			}
			
			
		}

		/*if (subFiltersYear.size() > 0) {
			result_year = loadCars(q_year, subFiltersYear);
			buildCarsList(result_year, carsList);
		}

		if (subFiltersPrice.size() > 0) {
			result_price = loadCars(q_price, subFiltersPrice);
			buildCarsList(result_price, carsList);
		}

		if (subFilters.size() > 0) {
			results = loadCars(q, subFilters);
			buildCarsList(results, carsList);
		}*/
		if ((result_year.isEmpty() && !subFiltersYear.isEmpty())
				|| (result_price.isEmpty() && !subFiltersPrice.isEmpty())
				|| (results.isEmpty() && !subFilters.isEmpty())) {
			carsList.clear();
		}
		return carsList;
	}

	private void buildCarsList(List<Entity> result_year,
			List<Entity> result_price, List<Car> carsList, SearchMeta searchMeta) {
		log.info("Start buildCarsList");
		Long year = null;
		Long price = null;
		
		if (result_year != null && result_price != null) {
			log.info("Start set for result_year");
			
			for (Entity res_year : result_year) {
				price = (Long) res_year.getProperty("price");
				log.info("price in query = " + price);
				if(price!= null 
						&& ( searchMeta.getPriceF()!=null && price>= searchMeta.getPriceF())  
						||	( searchMeta.getPriceT()!=null && price <= searchMeta.getPriceT()) ){
					Car car = new Car();
					car.setCar_id((Long) res_year.getProperty("Car id"));// car_id//Name//-Name/ID-Car id:5111418835697664
					log.info("getCar_id " + car.getCar_id());
					car.setCar_model_id((Long) res_year.getProperty("car_model_id"));
					car.setCar_type_id((Long) res_year.getProperty("car_type_id"));
					car.setColor((String) res_year.getProperty("color"));
					car.setCreated_time((Long) res_year.getProperty("created_time"));
					car.setUpdate_time((Long) res_year.getProperty("updated_time"));
					car.setKm((Long) res_year.getProperty("km"));
					car.setPrice((Long) res_year.getProperty("price"));
					car.setType_geare((String) res_year.getProperty("type_geare"));
					car.setUser_id((Long) res_year.getProperty("user_id"));
					car.setUpdate_time((Long) res_year.getProperty("update_time"));
					car.setVolume((String) res_year.getProperty("volume"));
					car.setYear((Long) res_year.getProperty("year"));
					log.info("carsList.add(car of price array): ");
					if (!carsList.contains(car)) {
					carsList.add(car);
					}
				}
			}
		for (Entity res_price : result_price) {
				year = (Long) res_price.getProperty("year");
				log.info("year in query = " + year);
				if(year!=null
						&& (searchMeta.getYearF()!=null && year>= searchMeta.getYearF())
						|| (searchMeta.getYearT() != null && year <= searchMeta.getYearT()) ){
					Car car = new Car();
					car.setCar_id((Long) res_price.getProperty("car_id"));// car_id
					log.info("getCar_id " + car.getCar_id());
					car.setCar_model_id((Long) res_price.getProperty("car_model_id"));
					car.setCar_type_id((Long) res_price.getProperty("car_type_id"));
					car.setColor((String) res_price.getProperty("color"));
					car.setCreated_time((Long) res_price.getProperty("created_time"));
					car.setUpdate_time((Long) res_price.getProperty("updated_time"));
					car.setKm((Long) res_price.getProperty("km"));
					car.setPrice((Long) res_price.getProperty("price"));
					car.setType_geare((String) res_price.getProperty("type_geare"));
					car.setUser_id((Long) res_price.getProperty("user_id"));
					car.setUpdate_time((Long) res_price.getProperty("update_time"));
					car.setVolume((String) res_price.getProperty("volume"));
					car.setYear((Long) res_price.getProperty("year"));
					log.info("carsList.add(car of year array): ");
					if (!carsList.contains(car)) {
					carsList.add(car);
					 }
			}
		}
			
		}
		
	}

	private List<Entity> loadCars(Query query, Collection<Filter> subFilters) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		List<Entity> results = null;
		log.info("subFilters.size() " + subFilters.size());
		query = setFilter(query, subFilters);
		log.info("query " + query);
		PreparedQuery pq = datastore.prepare(query);
		log.info("pq: in query " + pq);
		// pq.countEntities(FetchOptions.Builder.withLimit(5));
		log.info("startInsertCarsFromSearch query: "
				+ pq.asIterable().toString());
		log.info("Query: " + query.toString());
		// result_year = pq.asList(FetchOptions.Builder.withLimit(5));
		results = pq.asQueryResultList(FetchOptions.Builder.withLimit(5));
		int num = pq.countEntities(FetchOptions.Builder.withLimit(5));
		log.info("results num: " + num);
		log.info("results size: " + results.size());
		return results;
	}

	private void buildCarsList(List<Entity> resultList, List<Car> carsList) {
		if (resultList != null) {
			log.info("Start buildCarsList");
			for (Entity result : resultList) {
				Car car = new Car();
				car.setCar_id((Long) result.getProperty("Name"));// car_id
				log.info("getCar_id " + car.getCar_id());
				car.setCar_model_id((Long) result.getProperty("car_model_id"));
				car.setCar_type_id((Long) result.getProperty("car_type_id"));
				car.setColor((String) result.getProperty("color"));
				car.setCreated_time((Long) result.getProperty("created_time"));
				car.setUpdate_time((Long) result.getProperty("updated_time"));
				car.setKm((Long) result.getProperty("km"));
				car.setPrice((Long) result.getProperty("price"));
				car.setType_geare((String) result.getProperty("type_geare"));
				car.setUser_id((Long) result.getProperty("user_id"));
				car.setUpdate_time((Long) result.getProperty("update_time"));
				car.setVolume((String) result.getProperty("volume"));
				car.setYear((Long) result.getProperty("year"));
				log.info("carsList.add(getCar_model_id): "
						+ car.getCar_model_id());
				// if (!carsList.contains(car)) {
				carsList.add(car);
				// }
			}
		}
	}

	private void setSubFiltersCommon(SearchMeta searchMeta,
			Collection<Filter> subFilters) {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.DATE, -30);

		subFilters.add(new FilterPredicate("created_time",
				FilterOperator.GREATER_THAN, c.getTimeInMillis()));

		addOtherSubFilters(searchMeta, subFilters);
	}

	private void setSubFiltersPrice(SearchMeta searchMeta,
			Collection<Filter> subFiltersPrice) {
		if (searchMeta.getPriceF() != null || searchMeta.getPriceT() != null) {

			if (searchMeta.getPriceF() != null) {
				subFiltersPrice.add(new FilterPredicate("price",
						FilterOperator.GREATER_THAN_OR_EQUAL, searchMeta
								.getPriceF()));
				log.info("find cars per getPriceF " + subFiltersPrice.size());
			}

			if (searchMeta.getPriceT() != null) {
				subFiltersPrice.add(new FilterPredicate("price",
						FilterOperator.LESS_THAN_OR_EQUAL, searchMeta
								.getPriceT()));
				log.info("find cars per getPriceT " + subFiltersPrice.size());
			}
			addOtherSubFilters(searchMeta, subFiltersPrice);
		}

	}

	private void setSubFiltersYear(SearchMeta searchMeta,
			Collection<Filter> subFiltersYear) {
		if (searchMeta.getYearF() != null || searchMeta.getYearT() != null) {

			// q_year = new Query("Car");
			if (searchMeta.getYearF() != null) {
				subFiltersYear.add(new FilterPredicate("year",
						FilterOperator.GREATER_THAN_OR_EQUAL, searchMeta
								.getYearF()));
				log.info("find cars per yearF " + subFiltersYear.size());
			}
			if (searchMeta.getYearT() != null) {
				subFiltersYear.add(new FilterPredicate("year",
						FilterOperator.LESS_THAN_OR_EQUAL, searchMeta
								.getYearT()));
				log.info("find cars per yearT " + subFiltersYear.size());
			}
			addOtherSubFilters(searchMeta, subFiltersYear);

		}

	}

	private void addOtherSubFilters(SearchMeta searchMeta,
			Collection<Filter> subFilters) {
		if (searchMeta.getCar_type_id() != null) {
			subFilters.add(new FilterPredicate("car_type_id",
					FilterOperator.EQUAL, searchMeta.getCar_type_id()));
		}

		if (searchMeta.getCar_model_id() != null) {
			subFilters.add(new FilterPredicate("car_model_id",
					FilterOperator.EQUAL, searchMeta.getCar_model_id()));
		}

		if (searchMeta.getType_geare() != null
				&& !searchMeta.getType_geare().isEmpty()) {
			subFilters.add(new FilterPredicate("type_geare",
					FilterOperator.EQUAL, searchMeta.getType_geare()));
		}
	}

	private Query setFilter(Query query, Collection<Filter> subFilters) {
		if (subFilters.size() == 1) {
			// strs.iterator().next();

			query.setFilter(subFilters.iterator().next());

		} else {
			query.setFilter(CompositeFilterOperator.and(subFilters));
		}
		return query;
	}

}
