function MainCntl($scope,$http, $rootScope, $location, $route,$cookieStore) {
	userOperations($scope,$http, $rootScope, $location, $route,$cookieStore);
	popupFunctions($scope,$http, $rootScope, $location, $route,$cookieStore);
	
	 $scope.lastVal = $cookieStore.get('tab');
	
	$scope.years = [1970,1971,1972,1973,1974,1975,1976,1977,1978,1979,1980,1981,1982,1983,1984,1985,1986,1987,1988,1989,1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015];
	$scope.man_opts = [{"id":1,"name":"aaa"},{"id":2,"name":"bbb"},{"id":3,"name":"ccc"}];
	
	$scope.getListModel = function(id){
	console.log(id)
		if(id != "בחר יצרן"){
			$.each($scope.man_opts, function (key,val){
				if(val.name == id){$scope.typeId = val.id}
			});
			$http.get(''+$rootScope.mainurl+'/api/carmodels/get',{ "id"   :  $scope.typeId }).
			success(function(data, status) {
				console.log(data);
			}).error(function(data, status) {console.log(data);});
		}
	}
	
	$http.get(''+$rootScope.mainurl+'/api/cartypes/getalltypes').
		success(function(data, status) {
			console.log(data);
		}).error(function(data, status) {console.log(data);});
	
	$http.get(''+$rootScope.mainurl+'/api/home', {}).
		success(function(data, status) {
			console.log(data);
		}).error(function(data, status) {console.log(data);});
	
	$scope.search = function(){
		if($scope.SJmanuf != "בחר יצרן" && $scope.SJmanuf != ""){
			$.each($scope.man_opts, function (key,val){
				if(val.name == $scope.SJmanuf){$scope.SJtypeId = val.id}
			});
		}
		if($scope.SJmodel != "בחר דגם" && $scope.SJmodel != ""){
			$.each($scope.mod_opts, function (key,val){
				if(val.name == $scope.SJmodel){$scope.SJmodelId = val.id}
			});
		}
		if($scope.SJyearF != "משנה" && $scope.SJyearF != ""){
			$scope.SJyearFrom = $scope.SJyearF;
		}
		if($scope.SJyearT != "משנה" && $scope.SJyearT != ""){
			$scope.SJyearTo = $scope.SJyearT;
		}
		$scope.searchJson = {
							"car_type_id" : $scope.SJtypeId,
							 "car_model_id"  :"Long",
							"yearF" : num,        //משנה
							"yearT" : num,        //עד שנה
							"type_geare" : "string",  //תיבת הילוכים
							"volume" : num,     //נפח 
							"km" : num,         //ק"מ
							"color" :"string",
							"priceF" : num,     //цена от
							"priceT" : num,     //цена до
							"text" : "string"
										};
		$http.post(''+$rootScope.mainurl+'/search/searchResult', $scope.searchJson).
			success(function(data, status) {
				console.log(data);
			}).error(function(data, status) {console.log(data);});
	}
	
	$scope.showMessage = function(){
	
	}
		
}

