function userOperations($scope,$http, $rootScope, $location, $route) {
	
	$scope.pRegView = 0;
	$scope.pLoginView = 0;
	
	$scope.reg = function(){
		$scope.pPreloader = true;
		$scope.regJson = {"name" : "string","phone" : "string","email" : "string"};

		$http.post(''+$rootScope.mainurl+'/users/registration', $scope.regJson).
			success(function(data, status) {
				console.log(data);
				$scope.pRegView = 1;
				$scope.pPreloader = false;
			}).error(function(data, status) {
				console.log(data);
				$scope.pRegView = 2;
				$scope.pRegStatus = false;
				$scope.pPreloader = false;
			});
	}
	
	$scope.activ = function(){
		$scope.pPreloader = true;
		$scope.activJson = {"activCode" : "string","email" : "string"};

		$http.post(''+$rootScope.mainurl+'/users/authentication', $scope.activJson).
			success(function(data, status) {
				console.log(data);
				$scope.hidePopup();
				$rootScope.userStatus = 1;
			}).error(function(data, status) {
				console.log(data);
				$scope.pRegView = 2;
				$scope.pActivStatus = false;
				$scope.pPreloader = false;
			});
	}

	$scope.login = function(){
		$scope.pPreloader = true;
		$scope.loginJson = {"email" : "string",	"password" : "string"};

		$http.post(''+$rootScope.mainurl+'/users/logIn', $scope.loginJson).
			success(function(data, status) {
				console.log(data);
				$scope.hidePopup();
				$rootScope.userStatus = 1;
			}).error(function(data, status) {
				console.log(data);
				$scope.pLoginView = 2;
				$scope.pLoginStatus = false;
				$scope.pPreloader = false;
			});
	}
	
	$scope.forget = function(){
		$scope.pPreloader = true;
		$scope.forgetJson = {"email" : "string"};
		$http.post(''+$rootScope.mainurl+'/users/forgotPassword', $scope.forgetJson).
			success(function(data, status) {
				console.log(data);
				$scope.pLoginView = 2;
				$scope.pForgetStatus = true;
			}).error(function(data, status) {
				console.log(data);
				$scope.pLoginView = 2;
				$scope.pForgetStatus = false;
				$scope.pPreloader = false;
			});
	}

	$scope.chengeP = function(){
		$scope.pPreloader = true;
		$scope.chengePJson = {"email" : "string","OldPassword" : "string","NewPassword" : "string"};

		$http.post(''+$rootScope.mainurl+'/users/changePassword', $scope.chengePJson).
			success(function(data, status) {
				console.log(data);
				$scope.pChengePView = 1;
				$scope.pChengePStatus = true;
			}).error(function(data, status) {
				console.log(data);
				$scope.pChengePView = 1;
				$scope.pChengePStatus = false;
				
			});
	}
}