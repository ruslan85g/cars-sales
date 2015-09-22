function MainCntl($scope,$http, $rootScope, $location, $route) {
	$scope.userStatus = 0;
	$scope.years = [];
	$scope.pBack = false;
	$scope.pLogin = false;
	$scope.pReg = false;
	$scope.showPopup = function(p){
	console.log(p)
		$scope.hidePopup();
		$scope.pBack = true;
		if(p == 'pLogin'){
			$scope.pLogin = true;
			console.log($scope.pLogin)
		}
		if(p == 'pReg'){
			$scope.pReg = true;
		}
	}
	$scope.hidePopup = function(){
		$scope.pLogin = false;
		$scope.pReg = false;
		$scope.pBack = false;
	}
	$scope.getListModel = function(id){
		console.log('555')
	}
}

