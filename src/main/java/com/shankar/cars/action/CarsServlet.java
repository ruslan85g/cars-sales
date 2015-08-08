package com.shankar.cars.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import lombok.extern.java.Log;

import com.shankar.cars.data.meta.ApplicationMeta;
import com.shankar.cars.services.logic.ApplicationLogic;

@Path("/cars")
@Log
public class CarsServlet
{
	@Context HttpServletRequest request;
	@Context HttpServletResponse response;
	
	
	@Path("/get/{car_id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response newApplication( @QueryParam("car_id") Long car_id ) {
		
		log.info("Start newApplication ");
		
		CarDBService db = new CarDBService();
		 Car car = db.load(Car.class, car_id);
		 CarMeta carMeta = new CarMeta();
		 carMeta.setCar_id( car.getCar_id() );
		 
		/*Response resp = null;
	
		try{
			ApplicationLogic al = new ApplicationLogic();
			Boolean flag = al.saveApplication( appMeta );
			
			if(flag){
				resp = Response.ok().build();
			}else{
				resp = Response.notModified().build();
			}
			
		}catch(Exception e){
			log.info("Exception::" + e.getMessage());
			resp = Response.serverError().build();
		}*/
		
		log.info("End newApplication");
		return car;
	}
	
	@Path("/save")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response newApplication( CarMeta carMeta ) {
		
		log.info("Start newApplication ");
		
		car.setTime_created(System.currentMillis());
		/*Response resp = null;
	
		try{
			ApplicationLogic al = new ApplicationLogic();
			Boolean flag = al.saveApplication( appMeta );
			
			if(flag){
				resp = Response.ok().build();
			}else{
				resp = Response.notModified().build();
			}
			
		}catch(Exception e){
			log.info("Exception::" + e.getMessage());
			resp = Response.serverError().build();
		}*/
		
		log.info("End newApplication");
		return resp;
	}

}
