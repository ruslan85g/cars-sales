package com.shankar.cars.data.meta;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


public class CarModelMeta {
	
//	"typeId" :5,
//	"modelList" : 
	
	@Getter
	@Setter
	public Long carType_id;
	
	@Getter
	@Setter
	public List <ModelList> modelList;
}
