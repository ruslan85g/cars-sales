function AccountCtrl($scope,$http, $rootScope, $location, $route) {
	$scope.addView = 0;
	$scope.editView =0;
	$scope.userID = {"userId" : $rootScope.cookieUserID};
	$http.post(''+$rootScope.mainurl+'/api/users/get', $scope.userID).
		success(function(data, status) {
			console.log(data);
			$scope.userData = {"id" : $rootScope.cookieUserID,"dataName" : {"name" : data.user.user_name,"edit" : 0},"dataPhone" : {"phone" : data.user.mobilePhone,"edit" : 0},"dataEmail" : {"email" : data.user.email,"edit" : 0}};
			$scope.newUname = $scope.userData.dataName.name;
			$scope.newUphone = $scope.userData.dataPhone.phone;
			$scope.newUemail = $scope.userData.dataEmail.email;
		}).error(function(data, status) {
			console.log(data);
		});
	
	$scope.updateUname = function(){
		if($scope.newUname != "" && $scope.newUname != $scope.userData.dataName.name){
			$scope.updateUnameJson = {"userId" : $rootScope.cookieUserID,"name" : $scope.newUname };

			$http.post(''+$rootScope.mainurl+'/api/users/updateName', $scope.updateUnameJson).
				success(function(data, status) {
					console.log(data);
					$scope.userData.dataName.name = $scope.newUname;
					$scope.userData.dataName.edit = 0;
				}).error(function(data, status) {
					console.log(data);
					$scope.userData.dataName.edit = 0;
					$scope.newUname = $scope.userData.dataName.name;
				});
		}else{
			$scope.userData.dataName.edit = 0;
			$scope.newUname = $scope.userData.dataName.name;
		}
	}
	
	$scope.updateUphone = function(){
		if($scope.newUphone != "" && $scope.newUphone != $scope.userData.dataPhone.phone){
			$scope.updateUphoneJson = {"userId" : $rootScope.cookieUserID,"phone" : "string" };

			$http.post(''+$rootScope.mainurl+'/api/users/updatePhone', $scope.updateUphoneJson).
				success(function(data, status) {
					console.log(data);
					$scope.userData.dataPhone.phone = $scope.newUphone;
					$scope.userData.dataPhone.edit = 0;
				}).error(function(data, status) {
					console.log(data);
					$scope.userData.dataPhone.edit = 0;
					$scope.newUphone = $scope.userData.dataPhone.phone;
				});
		}else{
			$scope.userData.dataPhone.edit = 0;
			$scope.newUphone = $scope.userData.dataPhone.phone;
		}
	}
	
	$scope.updateUmail = function(){
		if($scope.newUemail != "" && $scope.newUemail != $scope.userData.dataEmail.email){
			$scope.updateUmailJson = {"userId" : $rootScope.cookieUserID,"email" : "string" };

			$http.post(''+$rootScope.mainurl+'/api/users/updateEmail', $scope.updateUmailJson).
				success(function(data, status) {
					console.log(data);
					$scope.userData.dataEmail.email = $scope.newUemail;
					$scope.userData.dataEmail.edit = 0;
				}).error(function(data, status) {
					console.log(data);
					$scope.userData.dataEmail.edit = 0;
					$scope.newUemail = $scope.userData.dataEmail.email;
				});
		}else{
			$scope.userData.dataEmail.edit = 0;
			$scope.newUemail = $scope.userData.dataEmail.email;
		}
	}
	
	$http.post(''+$rootScope.mainurl+'/api/cars/getCarsByUserId', $scope.viewAdsJson).
		success(function(data, status) {
			console.log(data);
			
		}).error(function(data, status) {
			console.log(data);
			
		});

	
	$scope.newCar = {	
						"car_type" : "",
						"model" : "",
						"year" : 0,
						"type" : "",  
						"volume" : 0,  
						"km" : 0,    
						"color" :"",
						"price" : 0,
						"text" : ""
					}
	$scope.newAd = function(){
		$.each($scope.mod_opts, function (key,val){
			if(val.model_name == $scope.newCar.model){
				$scope.newCar_type = val.car_type_id;
				$scope.newCar_mod = val.car_model_id;
			}
		});
		$scope.newAdJson = {	
								"user_id" : $rootScope.cookieUserID,
								"car_id" : "",
								"car_type" : ""+$scope.newCar_type+"",
								"model" : ""+$scope.newCar_mod+"",
								"year" : $scope.newCar.year,
								"type" : ""+$scope.newCar.type+"",  //תיבת הילוכים
								"volume" : $scope.newCar.volume,     //נפח 
								"km" : $scope.newCar.km,         //ק"מ
								"color" : ""+$scope.newCar.color+"",
								"price" : $scope.newCar.price
							};


		$http.post(''+$rootScope.mainurl+'/api/cars/save', $scope.newAdJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
	
	$scope.userAds = [
						{
							"car_type" : ""+$scope.newCar.car_type+"",
							"model" : ""+$scope.newCar.model+"",
							"year" : $scope.newCar.year,
							"type" : ""+$scope.newCar.type+"",  //תיבת הילוכים
							"volume" : $scope.newCar.volume,     //נפח 
							"km" : $scope.newCar.km,         //ק"מ
							"color" : ""+$scope.newCar.color+"",
							"price" : $scope.newCar.price,
							"text" : ""+$scope.newCar.text+""
						}
					]


	
	$scope.updateAd = function(){
		
		$scope.updateAdJson = {	
					"userId" : $rootScope.cookieUserID,
					"ad" :  {/*
								"id" :  num,
								"car_type" : "string",
								"model" : "string",
								"year" : num,
								"type" : "string",  //תיבת הילוכים
								"volume" : num,     //נפח 
								"km" : num,         //ק"מ
								"color" :"string",
								"price" : num"*/
							}
				};

		$http.post(''+$rootScope.mainurl+'/api/cars/updateCar', $scope.updateAdJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
	
	$scope.deleteAd = function(){
		
		$scope.deleteAdJson = {	
					"userId" : $rootScope.cookieUserID,
					"id" :  num
				};

		$http.post(''+$rootScope.mainurl+'/api/cars/deleteCarPerUserId', $scope.deleteAdJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
	
	$scope.file = function() {
		var fileInput = document.getElementById('file');
		fd = new FormData(fileInput.files[0]);
		fd.append( 'file', fileInput.files[0] );
		if(fileInput.files[0].size < 20*1024*1024){
			var xhr = new XMLHttpRequest;
			xhr.open('POST', '/upload/regular', true);
			xhr.onload = function (e) {
			  if (xhr.readyState === 4) {
				if (xhr.status === 200) {
					console.log(xhr.responseText);
				} else {
					$scope.showMessage('Error uploading image.');
				}
			  }
			}
			xhr.send(fd);
		}else{
			$scope.showMessage('Image too large (over 20 MB).');
		}
	}
	
	

}
