function AccountCtrl($scope,$http, $rootScope, $location, $route) {

/****************************************** GET and UPDATE USER DATA *********************************************/	
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
	
/******************************************************* GET CARS BY USER *******************************************/
	$scope.user = {"user_id" : $rootScope.cookieUserID};
	console.log($scope.user)
	$scope.getCarsList = function(){
	$scope.newImage='';
	$http.post(''+$rootScope.mainurl+'/api/cars/getCarsByUserId',$scope.user).
		success(function(data, status) {
			var typeName = "typeName";
			var modName = "modName";
			var viewImg = "viewImg";
			var editView = "editView";
			$.each(data, function (key,val){
			val.editView = 0;
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
				if(val.image != '' && val.image != 'undefined'){
					val.viewImg = 1;
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
	
/***************************************************** UPLOAD PIC *********************************************/	
$scope.dataFile = '';
$scope.$watch(function() {
	return $scope.dataFile;
});

$scope.newImage = '';
$scope.$watch(function() {
	return $scope.newImage;
});

function readImage(input,s) {
	console.log(input)
    if ( input.files && input.files[0] ) {
        var FR= new FileReader();
        FR.onload = function(e) {
            if(s==0){
				$scope.dataFile = e.target.result;
				$scope.$apply();
			}
			if(s==1){
				$scope.newImage = e.target.result;
				$scope.$apply();
			}
        };       
        FR.readAsDataURL( input.files[0] );
    }
}

$("#Upload").change(function(){
    readImage( this ,0);
});

$scope.changePic = function(th){
		readImage( th ,1);
	}
/*********************************************** NEW CAR *********************************************/	
	$scope.addView = 0;
	$scope.newCarPreloader = false;
	$scope.newCar = {"car_type" : "","model" : "","year" : "","type" : "","volume" : "","km" : "","color" :"","price" : "","text" : ""};
				
	$scope.newAd = function(){
	$scope.newCarPreloader = true;
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
								"car_url"  :"" ,
								"file"  : $scope.dataFile
							};
console.log($scope.newAdJson)
		$http.post(''+$rootScope.mainurl+'/api/cars/save', $scope.newAdJson).
			success(function(data, status) {
				console.log(data);
				$scope.getCarsList();
				$scope.reset('a');
			}).error(function(data, status) {
				console.log(data);
				$scope.reset('a');
			});
	}
	
/*******************************************************************************************************/
$scope.reset = function(e){
	if(e == 'a'){
		$scope.addView = 0;
		$scope.newCar = {"car_type" : "","model" : "","year" : "","type" : "","volume" : "","km" : "","color" :"","price" : "","text" : ""};
		$scope.newAdJson = {};
		$scope.dataFile = '';
		$scope.newCarPreloader = false;
	}else{
		$.each($scope.viewAdsJson, function (key,val){
			val.editView = 0;
		});
		$scope.editCarPreloader = false;
		$scope.newImage = '';
		$scope.updateAdJson = {};
		$scope.updCar = {"car_type" : "","model" : "","year" : '',"type" : "","volume" : '',"km" : '',"color" :"","price" : '',"text" : ""};
	}
}

$scope.editViewFun = function(n){
	$scope.reset('u');
}
/***************************************************  UPDATE CAR ****************************************/	
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
						console.log(data)
				}).error(function(data, status) {console.log(data);});
		}
	}
	
	$scope.editCarPreloader = false;
	
	$scope.updateAd = function(id){
	$scope.editCarPreloader = true;
		console.log($scope.updCar)
		$.each($scope.viewAdsJson, function (key,val){
			if(val.car_id == id){
			//console.log(val)
				if($scope.updCar.car_type != "" && $scope.updCar.car_type != "בחר יצרן" && $scope.updCar.car_type != "undefined"){
					$.each($scope.man_opts, function (k,v){
						if($scope.updCar.car_type == v.name){
							$scope.updCar_type = v.id;
						}
					});
					
					if($scope.updCar.model != "" && $scope.updCar.model != "בחר דגם"){
						$.each($scope.mod_opts, function (k,v){
							if(v.model_name == $scope.updCar.model){
								$scope.updCar_mod = v.car_model_id;
							}
						});
					}else{
						$scope.updCar_mod = '';
					}
				}else{
					$scope.updCar_type = val.car_type_id;
					$scope.updCar_mod = val.car_model_id;
					console.log(val.car_type_id);
					console.log($scope.updCar_type);
				}

				if(!$scope.updCar.year){$scope.updCar.year = val.year;}
				if(!$scope.updCar.type){$scope.updCar.type = val.type_geare;}
				if(!$scope.updCar.volume){$scope.updCar.volume = val.volume;}
				if(!$scope.updCar.km){$scope.updCar.km = val.km;}
				if(!$scope.updCar.color){$scope.updCar.color = val.color;}
				if(!$scope.updCar.price){$scope.updCar.price = val.price;}
				if($scope.newImage == ''){$scope.newImage = val.image;}
			}
		})
		
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
								"car_url"  :"" ,
								"file"  : $scope.newImage
							};
			console.log($scope.updateAdJson);
			
	
		$http.post(''+$rootScope.mainurl+'/api/cars/save', $scope.updateAdJson).
			success(function(data, status) {
				console.log(data);
				$scope.getCarsList();
				$scope.reset('u');
				
			}).error(function(data, status) {
				console.log(data);
				$scope.reset('u');
			});
	}
/******************************************************* DELETE CAR ***************************************************/	
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

}
