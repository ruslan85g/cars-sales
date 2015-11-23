function userOperations($scope,$http, $rootScope, $location, $route,$cookieStore) {
	// an login
	if($cookieStore.get("uID") && $cookieStore.get("uID") != ""){
		$rootScope.cookieUserID = $cookieStore.get("uID");
		$rootScope.userStatus = 1;
		console.log($rootScope.cookieUserID);
	}else{
		$rootScope.userStatus = 0;
	}
	
	$scope.logOut = function(){
		$cookieStore.put("uID", "");
		$rootScope.userStatus = 0;
		$rootScope.cookieUserID = "";
		location.replace("/");
	}
	
	$scope.changeView = function(n){
		$scope.pLoginView = n;
		$scope.error_text='';
		$scope.pLoginStatus='';
	}
	
	$scope.pRegView = 0;
	$scope.pLoginView = 0;
	$scope.pChengePView = 0;
	$scope.userReg = {"name" : "","phone" :"","email" : ""};
	$scope.reg = function(){
		if($scope.userReg.email != ""){
			$scope.errorMes = false;
			$scope.pPreloader = true;
			$scope.regJson = {"user_name" : $scope.userReg.name,"mobilePhone" : $scope.userReg.phone,"email" : $scope.userReg.email,"user_id":""};
			$http.post(''+$rootScope.mainurl+'/api/users/registration', $scope.regJson).
				success(function(data, status) {
					console.log(data);
					if(data.status == "success"){
						$scope.pRegView = 1;
						$scope.pRegStatus = true;
						$scope.pPreloader = false;
					}else{
						$scope.error_text = data.error_text;
						$scope.pRegStatus = false;
						$scope.pPreloader = false;
					}
				}).error(function(data, status) {
					console.log(data);
					$scope.error_text = "שגיאה, נסה שוב בעוד מספר דקות";
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
					if(data.status == "success"){
						$scope.hidePopup();
						$rootScope.cookieUserID = data.userId;
						$cookieStore.put("uID", $rootScope.cookieUserID);
						$rootScope.userStatus = 1;
					}else{
						console.log(data.error_text);
						$scope.pLoginView = 0;
						$scope.error_text = data.error_text;
						$scope.pLoginStatus = false;
						$scope.pPreloader = false;
					}
				}).error(function(data, status) {
					console.log(data);
					$scope.pLoginView = 0;
					$scope.error_text = "שגיאה, נסה שוב בעוד מספר דקות";
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
						if(data.status == "success"){
							$scope.hidePopup();
							$rootScope.cookieUserID = data.userId;
							$cookieStore.put("uID", $rootScope.cookieUserID);
							$rootScope.userStatus = 1;
							console.log($cookieStore)
						}else{
							console.log(data.error_text);
							$scope.pLoginView = 0;
							$scope.error_text = data.error_text;
							$scope.pLoginStatus = false;
							$scope.pPreloader = false;
						}
				}).error(function(data, status) {
					console.log(data);
					$scope.pLoginView = 0;
					$scope.error_text = "שגיאה, נסה שוב בעוד מספר דקות";
					$scope.pLoginStatus = false;
					$scope.pPreloader = false;
				});
		}else{
			$scope.errorMes = true;
		}
	}
	
	$scope.forgetJson = {"email" : ""};
	$scope.forget = function(){
	$scope.pLoginStatus = true;
		if($scope.loginJson.email != ""){
		$scope.forgetJson = {"email" : $scope.loginJson.email};
		$scope.pPreloader = true;
		$http.post(''+$rootScope.mainurl+'/api/users/forgotPassword', $scope.forgetJson).
			success(function(data, status) {
				console.log(data);
				if(data.status == "success"){
					$scope.pLoginView = 1;
					$scope.pForgetStatus = true;
					$scope.pPreloader = false;
				}else{
					$scope.pLoginView = 1;
					$scope.error_text = data.error_text;
					$scope.pForgetStatus = false;
					$scope.pPreloader = false;
				}
			}).error(function(data, status) {
				console.log(data);
				$scope.pLoginView = 1;
				$scope.error_text = "שגיאה, נסה שוב בעוד מספר דקות";
				$scope.pForgetStatus = false;
				$scope.pPreloader = false;
			});
		}else{
			$scope.errorMes = true;
		}
	}
	
	$scope.chengePJson = {"user_id" : $rootScope.cookieUserID,"currentPassword" : "","newPassword" : ""};
	$scope.chengeP = function(){
		if($scope.chengePJson.currentPassword != "" && $scope.chengePJson.newPassword != ""){
			$scope.pPreloader = true;
			$http.post(''+$rootScope.mainurl+'/api/users/changePassword', $scope.chengePJson).
				success(function(data, status) {
				$scope.chengePJson = {"user_id" : $rootScope.cookieUserID,"currentPassword" : "","newPassword" : ""};
					console.log(data);
					if(data.status == "success"){
						$scope.pChengePStatus = true;
						$scope.pPreloader = false;
					}else{
						$scope.error_text = data.error_text;
						$scope.pChengePStatus = false;
						$scope.pPreloader = false;
					}
				}).error(function(data, status) {
					$scope.chengePJson = {"user_id" : $rootScope.cookieUserID,"currentPassword" : "","newPassword" : ""};
					console.log(data);
					$scope.error_text = "שגיאה, נסה שוב בעוד מספר דקות";
					$scope.pChengePStatus = false;
					$scope.pPreloader = false;
				});
		}else{
				$scope.errorMes = true;
		}
	}
}