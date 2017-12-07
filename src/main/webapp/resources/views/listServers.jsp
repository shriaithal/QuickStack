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
	<div data-ng-app="openstackApp" data-ng-controller="listServersCtrl">

		
	<!-- <nav class="navbar" role="navigation" style="background-color: rgba(44, 116, 193, 0.76);">
			<a class="navbar-brand" style="/* margin-left: -588px; */color: white;">Quick Stack</a> <a class="navbar-right" style="margin-right: auto;margin-right:1480px;margin-botton:-34px;margin-top:15px;color: white;">Welcome {{authData.currentUser.username}}</a>
			<div class="navbar-header" style="margin-left:1500px;margin-bottom:-3px;margin-top:-17px;">
				<a onclick="logout()" href="#!login" style="/* margin-right: -320px; */color: white;"><span class="glyphicon glyphicon-log-out"></span>Logout</a>
			</div>
		</nav>


		<div style="margin-top: -3.5%;" class="row col-md-12">
			<div class="col-md-8" style="color: white;"></div>
			<div class="col-md-4">
				<div class="col-md-12" style="margin-left: 31%; max-width: 86%;margin-left: -278px;
    margin-top: 13px;">

					<div class="col-md-6">
						<a href="#/createProjects" style="color: white; color: white;">Create
							Project</a>
					</div>
					<div class="col-md-6" style="padding-left: 3%;">
						<a href="#/openStackMetrics" style="color: white; color: white;">OpenStack
							Metrics</a>
					</div>
				</div>
			</div>

		</div> -->
		
			<nav class="navbar" role="navigation" style="background-color: rgba(44, 116, 193, 0.76);">
			<a class="navbar-header" style="color: white;">Quick Stack</a>
			 <a class="navbar-header" style="color: white;padding-left: 12px;">Welcome {{authData.currentUser.username}}</a>
			<div class="navbar-header" style="float: right;">
				<span><a onclick="project()" href="#/createProjects"  style="color: white;padding:10px;">Create Project</a></span>
				<span><a onclick="metrics()" href="#/openStackMetrics"  style="color: white;padding:10px">Openstack Metrics</a></span>
				<span><a onclick="logout()" href="#!login" style="color: white;padding:10px"><span class="glyphicon glyphicon-log-out"></span>Logout</a></span>
			</div>
			</nav>

		<div class="container-fluid">
			<div class="alert alert-success" ng-show="showSuccessAlert">
				<button type="button" class="close"
					data-ng-click="switchBool('showSuccessAlert')">×</button>
				<strong>Done!</strong> {{successTextAlert}}
			</div>

			<div class="alert alert-danger" ng-show="showErrorAlert">
				<button type="button" class="close"
					data-ng-click="switchBool('showErrorAlert')">×</button>
				<strong>Error!</strong> {{errorTextAlert}}
			</div>
			<!-- <p><a href="#/login">Logout</a></a></p> -->
			<!-- <p><a href="#/openStackMetrics">OpenStack Metrics</a></a></p>
			<p><a href="#/createProjects">Create Project</a></a></p> -->
			<div ng-show="isDataPresent">
				<div class="table-responsive">
					<table id="mytable" class="table table-bordred table-striped">
						<thead>
							<th>Instance Name</th>
							<th>Flavor Name</th>
							<th>Image Name</th>
							<th>Network</th>
							<th>IP</th>
							<th>Status</th>
							<th>Start</th>
							<th>Stop</th>
						</thead>

						<tbody>

							<tr data-ng-repeat="vmMetadata in vmMetadataList">
								<td>{{vmMetadata.serverName}}</td>
								<td>{{vmMetadata.flavorName}}</td>
								<td>{{vmMetadata.imageName}}</td>
								<td>{{vmMetadata.networkName}}</td>
								<td>{{vmMetadata.ipAddress}}</td>
								<td>{{vmMetadata.status}}</td>
								<td><p data-placement="top" data-toggle="tooltip"
										title="Start">
										<button class="btn-xs" name="startServer" id="startServer"
											value="Start Server" ng-click="startServer(vmMetadata)">
											<span class="glyphicon glyphicon-play-circle"></span>
										</button>
									</p></td>
								<td><p data-placement="top" data-toggle="tooltip"
										title="Stop">
										<button class="btn-xs" name="stopServer" id="stopServer"
											value="Stop Server" ng-click="stopServer(vmMetadata)">
											<span class="glyphicon glyphicon-off"></span>
										</button>
									</p></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>

			<div ng-show="!isDataPresent">
				<p style="text-align: center; font-size: larger;">No Instances
					Found</p>
			</div>
		</div>
	</div>
</body>