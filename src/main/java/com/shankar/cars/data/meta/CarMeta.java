package com.shankar.cars.data.meta;

import com.googlecode.objectify.annotation.Entity;


import lombok.Getter;
import lombok.Setter;


public class CarMeta {
	
	@Getter
	@Setter
	public Long car_id;
	
	@Getter
	@Setter
	public Long user_id;
	
	@Getter
	@Setter
	public String car_model;
	
	@Getter
	@Setter
	public String car_name;
	
	@Getter
	@Setter
	public String car_url;

}
