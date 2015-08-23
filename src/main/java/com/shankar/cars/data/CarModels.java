package com.shankar.cars.data;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity(name = "CarType")
public class CarModels {
	
	@Id
	@Index
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
	public Long car_type_id;
	
	@Index
	@Getter
	@Setter
	protected Long created_time;
	
	@Index
	@Getter
	@Setter
	protected Long update_time;
	
}
