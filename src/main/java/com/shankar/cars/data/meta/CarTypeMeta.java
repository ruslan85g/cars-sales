package com.shankar.cars.data.meta;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Entity;



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
