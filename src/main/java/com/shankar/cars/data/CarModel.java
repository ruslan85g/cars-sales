package com.shankar.cars.data;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity(name = "CarModel")
public class CarModel {

	@Id
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

	@Index
	@Getter
	@Setter
	protected Long created_time;

	@Index
	@Getter
	@Setter
	protected Long update_time;

}
