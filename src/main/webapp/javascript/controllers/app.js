function MainCntl($scope,$http, $rootScope, $location, $route) {
	userOperations($scope,$http, $rootScope, $location, $route);
	popupFunctions($scope,$http, $rootScope, $location, $route);

	$scope.years = [];
	
	$scope.getListModel = function(id){
		$http.get(''+$rootScope.mainurl+'/getListModel',{ "id"   :  id }).
		success(function(data, status) {
			console.log(data);
		}).error(function(data, status) {console.log(data);});
	
	}
	
	$http.get(''+$rootScope.mainurl+'/getListMan').
		success(function(data, status) {
			console.log(data);
		}).error(function(data, status) {console.log(data);});
	
	$http.get(''+$rootScope.mainurl+'/home', {}).
		success(function(data, status) {
			console.log(data);
		}).error(function(data, status) {console.log(data);});
	
	$scope.search = function(){
		$scope.searchJson = {
							"car_type_id" : "Long",
							 "car_model_id"  :"Long",
							"yearF" : num,        //משנה
							"yearT" : num,        //עד שנה
							"type_geare" : "string",  //תיבת הילוכים
							"volume" : num,     //נפח 
							"km" : num,         //ק"מ
							"color" :"string",
							"priceF" : num,     //цена от
							"priceT" : num,     //цена до
							"text" : "string
										};
		$http.post(''+$rootScope.mainurl+'/search/searchResult', $scope.searchJson).
			success(function(data, status) {
				console.log(data);
			}).error(function(data, status) {console.log(data);});
	}
	
	$scope.showMessage = function(){
	
	}
		
}

