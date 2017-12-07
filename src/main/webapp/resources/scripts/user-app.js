var userApp = angular.module('openstackUserApp', [ 'ngRoute', 'ngCookies' ]);

userApp.config(function($routeProvider) {

	$routeProvider.when('/user', {
		templateUrl : '/resources/views/userLogin.jsp',
		controller : 'userLoginCtrl'
	}).when('/signup', {
		templateUrl : '/resources/views/signUp.jsp',
		controller : 'userLoginCtrl'
	}).when('/createServers', {
		templateUrl : '/resources/views/createServers.jsp',
		controller : 'createServersCtrl'
	}).when('/singleServerDetails/:id', {
		templateUrl : '/resources/views/singleServerDetails.jsp',
		controller : 'userListServersCtrl'
	}).when('/twoServerDetails/:id', {
		templateUrl : '/resources/views/twoServerDetails.jsp',
		controller : 'userListServersCtrl'
	}).when('/allServerDetails/:id', {
		templateUrl : '/resources/views/allServerDetails.jsp',
		controller : 'userListServersCtrl'
	}).otherwise({
		redirectTo : "/user"
	});
});

userApp.controller("userLoginCtrl",
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
												$location.path('/createServers');
											} else {
												$scope.showErrorAlert = true;
												$scope.errorTextAlert = "Username/password is incorrect";
											}
										})
					};
					
					$scope.signUpForm = function() {
						AuthenticationService.signUp($scope.userName, $scope.firstName, $scope.lastName, $scope.department, $scope.password, function(response) {
											if (response.data.statusCode === '200') {
												AuthenticationService.SetCredentials($scope.userName, response.data.firstName, response.data.lastName, response.data.department);
												$location.path('/user');
											} else {
												$scope.showErrorAlert = true;
												$scope.errorTextAlert = "User already exists";
											}
										})
					};

					$scope.switchBool = function(value) {
						$scope[value] = !$scope[value];
					};
				} ]);

userApp.factory('AuthenticationService', [
		'$http',
		'$cookieStore',
		'$rootScope',
		'$timeout',
		function($http, $cookieStore, $rootScope, $timeout) {
			var service = {};

			service.Login = function(username, password, callback) {

				var req = {
					method : 'POST',
					url : "/login/action",
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

				$http.defaults.headers.common['Authorization'] = 'Basic '
						+ authdata;
				$cookieStore.put('globals', $rootScope.globals);
			};

			service.ClearCredentials = function() {
				$rootScope.globals = {};
				$cookieStore.remove('globals');
				$http.defaults.headers.common.Authorization = 'Basic ';
			};
			
			service.signUp = function(username, firstName, lastName,
					department, password, callback) {

				var req = {
					method : 'POST',
					url : "/signup",
					data : {
						username : username,
						firstname : firstName,
						lastname : lastName,
						department : department,
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

			return service;
		} ]);

userApp.controller("userListServersCtrl", [ '$scope', '$http', '$location', '$routeParams',
	'$q', '$cookieStore',
	function($scope, $http, $location, $routeParams, $q, $cookieStore) {
	$scope.id = $routeParams.id;

	var init = function() {
		$scope.authData = $cookieStore.get('globals');
		if (!$scope.authData) {
			$location.path('/user');
		}
		getServerList();
	};
	
	var getServerList = function() {
			var req = {
					params : {
						"userName" : $scope.authData.currentUser.username,
						"projectId" : $scope.id
					},
					method : 'GET',
					url : "/project/server/details",
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
	
	$scope.createSnapshot = function(vmMetadata, snapshotName) {
		var req = {
				data : {
					"serverId" : vmMetadata.serverId,
					"serverName" : vmMetadata.serverName,
					"snapshotName" : snapshotName
				},
				method : 'POST',
				url : "/snapshot",
				headers : {
					'Content-Type' : "application/json"
				}
		};
		
		$http(req).then(function(response) {
			$scope.showSuccessAlert = true;
			$scope.successTextAlert = "Snapshot Created Successfully!";
		}, function(error) {
			$scope.showErrorAlert = true;
			$scope.errorTextAlert = "Snapshot creation Failed!";
		}); 
	};
	
	$scope.restore = function(vmMetadata, snapshotName) {
		var req = {
				data : {
					"projectId" : vmMetadata.project.id,
					"serverName" : vmMetadata.serverName,
					"snapshotName" : snapshotName,
					"userName" : $scope.authData.currentUser.username
				},
				method : 'POST',
				url : "/restore",
				headers : {
					'Content-Type' : "application/json"
				}
		};
		
		$http(req).then(function(response) {
			$scope.showSuccessAlert = true;
			$scope.successTextAlert = "Snapshot Restored Successfully!";
		}, function(error) {
			$scope.showErrorAlert = true;
			$scope.errorTextAlert = "Snapshot Restoration Failed!";
		}); 
	};
	
	$scope.generateBill = function() {
		
		var req = {
				params : {
					"userName" : $scope.authData.currentUser.username,
					"projectId" : $scope.id
				},
				method : 'POST',
				url : "/generate/bill",
				headers : {
					'Content-Type' : "application/json"
				}
		};
		
		$http(req).then(function(response) {
			$scope.amount = response.data;
			$scope.showBtn=!$scope.showBtn
		}, function(error) {
			$scope.showErrorAlert = true;
			$scope.errorTextAlert = "Billing Failed";
		});
	};
	
	$scope.endLab = function() {
		var req = {
				params : {
					"userName" : $scope.authData.currentUser.username,
					"projectId" : $scope.id
				},
				method : 'POST',
				url : "/compute/deleteServer",
				headers : {
					'Content-Type' : "application/json"
				}
		};
		
		$http(req).then(function(response) {
			$location.path('/createServers');
		}, function(error) {
			$scope.showErrorAlert = true;
			$scope.errorTextAlert = "End Lab Failed";
		});
	};
	
	$scope.switchBool = function(value) {
		$scope[value] = !$scope[value];
	};
	init();
	} ]);


userApp.controller("createServersCtrl", [ '$scope', '$http', '$location', '$routeParams',
	'$q', '$cookieStore',
	function($scope, $http, $location, $routeParams, $q, $cookieStore) {

		var init = function() {
			$scope.authData = $cookieStore.get('globals');
			if (!$scope.authData) {
				$location.path('/user');
			}
			
			getAllProjects();
			getAllServers();
		};
		
		var getAllProjects = function() {
			var req = {
					params : {
						"userName" : $scope.authData.currentUser.username
					},
					method : 'GET',
					url : "/all/projects",
					headers : {
						'Content-Type' : "application/json"
					}
			};
			
			$http(req).then(function(response) {
				$scope.allProjects = response.data.projectResponse;
				$scope.isProjectPresent = true;
			}, function(error) {
				$scope.isProjectPresent = false;
			});
			
		};
		
		var getAllServers = function() {
			var req = {
					params : {
						"userName" : $scope.authData.currentUser.username
					},
					method : 'GET',
					url : "/server/list",
					headers : {
						'Content-Type' : "application/json"
					}
			};
			
			$http(req).then(function(response) {
				$scope.servers = response.data.vmMetadata;
				$scope.isServersPresent = true;
			}, function(error) {
				$scope.isServersPresent = false;
			});
		};
		
		$scope.createServer = function(project) {
			var req = {
					method : 'POST',
					url : "/compute/createServer/project",
					data : {
						projectId : project.id,
						userName : $scope.authData.currentUser.username
					},
					headers : {
						'Content-Type' : "application/json"
					}
				};
				$http(req).then(function(response) {
					$scope.showSuccessAlert = true;
					$scope.successTextAlert = "Successfully Created Resources!";
					project.projectCreated = true;
					getAllServers();
				}, function(error) {
					$scope.showErrorAlert = true;
					$scope.errorTextAlert = "Resource creation Failed";
				});
			
		};
		
		$scope.navigatePage = function(vmMetadata) {
			if(vmMetadata.project.noOfInstances == 1) {
				$location.path('/singleServerDetails/' + vmMetadata.project.id);
			} else if(vmMetadata.project.noOfInstances == 2) {
				$location.path('/twoServerDetails/' + vmMetadata.project.id);
			} else {
				$location.path('/allServerDetails/' + vmMetadata.project.id);
			}
		};
		
		$scope.switchBool = function(value) {
			$scope[value] = !$scope[value];
		};
		init();
	} ]);