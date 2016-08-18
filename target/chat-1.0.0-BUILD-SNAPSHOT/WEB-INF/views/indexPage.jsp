<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/bootstrap.min.css">
	
	<!-- Optional theme -->
	<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/bootstrap-theme.min.css">
	
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/jquery-2.1.4.min.js"></script>
	
	<!-- Latest compiled and minified JavaScript -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/bootstrap.js"></script>
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/resources/js/holder.js"></script>
	
	<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/index.css" >
	
		
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Chat On Home</title>
</head>
<body>
	<div class="container">
		<!-- Static navbar -->
      	<nav class="navbar navbar-inverse navbar-default">
      		<div class="container-fluid">
          		<div class="navbar-header">
            		<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
              			<span class="sr-only">Toggle navigation</span>
              			<span class="icon-bar"></span>
              			<span class="icon-bar"></span>
              			<span class="icon-bar"></span>
            		</button>
            		<a class="navbar-brand" href="<%=request.getContextPath() %>/">Chat On</a>
          		</div>
          		<div id="navbar" class="navbar-collapse collapse">
            		<ul class="nav navbar-nav">
              			<li id="idHome" class="active"><a href="./">Home</a></li>
              			<li id="idLogin"><a href="#login" onclick='return btnLogin()'>Login</a></li>
              			<li id="idsignUp"><a href="" onclick='return btnSignUp()'>Sign up</a></li>
              			<li id="idAboutUs"><a href="" onclick='return btnAboutUs()'>About Us</a></li>
              			<li id="idContactUs"><a href="" onclick='return btnContactUs()'>Contact Us</a></li>
              			<li id="idLearn"><a href="" onclick='return btnLearn()'>Learn</a></li>
            		</ul>
          		</div><!--/.nav-collapse -->
        	</div><!--/.container-fluid -->
      	</nav>
      	<div id="middle">
      	<div class="container marketing">
      	<!-- Three columns of text below the carousel -->
      	<div class="row">
        	<div class="col-lg-4">
          		<img class="img-circle" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="Generic placeholder image" width="140" height="140">
          		<h2>Heading</h2>
          		<p>Donec sed odio dui. Etiam porta sem malesuada magna mollis euismod. Nullam id dolor id nibh ultricies vehicula ut id elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna.</p>
          		<p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        	</div><!-- /.col-lg-4 -->
        	<div class="col-lg-4">
          		<img class="img-circle" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="Generic placeholder image" width="140" height="140">
          		<h2>Heading</h2>
          		<p>Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Cras mattis consectetur purus sit amet fermentum. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh.</p>
          		<p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        	</div><!-- /.col-lg-4 -->
        	<div class="col-lg-4">
          		<img class="img-circle" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="Generic placeholder image" width="140" height="140">
          		<h2>Heading</h2>
          		<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Vestibulum id ligula porta felis euismod semper. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus.</p>
          		<p><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
        	</div><!-- /.col-lg-4 -->
      	</div><!-- /.row -->
      	
      	<!-- START THE FEATURETTES -->

      	<hr class="featurette-divider">

      	<div class="row featurette">
        	<div class="col-md-7">
          		<h2 class="featurette-heading">First featurette heading. <span class="text-muted">It'll blow your mind.</span></h2>
          		<p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
        	</div>
        	<div class="col-md-5">
        	  	<img class="featurette-image img-responsive center-block" data-src="holder.js/500x500/auto" alt="Generic placeholder image">
        	</div>
    	</div>

      	<hr class="featurette-divider">
      	<div class="row featurette">
      		<div class="col-md-7 col-md-push-5">
          		<h2 class="featurette-heading">Oh yeah, it's that good. <span class="text-muted">See for yourself.</span></h2>
          		<p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
        	</div>
        	<div class="col-md-5 col-md-pull-7">
          		<img class="featurette-image img-responsive center-block" data-src="holder.js/500x500/auto" alt="Generic placeholder image">
        	</div>
      	</div>

      	<hr class="featurette-divider">

      	<div class="row featurette">
        	<div class="col-md-7">
          		<h2 class="featurette-heading">And lastly, this one. <span class="text-muted">Checkmate.</span></h2>
          		<p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
        	</div>
        	<div class="col-md-5">
          		<img class="featurette-image img-responsive center-block" data-src="holder.js/500x500/auto" alt="Generic placeholder image">
        	</div>
      	</div>

      	<hr class="featurette-divider">

      	<!-- /END THE FEATURETTES -->

      	<!-- FOOTER -->
      	<footer>
      	  	<p class="pull-right"><a href="#">Back to top</a></p>
        	<p>&copy; 2014 Company, Inc. &middot; <a href="#">Privacy</a> &middot; <a href="#">Terms</a></p>
      	</footer>

    	</div><!-- /.container -->
      	
	</div>
	</div>
	
	<script type="text/javascript">
		
		$(document).ready(function(){
			var url="<%=request.getContextPath() %>/#login";
			if(window.location.pathname+window.location.hash==url){
				btnLogin();
			}
		});
	
		var ssotoken;
		function btnLogin()
	   	{
	   		console.log("btnLogin()");
	   		var url="<%=request.getContextPath() %>/loginPage";
			var httpMethod="GET";
			var ajax=new XMLHttpRequest();
			ajax.open(httpMethod,url,true);
			ajax.onreadystatechange=function(){
				if(ajax.readyState==4){
					if(ajax.status==200){
						$("#middle").html(ajax.responseText);
						cleanSelections();
						$("#idLogin").addClass("active");
					}
		 		}
			};
			ajax.send(null);
	   	    return false;
	   	}
		
		function btnSignUp()
	   	{
	   		console.log("btnSignUp()");
	   		var url="<%=request.getContextPath() %>/signUpPage";
			var httpMethod="GET";
			var ajax=new XMLHttpRequest();
			ajax.open(httpMethod,url,true);
			ajax.onreadystatechange=function(){
				if(ajax.readyState==4){
					if(ajax.status==200){
						$("#middle").html(ajax.responseText);
						cleanSelections();
						$("#idSignUp").addClass("active");
					}
		 		}
			};
			ajax.send(null);
			captcha();
	   	    return false;
	   	}
		
		function btnAboutUs()
	   	{
	   		console.log("btnAboutUs()");
	   		var url="<%=request.getContextPath() %>/aboutUsPage";
			var httpMethod="GET";
			var ajax=new XMLHttpRequest();
			ajax.open(httpMethod,url,true);
			ajax.onreadystatechange=function(){
				if(ajax.readyState==4){
					if(ajax.status==200){
						$("#middle").html(ajax.responseText);
						cleanSelections();
						$("#idAboutUs").addClass("active");
					}
		 		}
			};
			ajax.send(null);
	   	    return false;
	   	}
		
		function btnContactUs()
	   	{
	   		console.log("btnContactUs()");
	   		var url="<%=request.getContextPath() %>/contactUsPage";
			var httpMethod="GET";
			var ajax=new XMLHttpRequest();
			ajax.open(httpMethod,url,true);
			ajax.onreadystatechange=function(){
				if(ajax.readyState==4){
					if(ajax.status==200){
						$("#middle").html(ajax.responseText);
						cleanSelections();
						$("#idContactUs").addClass("active");
					}
		 		}
			};
			ajax.send(null);
	   	    return false;
	   	}
		
		function btnLearn()
	   	{
	   		console.log("btnLearn()");
	   		var url="<%=request.getContextPath() %>/learnPage";
			var httpMethod="GET";
			var ajax=new XMLHttpRequest();
			ajax.open(httpMethod,url,true);
			ajax.onreadystatechange=function(){
				if(ajax.readyState==4){
					if(ajax.status==200){
						$("#middle").html(ajax.responseText);
						cleanSelections();
						$("#idLearn").addClass("active");
					}
		 		}
			};
			ajax.send(null);
	   	    return false;
	   	}
		
		function cleanSelections(){
			$("#idHome").removeClass("active");
	   		$("#idLogin").removeClass("active");
	   		$("#idSignUp").removeClass("active");
	   		$("#idAboutUs").removeClass("active");
	   		$("#idContactUs").removeClass("active");
	   		$("#idLearn").removeClass("active");
	   	}
	
	
		$(document).ready(function(){
	   		$(document).on('click',"#btnLogin", function(e){
	   			console.log("btnLogin clicked");
	   			e.preventDefault();
	   			$("#username-error-message").text("");
	   			$("#password-error-message").text("");
	   			var url="<%=request.getContextPath() %>/login";
	   			var httpMethod="POST";
	   			var userCredentials="username="+$("#inputUsername").val()+"&"+"password="+$("#inputPassword").val();
	   			console.log(userCredentials);
	   			var ajax=new XMLHttpRequest();
	   			ajax.open(httpMethod,url,true);
	   			ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	   			console.log(userCredentials)
	   			ajax.send(userCredentials);
	   			ajax.onreadystatechange=function(){
	   				if(ajax.readyState==4){
	   					if(ajax.status==200){
	   						console.log(ajax.responseText);
	   						window.location="<%=request.getContextPath() %>/home";
	   					}else if(ajax.status==400){
	   		 				var errorUser=JSON.parse(ajax.responseText);
	   		 				var fieldErrors=errorUser.fieldErrors;
	   		 				for(i=0;i<fieldErrors.length;i++){
	   		 					var errorId=fieldErrors[i].fieldName;
	   		 					errorId="#"+errorId+"-error-message";
	   		 					errorMessage=fieldErrors[i].fieldError;
	   		 					$(errorId).text(errorMessage);
	   		 				}
	   		 			}else if(ajax.status=401){
	   		 				$("#login-error-message").text("");
	   		 				var error=JSON.parse(ajax.responseText);
	   		 				var messageDescription=error.messageDescription;
	   		 				var box='<div class="alert alert-danger alert-dismissible" role="alert">';
	   		 				box=box+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
	   		 		  		box=box+'<strong>Login Error!</strong>'+' '+messageDescription;
	   		 				box=box+'</div>';
	   		 				console.log(box);
	   		 				$('#login-error-message').html(box);
	   		 			}
	   		 		}
	   			};
	   		})
	   	});		
		
		$(document).on('click','#btnCaptcha',captcha);
		
		function captcha(){
			console.log("btnCaptcha()");
			var url="<%=request.getContextPath() %>/getCaptcha";
			var captchaContainer='<img src="'+url+'" alt="ssdf" height="140px" width="100px">'
			$('#inputCaptchaImage').attr("src",url);
		}
		
		//$(document).("#btnCaptcha").click();
		
		
		$(document).ready(function(){
	   		$(document).on('click',"#btnSignUp", function(e){
	   			e.preventDefault();
	   			clearErrors();
	   			//dob=new Date();
	   			var url="<%=request.getContextPath() %>/signUp";
	   			var httpMethod="POST";
	   			var user={
	   				"firstName":$("#inputFirstName").val(),
	   			    "lastName":$("#inputLastName").val(),
	   			    "username":$("#inputUsername").val(),
	   			    "password":$("#inputPassword").val(),
	   			    "rePassword":$("#inputRePassword").val(),
	   			    "dateOfBirth":$("#inputDateOfBirth").val(),
	   			    "mobileNumber":$("#inputMobileNumber").val(),
	   			    "houseNumber":$("#inputHouseNumber").val(),
	   			    "street":$("#inputStreet").val(),
	   			    "city":$("#inputCountry").val(),
	   			    "state":$("#inputState").val(),
	   			    "country":$("#inputCountry").val(),
	   			    "captchaText":$("#inputCaptchaText").val()
	   			};
	   			user=JSON.stringify(user);
	   			var ajax=new XMLHttpRequest();
	   			ajax.open(httpMethod,url,true);
	   			//ajax.setRequestHeader("ssotoken",ssotoken);
	   			ajax.onreadystatechange=function(){
	   				if(ajax.readyState==4){
	   					if(ajax.status==200){
	   						//window.location="<%=request.getContextPath() %>/loginPage";
	   						btnLogin();
	   					}else if(ajax.status==400){
	   		 				var errorUser=JSON.parse(ajax.responseText);
	   		 				var fieldErrors=errorUser.fieldErrors;
	   		 				for(i=0;i<fieldErrors.length;i++){
	   		 					var fieldName=fieldErrors[i].fieldName;
	   		 					var firstChar=fieldName[0].toUpperCase();
	   		 					var fieldId="#input"+firstChar+fieldName.substring(1,fieldName.length);
	   		 					var errorId="#"+fieldName+"-error-message";
	   		 					var errorMessage=fieldErrors[i].fieldError;
	   		 					$(fieldId).val("");
	   		 					$(errorId).text(errorMessage);
	   		 				}
	   		 				
	   		 			}
	   		 		}
	   			};
	   			ajax.setRequestHeader("Content-Type","application/json");
	   			console.log(user);
	   			ajax.send(user);
	   		})
	   	});
		
  		$(document).ready(function(){
	   		$(document).on('click',"#btnReset",function (e){
	   			e.preventDefault();
	  			$("#inputFirstName").val("");
				$("#inputLastName").val("");
				$("#inputEmailId").val("");
				$("#inputPassword").val("");
				$("#inputRePassword").val("");
				$("#inputDateOfBirth").val("");
				$("#inputMobileNumber").val("");
				$("#inputHouseNumber").val("");
				$("#inputStreet").val("");
				$("#inputCountry").val("");
				$("#inputState").val("");
				$("#inputCountry").val("");
				$("#inputCaptchaText").val("");
				clearErrors();
  			})
  		});
  		function clearErrors(){
  			$("#firstName-error-message").text("");
  			$("#lastName-error-message").text("");
  			$("#emailId-error-message").text("");
  			$("#password-error-message").text("");
  			$("#rePassword-error-message").text("");
  			$("#dateOfBirth-error-message").text("");
  			$("#mobileNumber-error-message").text("");
  			$("#houseNumber-error-message").text("");
  			$("#street-error-message").text("");
  			$("#state-error-message").text("");
  			$("#country-error-message").text("");
  			$("#captchaText-error-message").text("");
  		}
		
	</script>
</body>
</html>