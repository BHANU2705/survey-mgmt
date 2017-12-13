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

<script src="../Test/js/vendor/jquery-slim.min.js"></script>
<script src="../Test/js/vendor/popper.min.js"></script>
<script src="../Test/js/bootstrap.min.js"></script>
<script src="../Test/js/vendor/Chart.js"></script>


<link href="../Test/css/bootstrap.min.css" rel="stylesheet">
<link href="../Test/css/dashboard.css" rel="stylesheet">
<link href="../Test/css/carousel.css" rel="stylesheet">
<link href="../Test/css/footer.css" rel="stylesheet">

<script>
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
	});
</script>
</head>

<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark fixed-top bg-info">
			<a class="navbar-brand" href="#">
				<img src="../Test/images/logo.png" width="30" height="30" style="display: inline-block;">
  				<span style="display: inline-block;">Dalin's Survey</span>
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
					<li class="nav-item"><a class="nav-link" href="#">Profile</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#">About</a></li>
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
					<li class="nav-item"><a id="a_survey" class="nav-link"
						onclick="handleLeftPane('survey')" href="">Surveys</a></li>
					<li class="nav-item"><a id="a_user" class="nav-link"
						onclick="handleLeftPane('user')" href="">My Users</a></li>
					<li class="nav-item"><a id="a_analytics" class="nav-link"
						onclick="handleLeftPane('analytics')" href="">Analytics</a></li>
					<li class="nav-item"><a id="a_subscription" class="nav-link"
						onclick="handleLeftPane('subscription')" href="">Subscription</a>
					</li>
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
				        <div class="col-lg-4"><h1>How It Works</h1></div>
				        <div class="col-lg-4"></div>
			        </div>
			        <div class="row">
			          <div class="col-lg-4">
			            <img class="rounded-circle" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAYCAYAAAAPtVbGAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAAwSURBVEhL7c0hEQAACAAx+scgAZVIAhleYLgX04vsmmsmiAligpggJogJYoJ8SWoWvwt9ml4QbdsAAAAASUVORK5CYII=" alt="Generic placeholder image" width="140" height="140">
			            <h2>Subscribe</h2>
			            <p>Donec sed odio dui. Etiam porta sem malesuada magna mollis euismod. Nullam id dolor id nibh ultricies vehicula ut id elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna.</p>
			            <!-- <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p> -->
			          </div><!-- /.col-lg-4 -->
			          <div class="col-lg-4">
			            <img class="rounded-circle" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAYCAYAAAAPtVbGAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAAwSURBVEhL7c0hEQAACAAx+scgAZVIAhleYLgX04vsmmsmiAligpggJogJYoJ8SWoWvwt9ml4QbdsAAAAASUVORK5CYII=" alt="Generic placeholder image" width="140" height="140">
			            <h2>Create</h2>
			            <p>Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Cras mattis consectetur purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh.</p>
			            <!-- <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p> -->
			          </div><!-- /.col-lg-4 -->
			          <div class="col-lg-4">
			            <img class="rounded-circle" src="data:image/gif;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAYCAYAAAAPtVbGAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAAwSURBVEhL7c0hEQAACAAx+scgAZVIAhleYLgX04vsmmsmiAligpggJogJYoJ8SWoWvwt9ml4QbdsAAAAASUVORK5CYII=" alt="Generic placeholder image" width="140" height="140">
			            <h2>Publish</h2>
			            <p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum.</p>
			            <!-- <p><a class="btn btn-secondary" href="#" role="button">View details &raquo;</a></p> -->
			          </div><!-- /.col-lg-4 -->
			        </div><!-- /.row -->
			      </div><!-- /.container -->
			</div>
			<div id="survey">
				<div class="alert alert-info">
					<strong>Info!</strong> You should <a id="a_survey_subscription"
						onclick="navigateToSubscription()" href="" class="alert-link">subscribe</a>
					to a plan before creating a survey.
				</div>
				<ul class="nav nav-tabs" id="survey_tab">
					<li class="nav-item"><a id="a_survey_new"
						onclick="handleSurveyTab('survey_new')" class="nav-link active"
						href="">New</a></li>
					<li class="nav-item"><a id="a_survey_existing"
						onclick="handleSurveyTab('survey_existing')" class="nav-link"
						href="">Existing</a></li>
				</ul>
				<div id="div_survey_new">
					<div class="row">
						<div class="col-lg-12">
							<h1 class="page-header">Survey</h1>
						</div>
					</div>
					<div class="row">
						<div class="col-lg-12">
							<div class="panel panel-default">
								<div class="panel-heading">Enter Survey Details</div>
								<div class="panel-body">
									<div class="row">
										<div class="col-lg-12">
											<form role="form">
												<div class="form-group">
													<input class="form-control" placeholder="Survey Name">
													<label>Survey Description</label>
													<textarea class="form-control" rows="2"></textarea>
													<label>Question Type</label> <select class="form-control">
														<option>Check Box</option>
														<option>Radio Button</option>
														<option>Text</option>
														<option>Location</option>
														<option>Image</option>
													</select>
												</div>
												<button type="submit" class="btn btn-default">Submit
													Button</button>
												<button type="reset" class="btn btn-default">Reset
													Button</button>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="user">This is the user page.</div>
			<div id="analytics">
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
			<div id="subscription">
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
			</div>
			<div>
			
			<footer id="myFooter">
			<hr>
					<div class="container">
						<ul>
							<li><a href="#">Company Information</a></li>
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
	<script src="../Test/js/adminHome.js"></script>
</body>
</html>
