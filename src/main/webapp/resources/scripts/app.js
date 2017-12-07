var app = angular.module('openstackApp', [ 'ngRoute', 'ngCookies', 'angular.directives-round-progress'  ]);

app.config(function($routeProvider) {

	$routeProvider.when('/', {
		templateUrl : '/resources/views/login.jsp',
		controller : 'loginCtrl'
	}).when('/openStackMetrics', {
		templateUrl : '/resources/views/openStackMetrics.jsp',
		controller : 'openStackMetricsCtrl'
	}).when('/listServers', {
		templateUrl : '/resources/views/listServers.jsp',
		controller : 'listServersCtrl'
	}).when('/createProjects', {
		templateUrl : '/resources/views/createProjects.jsp',
		controller : 'createProjectsCtrl'
	}).otherwise({
		redirectTo : "/"
	});
});

app.controller("loginCtrl",
				[
						'$scope',
						'$http',
						'$location',
						'$routeParams',
						'AuthenticationService',
						function($scope, $http, $location, $routeParams, AuthenticationService) {

							AuthenticationService.ClearCredentials();

							$scope.loginForm = function() {
								AuthenticationService.Login($scope.userName,$scope.password,function(response) {
													if (response.data.statusCode === '200') {
														AuthenticationService.SetCredentials($scope.userName);
														$location.path('/openStackMetrics');
													} else {
														$scope.showErrorAlert = true;
														$scope.errorTextAlert = "Username/password is incorrect";
													}
												})
							};

							$scope.switchBool = function(value) {
								$scope[value] = !$scope[value];
							};
						} ]);

app.factory('AuthenticationService', [
	'$http',
	'$cookieStore',
	'$rootScope',
	'$timeout',
	function($http, $cookieStore, $rootScope, $timeout) {
		var service = {};

		service.Login = function(username, password, callback) {

			var req = {
				method : 'POST',
				url : "/admin/login",
				data : {
					userName : username,
					password : password
				},
				headers : {
					'Content-Type' : "application/json"
				}
			};
			$http(req).then(function(response) {
				callback(response);
			}, function(error) {
				callback(error);
			});
		};

		service.SetCredentials = function(username, firstName, lastName,
				department) {
			var authdata = username;

			$rootScope.globals = {
				currentUser : {
					username : username,
					authdata : authdata
				}
			};

			$http.defaults.headers.common['Authorization'] = 'Basic '+ authdata;
			$cookieStore.put('globals', $rootScope.globals);
		};

		service.ClearCredentials = function() {
			$rootScope.globals = {};
			$cookieStore.remove('globals');
			$http.defaults.headers.common.Authorization = 'Basic ';
		};

		return service;
	} ]);

app.controller("listServersCtrl", [ '$scope', '$http', '$location', '$routeParams',
	'$q', '$cookieStore',
	function($scope, $http, $location, $routeParams, $q, $cookieStore, $modal) {

		var init = function() {
			$scope.authData = $cookieStore.get('globals');
			if (!$scope.authData) {
				$location.path('/');
			}
			getServerList();
		};
		
		var getServerList = function() {

				var req = {
						method : 'GET',
						url : "/all/server/list",
						headers : {
							'Content-Type' : "application/json"
						}
				};
				
				$http(req).then(function(response) {
					$scope.vmMetadataList = response.data.vmMetadata;
					$scope.isDataPresent = true;
				}, function(error) {
					$scope.isDataPresent = false;
				});
			};
			
		$scope.startServer = function(vmMetadata) {
			var req = {
					params : {
						"serverName" : vmMetadata.serverName
					},
					method : 'POST',
					url : "/compute/startServer",
					headers : {
						'Content-Type' : "application/json"
					}
			};
			
			$http(req).then(function(response) {
				$scope.showSuccessAlert = true;
				$scope.successTextAlert = "Instance Started Successfully!";
				for (var i = 0; i < $scope.vmMetadataList.length; i++) {
					if ($scope.vmMetadataList[i].id == vmMetadata.id) {
						$scope.vmMetadataList[i].status = "ACTIVE";
						break;
					}
				}
			}, function(error) {
				$scope.showErrorAlert = true;
				$scope.errorTextAlert = "Instance Start Failed!";
			});
		};
		
		$scope.stopServer = function(vmMetadata) {
			var req = {
					params : {
						"serverName" : vmMetadata.serverName
					},
					method : 'POST',
					url : "/compute/stopServer",
					headers : {
						'Content-Type' : "application/json"
					}
			};
			
			$http(req).then(function(response) {
				$scope.showSuccessAlert = true;
				$scope.successTextAlert = "Instance Stopped Successfully!";
				for (var i = 0; i < $scope.vmMetadataList.length; i++) {
					if ($scope.vmMetadataList[i].id == vmMetadata.id) {
						$scope.vmMetadataList[i].status = "SHUTOFF";
						break;
					}
				}
			}, function(error) {
				$scope.showErrorAlert = true;
				$scope.errorTextAlert = "Instance Stop Failed!";
			});
		};

		$scope.switchBool = function(value) {
			$scope[value] = !$scope[value];
		};
		init();
	} ]);

app.controller("createProjectsCtrl", [
		'$scope',
		'$http',
		'$location',
		'$routeParams',
		'$q',
		'$cookieStore',
		function($scope, $http, $location, $routeParams, $q,
				$cookieStore) {

			var init = function() {
				$scope.authData = $cookieStore.get('globals');
				if (!$scope.authData) {
					$location.path('/');
				}

				getFlavorList();
			};
			
			var getFlavorList = function() {

				var req = {
					method : 'GET',
					url : "/compute/flavorlist",
					headers : {
						'Content-Type' : "application/json"
					}
				};

				$http(req).then(function(response) {
					console.log(response);
					$scope.flavors = response.data;
				}, function(error) {
				});
			};

			$scope.createProjectForm = function() {
				
				var req = {
						method : 'POST',
						url : "/create/project",
						data : {
							projectName : $scope.projectName,
							noOfInstances: $scope.noOfInstances,
							description : $scope.description,
							flavor : $scope.selectedFlavor,
							imageName : $scope.imageName,
							costPerHour : $scope.costPerHour
						},
						headers : {
							'Content-Type' : "application/json"
						}
					};
				$http(req).then(function(response) {
					$scope.showSuccessAlert = true;
					$scope.successTextAlert = "Project Created Successfully!";
				}, function(error) {
					$scope.showErrorAlert = true;
					$scope.errorTextAlert = "Project creation Failed!";
				}); 
			};
			$scope.switchBool = function(value) {
				$scope[value] = !$scope[value];
			};
			init();
		} ]);


app.controller("openStackMetricsCtrl", [ '$scope', '$http', '$location', '$routeParams',
	'$q', '$cookieStore',
	function($scope, $http, $location, $routeParams, $q, $cookieStore) {
		
		var init = function() {
			$scope.authData = $cookieStore.get('globals');
			if (!$scope.authData) {
				$location.path('/');
			}
		};

		$scope.switchBool = function(value) {
			$scope[value] = !$scope[value];
		};
		init();
		
		$scope.cpuLabel = "Loading metrics...";
		$scope.memLabel = "Loading metrics...";
		$scope.diskLabel = "Loading metrics...";
		var req = {
				method : 'GET',
				url : "/compute/diagnostics/cli",
				headers : {
					'Content-Type' : "application/json"
				}
			};

			$http(req).then(function(response) {
				
				
				console.log(response);
				$scope.cliData = response.data;
				
				var memRatio = Math.round($scope.cliData.memoryMB/$scope.cliData.totMemoryMB * 100) / 100;
				var memPercent = memRatio*100;
				$scope.memData = {
						  label: memPercent + "%",
						  percentage: memRatio
						};
				$scope.memLabel = $scope.cliData.memoryMB +" of " +$scope.cliData.totMemoryMB + "MB";
				
				var cpuRatio = Math.round($scope.cliData.cpuNumb/$scope.cliData.totCpuNumb* 100) / 100;
				var cpuPercent = cpuRatio*100;
				$scope.cpuData = {
						  label: cpuPercent + "%",
						  percentage: cpuRatio
						};
				$scope.cpuLabel = $scope.cliData.cpuNumb + " of " + $scope.cliData.totCpuNumb;
				
				var diskRatio = Math.round($scope.cliData.diskGB/$scope.cliData.totDiskGB * 100) / 100;
				var diskPercent = memRatio*100;
				$scope.diskData = {
						  label: diskPercent + "%",
						  percentage: diskRatio
						};		
				$scope.diskLabel = $scope.cliData.diskGB+" of "+$scope.cliData.totDiskGB+"GB";
			}, function(error) {
			});
	} ]);

