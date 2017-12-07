<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Open Stack</title>
</head>
<style>
.btn.btn-default {
    background-color: steelblue;
    color: #fff;
    border-radius: 0;
    border: none;
    padding: 13px 20px;
    font-size: 15px;
    font-weight: 600;
   margin-top: 0px;
    margin-left: 78px;
}
.loader {
    border: 16px solid #f3f3f3; /* Light grey */
    border-top: 16px solid #3498db; /* Blue */
    border-radius: 50%;
    width: 120px;
    height: 120px;
    animation: spin 2s linear infinite;
 }

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}

.hide-loader{
display:none;
}
</style>
<body>
	<div ng-app="openstackUserApp" ng-controller="createServersCtrl">
	
	
		<nav class="navbar" role="navigation" style="background-color: rgba(44, 116, 193, 0.76);">
			<a class="navbar-header" style="color: white;">Quick Stack</a>
			 <a class="navbar-header" style="color: white;padding-left: 12px;">Welcome {{authData.currentUser.username}}</a>
			<div class="navbar-header" style="float: right;">
				<span><a onclick="logout()" href="#!/user/" style="color: white;padding:10px"><span class="glyphicon glyphicon-log-out"></span>Logout</a></span>
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
		</div>
		
		<div class="container">
			<div class="row">
				<div class="col-xs-4"   data-ng-repeat="project in allProjects">
					<div class="well">
						<h2 class="muted" style=" margin-left: 90px;">{{project.projectName}}</h2>
						<ul>
							<li>Project Description : {{project.description}}</li>
							<li>Flavor Name : {{project.flavor.name}}</li>
							<li>Image Name : {{project.imageName}}</li>
							<li>Number of Instances : {{project.noOfInstances}}</li>
						</ul>
						<hr><h3 class="ng-binding" style="margin: center;margin-left: 90px;">$ {{project.costPerHour}}.0/hour</h3><hr>
						<p data-placement="top" data-toggle="tooltip" title="createServer">
						<!-- <div class="loader" id="loader">
						</div> -->
									
									<button class="btn btn-default" name="createServer" id="createServer" value="Create Server" ng-click="createServer(project)" ng-show="!project.projectCreated">
										<span>Create Server</span>
									</button>
									
									<button class="btn btn-default" name="createServer" id="createServer" value="Create Server" disabled ng-show="project.projectCreated">
										<span>Create Server</span>
									</button>
								</p>
					</div>
				</div>
			</div>
			<!-- <script type="text/javascript">
			$('#createServer').on('click',function(){
				$('#loader').addClass("hide-loader");
			}) -->
			<!-- </script> -->
			
			<div ng-show="isServersPresent">
			<h3>Active Project Resources</h3>
			<div class="row">
				<section class="content">
				<div class="panel panel-default">
					<div class="table-container">
						<table class="table table-filter">
							<tbody>
								<tr data-ng-repeat="vmMetadata in servers">
									<td><a href="javascript:;" class="star"> <i
											class="glyphicon glyphicon-star"></i>
									</a></td>
									<td>{{vmMetadata.serverName}}</td>
									<td>{{vmMetadata.project.projectName}}</td>
									<td><button class="btn btn-xs" name="viewDetails" id="viewDetails" ng-click="navigatePage(vmMetadata)"><span class="glyphicon glyphicon-eye-open"></button></td>
								</tr>
							</tbody>
						</table>
					</div>
					</div>
				</section>
			</div>
			</div>
		</div>
</div>
</body>
</html>