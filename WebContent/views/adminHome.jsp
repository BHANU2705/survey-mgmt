<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../../../favicon.ico">

    <title>Admin Home</title>

    <link href="../Test/css/bootstrap.min.css" rel="stylesheet">
    <link href="../Test/css/dashboard.css" rel="stylesheet">
    <script src="../Test/js/vendor/jquery-slim.min.js"></script>
    <script src="../Test/js/vendor/popper.min.js"></script>
    <script src="../Test/js/bootstrap.min.js"></script>
    
    <!-- <link href="../Test/css/metisMenu.min.css" rel="stylesheet">
    <link href="../Test/css/sb-admin-2.css" rel="stylesheet">
    <link href="../Test/css/font-awesome.min.css" rel="stylesheet" type="text/css"> -->
    
    <script>
    	$(document).ready(function(){
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
    	});
    </script>
    
  </head>

  <body>
    <header>
      <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="#">Survey Management</a>
        <button class="navbar-toggler d-lg-none" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExampleDefault">
          <ul class="navbar-nav ml-auto">
            <li id="li_home"class="nav-item active">
              <a class="nav-link" onclick="handleLeftPane('overview')" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Profile</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">About</a>
            </li>
          </ul>
          <!-- <form class="form-inline mt-2 mt-md-0">
            <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
          </form> -->
        </div>
      </nav>
    </header>

    <div class="container-fluid">
      <div class="row">
        <nav class="col-sm-3 col-md-2 d-none d-sm-block bg-light sidebar">
          <ul class="nav nav-pills flex-column" id="left_panel">
            <li class="nav-item">
              <a id="a_overview" class="nav-link active" onclick="handleLeftPane('overview')" href="#">Overview <!-- <span class="sr-only">(current)</span> --></a>
            </li>
            <li class="nav-item">
              <a id="a_survey" class="nav-link" onclick="handleLeftPane('survey')" href="">Surveys</a>
            </li>
            <li class="nav-item">
              <a id="a_user" class="nav-link" onclick="handleLeftPane('user')" href="" >My Users</a>
            </li>
            <li class="nav-item">
              <a id="a_analytics" class="nav-link" onclick="handleLeftPane('analytics')" href="">Analytics</a>
            </li>
            <li class="nav-item">
              <a id="a_subscription" class="nav-link" onclick="handleLeftPane('subscription')" href="">Subscription</a>
            </li>
          </ul>
        </nav>
        <main id="main" role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
			<div id="overview">
				This is the overview page.
			</div>
			<div id="survey">
				<ul class="nav nav-tabs" id="survey_tab">
				  <li class="nav-item">
				    <a id="a_survey_new" onclick="handleSurveyTab('survey_new')" class="nav-link active" href="">New</a>
				  </li>
				  <li class="nav-item">
				    <a id="a_survey_existing" onclick="handleSurveyTab('survey_existing')" class="nav-link" href="">Existing</a>
				  </li>
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
				                <div class="panel-heading">
				                    Enter Survey Details
				                </div>
				                <div class="panel-body">
				                    <div class="row">
				                        <div class="col-lg-12">
				                            <form role="form">
				                                <div class="form-group">
				                                    <input class="form-control" placeholder="Survey Name">
				                                    <label>Survey Description</label>
				                                    <textarea class="form-control" rows="2"></textarea>
				                                    <label>Question Type</label>
				                                    <select class="form-control">
				                                        <option>Check Box</option>
				                                        <option>Radio Button</option>
				                                        <option>Text</option>
				                                        <option>Location</option>
				                                        <option>Image</option>
				                                    </select>
				                                </div>
				                                <button type="submit" class="btn btn-default">Submit Button</button>
				                                <button type="reset" class="btn btn-default">Reset Button</button>
				                            </form>
				                        </div>
				                    </div>
				                </div>
				            </div>
				        </div>
				    </div>
				</div>
			</div>
			<div id="user">
				This is the user page.
			</div>
			<div id="analytics">
				This is the analytics page.
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
		</main>
      </div>
    </div>
    <script>
    function handleLeftPane(caller) {
    	if(caller) {
    		if (caller === 'overview') {
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
    			
    		} else if (caller === 'survey') {
    			$("#overview").hide();
    			$("#survey").show();
    			$("#user").hide();
    			$("#analytics").hide();
    			$("#subscription").hide();
    			
    			$("#a_overview").removeClass("active");
    			$("#li_home").removeClass("active");
    			$("#a_survey").addClass("active");
    			$("#a_user").removeClass("active");
    			$("#a_analytics").removeClass("active");
    			$("#a_subscription").removeClass("active");
    		} else if(caller === 'user') {
    			$("#overview").hide();
    			$("#survey").hide();
    			$("#user").show();
    			$("#analytics").hide();
    			$("#subscription").hide();
    			
    			$("#a_overview").removeClass("active");
    			$("#li_home").removeClass("active");
    			$("#a_survey").removeClass("active");
    			$("#a_user").addClass("active");
    			$("#a_analytics").removeClass("active");
    			$("#a_subscription").removeClass("active");
    		} else if(caller === 'analytics') {
    			$("#overview").hide();
    			$("#survey").hide();
    			$("#user").hide();
    			$("#analytics").show();
    			$("#subscription").hide();
    			
    			$("#a_overview").removeClass("active");
    			$("#li_home").removeClass("active");
    			$("#a_survey").removeClass("active");
    			$("#a_user").removeClass("active");
    			$("#a_analytics").addClass("active");
    			$("#a_subscription").removeClass("active");
    		} else if(caller === 'subscription') {
    			$("#overview").hide();
    			$("#survey").hide();
    			$("#user").hide();
    			$("#analytics").hide();
    			$("#subscription").show();
    			
    			$("#a_overview").removeClass("active");
    			$("#li_home").removeClass("active");
    			$("#a_survey").removeClass("active");
    			$("#a_user").removeClass("active");
    			$("#a_analytics").removeClass("active");
    			$("#a_subscription").addClass("active");
    		}
    	}
	};
	
	function handleSurveyTab(caller) {
		if (caller) {
			if(caller === 'survey_new') {
				$("#a_survey_new").addClass("active");
				$("#a_survey_existing").removeClass("active");
				$("#div_survey_new").show();
			} else if(caller === 'survey_existing') {
				$("#a_survey_new").removeClass("active");
				$("#a_survey_existing").addClass("active");
			}
		}
	}
    </script>
  </body>
</html>
