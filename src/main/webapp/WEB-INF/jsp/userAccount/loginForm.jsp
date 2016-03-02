<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template" %>

<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Bootstrap Registration Form Template</title>

        <!-- CSS -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
        <link rel="stylesheet" href="<c:url value='/secure/assets/bootstrap/css/bootstrap.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/secure/assets/font-awesome/css/font-awesome.min.css'/>">
        <link rel="stylesheet" href="<c:url value='/secure/assets/css/form-elements.css'/>">
        <link rel="stylesheet" href="<c:url value='/secure/assets/css/style.css'/>">
       
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

        <!-- Favicon and touch icons -->
        <link rel="shortcut icon" href="<c:url value='/secure/assets/ico/favicon.png'/>">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="<c:url value='/secure/assets/ico/apple-touch-icon-144-precomposed.png'/>">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="<c:url value='/secure/assets/ico/apple-touch-icon-114-precomposed.png'/>">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="<c:url value='/secure/assets/ico/apple-touch-icon-72-precomposed.png'/>">
        <link rel="apple-touch-icon-precomposed" href="<c:url value='/secure/assets/ico/apple-touch-icon-57-precomposed.png'/>">
       

    </head>

    <body>

		<!-- Top menu -->
		<nav class="navbar navbar-inverse navbar-no-bg" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#top-navbar-1">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="index.html"></a>
				</div>
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="top-navbar-1">
					<ul class="nav navbar-nav navbar-right">
						<li>
							<span class="li-text">
								
							</span> 
							<a href="#"><strong>SecureLogin</strong></a> 
							<span class="li-text">
								
							</span> 
							<span class="li-social">
								<a href="#"><i class="fa fa-facebook"></i></a> 
								<a href="#"><i class="fa fa-twitter"></i></a> 
								<a href="#"><i class="fa fa-envelope"></i></a> 
								<a href="#"><i class="fa fa-skype"></i></a>
							</span>
						</li>
					</ul>
				</div>
			</div>
		</nav>

        <!-- Top content -->
        <div class="top-content">
            <div class="inner-bg">
                <div class="container">
                           <template:admin>
                                <div class ="container">
							       <form action="<c:url value="login"/>" method="POST">
							         <div class="col-md-12">
								       <div class="form-group col-md-4">
				                    		<label class="sr-only" for="form-first-name">Login:</label>
				                        	<input id="userAccount.userName" class="form-first-name form-control" type="text" placeholder="User Name..." name="userAccount.userName" value='${userAccount.userName}'/>
				                        </div>
				                      </div>
				                      <div class="col-md-12">
								        <div class="form-group col-md-4">
				                    		<label class="sr-only" for="form-first-name">Password:</label>
				                        	<input id="userAccount.userPwd" class="form-first-name form-control" type="password" placeholder="Password..." name="userAccount.userPwd" value='${userAccount.userPwd}'/>
				                        </div>
				                       
				                     </div>
				                      <div class="col-md-12">
				                         <div class="form-group col-md-4">
				                            <input type="submit" class="btn-large" value="Submit" />
				                            <span class="error">${badUserLogin}</span>
								    	      <a href="<c:url value="/useraccount/form"/>">Register</a>
				                         </div>
				                      </div>
									</form>
								    <div class ="container min-container">   
								       <h2> USER LOGIN REPORT</h2>
											<form name="timeform">
											Time: <input type=text name="timetextarea" value="00:00" size="10" style = "font-size:20px"> 
											Lap: <input type=text name="laptime" size="10" style = "font-size:20px"><br>
											<br>
											<input type=button name="start" value="Start/Lap" onclick="sw_start()"> 
											<input type=button name="stop" value="Stop" onclick="Stop()"> 
											<input type=button name="reset" value="Reset" onclick="Reset()">
											</form>
								    </div>
								</div>
							</template:admin>
                    </div>
                </div>
         </div> 
	
        <!-- Javascript -->
        <script src="<c:url value='/secure/assets/js/jquery-1.11.1.min.js'/>"></script>
        <script src="<c:url value='/secure/assets/bootstrap/js/bootstrap.min.js'/>"></script>
        <script src="<c:url value='/secure/assets/js/jquery.backstretch.min.js'/>"></script>
        <script src="<c:url value='/secure/assets/js/retina-1.1.0.min.js'/>"></script>
        <script src="<c:url value='/secure/assets/js/scripts.js'/>"></script>
        
        <!--[if lt IE 10]>
            <script src="assets/js/placeholder.js"></script>
        <![endif]-->
		<script type="text/javascript">
		var timercount = 0;
		var timestart  = null;
		 
		function showtimer() {
			if(timercount) {
				clearTimeout(timercount);
				clockID = 0;
			}
			if(!timestart){
				timestart = new Date();
			}
			var timeend = new Date();
			var timedifference = timeend.getTime() - timestart.getTime();
			timeend.setTime(timedifference);
			var minutes_passed = timeend.getMinutes();
			if(minutes_passed < 10){
				minutes_passed = "0" + minutes_passed;
			}
			var seconds_passed = timeend.getSeconds();
			if(seconds_passed < 10){
				seconds_passed = "0" + seconds_passed;
			}
			document.timeform.timetextarea.value = minutes_passed + ":" + seconds_passed;
			timercount = setTimeout("showtimer()", 1000);
		}
		 
		function sw_start(){
			if(!timercount){
			timestart   = new Date();
			document.timeform.timetextarea.value = "00:00";
			document.timeform.laptime.value = "";
			timercount  = setTimeout("showtimer()", 1000);
			}
			else{
			var timeend = new Date();
				var timedifference = timeend.getTime() - timestart.getTime();
				timeend.setTime(timedifference);
				var minutes_passed = timeend.getMinutes();
				if(minutes_passed < 10){
					minutes_passed = "0" + minutes_passed;
				}
				var seconds_passed = timeend.getSeconds();
				if(seconds_passed < 10){
					seconds_passed = "0" + seconds_passed;
				}
				var milliseconds_passed = timeend.getMilliseconds();
				if(milliseconds_passed < 10){
					milliseconds_passed = "00" + milliseconds_passed;
				}
				else if(milliseconds_passed < 100){
					milliseconds_passed = "0" + milliseconds_passed;
				}
				document.timeform.laptime.value = minutes_passed + ":" + seconds_passed + "." + milliseconds_passed;
			}
		}
		 
		function Stop() {
			if(timercount) {
				clearTimeout(timercount);
				timercount  = 0;
				var timeend = new Date();
				var timedifference = timeend.getTime() - timestart.getTime();
				timeend.setTime(timedifference);
				var minutes_passed = timeend.getMinutes();
				if(minutes_passed < 10){
					minutes_passed = "0" + minutes_passed;
				}
				var seconds_passed = timeend.getSeconds();
				if(seconds_passed < 10){
					seconds_passed = "0" + seconds_passed;
				}
				var milliseconds_passed = timeend.getMilliseconds();
				if(milliseconds_passed < 10){
					milliseconds_passed = "00" + milliseconds_passed;
				}
				else if(milliseconds_passed < 100){
					milliseconds_passed = "0" + milliseconds_passed;
				}
				document.timeform.timetextarea.value = minutes_passed + ":" + seconds_passed + "." + milliseconds_passed;
			}
			timestart = null;
		}
		 
		function Reset() {
			timestart = null;
			document.timeform.timetextarea.value = "00:00";
			document.timeform.laptime.value = "";
		}
		 
		</script>
    </body>

</html>
                     
