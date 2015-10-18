function MainCntl($scope,$http, $rootScope, $location, $route,$cookieStore) {
	userOperations($scope,$http, $rootScope, $location, $route,$cookieStore);
	popupFunctions($scope,$http, $rootScope, $location, $route,$cookieStore);

	$scope.userID = "string";
	console.log($cookieStore)
	$scope.years = [1970,1971,1972,1973,1974,1975,1976,1977,1978,1979,1980,1981,1982,1983,1984,1985,1986,1987,1988,1989,1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015];
	$scope.years.reverse();
	$scope.man_opts = [];
	$scope.mod_opts = [];
	
	$scope.getListModel = function(id){
	//console.log(id)
	
		if(id != "בחר יצרן"){
			$.each($scope.man_opts, function (key,val){
				if(val.name == id){
					$scope.typeId = val.id;
				}
			});
			$scope.listModel = {"carType_id" : $scope.typeId,"carType_Name":""};
			$http.post(''+$rootScope.mainurl+'/api/carmodels/get',$scope.listModel).
			success(function(data, status) {
				//console.log(data);
				
					$scope.mod_opts = data;
//console.log($scope.mod_opts);
			}).error(function(data, status) {console.log(data);})
		}
	}
	$scope.man_opts = [];
	$http.get(''+$rootScope.mainurl+'/api/cartypes/getalltypes').
		success(function(data, status) {
			//console.log(data);
			$.each(data, function (key,val){
				var tmp = {"id":val.car_type_id,"name":val.car_type_name};
				$scope.man_opts.push(tmp)
			});
		}).error(function(data, status) {console.log(data);});
	
	$http.post(''+$rootScope.mainurl+'/api/search/searchResult', {}).
		success(function(data, status) {
			$scope.listCars = data;
		}).error(function(data, status) {console.log(data);});
		
		
/************************************************ search *****************************************/		
	$scope.selectedModel = function(name){
		if(name != "בחר דגם" && name != ""){
			$.each($scope.mod_opts, function (key,val){
				if(val.model_name == name){$scope.SJmodelId = val.car_model_id;}
			});
		}
	};
	
	$scope.selectedYearF = function(year){
		if(year != "משנה" && year != ""){
			$scope.SJyearFrom = year;
		}
	};
	
	$scope.selectedYearT = function(year){
		if(year != "עד שנה" && year != ""){
			$scope.SJyearTo = year;
		}
	};
	
	$scope.selectedTypeGear = function(type){
		if(type != "בחר" && type != ""){
			$scope.SJsontypeGear = type;
		}
	};
	
	$scope.model = {};
	$scope.model.SJpriceF;
	$scope.model.SJpriceT;
	
	$scope.search = function(){
		
		if($scope.model.SJpriceF != "ממחיר" && $scope.model.SJpriceF != ""){
			$scope.SJpriceFrom = $scope.model.SJpriceF;
		}
		if($scope.model.SJpriceT != "עד מחיר" && $scope.model.SJpriceT != ""){
			$scope.SJpriceTo = $scope.model.SJpriceT;
		}

		$scope.searchJson = {
							"car_type_id" : $scope.typeId,
							 "car_model_id"  : $scope.SJmodelId,
							"yearF" : $scope.SJyearFrom, 
							"yearT" : $scope.SJyearTo, 
							"type_geare" : $scope.SJsontypeGear,
							"priceF" : $scope.SJpriceFrom,
							"priceT" : $scope.SJpriceTo
						};

		$http.post(''+$rootScope.mainurl+'/api/search/searchResult', $scope.searchJson).
			success(function(data, status) {
				var typeName = "typeName";
				var modName = "modName";
				$.each(data, function (key,val){
					if(val.car_type_id){
						$.each($scope.man_opts, function (k,v){
							if(val.car_type_id == v.id){
								val.typeName = v.name;
							}
						});
					}
					if(val.car_model_id){
					$scope.listModel = {"carType_id" : val.car_type_id,"carType_Name":""};
						$http.post(''+$rootScope.mainurl+'/api/carmodels/get',$scope.listModel).
							success(function(data, status) {
								$scope.modN = data;
								$.each($scope.modN, function (k,v){
									if(val.car_model_id == v.car_model_id){
										val.modName = v.model_name;
									}
							});
						}).error(function(data, status) {console.log(data);});
					}
				});
				$scope.listCars = data;
			}).error(function(data, status) {console.log(data);});
	}
	
	$scope.showMessage = function(){
	
	}
		
}

