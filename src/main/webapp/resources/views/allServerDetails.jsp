<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
	<div ng-app="openstackUserApp" ng-controller="userListServersCtrl">
	
	<nav class="navbar" role="navigation" style="background-color: rgba(44, 116, 193, 0.76);">
			<a class="navbar-header" style="color: white;">Quick Stack</a>
			 <a class="navbar-header" style="color: white;padding-left: 12px;">Welcome {{authData.currentUser.username}}</a>
			<div class="navbar-header" style="float: right;">
				<span><a onclick="project()" href="#/createServers"  style="color: white;padding:10px;">Create New Project</a></span>
				<span><a onclick="logout()" href="#!/user/" style="color: white;padding:10px"><span class="glyphicon glyphicon-log-out"></span>Logout</a></span>
			</div>
			</nav>
			
		<div class="container-fluid">
			<div class="alert alert-success" ng-show="showSuccessAlert">
				<button type="button" class="close" data-ng-click="switchBool('showSuccessAlert')">×</button>
				<strong>Done!</strong> {{successTextAlert}}
			</div>

			<div class="alert alert-danger" ng-show="showErrorAlert">
				<button type="button" class="close" data-ng-click="switchBool('showErrorAlert')">×</button>
				<strong>Error!</strong> {{errorTextAlert}}
			</div>
			
			<div style="padding-left:520px; margin:111px;margin-left: 68px;">
				<img src="/resources/Images/allserver.png" style="cursor:pointer;max-width:100%;"/>
			</div>
			<div ng-show="isDataPresent">
				<div data-ng-repeat="vmMetadata in vmMetadataList">
					<div class="panel panel-default">
						<div class="panel-heading" toggle target="collapse{{ $index + 1 }}" style="background-color:#d4d6eb;color:#7b7b7c;">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" data-target="#collapse{{ $index + 1 }}" aria-expanded="true" aria-controls="collapse{{ $index + 1 }}">{{vmMetadata.project.projectName}} : Instance Number {{ $index + 1 }}</a>
							</h4>
						</div>
						<div id="collapse{{ $index + 1 }}" toggleable active-class="in" exclusion-group="accordion1" default="active"
							class="panel-collapse collapse in" style="background-color: #fafafa;">
							<div class="panel-body">
								<div class="table-responsive">
									<table id="mytable" class="table table-bordred table-striped">
										<thead>
											<th>Instance Name</th>
											<th>Flavor Name</th>
											<th>Image Name</th>
											<th>Network</th>
											<th>IP</th>
											<th>Status</th>
										</thead>
										<tbody>
											<td style="background-color: #fafafa;">{{vmMetadata.serverName}}</td>
											<td style="background-color: #fafafa;">{{vmMetadata.flavorName}}</td>
											<td style="background-color: #fafafa;">{{vmMetadata.imageName}}</td>
											<td style="background-color: #fafafa;">{{vmMetadata.networkName}}</td>
											<td style="background-color: #fafafa;">{{vmMetadata.ipAddress}}</td>
											<td style="background-color: #fafafa;">{{vmMetadata.status}}</td>
										</tbody>
									</table>
								</div>
								
								<div class="table-responsive">
									<table id="mytable2" class="table table-bordred table-striped">
										<thead>
											<th>Snapshot Name</th>
											<!-- <th>Restore</th> -->
										</thead>
										<tbody>
										<tr ng-repeat="snapShot in vmMetadata.snapShots">
											<td>{{snapShot.snapShotName}}
											<span data-placement="top" data-toggle="tooltip" title="Restore">
									<button class="btn btn-secondary btn-lg" name="restore" id="restore" value="Restore" ng-click="restore(vmMetadata, snapShot.snapShotName)" style="background-color:#fafafa;">
										<span class="glyphicon glyphicon-share"></span>
									</button>
								</span></td>
								</tr>
										</tbody>
									</table>
								</div>
								
								<div class="btn-group">
								<span data-placement="top" data-toggle="tooltip" title="Start">
									<button class="btn btn-secondary btn-lg" name="startServer" id="startServer" value="Start Server" ng-click="startServer(vmMetadata)" style="background-color:#fafafa;">
										<span class="glyphicon glyphicon-play-circle"></span>
									</button>
								</span>
								<span data-placement="top" data-toggle="tooltip" title="Stop">
									<button class="btn btn-secondary btn-lg" name="stopServer" id="stopServer" value="Stop Server" ng-click="stopServer(vmMetadata)" style="background-color:#fafafa;">
										<span class="glyphicon glyphicon-off"></span>
									</button>
								</span>
								<span data-placement="top" data-toggle="tooltip" title="Snapshot">
										<button ng-show="!showText" class="btn btn-secondary btn-lg" name="Snapshot" id="Snapshot" value="Snapshot" ng-click="showText=!showText" style="background-color:#fafafa;">
											<span class="glyphicon glyphicon-hdd"></span>
										</button>
										<span ng-show="showText">
											<input type="text" name="snapshotName" id="snapshot" placeholder="snapshot name" ng-model="snapshotName">
											<button class="btn btn-secondary btn-lg" name="createSnapshot" id="createSnapshot" value="Create Snapshot" ng-click="showText=!showText;createSnapshot(vmMetadata, snapshotName)" style="background-color:#fafafa;">
												<span class="glyphicon glyphicon-hdd"></span>
											</button>
											<button class="btn btn-secondary btn-lg" ng-click="showText=!showText" style="background-color:#fafafa;">
												<span class="glyphicon glyphicon-remove"></span>
											</button>
										</span>
								</span>
								</div>
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div style="float: right;padding-bottom: 20px;">
					<button class="btn btn-primary" name="generateBill" id="generateBill" value="Generate Bill" ng-click="generateBill()" ng-show="!showBtn">Generate Bill</button>
					<p ng-show="showBtn">Total Bill Amount = $ {{amount}}.0   <button class="btn btn-primary" name="endLab" id="endLab" value="End Lab" ng-click="endLab()" ng-show="showBtn">End Lab</button></p>
				</div>
		</div>
		</div>
		
</body>
</html>