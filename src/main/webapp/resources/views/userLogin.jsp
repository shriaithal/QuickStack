<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>
	<div data-ng-app="openstackUserApp" data-ng-controller="userLoginCtrl" style="background-image:url(/resources/views/datacenterservices_lp_fod.jpg); height: 1100px">

	<div class="alert alert-danger" ng-show="showErrorAlert">
        <button type="button" class="close" data-ng-click="switchBool('showErrorAlert')">×</button> <strong>Error!</strong> {{errorTextAlert}}</div>
        
	<div class="container">
	<h1 class="welcome text-center">Welcome</h1>
        <div class="card card-container"div class="" style="    position: absolute;top: 68px;right: 5%;width: 517px;background-color: gainsboro;">
	<h1 class="welcome text-center">LOGIN</h1>
        <div class="card card-container">
   <!--  <h2 class='login_title text-center title-login'>Login</h2>
    <hr> -->
    	<form class="form-signin" name="login" ng-submit="loginForm()">
                
           <span id="reauth-email" class="reauth-email"></span>
           <!-- <p class="input_title">Email</p>
           <input type='text' name='userName' ng-model='userName' id='userName' class="login_box"  required autofocus> -->
           <div class="col-md-11 multi-horizontal" data-for="email">
              <div class="form-group">
                  <label class="form-control-label mbr-fonts-style display-7" for="name-form1-4t">Email</label>
                  <!-- <input type="text" class="form-control" name="name" data-form-field="Name" required="" id="name-form1-4t"> -->
                  <input type='text' name='userName' class="form-control" ng-model='userName' id='userName' class="login_box"  required autofocus>

              </div>
          </div>
           
           <!-- <p class="input_title">Password</p>
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
      			<input name="submit" type="submit" id="submitForm" value="Submit" class="btn btn-primary btn-sm"/>
      		</div>
      	
      		<div class="col-xs-4 buttons-login">
      			<input name="cancel" type="reset" id="resetForm" value="Cancel" class="btn btn-primary btn-sm"/>
      		</div>
      	</div>
      	<div class="col-xs-4 buttons-login" >
      	<a href="#/signup" style="font-size: smaller; padding-left: 11px;">New User? Sign up here!</a>
      	</div>
     </form><!-- /form -->
  </div><!-- /card-container -->
</div>
	</div>
	</div>
	</body>
	</html>