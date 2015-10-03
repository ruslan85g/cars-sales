var app = angular.module("Main", ['ngRoute','ngCookies']);

app.run(function ($rootScope, $location) {

/************************************************ 1.1.1 Global Variables ************************************************/
	$rootScope.mainurl ='https://cars-sales.appspot.com';
	$rootScope.userStatus = 0;
	$rootScope.cookieUserID;
	//$rootScope.locPath;
});
	
	app.config (function ($routeProvider, $locationProvider) {
	
	//$locationProvider.html5Mode({enabled:true, requireBase:false});
	//$locationProvider.hashPrefix('!');

	$routeProvider.when('/', {
                templateUrl: 'templates/home.html'
        }).when('/account', {
                templateUrl: 'templates/account.html',
				controller: AccountCtrl		
     	})
		.otherwise({redirectTo: '/'});	
		
		});