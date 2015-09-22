function AccountCtrl($scope,$http, $rootScope, $location, $route) {
	$scope.userData = {"id" : 123,"dataName" : {"name" : "מריה","edit" : 0},"dataPhone" : {"phone" : "054-803-24-24","edit" : 0},"dataEmail" : {"email" : "ksksk@gmail.com","edit" : 0}};
	$scope.addView = 0;
	$scope.editView =0;
}
