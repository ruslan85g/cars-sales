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

		$http.post(''+$rootScope.mainurl+'/updateUname', $scope.updateUnameJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
	
	$scope.updateUphone = function(){
		
		$scope.updateUphoneJson = {"userId" : "string","phone" : "string" };

		$http.post(''+$rootScope.mainurl+'/updateUphone', $scope.updateUphoneJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
	
	$scope.updateUmail = function(){
		
		$scope.updateUmailJson = {"userId" : "string","email" : "string" };

		$http.post(''+$rootScope.mainurl+'/updateUmail', $scope.updateUmailJson).
			success(function(data, status) {
				console.log(data);
				
			}).error(function(data, status) {
				console.log(data);
				
			});
	}
}
