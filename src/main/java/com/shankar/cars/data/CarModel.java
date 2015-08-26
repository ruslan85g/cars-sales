package com.shankar.cars.data;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.shankar.cars.data.meta.ModelList;

@Entity(name = "CarType")
public class CarModel {

	@Id
	@Index
	@Getter
	@Setter
	public Long carType_id;

	@Getter
	@Setter
	public List<ModelList> modelList;

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
