package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.Iterable;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Result;

public class CarDBService extends DBService {	

	public <E> List<E> loadCars(Class<E> type, String feild0, Boolean value0,String feild1, Boolean value1,String feild, Boolean value,String feild, Boolean value,String feild, Boolean value)
	{
		return ofy().load().type(type).filter(feild0+" ==",value0).filter(feild1+" ==",value1).filter(feild+" ==",value).filter(feild+" ==",value).filter(feild+" ==",value).filter(feild+" ==",value).filter(feild+" ==",value).list();
	}
}
