<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
	<div data-ng-app="openstackUserApp" data-ng-controller="userLoginCtrl" style="background-image:url(/resources/views/datacenterservices_lp_fod.jpg); height: 1100px">
	<div class="alert alert-danger" ng-show="showErrorAlert">
	<div  ng-include src="'/resources/views/nav.jsp'"></div>
        <button type="button" class="close" data-ng-click="switchBool('showErrorAlert')">×</button> <strong>Error!</strong> {{errorTextAlert}}</div>
        
	<div class="" style="    position: absolute;top: 68px;right: 5%;width: 517px;background-color: gainsboro;">
	<h1 class="welcome text-center">Create an Account</h1>
        <div class="card card-container">
   <!-- <h2 class='login_title text-center title-login'>Login</h2>-->
    <!-- <hr> --> 
    	<form class="form-signin" name="signup" ng-submit="signUpForm()">
                
   <span id="reauth-email" class="reauth-email"></span>
           <!-- <p class="input_title">Email</p>
           <input type='text' name='userName' ng-model='userName' id='userName' class="login_box"  required autofocus>-->
           <div class="col-md-11 multi-horizontal" data-for="email">
              <div class="form-group">
                  <label class="form-control-label mbr-fonts-style display-7" for="name-form1-4t">Email</label>
                  <!-- <input type="text" class="form-control" name="name" data-form-field="Name" required="" id="name-form1-4t"> -->
                  <input type='text' name='userName' class="form-control" ng-model='userName' id='userName' class="login_box"  required autofocus>

              </div>
          </div>
           <!-- <p class="input_title">First Name</p>
           <!-- <input type='text' name='firstName' ng-model='firstName' id='firstName' class="login_box"  required> -->
           <!-- <input type='text' name='firstName' ng-model='firstName' id='firstName' class="login_box"  required> -->
           <div class="col-md-11 multi-horizontal" data-for="firstname">
              <div class="form-group">
                  <label class="form-control-label mbr-fonts-style display-7" for="name-form1-4t">First Name</label>
                  <input type='text' name='firstName' class="form-control" ng-model='firstName' id='firstName' class="login_box"  required autofocus>
              </div>
          </div>
           
           <!-- <p class="input_title">Last Name</p>
           <input type='text' name='lastName' ng-model='lastName' id='lastName' class="login_box"  required> -->
           <div class="col-md-11 multi-horizontal" data-for="lastname">
              <div class="form-group">
                  <label class="form-control-label mbr-fonts-style display-7" for="name-form1-4t">Last Name</label>
                  <input type='text' name='lastName' class="form-control" ng-model='lastName' id='lastName' class="login_box"  required autofocus>
              </div>
          </div>
           
          <!--  <p class="input_title">Password</p>
          	<input type='password' name='password' ng-model='password' id='password' class="login_box"  required> -->
          	<div class="col-md-11 multi-horizontal" data-for="password">
            <div class="form-group">
                <label class="form-control-label mbr-fonts-style display-7" for="name-form1-4t">Password</label>
                <!-- <input type='text' name='department'  ng-model='department' id='department' class="login_box"  required > -->
                <input type='password' name='password' class="form-control" ng-model='password' id='password' class="login_box"  required autofocus>
            </div>
          </div>
          <div class="row col-md-11 multi-horizontal" style="padding: 25px;">
      		<div class="col-xs-4 buttons-login">
      			<input name="submit" type="submit" id="submitForm" value="Sign Up" class="btn btn-primary btn-md"/>
      		</div>
      	
      		<div class="col-xs-4 buttons-login">
      			<a name="cancel" type="reset" id="resetForm" value="Cancel" class="btn btn-primary btn-md" href="#!/user/">Cancel</a>
      		</div>
      	</div>
      	<!-- <div class="row">
      	<a href="#!signup" style="font-size: small; padding-left: 15px;">New User? Sign up here!</a>
      	</div> -->
     </form>
  </div>
</div>
	</div>