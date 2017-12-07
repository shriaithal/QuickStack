<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
	
	<div data-ng-app="openstackApp" data-ng-controller="loginCtrl" style="background-image:url(/resources/Images/Top-5-Trends-Shaping-the-Modern-Data-Center.jpg); height: 1100px">
	<div class="alert alert-danger" ng-show="showErrorAlert">
        <button type="button" class="close" data-ng-click="switchBool('showErrorAlert')">×</button> <strong>Error!</strong> {{errorTextAlert}}</div>
        
	<div class="" style="    top: 60px;
    position: absolute;    right: 8%;
    background-color: white;
    width: 25%;
    text-align: left;
    padding: 20px;
    margin-right:560px;">
	<!-- <h1 class="welcome text-center">Welcome</h1> -->
        <div class="card card-container">
    <h2 class='login_title text-center title-login'>Login</h2>
    <hr>
    	<form class="form-signin" name="login" ng-submit="loginForm()">
                
           <span id="reauth-email" class="reauth-email"></span>
           <p class="input_title">Email</p>
           <input type='text' name='userName' ng-model='userName' id='userName' class="login_box"  required autofocus>
           
           <p class="input_title">Password</p>
          	<input type='password' name='password' ng-model='password' id='password' class="login_box"  required>
                
          <div class="row" style="margin-top: 20px">
      		<div class="col-xs-4 buttons-login">
      			<input name="submit" type="submit" id="submitForm" value="Submit" class="btn btn-primary btn-sm"/>
      		</div>
      	
      		<div class="col-xs-4 buttons-login">
      			<input name="cancel" type="reset" id="resetForm" value="Cancel" class="btn btn-primary btn-sm"/>
      		</div>
      	</div>
     </form>
  </div>
</div>
	</div>