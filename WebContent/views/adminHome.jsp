<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../../../favicon.ico">

    <title>Admin Home</title>

    <!-- Bootstrap core CSS -->
    <link href="../Test/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../Test/css/dashboard.css" rel="stylesheet">
    <!-- <link rel="import" href="views/survey.html" id="s1"> -->
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
          <ul class="nav nav-pills flex-column">
            <li class="nav-item">
              <a class="nav-link active" href="#">Overview <!-- <span class="sr-only">(current)</span> --></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="">Surveys</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="" >My Users</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="">Analytics</a>
            </li>
          </ul>
        </nav>
        <main id="main" role="main" class="col-sm-9 ml-sm-auto col-md-10 pt-3">
			<div>
				Bhanu
				<script src="https://www.w3schools.com/lib/w3data.js"></script>
				<div w3-include-html="views/survey.html"></div>
			</div>
		</main>
      </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <!-- <script>window.jQuery || document.write('<script src="../../../../assets/js/vendor/jquery.min.js"><\/script>')</script> -->
    <script src="../Test/js/vendor/jquery-slim.min.js"></script>
    <script src="../Test/js/vendor/popper.min.js"></script>
    <script src="../Test/js/bootstrap.min.js"></script>
    
    <!-- <script>
    function myFunction(caller) {
    	var html = null;
    	if(caller) {
    		if (caller === 'survey') {
    			html = "views/survey.html";
    		} else if(caller === 'user') {
    			html = "views/survey.html";
    		} else if(caller == 'analytics') {
    			html = "views/survey.html";
    		}
    	}
    	document.getElementById("page1").innerHTML = html;
	}
    </script> -->
    <script type="text/javascript">
    	w3IncludeHTML();
	</script>
  </body>
</html>
