package com.shankar.cars.data.meta;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "ApplicationMeta")
public class CarMeta {
	
	@Getter
	@Setter
	public Long car_id;
	
	@Getter
	@Setter
	public Long car_model;
	
	@Getter
	@Setter
	public String car_name;
	
	@Getter
	@Setter
	public String car_url;

}