package com.shankar.cars.data;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "Car")
public class CarImage {
	
	@Id
	@Getter
	@Setter
	protected Long car_id;
	
	
	@Index
	@Getter
	@Setter
	public String image;
	
	@Index
	@Getter
	@Setter
	protected Long created_time;
	
	@Index
	@Getter
	@Setter
	protected Long update_time;

}
