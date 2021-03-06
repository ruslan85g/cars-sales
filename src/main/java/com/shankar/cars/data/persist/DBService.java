package com.shankar.cars.data.persist;

import static com.shankar.cars.data.persist.OfyService.ofy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Result;

public class DBService {	
	public <E> E save(E object)
	{
		ofy().save().entity(object).now();
		return object;
	}

	public <E> Result<Map<Key<E>, E>> storeList(List<E> list)
	{
		return save(list);
	}
	
	public <E> Result<Map<Key<E>, E>> save(List<E> list)
	{
		return ofy().save().entities(list);
	}
	
	public <E> Map<Key<E>, E> saveNow(List<E> list)
	{
		return ofy().save().entities(list).now();
	}
	
	public <E> Key<E> createKey(E object)
	{
		return Key.create(object);
	}
	
	public <E> List<E> load(List<Key<E>> keys)
	{
		return new ArrayList<E>(ofy().load().keys(keys).values());
	}
	
	public <E> E load(Key<E> key)
	{
		return ofy().load().key(key).now();
	}

	public <E> void deleteAll(Class<E> type)
	{
		ofy().delete().keys(ofy().load().type(type).keys());
	}
	
	public <E> void deleteAllNow(Class<E> type)
	{
		ofy().delete().keys(ofy().load().type(type).keys()).now();
	}
	
	public <E> E load(Class<E> type,String id) {
		return ofy().load().type(type).id(id).now();
	}
	
	public <E> E load(Class<E> type, long id) {
		return ofy().load().type(type).id(id).now();
	}

	public <E> List<E> loadAll(Class<E> type) {
		return ofy().load().type(type).list();
	}
	public <E> List<E> load(Class<E> class1, String field, String operator, Long long_value)
	{
		return ofy().load().type(class1).filter( field+operator, long_value ).list();
	}

	public <E> List<E> load(Class<E> type, String feild, String value)
	{
		return ofy().load().type(type).filter(feild+" ==",value).list();
	}
	public <E> E loadOne(Class<E> type, String feild, String value)
	{
		return ofy().load().type(type).filter(feild+" ==",value).first().now();
	}
	public <E> List<E> load(Class<E> type, String feild, long value)
	{
		return ofy().load().type(type).filter(feild+" ==",value).list();
	}
	public <E> List<E> load(Class<E> type, String feild, Boolean value)
	{
		return ofy().load().type(type).filter(feild+" ==",value).list();
	}
	
}
