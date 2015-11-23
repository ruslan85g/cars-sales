package com.shankar.cars.action;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.shankar.cars.data.meta.SearchMeta;
import com.shankar.cars.data.meta.SearchResponseMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;

public class CarsServletTest {

	CarDBService carDBService = new CarDBService();
	CarModelsDBService carModelsDBService = new CarModelsDBService();
	CarTypeDBService carTypeDBService = new CarTypeDBService();
	CarsServlet carsServlet = new CarsServlet();
	SearchServlet searchServlet = new SearchServlet();

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig()
					.setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

	private Closeable closeable;

	@Before
	public void setUp() {
		helper.setUp();
		// ObjectifyRegistrar.registerDataModel();
		closeable = ObjectifyService.begin();
	}

	@After
	public void tearDown() {
		closeable.close();

		helper.tearDown();
	}

	// @Test
	// public void Test1_saveCar() throws Exception {
	//
	// CarMeta carMeta = new CarMeta();
	// Long car_id = null;
	// String car_model = "968";
	// String car_name = "zaparojec";
	// String car_url = "awawwa.com";
	// Long user_id = 999987l;
	// carMeta.setCar_id(car_id);
	// carMeta.setCar_model(car_model);
	// carMeta.setCar_name(car_name);
	// carMeta.setCar_url(car_url);
	// carMeta.setUser_id(user_id);
	// // User user = new User();
	// carsServlet.saveCar(carMeta);
	// // Thread.sleep(1000);
	// /* Car car = */carDBService.loadByCarName(Car.class,
	// carMeta.getCar_name());
	// // assertNotNull(car);
	// }

	// public List<SearchResponseMeta> search(SearchMeta searchMeta) {
	//@Test
	public void Test2_serchCar() throws Exception {

		SearchMeta searchMeta = new SearchMeta();
		searchMeta.setYearF(2014l);
		searchMeta.setPriceF(989l);

		// User user = new User();

		// Thread.sleep(1000);
		List<SearchResponseMeta> result = searchServlet.search(searchMeta);
		assertNotNull(result);
		assertTrue(result.size()>0);
		
	}
	/*
	 * @Test public void Test_getCar() throws InterruptedException {
	 * 
	 * // CarMeta carMeta = new CarMeta(); // Long car_id = null; // String
	 * car_model = "968"; // String car_name = "zaparojec"; // String car_url =
	 * "awawwa.com"; // Long user_id = 999987l; // carMeta.setCar_id(car_id); //
	 * carMeta.setCar_model(car_model); // carMeta.setCar_name(car_name); //
	 * carMeta.setCar_url(car_url); // carMeta.setUser_id(user_id); // // User
	 * user = new User(); // carsServlet.newApplication(carMeta); // //
	 * Thread.sleep(1000); long id = 5629499534213120l; Car car =
	 * carDBService.load(Car.class, id); assertNotNull(car); }
	 */
}
