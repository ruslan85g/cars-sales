function AccountCtrl($scope,$http, $rootScope, $location, $route) {
	$scope.userData = {"id" : 123,"dataName" : {"name" : "מריה","edit" : 0},"dataPhone" : {"phone" : "054-803-24-24","edit" : 0},"dataEmail" : {"email" : "ksksk@gmail.com","edit" : 0}};
	$scope.addView = 0;
	$scope.editView =0;
	
	$scope.viewAds = function(){
		
		$scope.viewAdsJson = {"userId" : "string"};

		$http.post(''+$rootScope.mainurl+'/viewAds', $scope.viewAdsJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
	
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
		
		$scope.newAdJson = {	
					"userId" : "string",
					"ad" :  {
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
				};

		$http.post(''+$rootScope.mainurl+'/newAd', $scope.newAdJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
	
	$scope.updateAd = function(){
		
		$scope.updateAdJson = {	
					"userId" : "string",
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

		$http.post(''+$rootScope.mainurl+'/updateAd', $scope.updateAdJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
	
	$scope.deleteAd = function(){
		
		$scope.deleteAdJson = {	
					"userId" : "Long",
					"id" :  num
				};

		$http.post(''+$rootScope.mainurl+'/deleteAd', $scope.deleteAdJson).
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
	
	
	$scope.updateUname = function(){
		
		$scope.updateUnameJson = {"userId" : "string","name" : "string" };

		$http.post(''+$rootScope.mainurl+'/users/updateName', $scope.updateUnameJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
	
	$scope.updateUphone = function(){
		
		$scope.updateUphoneJson = {"userId" : "string","phone" : "string" };

		$http.post(''+$rootScope.mainurl+'/users/updatePhone', $scope.updateUphoneJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
	
	$scope.updateUmail = function(){
		
		$scope.updateUmailJson = {"userId" : "string","email" : "string" };

		$http.post(''+$rootScope.mainurl+'/users/updateEmail', $scope.updateUmailJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
}
