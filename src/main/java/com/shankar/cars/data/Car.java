package com.shankar.cars.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "Car")
public class Car {
	
	@Id
	@Getter
	@Setter
	protected Long car_id;
	
	@Index
	@Getter
	@Setter
	protected Long user_id;
	
	@Index
	@Getter
	@Setter
	protected Long car_model_id;
	
	@Index
	@Getter
	@Setter
	protected Long car_type_id;
	
	
	@Index
	@Getter
	@Setter
	public String car_url;

	@Getter
	@Setter
	public Long year;

	@Index
	@Getter
	@Setter
	public String type_geare;

	@Getter
	@Setter
	public String volume;
	
	@Getter
	@Setter
	public Long km;
	
	@Getter
	@Setter
	public String color;
	
	@Index
	@Getter
	@Setter
	public Long price;
	
	@Index
	@Getter
	@Setter
	protected Long created_time;
	
	@Index
	@Getter
	@Setter
	protected Long update_time;

}
