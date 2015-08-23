package com.shankar.cars.data.meta;

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
	
	@Getter
	@Setter
	public Long carType_id;
	

}
