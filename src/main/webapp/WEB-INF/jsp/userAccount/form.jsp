
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
           
                    <div class="row">
                    	<div class="col-sm-6 book">
                    		<img src="<c:url value='/secure/assets/img/ebook.png'/>" alt="">
                    	</div>
                        <div class="col-sm-1">

                           <template:admin>
							  
							    <div class ="container min-container">      
							      <form action="add" method="POST">
							        <div class="form-group">
			                    		<label class="sr-only" for="form-first-name">First Name:</label>
			                        	<input type="text" class="form-first-name form-control" placeholder="First name..." name="userAccount.firstName" id="userAccount.firstName" value='${userAccount.firstName}' />
			                        </div>
			                        <div class="form-group">
			                    		<label class="sr-only" for="form-first-name">Last Name:</label>
			                        	<input type="text" name="userAccount.lastName" id="userAccount.lastName" value='${userAccount.lastName}' class="form-first-name form-control" placeholder="Last name..."   />
			                        </div>
								    <div class="form-group">
			                    		<label class="sr-only" for="form-first-name">User Name:</label>
			                        	<input type="text" name="userAccount.userName" id="userAccount.userName" value='${userAccount.userName}' class="form-first-name form-control" placeholder="User name..."   />
			                        </div>
								    <div class="form-group">
			                    		<label class="sr-only" for="form-first-name">Password:</label>
			                        	<input type="text" name="userAccount.userPwd" id="userAccount.userPwd" value='${userAccount.userPwd}' class="form-first-name form-control" placeholder="Password..."   />
			                        </div>
								    <div class="form-group">
			                    		<label class="sr-only" for="form-first-name">Email:</label>
			                        	<input type="text" name="userAccount.email" id="userAccount.email" value='${userAccount.email}' class="form-first-name form-control" placeholder="Email..."   />
			                        </div>
								    <div class="form-group">
			                    		<label class="sr-only" for="form-first-name">Born Date:</label>
			                        	<input type="date" name="userAccount.bornDate" id="userAccount.bornDate" value='${userAccount.bornDate}' class="form-first-name form-control" placeholder="Born Date..."   />
			                        </div>
								    <div class="form-group">
			                    		<label class="sr-only" for="form-first-name">Mobile:</label>
			                        	<input type="text" name="userAccount.mobilePhone" id="userAccount.mobilePhone" value='${userAccount.mobilePhone}' class="form-first-name form-control" placeholder="Mobile Phone..."   />
			                        </div>
								    <div class="form-group">
			                    		<label class="sr-only" for="form-first-name">Home Phone:</label>
			                        	<input type="text" name="userAccount.homePhone" id="userAccount.homePhone" value='${userAccount.homePhone}' class="form-first-name form-control" placeholder="Home Phone..."   />
			                        </div>
								    <div class="form-group">
			                    		<label class="sr-only" for="form-first-name">Work Phone:</label>
			                        	<input type="text" name="userAccount.workPhone" id="userAccount.workPhone" value='${userAccount.workPhone}' class="form-first-name form-control" placeholder="Work Phone..."   />
			                        </div>
								     <div class="form-group col-sm-1">
			                    		<label class="sr-only" for="form-first-name">Admin Account:</label>Admin?
			                        	<input type="checkbox" name="userAccount.adminAccount" id="userAccount.adminAccount" checked="checked" class="form-first-name form-control"/>
			                          
			                        </div>
							    	 <input type="submit" class="btn-large" value="Submit" />
								</form>
							    </div>   
							  
							</template:admin>
		                   
                        </div>
                    </div>
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

    </body>

</html>
