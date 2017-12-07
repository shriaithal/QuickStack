<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div data-ng-app="openstackApp" data-ng-controller="createProjectsCtrl"
		class="ng-scope">


		
	
			<nav class="navbar" role="navigation" style="background-color: rgba(44, 116, 193, 0.76);">
			<a class="navbar-header" style="color: white;">Quick Stack</a>
			 <a class="navbar-header" style="color: white;padding-left: 12px;">Welcome {{authData.currentUser.username}}</a>
			<div class="navbar-header" style="float: right;">
				<span><a onclick="list()" href="#/listServers"  style="color: white;padding:10px;">List Servers</a></span>
				<span><a onclick="metrics()" href="#/openStackMetrics"  style="color: white;padding:10px">Openstack Metrics</a></span>
				<span><a onclick="logout()" href="#!login" style="color: white;padding:10px"><span class="glyphicon glyphicon-log-out"></span>Logout</a></span>
			</div>
			</nav>




		<div class="container-fluid">
			<div class="alert alert-success ng-binding ng-hide"
				ng-show="showSuccessAlert">
				<button type="button" class="close"
					data-ng-click="switchBool('showSuccessAlert')">×</button>
				<strong>Done!</strong>
			</div>

			<div class="alert alert-danger ng-binding ng-hide"
				ng-show="showErrorAlert">
				<button type="button" class="close"
					data-ng-click="switchBool('showErrorAlert')">×</button>
				<strong>Error!</strong>
			</div>
			<h1>
				<center>Create Project</center>
			</h1>
			<!-- <p><a href="#/login">Logout</a></a></p> -->
			<!-- <p><a href="#/listServers">List Server</a></p>
			<p><a href="#/openStackMetrics">OpenStack Metrics</a></p> -->

			<form class="form-signin ng-pristine ng-invalid ng-invalid-required"
				name="createProject" ng-submit="createProjectForm()">
				<div class="container">
					<div class="row">
						<div class="col-md-6">
							<p class="input_title">
								Project Name <input
									class="form-control ng-pristine ng-invalid ng-invalid-required"
									type="text" name="projectName" ng-model="projectName"
									id="projectName" required="" autofocus="">
							</p>
							<div class="form-group">

								<p class="input_title">
									Number of instances
									<!-- <input type="number" name='noOfInstances' ng-model='noOfInstances' id='noOfInstances' class="login_box"  required> -->
									<select class="form-control ng-pristine ng-valid"
										ng-model="noOfInstances" ng-options="n for n in [1,2,3]"><option
											value="?"></option>
										<option value="0">1</option>
										<option value="1">2</option>
										<option value="2">3</option></select>
								</p>
							</div>
							<p class="input_title">
								Cost/hour <input
									class="form-control ng-pristine ng-invalid ng-invalid-required"
									type="text" name="costPerHour" ng-model="costPerHour"
									id="costPerHour" required="" autofocus="">
							</p>
							<p class="input_title">
								Flavor List <select class="form-control ng-pristine ng-valid"
									ng-model="selectedFlavor" name="selectedFlavor"
									id="selectedFlavor"><option ng-repeat="option in flavors" value="{{option.name}}">{{option.name}}</option>
									<!-- ngRepeat: option in flavors -->
								</select>
							</p>
							<p class="input_title">
								Image List
								<!-- <input type="number" name='noOfInstances' ng-model='noOfInstances' id='noOfInstances' class="login_box"  required> -->
								<select class="form-control ng-pristine ng-valid"
									ng-model="imageName" ng-options="n for n in ['cirros']"><option
										value="?"></option>
									<option value="0">cirros</option></select>
							</p>

							<p class="input_title">Description</p>
							<textarea
								class="form-control ng-pristine ng-invalid ng-invalid-required"
								rows="10" name="description" ng-model="description"
								id="description" cols="50" required="">           </textarea>

							<br>
							<br>
							<div class="row text-center" style="">
								<div class="col-xs-3"></div>
								<div class="col-xs-3 buttons-login">

									<input name="submit" type="submit" id="submitForm"
										value="Create Project" class="btn btn-primary btn-sm">
								</div>

								<div class="col-xs-3 buttons-login">
									<input name="cancel" type="reset" id="resetForm" value="Cancel"
										class="btn btn-primary btn-sm">
								</div>
								<div class="col-xs-3"></div>
							</div>
						</div>
						<br>
						<br> <br>
						<br> <br>
						<br>
						<!-- <div class="row">
      	<a href="#!signup" style="font-size: small; padding-left: 15px;">New User? Sign up here!</a>
      	</div> -->
						<!-- /form -->
					</div>
				</div>
		</div>
		</form>
	</div>
	</div>
	<br></br>

	<br></br>
	<br></br>
	<br></br>
	<!-- <div class="row">
      	<a href="#!signup" style="font-size: small; padding-left: 15px;">New User? Sign up here!</a>
      	</div> -->
	</form>
	<!-- /form -->
	</div>
	</div>
</body>