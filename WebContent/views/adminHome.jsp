<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../../../favicon.ico">

<title>Admin Home</title>

<script src="..<%=request.getContextPath()%>/js/vendor/jquery-3.2.1.js"></script>
<script src="..<%=request.getContextPath()%>/js/vendor/popper.min.js"></script>
<script src="..<%=request.getContextPath()%>/js/vendor/bootstrap.min.js"></script>
<script src="..<%=request.getContextPath()%>/js/vendor/Chart.js"></script>
<script src="..<%=request.getContextPath()%>/js/vendor/jquery.blockUI.js"></script>
<script defer src="..<%=request.getContextPath()%>/js/vendor/fontawesome-all.min.js"></script>
<script src="..<%=request.getContextPath()%>/js/core/Utility.js"></script>

<link href="..<%=request.getContextPath()%>/css/bootstrap.css" rel="stylesheet">
<link href="..<%=request.getContextPath()%>/css/carousel.css" rel="stylesheet">
<link href="..<%=request.getContextPath()%>/css/dashboard.css" rel="stylesheet">
<link href="..<%=request.getContextPath()%>/css/footer.css" rel="stylesheet">

<script>
	var contextPath ='<%=request.getContextPath()%>';
	$(document).ready(function() {
		$("#overview").show();
		$("#survey").hide();
		$("#user").hide();
		$("#analytics").hide();
		$("#subscription").hide();

		$("#a_overview").addClass("active");
		$("#li_home").addClass("active");
		$("#a_survey").removeClass("active");
		$("#a_user").removeClass("active");
		$("#a_analytics").removeClass("active");
		$("#a_subscription").removeClass("active");

		$("#a_survey_new").addClass("active");
		$("#a_survey_existing").removeClass("active");

		$(document).on('click', '#left_panel', function(e) {
			e.preventDefault();
		});
		$(document).on('click', '#survey_tab', function(e) {
			e.preventDefault();
		});
		$(document).on('click', '#a_survey_subscription', function(e) {
			e.preventDefault();
		});
		$(document).on('click', '#a_profile_general', function(e) {
			e.preventDefault();
		});
		$(document).on('click', '#a_profile_account', function(e) {
			e.preventDefault();
		});
		var userName = "<%= session.getAttribute("name") %>";
		if (userName) {
			$("#a_user_dropdown").text(userName);
		} else {
			$("#a_user_dropdown").text("User");
		}
		
		$("#nameChangeSuccess").hide();
		$("#nameChangeFailed").hide();
		var isNameChangeSuccessful = <%= request.getAttribute("isNameChangeSuccessful") %>;
		if (isNameChangeSuccessful != null) {
			document.getElementById("a_my_profile").click();
			handleProfileTab('account', true);
			if (isNameChangeSuccessful === true) {
				$("#nameChangeSuccess").show();
				$("#nameChangeFailed").hide();
				<% request.removeAttribute("isNameChangeSuccessful"); %>;
				var userName = "<%= session.getAttribute("name") %>";
				if (userName) {
					$("#a_user_dropdown").text(userName);
				} else {
					$("#a_user_dropdown").text("User");
				}
			} else {
				$("#nameChangeSuccess").hide();
				$("#nameChangeFailed").show();
			}
		}
		$("#pwdChangeSuccess").hide();
		$("#pwdChangeFailed").hide();
		var isPwdChangeSuccessful = <%= request.getAttribute("isPasswordChangeSuccessful") %>;
		if (isPwdChangeSuccessful != null) {
			document.getElementById("a_my_profile").click();
			handleProfileTab('account', true);
			if (isPwdChangeSuccessful === true) {
				$("#pwdChangeSuccess").show();
				$("#pwdChangeFailed").hide();
			} else {
				var initialMsg = $("#lbl_pwdChangeFailed").text();
				$("#lbl_pwdChangeFailed").text(initialMsg + " - <%= request.getAttribute("msg") %>");
				$("#pwdChangeSuccess").hide();
				$("#pwdChangeFailed").show();
			}
		}
		$("#accountDeletionError").hide();
		var isAccountDeletionSuccessful = <%= request.getAttribute("isAccountDeletionSuccessful") %>;
		if (isAccountDeletionSuccessful != null) {
			document.getElementById("a_my_profile").click();
			handleProfileTab('account', true);
			if (isAccountDeletionSuccessful === false) {
				$("#lbl_accDeletionFailed").text("<%= request.getAttribute("msg") %>");
				$("#accountDeletionError").show();
			}
		}
	});
	var isAdmin = <%= request.getAttribute("isAdmin") %>;
	var isOwner = <%= request.getAttribute("isOwner") %>;
	var isUser = <%= request.getAttribute("isUser") %>;
	if (isUser) {
		$("#a_survey").hide();
	}
</script>
</head>

<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-info">
			<a class="navbar-brand" href="#"> <img
				src="..<%=request.getContextPath()%>/images/logo.png" width="30" height="30"
				style="display: inline-block;"> <span
				style="display: inline-block;">Dalin's Survey</span>
			</a>
			<button class="navbar-toggler d-lg-none" type="button"
				data-toggle="collapse" data-target="#navbarsExampleDefault"
				aria-controls="navbarsExampleDefault" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse" id="navbarsExampleDefault">
				<ul class="navbar-nav ml-auto">
					<li id="li_home" class="nav-item active"><a class="nav-link"
						onclick="handleLeftPane('overview')" href="#">Home <span
							class="sr-only">(current)</span></a></li>
					<li class="nav-item dropdown" id="li_user"><a
						class="nav-link dropdown-toggle" href="#" id=a_user_dropdown
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false">User</a>
						<div class="dropdown-menu" aria-labelledby="a_user_dropdown"
							style="right: 0; left: auto;">
							<a class="dropdown-item" id="a_my_profile"
								onclick="handleMyProfile()" href="#">My Profile</a>
							<!-- <div class="dropdown-divider"></div> -->
							<a class="dropdown-item" href="logout">Logout</a>
						</div></li>
					<!-- <li class="nav-item"><a class="nav-link" href="#">About</a></li> -->
				</ul>
			</div>
		</nav>
	</header>

	<div class="container-fluid">
		<div class="row">
			<nav class="col-sm-3 col-md-2 d-none d-sm-block bg-light sidebar">
				<ul class="nav nav-pills flex-column" id="left_panel">
					<li class="nav-item"><a id="a_overview"
						class="nav-link active" onclick="handleLeftPane('overview')"
						href="#">Overview <!-- <span class="sr-only">(current)</span> --></a>
					</li>
					<%
					Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");
					if(isAdmin) { %>
					<li class="nav-item"><a id="a_survey" class="nav-link"
						onclick="handleLeftPane('survey')" href="">Surveys</a></li>
					<li class="nav-item"><a id="a_user" class="nav-link"
						onclick="handleLeftPane('user')" href="">My Users</a></li>
					<% } %>
					
					<%
					Boolean isUser = (Boolean) request.getAttribute("isUser");
					if(isUser) { %>
					<li class="nav-item"><a id="a_assigned_surveys" class="nav-link"
						onclick="handleLeftPane('assigned_surveys')" href="">Assigned Surveys</a></li>
					<% } %>

					<li class="nav-item"><a id="a_analytics" class="nav-link"
						onclick="handleLeftPane('analytics')" href="">Analytics</a></li>
					<!-- <li class="nav-item"><a id="a_subscription" class="nav-link"
						onclick="handleLeftPane('subscription')" href="">Subscription</a>
					</li> -->
				</ul>
			</nav>
			<main id="main" role="main"
				class="col-sm-9 ml-sm-auto col-md-10 pt-3">
			<div id="overview">
				<div id="myCarousel" class="carousel slide" data-ride="carousel">
					<ol class="carousel-indicators">
						<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li>
					</ol>
					<div class="carousel-outer">
						<div class="carousel-item active">
							<!-- <img class="first-slide"
								src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAB0AAAAVCAYAAAC6wOViAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAAhdEVYdENyZWF0aW9uIFRpbWUAMjAxNzoxMjoxMCAxOToxNzowN5BAw24AAAAzSURBVEhLYxRftOM/A50BE5SmKxi1lKZg1FKaglFLaQpGLaUpGLWUpmDUUpqCAbCUgQEAMf8Cmv9FGeUAAAAASUVORK5CYII="
								alt="First slide"> -->
							<div class="container">
								<div class="carousel-caption text-left">
									<h1>Survey Management.</h1>
									<p>This is Survey Management Solution. This is Survey
										Management Solution. This is Survey Management Solution. This
										is Survey Management Solution. This is Survey Management
										Solution. This is Survey Management Solution. This is Survey
										Management Solution.</p>
									<p>
										<a class="btn btn-lg btn-primary" href="#" role="button">Sign
											up today</a>
									</p>
								</div>
							</div>
						</div>
						<div class="carousel-item">
							<!-- <img class="second-slide"
								src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAB0AAAAVCAYAAAC6wOViAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAAhdEVYdENyZWF0aW9uIFRpbWUAMjAxNzoxMjoxMCAxOToxNzowN5BAw24AAAAzSURBVEhLYxRftOM/A50BE5SmKxi1lKZg1FKaglFLaQpGLaUpGLWUpmDUUpqCAbCUgQEAMf8Cmv9FGeUAAAAASUVORK5CYII="
								alt="Second slide"> -->
							<div class="container">
								<div class="carousel-caption">
									<h1>Another example headline.</h1>
									<p>Cras justo odio, dapibus ac facilisis in, egestas eget
										quam. Donec id elit non mi porta gravida at eget metus. Nullam
										id dolor id nibh ultricies vehicula ut id elit.</p>
									<p>
										<a class="btn btn-lg btn-primary" href="#" role="button">Learn
											more</a>
									</p>
								</div>
							</div>
						</div>
						<div class="carousel-item">
							<!-- <img class="third-slide"
								src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAAB0AAAAVCAYAAAC6wOViAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAAhdEVYdENyZWF0aW9uIFRpbWUAMjAxNzoxMjoxMCAxOToxNzowN5BAw24AAAAzSURBVEhLYxRftOM/A50BE5SmKxi1lKZg1FKaglFLaQpGLaUpGLWUpmDUUpqCAbCUgQEAMf8Cmv9FGeUAAAAASUVORK5CYII="
								alt="Third slide"> -->
							<div class="container">
								<div class="carousel-caption text-right">
									<h1>One more for good measure.</h1>
									<p>Cras justo odio, dapibus ac facilisis in, egestas eget
										quam. Donec id elit non mi porta gravida at eget metus. Nullam
										id dolor id nibh ultricies vehicula ut id elit.</p>
									<p>
										<a class="btn btn-lg btn-primary" href="#" role="button">Browse
											gallery</a>
									</p>
								</div>
							</div>
						</div>
					</div>
					<a class="carousel-control-prev" href="#myCarousel" role="button"
						data-slide="prev"> <span class="carousel-control-prev-icon"
						aria-hidden="true"></span> <span class="sr-only">Previous</span>
					</a> <a class="carousel-control-next" href="#myCarousel" role="button"
						data-slide="next"> <span class="carousel-control-next-icon"
						aria-hidden="true"></span> <span class="sr-only">Next</span>
					</a>
				</div>
				<div class="container marketing">
					<!-- Three columns of text below the carousel -->
					<div class="row">
						<div class="col-lg-4"></div>
						<div class="col-lg-4">
							<h1>How It Works</h1>
						</div>
						<div class="col-lg-4"></div>
					</div>
					<div class="row">
						<div class="col-lg-4">
							<img class="rounded-circle"
								src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAYCAYAAAAPtVbGAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAAwSURBVEhL7c0hEQAACAAx+scgAZVIAhleYLgX04vsmmsmiAligpggJogJYoJ8SWoWvwt9ml4QbdsAAAAASUVORK5CYII="
								alt="Generic placeholder image" width="140" height="140">
							<h2>Subscribe</h2>
							<p>Donec sed odio dui. Etiam porta sem malesuada magna mollis
								euismod. Nullam id dolor id nibh ultricies vehicula ut id elit.
								Morbi leo risus, porta ac consectetur ac, vestibulum at eros.
								Praesent commodo cursus magna.</p>
							<!-- <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p> -->
						</div>
						<!-- /.col-lg-4 -->
						<div class="col-lg-4">
							<img class="rounded-circle"
								src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAYCAYAAAAPtVbGAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAAwSURBVEhL7c0hEQAACAAx+scgAZVIAhleYLgX04vsmmsmiAligpggJogJYoJ8SWoWvwt9ml4QbdsAAAAASUVORK5CYII="
								alt="Generic placeholder image" width="140" height="140">
							<h2>Create</h2>
							<p>Duis mollis, est non commodo luctus, nisi erat porttitor
								ligula, eget lacinia odio sem nec elit. Cras mattis consectetur
								purus sit amet fermentum. Fusce dapibus, tellus ac cursus
								commodo, tortor mauris condimentum nibh.</p>
							<!-- <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p> -->
						</div>
						<!-- /.col-lg-4 -->
						<div class="col-lg-4">
							<img class="rounded-circle"
								src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAYCAYAAAAPtVbGAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAAwSURBVEhL7c0hEQAACAAx+scgAZVIAhleYLgX04vsmmsmiAligpggJogJYoJ8SWoWvwt9ml4QbdsAAAAASUVORK5CYII="
								alt="Generic placeholder image" width="140" height="140">
							<h2>Publish</h2>
							<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis
								in, egestas eget quam. Vestibulum id ligula porta felis euismod
								semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris
								condimentum nibh, ut fermentum.</p>
							<!-- <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p> -->
						</div>
						<!-- /.col-lg-4 -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.container -->
			</div>
			<div id="survey" style="display: none;">
				<div id = "subscriptionInfo" class="alert alert-info">
					<strong>Info!</strong> You should <a id="a_survey_subscription"
						onclick="navigateToSubscription()" href="" class="alert-link">subscribe</a>
					to a plan before creating a survey.
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					 	<span aria-hidden="true">&times;</span>
					 </button>
				</div>
				<div id = "surveyAllPage" style="display: none;"></div>
			</div>
			<div id="user" style="display: none;"></div>
			<div id = "assignedSurveyList" style="display: none;"></div>
			<div id="analytics" style="display: none;">
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Analytical Charts</h1>
					</div>
				</div>
				<div class="row container">
					<div class="col-md-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								<strong>Survey vs End-User Count</strong>
							</div>
							<div class="panel-body">
								<canvas id="myChart1"></canvas>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								<strong>Survey vs Survey Response</strong>
							</div>
							<div class="panel-body">
								<canvas id="myChart2"></canvas>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- <div id="subscription" style="display: none;">
				<div class="container-fluid">
					<div class="alert alert-info">
						Your current subscription is <strong>Basic</strong> Package.
					</div>
					<div class="text-center">
						<h2>Pricing</h2>
						<h4>Choose a payment plan that works for you</h4>
					</div>
					<div class="row">
						<div class="col-sm-4">
							<div class="panel panel-default text-center">
								<div class="panel-heading">
									<h1>Free</h1>
								</div>
								<div class="panel-body">
									<p>
										<strong>10</strong> Questions
									</p>
									<p>
										<strong>25</strong> Responses
									</p>
									<p>
										<strong>10 MB</strong> Upload Storage
									</p>
									<p>
										<strong>1 Month Validity</strong>
									</p>
								</div>
								<div class="panel-footer">
									<h3>$0</h3>
									<h4>per month</h4>
									<button class="btn btn-lg">Subscribe</button>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="panel panel-default text-center">
								<div class="panel-heading">
									<h1>Basic</h1>
								</div>
								<div class="panel-body">
									<p>
										<strong>100</strong> Questions
									</p>
									<p>
										<strong>1000</strong> Responses
									</p>
									<p>
										<strong>350 MB</strong> Upload Storage
									</p>
									<p>
										<strong>Monthly/ Annually</strong>
									</p>
								</div>
								<div class="panel-footer">
									<h3>$29</h3>
									<h4>per month</h4>
									<button class="btn btn-lg">Subscribe</button>
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="panel panel-default text-center">
								<div class="panel-heading">
									<h1>Advanced</h1>
								</div>
								<div class="panel-body">
									<p>
										<strong>Unlimited</strong> Questions
									</p>
									<p>
										<strong>Unlimited</strong> Responses
									</p>
									<p>
										<strong>1 GB</strong> Upload Storage
									</p>
									<p>
										<strong>Monthly/ Annually</strong>
									</p>
								</div>
								<div class="panel-footer">
									<h3>$49</h3>
									<h4>per month</h4>
									<button class="btn btn-lg">Subscribe</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div> -->
			<div id="myProfile" style="display: none;">
				<ul class="nav nav-tabs" id="profile_tab">
					<li class="nav-item"><a id="a_profile_general"
						onclick="handleProfileTab('general')" class="nav-link active"
						href="">Profile</a></li>
					<li class="nav-item"><a id="a_profile_account"
						onclick="handleProfileTab('account')" class="nav-link" href="">Account</a></li>
				</ul>
				<div id="div_general">General Settings</div>
				<div id="div_account">
					<div class="row">
						<div class="col">
							<div class="card" style="margin-top: 20px; /* width: 20rem */">
								<div class="card-body">
									<h4 class="card-title">Name</h4>
									<h6 class="card-subtitle mb-2 text-muted">Change your name</h6>
									<div class="alert alert-success alert-dismissible fade show" role="alert" id="nameChangeSuccess" style="display: none;">
									  Name changed successfully !
									  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
					    				<span aria-hidden="true">&times;</span>
					 				  </button>
									</div>
									<div class="alert alert-danger alert-dismissible fade show" role="alert" id="nameChangeFailed" style="display: none;">
									  Name change failed - Try Again !
									  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
					    				<span aria-hidden="true">&times;</span>
					 				  </button>
									</div>
									<form class="form-signin" action="<%=request.getContextPath()%>/user" method="post" onerror="onError()">
										<input type="text" name="name" id="name" class="form-control"
											placeholder="Name" required style="margin-top: 20px;">
										<input type="hidden" name="action" value="changeName">
										<button class="btn btn-md btn-primary btn-block" type="submit"
											style="margin-top: 20px;">Save</button>
										<button class="btn btn-md btn-primary btn-block" type="reset"
											style="margin-top: 20px;">Reset</button>
									</form>
								</div>
							</div>
						</div>
						<div class="col">
							<div class="card" style="margin-top: 20px; /* width: 20rem */">
								<div class="card-body">
									<h4 class="card-title">Password</h4>
									<h6 class="card-subtitle mb-2 text-muted">Change your
										password</h6>
									<div class="alert alert-success alert-dismissible fade show" role="alert" id="pwdChangeSuccess" style="display: none;">
									  Password changed successfully !
									  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
					    				<span aria-hidden="true">&times;</span>
					 				  </button>
									</div>
									<div class="alert alert-danger alert-dismissible fade show" role="alert" id="pwdChangeFailed" style="display: none;">
									  <label id=lbl_pwdChangeFailed style="height: fit-content;">Password change failed</label>
									  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
					    				<span aria-hidden="true">&times;</span>
					 				  </button>
									</div>
									<form name = "pwdChangeForm" class="form-signin" action="<%=request.getContextPath()%>/user" method="post" onsubmit="return validatePasswordChange()">
										<input type="password" name="old_password" id="oldPassword"
											class="form-control" placeholder="Current Password" required
											style="margin-top: 20px;"> <input type="password"
											name="newPassword1" id="newPassword1" class="form-control"
											placeholder="New Password" required style="margin-top: 20px;">
										<input type="password" name="newPassword2" id="newPassword2"
											class="form-control" placeholder="Retype New Password"
											required style="margin-top: 20px;">
										<input type="hidden" name="action" value="changePassword">
										<button class="btn btn-md btn-primary btn-block" type="submit"
											style="margin-top: 20px;">Save</button>
										<button class="btn btn-md btn-primary btn-block" type="reset"
											style="margin-top: 20px;">Reset</button>
									</form>
								</div>
							</div>
						</div>
						<div class="col">
							<div class="card" style="margin-top: 20px; /* width: 20rem */">
								<div class="card-body">
									<h4 class="card-title">Account</h4>
									<h6 class="card-subtitle mb-2 text-muted">Delete your account. All your work and data will be permanently lost. This action is irreversible. You will be redirected to login page, if your account gets deleted successfully.</h6>
									<div id = "confirmationModal" class="modal" tabindex="-1" role="dialog">
									  <div class="modal-dialog" role="document">
									    <div class="modal-content">
									      <div class="modal-header">
									        <h5 class="modal-title">Delete your account?</h5>
									        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
									          <span aria-hidden="true">&times;</span>
									        </button>
									      </div>
									      <div class="modal-body">
									        <p>Are you sure to delete your account?</p><hr><p>This action is irreversible. You will be redirected to login page, if your account gets deleted successfully.</p>
									      </div>
									      <div class="modal-footer">
									        <button id = "btn_yes" type="button" class="btn btn-primary" onclick="return confirmationCheck(true);">Yes, go ahead and delete</button>
									        <button id = "btn_no" type="button" class="btn btn-secondary" data-dismiss="modal" onclick="return confirmationCheck(false);">No</button>
									      </div>
									    </div>
									  </div>
									</div>
									<div class="alert alert-danger alert-dismissible fade show" role="alert" id="accountDeletionError" style="display: none;">
									  <label id=lbl_accDeletionFailed style="height: fit-content;">Missing Current Password</label>
									  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
					    				<span aria-hidden="true">&times;</span>
					 				  </button>
									</div>
									<form id = "accountDeletionForm" class="form-signin" action="<%=request.getContextPath()%>/user" method="post" novalidate>
										<input type="password" name="del_pwd" id="del_pwd"
											class="form-control" placeholder="Current Password" required
											style="margin-top: 20px;">
										<input type="hidden" name="action" value="deleteAccount">
										<button class="btn btn-md btn-primary btn-block" type="button" onclick="showConfirmationPopUp()"
											style="margin-top: 20px;">Delete</button>
										<button class="btn btn-md btn-primary btn-block" type="reset"
											style="margin-top: 20px;">Reset</button>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div>
				<footer id="myFooter">
					<hr>
					<div class="container">
						<ul>
							<li><a href="#">About Us</a></li>
							<li><a href="#">Contact us</a></li>
							<li><a href="#">Reviews</a></li>
							<li><a href="#">Terms of service</a></li>
						</ul>
						<p class="footer-copyright">© 2016 Copyright Text</p>
					</div>
				</footer>
			</div>
			</main>
		</div>
	</div>
	<script src="..<%=request.getContextPath()%>/js/core/adminHome.js"></script>
	<script src="..<%=request.getContextPath()%>/js/core/surveyList.js"></script>
	<script src="..<%=request.getContextPath()%>/js/core/survey.js"></script>
	<script src="..<%=request.getContextPath()%>/js/core/adminUser.js"></script>
	<script src="..<%=request.getContextPath()%>/js/core/surveyReader.js"></script>
	<script src="..<%=request.getContextPath()%>/js/core/assignedSurveysList.js"></script>
</body>
</html>
