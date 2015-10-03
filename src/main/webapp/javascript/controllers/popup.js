function popupFunctions($scope,$http, $rootScope, $location, $route,$cookieStore) {
	$scope.pBack = false;
	$scope.pPreloader = false;
	$scope.errorMes = false;
	$scope.pReg = false;
	$scope.pLogin = false;
	$scope.pChengeP = false;
	
	
	$scope.showPopup = function(p){
	console.log(p)
		$scope.hidePopup();
		$scope.pBack = true;
		if(p == 'pReg'){
			$scope.pReg = true;
		}
		if(p == 'pLogin'){
			$scope.pLogin = true;
		}
		if(p == 'pChengeP'){
			$scope.pChengeP = true;
		}
		
	}
	
	$scope.hidePopup = function(){
		$scope.pBack = false;
		$scope.pPreloader = false;
		$scope.errorMes = false;
		$scope.pReg = false;
		$scope.pLogin = false;
		$scope.pChengeP = false;
	}
}