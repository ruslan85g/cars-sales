package com.shankar.cars.data.meta;

import com.googlecode.objectify.annotation.Entity;


import lombok.Getter;
import lombok.Setter;


public class CarModelMeta {
	
	@Getter
	@Setter
	public Long model_id;
	
	@Getter
	@Setter
	public Long manufature_id;
	

	
	@Getter
	@Setter
	public String model_name;
	
	

}
