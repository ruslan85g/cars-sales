package com.shankar.cars.data.persist;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Entity;

import static com.shankar.cars.data.persist.OfyService.ofy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.extern.java.Log;

import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Loader;
import com.shankar.cars.data.Car;
import com.shankar.cars.data.meta.SearchMeta;
@Log
public class SearchDBService extends DBService {

	public List<Car> load(Class<Car> class1, SearchMeta searchMeta) {
		// Get the Datastore Service
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Collection<Filter> subFilters = new ArrayList<Filter>();
		List<Car> carsList = new ArrayList<Car>();
		if (searchMeta == null) {
			throw new RuntimeException("searchMeta cannot be null");
		}
		
		subFilters.add( new FilterPredicate("car_name",
		        FilterOperator.NOT_EQUAL, 
		        ""
		        ));
		
		
		if (searchMeta.getCar_type_id() != null) {
			subFilters.add( new FilterPredicate("car_type_id",
					        FilterOperator.EQUAL, 
					        searchMeta.getCar_type_id()
					        ));
		}
		if (searchMeta.getCar_model_id() != null) {
			log.info("car_model_id: "+searchMeta.getCar_model_id());
			subFilters.add( new FilterPredicate("car_model_id",
					        FilterOperator.EQUAL, 
					        searchMeta.getCar_model_id()
					        ));
		}
		if (searchMeta.getYearF() != null) {
			subFilters.add( new FilterPredicate("yearF",
					        FilterOperator.GREATER_THAN_OR_EQUAL, 
					        searchMeta.getYearF()
					        ));
		}
		if (searchMeta.getType_geare() != null
				&& !searchMeta.getType_geare().isEmpty()) {
			subFilters.add( new FilterPredicate("type_geare",
			        FilterOperator.EQUAL, 
			        searchMeta.getType_geare()
			        ));
		}
		if (searchMeta.getVolume() != null && !searchMeta.getVolume().isEmpty()) {
			subFilters.add( new FilterPredicate("volume",
			        FilterOperator.EQUAL, 
			        searchMeta.getVolume()
			        ));
		}
		if (searchMeta.getKm() != null) {
			subFilters.add( new FilterPredicate("km",
			        FilterOperator.GREATER_THAN_OR_EQUAL, 
			        searchMeta.getKm()
			        ));
		}
		if (searchMeta.getColor() != null && !searchMeta.getColor().isEmpty()) {
			subFilters.add( new FilterPredicate("color",
			        FilterOperator.EQUAL, 
			        searchMeta.getColor()
			        ));
		}
		if (searchMeta.getPriceF() != null) {
			subFilters.add( new FilterPredicate("priceF",
			        FilterOperator.GREATER_THAN_OR_EQUAL, 
			        searchMeta.getPriceF()
			        ));
		}
		

		// Use class Query to assemble a query
		Query q = new Query("Car").setFilter(CompositeFilterOperator.and(subFilters));

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		
		for (Entity result : pq.asIterable()) {
			

			Car car = new Car();
			car.setCar_id((Long) result.getProperty("car_id"));
			car.setCar_model_id((Long) result.getProperty("car_model_id"));
			car.setCar_type_id((Long) result.getProperty("car_type_id"));
			car.setCar_name((String) result.getProperty("car_name"));
			car.setColor((String) result.getProperty("color"));
			car.setCreated_time((Long) result.getProperty("created_time"));
			car.setKm((Long) result.getProperty("km"));
			car.setPrice((Long) result.getProperty("price"));
			car.setType_geare((String) result.getProperty("type_geare"));
			car.setUser_id((Long) result.getProperty("user_id"));
			car.setUpdate_time((Long) result.getProperty("update_time"));
			car.setVolume((String) result.getProperty("volume"));
			car.setYear((Long) result.getProperty("year"));
		 /* String firstName = (String) result.getProperty("firstName");
		  String lastName = (String) result.getProperty("lastName");
		  Long height = (Long) result.getProperty("height");*/
			log.info("carsList.add(car): "+car.getCar_model_id());
		  carsList.add(car);
		  
		}

		return carsList;
	}
	// public <E> List<E> load(Class<E> type, String feild, String value)
	// {
	// return ofy().load().type(type).filter(feild+" ==",value).list();
	// }

}
