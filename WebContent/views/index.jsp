<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login | Survey Management</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<script src="..<%=request.getContextPath()%>/js/vendor/jquery-3.2.1.js"></script>

	<link href="..<%=request.getContextPath()%>/css/bootstrap.css" rel="stylesheet">
	<link href="..<%=request.getContextPath()%>/css/signin.css" rel="stylesheet">
	
	<script>
	var contextPath='<%=request.getContextPath()%>';
	$(document).ready(function() {
		var isLoginFailed = <%= request.getAttribute("isLoginFailed") %>;
		$("#loginFailure").hide();
		if (isLoginFailed) {
			$("#loginFailure").show();
		}

		var isSignUpFailed = <%= request.getAttribute("isSignUpFailed") %>;
		$("#signUpFailure").hide();
		if (isSignUpFailed) {
			$('#loginbox').hide();
			$('#signupbox').show();
			$("#signUpFailure").show();
		}
	});
	</script>
</head>
<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-info">
			<a class="navbar-brand" href="#">Survey Management</a>
		</nav>
	</header>
    <div class="container">
	    <div id="loginbox">
		    <form class="form-signin" action="<%=request.getContextPath()%>/home" method="post">
				<h2 class="form-signin-heading">Please Sign In</h2>
				<div class="alert alert-danger alert-dismissible fade show" role="alert" id="loginFailure" style="display: none;">
				  Incorrect Credentials - Try Again !
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    				<span aria-hidden="true">&times;</span>
 				  </button>
				</div>
				<input type="email" name="email" id="loginEmail" class="form-control" placeholder="Email address" required autofocus>
				<input type="password" name="password" id="loginPassword" class="form-control" placeholder="Password" required>
				<input type="hidden" name="action" value="signin">
				<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
			</form>
           <div class="col-md-12 control">
               <div style="text-align:right; border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                   Don't have an account! 
               <a href="#" onClick="$('#loginbox').hide(); $('#signupbox').show()" style="color: blue;">
                   Sign Up Here
               </a>
               </div>
           </div>
		</div>
	    <div id="signupbox" style="display:none;">
	    	<form class="form-signin" action="<%=request.getContextPath()%>/home" method="post">
				<h2 class="form-signin-heading">Please Sign Up</h2>
				<div class="alert alert-danger alert-dismissible fade show" role="alert" id="signUpFailure" style="display: none;">
				  Unable to sign up - Missing mandatory data!
				  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
    				<span aria-hidden="true">&times;</span>
 				  </button>
				</div>
				<input type="email" name="email" id="signUpEmail" class="form-control" placeholder="Email address" required autofocus>
				<input type="text" name="name" id="name" class="form-control" placeholder="Name" required>
				<input type="password" name="password" id="password" class="form-control" placeholder="Password" required>
				<input type="hidden" name="action" value="signup">
				<button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
			</form>
           <div class="col-md-12 control">
               <div style="text-align:right; border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                   Already have an account! 
               <a href="#" onClick="$('#signupbox').hide(); $('#loginbox').show()" style="color: blue;">
                   Login Here
               </a>
               </div>
           </div>
	    </div>
    </div>
</body>
</html>