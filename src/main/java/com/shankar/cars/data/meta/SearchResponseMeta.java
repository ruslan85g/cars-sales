package com.shankar.cars.data.meta;

import lombok.Getter;
import lombok.Setter;

public class SearchResponseMeta {

	@Getter
	@Setter
	public String car_type_name;

	@Getter
	@Setter
	public String car_model_name;

	@Getter
	@Setter
	protected Long car_id;
	
//	@Getter
//	@Setter
//	protected User user;

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
