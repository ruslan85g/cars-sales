package com.shankar.cars.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;


@Entity(name = "CarType")
public class CarType {
	
	@Id
	@Index
	@Getter
	@Setter

	public Long car_firma_id;
	
	@Index
	@Getter
	@Setter
	public String car_firma_name;
	
	@Index
	@Getter
	@Setter
	public List<CarModels> car_models;
	
	@Index
	@Getter
	@Setter
	protected String car_url;
	
	@Index
	@Getter
	@Setter
	protected Long created_time;
	
	@Index
	@Getter
	@Setter
	protected Long update_time;
	@Index
	@Getter
	@Setter
	protected Boolean is_active;
}
