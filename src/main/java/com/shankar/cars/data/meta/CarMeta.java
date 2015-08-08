package com.somoto.pronto.data.meta;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "ApplicationMeta")
public class CarMeta {
	
	@Id
	@Index
	@Getter
	@Setter
	protected Long car_id;
	
	@Index
	@Getter
	@Setter
	protected Long car_model;
	
	@Index
	@Getter
	@Setter
	protected String car_name;
	
	@Index
	@Getter
	@Setter
	protected String car_url;

}
