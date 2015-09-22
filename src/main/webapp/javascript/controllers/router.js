var app = angular.module("Main", ['ngRoute']);

app.run(function ($rootScope, $location) {

/************************************************ 1.1.1 Global Variables ************************************************/
	//$rootScope.mainurl ='https://'+ location.host;
	/*routeChangeStart*/
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