function AccountCtrl($scope,$http, $rootScope, $location, $route) {
	$scope.addView = 0;
	$scope.editView =0;
	$scope.userID = {"user_id" : $rootScope.cookieUserID,"user_name" :"","mobilePhone" :"","email" :"" };
	console.log($rootScope.cookieUserID)
	$http.post(''+$rootScope.mainurl+'/api/users/get', $scope.userID).
		success(function(data, status) {
		console.log(data)
			$scope.userData = {"id" : $rootScope.cookieUserID,"dataName" : {"name" : data.user_name,"edit" : 0},"dataPhone" : {"phone" : data.mobilePhone,"edit" : 0},"dataEmail" : {"email" : data.email,"edit" : 0}};
			$scope.newUname = $scope.userData.dataName.name;
			$scope.newUphone = $scope.userData.dataPhone.phone;
			$scope.newUemail = $scope.userData.dataEmail.email;
		}).error(function(data, status) {
			console.log(data);
		});
	
		$scope.updateUname = function(newValue){

		if(newValue != "" && newValue != $scope.userData.dataName.name){
			$scope.updateUnameJson = {"user_id" : $rootScope.cookieUserID,"user_name" : newValue,"mobilePhone" :"","email" :"" };
			$http.post(''+$rootScope.mainurl+'/api/users/updateName', $scope.updateUnameJson).
				success(function(data, status) {
					$scope.userData.dataName.name = newValue;
					$scope.userData.dataName.edit = 0;
				}).error(function(data, status) {
					console.log(data);
					$scope.newUname = $scope.userData.dataName.name;
					$scope.userData.dataName.edit = 0;
				});
		}else{
			$scope.newUname = $scope.userData.dataName.name;
			$scope.userData.dataName.edit = 0;
		}
	}
	
	$scope.updateUphone = function(newValue){
		if(newValue != "" && newValue != $scope.userData.dataPhone.phone){
			$scope.updateUphoneJson = {"user_id" : $rootScope.cookieUserID,"user_name" : "","mobilePhone" : newValue,"email" :"" };
			$http.post(''+$rootScope.mainurl+'/api/users/updatePhone', $scope.updateUphoneJson).
				success(function(data, status) {
				console.log(data);
					$scope.userData.dataPhone.phone = newValue;
					$scope.userData.dataPhone.edit = 0;
				}).error(function(data, status) {
					console.log(data);
					$scope.newUphone = $scope.userData.dataPhone.phone;
					$scope.userData.dataPhone.edit = 0;	
				});
		}else{
			$scope.newUphone = $scope.userData.dataPhone.phone;
			$scope.userData.dataPhone.edit = 0;
		}
	}
	
	$scope.updateUmail = function(newValue){
		if(newValue != "" && newValue != $scope.userData.dataEmail.email){
			$scope.updateUmailJson ={"user_id" : $rootScope.cookieUserID,"user_name" : "","mobilePhone" : "","email" :newValue};
			$http.post(''+$rootScope.mainurl+'/api/users/updateEmail', $scope.updateUmailJson).
				success(function(data, status) {
					console.log(data);
					$scope.userData.dataEmail.email = newValue;
					$scope.userData.dataEmail.edit = 0;
				}).error(function(data, status) {
					console.log(data);
					$scope.newUemail = $scope.userData.dataEmail.email;
					$scope.userData.dataEmail.edit = 0;
				});
		}else{
			$scope.newUemail = $scope.userData.dataEmail.email;
			$scope.userData.dataEmail.edit = 0;
		}
	}
	$scope.user = {"user_id" : $rootScope.cookieUserID};
	console.log($scope.user)
	$scope.getCarsList = function(){
	$http.post(''+$rootScope.mainurl+'/api/cars/getCarsByUserId',$scope.user).
		success(function(data, status) {
			var typeName = "typeName";
			var modName = "modName";
			$.each(data, function (key,val){
			console.log($scope.man_opts);
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
									console.log(val.modName)
								}
						});
					}).error(function(data, status) {console.log(data);});
		
				}
			});
		console.log($scope.mod_opts)
			$scope.viewAdsJson = data;
			console.log($scope.viewAdsJson);
		}).error(function(data, status) {
			console.log(data);
			
		});
	}
	$scope.getCarsList();
	
	
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
								"car_type_id" : $scope.newCar_type,
								"car_model_id" : $scope.newCar_mod,
								"car_model": $scope.newCar.model,
								"year" : $scope.newCar.year,
								"type_geare" : ""+$scope.newCar.type+"",  //תיבת הילוכים
								"volume" : $scope.newCar.volume,     //נפח 
								"km" : $scope.newCar.km,         //ק"מ
								"color" : $scope.newCar.color,
								"price" : $scope.newCar.price,
								"car_url"  :""  
							};
console.log($scope.newAdJson)
		$http.post(''+$rootScope.mainurl+'/api/cars/save', $scope.newAdJson).
			success(function(data, status) {
				console.log(data);
				$scope.newCarID = data.car_id;
				$("#Upload").click();
				setTimeout(function() {console.log('3000');$scope.getCarsList();}, 3000)
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

	
	$scope.getNewTypeId = function(id){
	$scope.getListModel(id);
		if(id != "בחר יצרן"){
			$.each($scope.man_opts, function (key,val){
				if(val.name == id){
					$scope.updCar_type = val.id;
				}
			});
			$scope.listModel = {"carType_id" : $scope.updCar_type,"carType_Name":""};
					$http.post(''+$rootScope.mainurl+'/api/carmodels/get',$scope.listModel).
						success(function(data, status) {
							$scope.mod_opts = data;
					}).error(function(data, status) {console.log(data);});
		
			
		}
	}
	
	$scope.reset = function(){
		$scope.updateAdJson = {};
		$scope.updCar = {	
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
	}
	
	$scope.updateAd = function(id){
		
		/*$.each($scope.viewAdsJson, function (key,val){
			if($scope.viewAdsJson.car_id == id){
				if(!$scope.updCar.model){
					$scope.updCar_mod = $scope.viewAdsJson.car_model_id;
					if(!$scope.updCar_type){
						$scope.updCar_type = $scope.viewAdsJson.car_type_id;	
					}
				}else{
					$.each($scope.mod_opts, function (key,val){
						if(val.model_name == $scope.updCar.model){
							$scope.updCar_type = val.car_type_id;
							$scope.updCar_mod = val.car_model_id;
						}
					});
				}
				if(!$scope.updCar.year){$scope.updCar.year = val.year;}
				if(!$scope.updCar.type){$scope.updCar.type = val.type_geare;}
				if(!$scope.updCar.volume){$scope.updCar.volume = val.volume;}
				if(!$scope.updCar.km){$scope.updCar.km = val.km;}
				if(!$scope.updCar.color){$scope.updCar.color = val.color;}
				if(!$scope.updCar.price){$scope.updCar.price = val.price;}
			}
		}
		
		$scope.updateAdJson = {	"car_id" : id,
								"user_id" : $rootScope.cookieUserID,
								"car_type_id" : $scope.updCar_type,
								"car_model_id" : $scope.updCar_mod,
								"car_model": $scope.updCar.model,
								"year" : $scope.updCar.year,
								"type_geare" : ""+$scope.updCar.type+"",  //תיבת הילוכים
								"volume" : $scope.updCar.volume,     //נפח 
								"km" : $scope.updCar.km,         //ק"מ
								"color" : $scope.updCar.color,
								"price" : $scope.updCar.price,
								"car_url"  :""  
							};
			console.log($scope.updateAdJson);
			
	*/
		$http.post(''+$rootScope.mainurl+'/api/cars/save', $scope.updateAdJson).
			success(function(data, status) {
				console.log(data);
				$scope.getCarsList();
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
	
	$scope.deleteAd = function(id){
		
		$scope.deleteAdJson = {	
					"car_id" :  id
				};

		$http.post(''+$rootScope.mainurl+'/api/cars/deleteCar', $scope.deleteAdJson).
			success(function(data, status) {
				console.log(data);
				$scope.getCarsList();
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
