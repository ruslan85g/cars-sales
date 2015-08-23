package com.shankar.cars.data.meta;

import java.util.List;

import lombok.Getter;
import lombok.Setter;



public class CarTypeMeta {
	
	@Getter
	@Setter
	public Long manufature_id;
	
	@Getter
	@Setter
	public String manufature_name;
	
	@Getter
	@Setter
	public List<CarModelMeta> car_models;

}
