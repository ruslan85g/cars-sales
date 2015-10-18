package com.shankar.cars.data.meta;


import lombok.Getter;
import lombok.Setter;

public class CarMeta {

	@Getter
	@Setter
	public Long car_id;

	@Getter
	@Setter
	public Long car_model_id;
	
	@Getter
	@Setter
	protected Long car_type_id;

	@Getter
	@Setter
	public Long user_id;

	@Getter
	@Setter
	public String car_model;

	@Getter
	@Setter
	public String car_name;

	@Getter
	@Setter
	public String file;
	
	@Getter
	@Setter
	public String car_url;

	@Getter
	@Setter
	public Long year;

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

	@Getter
	@Setter
	public Long price;

}
