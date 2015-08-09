package com.shankar.cars.data;

import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.shankar.cars.data.meta.CarModelMeta;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "CarType")
public class CarType {
	
	@Id
	@Index
	@Getter
	@Setter

	public Long manufature_id;
	
	@Index
	@Getter
	@Setter
	public String manufature_name;
	
	@Index
	@Getter
	@Setter
	public List<CarModelMeta> car_models;
	
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

}
