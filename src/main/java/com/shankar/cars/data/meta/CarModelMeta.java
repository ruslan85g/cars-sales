package com.shankar.cars.data.meta;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


public class CarModelMeta {
	

	
	@Id
	@Index
	@Getter
	@Setter
	public Long car_model_id;
	
	@Index
	@Getter
	@Setter
	public Long car_type_id;

	@Getter
	@Setter
	public String model_name;
	
}
