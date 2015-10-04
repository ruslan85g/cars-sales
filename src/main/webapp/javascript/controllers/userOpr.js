function userOperations($scope,$http, $rootScope, $location, $route,$cookieStore) {
	// an login
	if($cookieStore.get("uID") && $cookieStore.get("uID") != ""){
		$rootScope.cookieUserID = $cookieStore.get("uID");
		$rootScope.userStatus = 1;
	}else{
		$rootScope.userStatus = 0;
	}
	
	$scope.logOut = function(){
		$cookieStore.put("uID") = "";
		$rootScope.userStatus = 0;
		$rootScope.cookieUserID = "";
		location.replace("/");
	}
	
	$scope.pRegView = 0;
	$scope.pLoginView = 0;
	$scope.userReg = {"name" : "","phone" :"","email" : ""};
	$scope.reg = function(){
		if($scope.userReg.email != ""){
			$scope.errorMes = false;
			$scope.pPreloader = true;
			$scope.regJson = {"user_name" : $scope.userReg.name,"mobilePhone" : $scope.userReg.phone,"email" : $scope.userReg.email,"user_id":""};
			$http.post(''+$rootScope.mainurl+'/api/users/registration', $scope.regJson).
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
		}else{
			$scope.errorMes = true;
		}
	}
	
	$scope.userAct = {"activCode" : "","email" : ""};
	$scope.activ = function(){
		if($scope.userAct.email != "" && $scope.userAct.activCode != ""){
			$scope.errorMes = false;
			$scope.pPreloader = true;
			$scope.activJson = {"activationCode" : $scope.userAct.activCode,"email" : $scope.userAct.email};

			$http.post(''+$rootScope.mainurl+'/api/users/authentication', $scope.activJson).
				success(function(data, status) {
					console.log(data);
					$scope.hidePopup();
					$rootScope.cookieUserID = data.userId;
					$cookieStore.put("uID", $rootScope.cookieUserID);
					$rootScope.userStatus = 1;
				}).error(function(data, status) {
					console.log(data);
					$scope.pRegView = 2;
					$scope.pActivStatus = false;
					$scope.pPreloader = false;
				});
		}else{
			$scope.errorMes = true;
		}
	}
	
	$scope.loginJson = {"email" : "",	"activationCode" : ""};
	//$scope.kkk = {"activationCode" : $scope.userAct.activCode,"email" : $scope.userAct.email};
	$scope.login = function(){
		if($scope.loginJson.email != "" && $scope.loginJson.activationCode != ""){
			$scope.errorMes = false;
			$scope.pPreloader = true;
			
			$http.post(''+$rootScope.mainurl+'/api/users/authentication', $scope.loginJson).
				success(function(data, status) {
					console.log(data);
					$scope.hidePopup();
					$rootScope.cookieUserID = data.userId;
					$cookieStore.put("uID", $rootScope.cookieUserID);
					$rootScope.userStatus = 1;
				}).error(function(data, status) {
					console.log(data);
					$scope.pLoginView = 2;
					$scope.pLoginStatus = false;
					$scope.pPreloader = false;
				});
		}else{
			$scope.errorMes = true;
		}
	}
	
	$scope.forgetJson = {"email" : ""};
	$scope.forget = function(){
		if($scope.loginJson.email != ""){
		$scope.forgetJson = {"email" : $scope.loginJson.email};
		$scope.pPreloader = true;
		$http.post(''+$rootScope.mainurl+'/api/users/forgotPassword', $scope.forgetJson).
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
		}else{
			$scope.errorMes = true;
		}
	}
	
	$scope.chengePJson = {"email" : "","OldPassword" : "","NewPassword" : ""};
	$scope.chengeP = function(){
		if($scope.chengePJson.email != "" && $scope.chengePJson.OldPassword != "" && $scope.chengePJson.NewPassword != ""){
			$scope.pPreloader = true;
			$http.post(''+$rootScope.mainurl+'/api/users/changePassword', $scope.chengePJson).
				success(function(data, status) {
					console.log(data);
					$scope.pChengePView = 1;
					$scope.pChengePStatus = true;
				}).error(function(data, status) {
					console.log(data);
					$scope.pChengePView = 1;
					$scope.pChengePStatus = false;
					
				});
		}else{
				$scope.errorMes = true;
		}
	}
}