<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>

	<!-- <nav class="navbar" role="navigation" style="background-color: rgba(44, 116, 193, 0.76);">
			<a class="navbar-brand" style="/* margin-left: -588px; */color: white;">Quick Stack</a> <a class="navbar-right" style="margin-right: auto;margin-right:1593px;margin-botton:-34px;margin-top:14px;color: white;">Welcome {{authData.currentUser.username}}</a>
			<div class="navbar-header" style="margin-left:1290px;margin-bottom:-3px;margin-top:-17px;">
				<a href="#/login" style="/* margin-right: -320px; */color: white;"><span class="glyphicon glyphicon-log-out" style="margin-left:378px;"></span>Logout</a>
			</div> 
		</nav>
	<div style="margin-top: -3.5%;/* color: white; */" class="row col-md-12">
    <div class="col-md-8" style="color: white;"></div>
    <div class="col-md-4">
     <div class="col-md-12" style="
    margin-left: 31%;
    margin-top: 12px;
    max-width: 86%;
">
    	 <div class="col-md-2"></div>
         <div class="col-md-4"><a href="#/listServers" style="*/color: white;color: white;margin-left: -36px;/* padding-left: 5%; */">List Server</a></div>
         <div class="col-md-6" style="
    padding-left: 3%;margin-bottom: -22px;
    margin-left: 114px;
    margin-top: -17px;
    margin-top: -20px;
"><a href="#/createProjects" style="*/color: white;color: white;margin-left: -73px;">Create Project</a></div>

      </div>
      </div>       

 </div>
  -->
  
			<nav class="navbar" role="navigation" style="background-color: rgba(44, 116, 193, 0.76);">
			<a class="navbar-header" style="color: white;">Quick Stack</a>
			 <a class="navbar-header" style="color: white;padding-left: 12px;">Welcome {{authData.currentUser.username}}</a>
			<div class="navbar-header" style="float: right;">
				<span><a onclick="list()" href="#/listServers"  style="color: white;padding:10px;">List Servers</a></span>
				<span><a onclick="project()" href="#/createProjects"  style="color: white;padding:10px">Create Project</a></span>
				<span><a onclick="logout()" href="#!login" style="color: white;padding:10px"><span class="glyphicon glyphicon-log-out"></span>Logout</a></span>
			</div>
			</nav>
	<div ng-app="openstackApp" ng-controller="openStackMetricsCtrl">
		<div class="container-fluid">
			<div class="alert alert-success" ng-show="showSuccessAlert">
				<button type="button" class="close" data-ng-click="switchBool('showSuccessAlert')">×</button>
				<strong>Done!</strong> {{successTextAlert}}
			</div>

			<div class="alert alert-danger" ng-show="showErrorAlert">
				<button type="button" class="close" data-ng-click="switchBool('showErrorAlert')">×</button>
				<strong>Error!</strong> {{errorTextAlert}}
			</div>
			<center><h3>Server Metrics</h3><center>
			<br/><br/>
			<!-- <p>You're logged in!! {{authData}}</p>
			<p><a href="#/login">Logout</a></a></p>
			<p><a href="#/listServers">List Server</a></a></p>
			<p><a href="#/createProjects">Create Project</a></a></p>-->
		</div>
		
		<!-- Metrics code -->
		<div>
		<div class="container">
		  <div class="row">
		    <div class="col-lg-4" align="center"><h4>Memory Usage <br/></h4>
		    									<h5>{{memLabel}}</h5>
		    									
		    									</div>
		    <div class="col-lg-4" align="center"><h4>Disk Usage <br/></h4>
		    									<h5>{{diskLabel}}</h5>
		    									
		    									</div>
		    <div class="col-lg-4" align="center"><h4>CPU Usage <br/></h4>
		    									<h5>{{cpuLabel}}</h5>
		    									</div>
		  </div>
		  <div class="row">
		    <div class="col-lg-4" align="center">
		    	<div ang:round:progress data-round-progress-model="memData"  
		    				data-round-progress-width="250"
                            data-round-progress-height="250"
                            data-round-progress-outer-circle-width="20"
                            data-round-progress-inner-circle-width="5"
                            data-round-progress-outer-circle-radius="100"
                            data-round-progress-inner-circle-radius="70"
							data-round-progress-outer-circle-background-color="#808080"
                            data-round-progress-outer-circle-foreground-color="#0000FF"
                            data-round-progress-inner-circle-color="#808080"
							data-round-progress-label-color="#0000FF"
							data-round-progress-label-font="32pt Arial"></div>
		    </div>
		    <div class="col-lg-4" align="center">
		    	<div ang:round:progress data-round-progress-model="diskData"  
		    				data-round-progress-width="250"
                            data-round-progress-height="250"
                            data-round-progress-outer-circle-width="20"
                            data-round-progress-inner-circle-width="5"
                            data-round-progress-outer-circle-radius="100"
                            data-round-progress-inner-circle-radius="70"
							data-round-progress-outer-circle-background-color="#808080"
                            data-round-progress-outer-circle-foreground-color="#32CD32"
                            data-round-progress-inner-circle-color="#808080"
							data-round-progress-label-color="#32CD32"
							data-round-progress-label-font="32pt Arial"></div>
		    </div>
		    <div class="col-lg-4" align="center">
		    	<div ang:round:progress data-round-progress-model="cpuData"  
		    				data-round-progress-width="250"
                            data-round-progress-height="250"
                            data-round-progress-outer-circle-width="20"
                            data-round-progress-inner-circle-width="5"
                            data-round-progress-outer-circle-radius="100"
                            data-round-progress-inner-circle-radius="70"
							data-round-progress-outer-circle-background-color="#808080"
                            data-round-progress-outer-circle-foreground-color="#B22222"
                            data-round-progress-inner-circle-color="#808080"
							data-round-progress-label-color="#B22222"
							data-round-progress-label-font="32pt Arial"></div>
		    </div>
		  </div>
	
		</div>
		
		
		</div>
	</div>
</body>
	