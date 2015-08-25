package com.shankar.cars.data;

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

	public Long car_type_id;
	
	@Index
	@Getter
	@Setter
	public String car_type_name;
	
	
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
