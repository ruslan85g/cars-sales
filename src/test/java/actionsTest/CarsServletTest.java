package actionsTest;

import static org.junit.Assert.assertNotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.junit.Test;

import com.shankar.cars.action.CarsServlet;
import com.shankar.cars.data.Car;
import com.shankar.cars.data.meta.CarMeta;
import com.shankar.cars.data.persist.CarDBService;
import com.shankar.cars.data.persist.CarModelsDBService;
import com.shankar.cars.data.persist.CarTypeDBService;

//
//@Path("/cars")
//@Log
public class CarsServletTest {
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;

	CarDBService carDBService = new CarDBService();
	CarModelsDBService carModelsDBService = new CarModelsDBService();
	CarTypeDBService carTypeDBService = new CarTypeDBService();
	CarsServlet carsServlet = new CarsServlet();

	@Test
	public void Test1_saveCar() throws InterruptedException {

		CarMeta carMeta = new CarMeta();
		Long car_id = null;
		String car_model = "968";
		String car_name = "zaparojec";
		String car_url = "awawwa.com";
		Long user_id = 999987l;
		carMeta.setCar_id(car_id);
		carMeta.setCar_model(car_model);
		carMeta.setCar_name(car_name);
		carMeta.setCar_url(car_url);
		carMeta.setUser_id(user_id);
		// User user = new User();
		carsServlet.newApplication(carMeta);
		// Thread.sleep(1000);
		Car car = carDBService.loadByCarName(Car.class, carMeta.getCar_name());
		assertNotNull(car);
	}

	@Test
	public void Test_getCar() throws InterruptedException {

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
		// carsServlet.newApplication(carMeta);
		// // Thread.sleep(1000);
		long id = 5629499534213120l;
		Car car = carDBService.load(Car.class, id);
		assertNotNull(car);
	}
}
