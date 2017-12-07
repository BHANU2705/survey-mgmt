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
            <li class="nav-item active">
              <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <!-- <li class="nav-item">
              <a class="nav-link" href="#">Settings</a>
            </li> -->
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
          <ul class="nav nav-pills flex-column" id="someid">
            <li class="nav-item">
              <a class="nav-link active" onclick="myFunction('common')" href="#">Overview <!-- <span class="sr-only">(current)</span> --></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" onclick="myFunction('survey')" href="">Surveys</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" onclick="myFunction('user')" href="" >My Users</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" onclick="myFunction('analytics')" href="">Analytics</a>
            </li>
          </ul>
        </nav>
        <main id="main" role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
			<div id="common">
				This is the common page.
			</div>
			<div id="survey">
				This is the survey page.
			</div>
			<div id="user">
				This is the user page.
			</div>
			<div id="analytics">
				This is the analytics page.
			</div>
		</main>
      </div>
    </div>

    <script>
    function myFunction(caller) {
    	if(caller) {
    		if (caller === 'common') {
    			$("#common").show();
    			$("#survey").hide();
    			$("#user").hide();
    			$("#analytics").hide();
    		} else if (caller === 'survey') {
    			$("#common").hide();
    			$("#survey").show();
    			$("#user").hide();
    			$("#analytics").hide();
    		} else if(caller === 'user') {
    			$("#common").hide();
    			$("#survey").hide();
    			$("#user").show();
    			$("#analytics").hide();
    		} else if(caller === 'analytics') {
    			$("#common").hide();
    			$("#survey").hide();
    			$("#user").hide();
    			$("#analytics").show();
    		}
    	}
	};
    $(function() {
    	$("#common").show();
		$("#survey").hide();
		$("#user").hide();
		$("#analytics").hide();
        $(document).on('click', '#someid', function(e) {
            e.preventDefault();
        });
    });
    </script>
  </body>
</html>
