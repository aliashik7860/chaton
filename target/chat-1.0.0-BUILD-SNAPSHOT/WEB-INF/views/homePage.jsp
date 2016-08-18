<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<title>User Home</title>
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
            		<a class="navbar-brand" href="<%=request.getContextPath() %>/home">Chat On</a>
          		</div>
          		<div id="navbar" class="navbar-collapse collapse">
            		
            		<ul class="nav navbar-nav navbar-right">
              			<li class="dropdown">
               				<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Dropdown <span class="caret"></span></a>
                			<ul class="dropdown-menu" role="menu">
                  				<li><a href="" onclick='return btnProfile()'>Profile</a></li>
                  				<li><a href="" onclick='return btnSetting()'>Setting</a></li>
                  				<li class="divider"></li>
                  				<li><a href="" onclick='return btnAccountSetting()'>Account Setting</a></li>
                  				<li><a href="" onclick='return btnLogout()'>Logout</a></li>
                			</ul>
              			</li>
            		</ul>
          		</div><!--/.nav-collapse -->
        	</div><!--/.container-fluid -->
      	</nav>
      	<div class="col-sm-3">
	      	<div class="panel panel-default">
	  			<div class="panel-heading login-heading">
	    			<h3 class="panel-title">Notification Panel</h3>
	  			</div>
	  			<div class="panel-body">
	  				<ul class="nav nav-pills nav-stacked">
  						<li role="presentation" id="idHome" class="active"><a href="" onclick='return btnHome()'><span class="glyphicon glyphicon-home" aria-hidden="true"></span>&nbsp;&nbsp;Home</a></li>
  						<li role="presentation" id="idCompose"><a href="" onclick='return btnCompose()'><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>&nbsp;&nbsp;Compose</a></li>
  						<li role="presentation" id="idInbox"><a href="" onclick='return btnInbox()'><span class="glyphicon glyphicon-inbox" aria-hidden="true"></span>&nbsp;&nbsp;Inbox</a></li>
  						<li role="presentation" id="idOutbox"><a href="" onclick='return btnOutbox()'><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>&nbsp;&nbsp;Outbox</a></li>
  						<li role="presentation" id="idTrash"><a href="" onclick='return btnTrash()'><span class="glyphicon glyphicon-trash" aria-hidden="true"></span>&nbsp;&nbsp;Trash</a></li>
  						<li role="presentation" id="idPhoto"><a href="" onclick='return btnPhoto()'><span class="glyphicon glyphicon-picture" aria-hidden="true"></span>&nbsp;&nbsp;Photo</a></li>
  						<li role="presentation" id="idAudio"><a href="" onclick='return btnAudio()'><span id="btnAudio" class="glyphicon glyphicon-music" aria-hidden="true"></span>&nbsp;&nbsp;Audio</a></li>
  						<li role="presentation" id="idVideo"><a href="" onclick='return btnVideo()'><span class="glyphicon glyphicon-film" aria-hidden="true"></span>&nbsp;&nbsp;Video</a></li>
  						<li role="presentation" id="idFile"><a href="" onclick='return btnFile()'><span class="glyphicon glyphicon-file" aria-hidden="true"></span>&nbsp;&nbsp;File</a></li>
  						<li role="presentation" id="idUpload"><a href="" onclick='return btnUpload()'><span class="glyphicon glyphicon-upload" aria-hidden="true"></span>&nbsp;&nbsp;Upload</a></li>
					</ul>
	  			</div>
	  			<div class="panel-footer">
	  				Panel Footer
	  			</div>
			</div>
		</div>
		<div class="col-sm-9" id="middle-content">
			
		</div>
      </div>
      
      
      <script type="text/javascript">
 		
      	function btnProfile()
	   	{
	   		console.log("btnProfile()");
	   		var data=commonPanelBeforeBody('Profile Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			return false;
	   	}
      	
      	function btnSetting()
	   	{
	   		console.log("btnSetting()");
	   		var data=commonPanelBeforeBody('Setting Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			return false;
	   	}
      	
      	function commonPanelBeforeBody(title){
      		var htmlContent='\
      		<div class="panel panel-default">\
      			<div class="panel-heading login-heading">\
      				<h3 class="panel-title">'+title+'</h3>\
      			</div>\
      			<div class="panel-body">';
      		return htmlContent;
      	}
      	function commonPanelAfterBody(){
      		var htmlContent='</div>\
      		<div class="panel-footer">\
      			Panel Footer\
      		</div>\
      		</div>';
      		return htmlContent;
      	}
      	function btnAccountSetting()
	   	{
	   		console.log("btnAccountSetting()");	   		
	   		var data=commonPanelBeforeBody('Change Password Panel');
	   		data+='\
	   		<form class="form-horizontal">\
	   			<div class="form-group" id="idUserName">\
	   				<label for="inputOldPassword" class="col-sm-3 control-label">Old Password</label>\
	   				<div class="col-sm-6">\
	   					<input name="oldPassword" type="password" class="form-control" id="inputOldPassword" placeholder="Old Password"/><span id="oldPassword-error-message" class="text-danger"></span>\
	   				</div>\
	   			</div>\
	   			<div class="form-group">\
	   				<label for="inputNewPassword" class="col-sm-3 control-label">New Password</label>\
	   				<div class="col-sm-6">\
	   					<input name="newPassword" type="password" class="form-control" id="inputNewPassword" placeholder="New Password" /><span id="newPassword-error-message" class="text-danger"></span>\
	   				</div>\
	   			</div>\
   				<div class="form-group">\
					<label for="inputReNewPassword" class="col-sm-3 control-label">Re New Password</label>\
					<div class="col-sm-6">\
						<input name="reNewPassword" type="password" class="form-control" id="inputReNewPassword" placeholder="Re New Password" /><span id="reNewPassword-error-message" class="text-danger"></span>\
					</div>\
				</div>\
				<div class="form-group">\
					<div class="col-sm-offset-3 col-sm-6">\
						<button id="btnChangePassword" class="btn btn-default">Change Password</button>\
						<button id="btnReset" class="btn btn-default">Reset</button>\
					</div>\
				</div>\
			</form>';
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
			cleanSelections();
	   	    return false;
	   	}
      	
      	
      	function btnLogout(){
      		console.log("btnLogout()");
      		var urlLogout="<%=request.getContextPath() %>/home/logout";
   			var httpMethod="GET";
   			var ajaxLogout=new XMLHttpRequest();
   			ajaxLogout.open(httpMethod,urlLogout,true);
   			ajaxLogout.onreadystatechange=function(){
   				if(ajaxLogout.readyState==4){
   					console.log(ajaxLogout.responseText);
   					if(ajaxLogout.status==200){
   						window.location="<%=request.getContextPath() %>"+"#login";
   					}else if(ajaxLogout.status=401){
   						var error=JSON.parse(ajaxLogout.responseText);
   						var yesOrNo=confirm("session has expired or you are not authorized to access this resource press ok to go to login page");
   						if(yesOrNo===true){
   							window.location="<%=request.getContextPath() %>"+"#login";
   						}
   					}
   				}
   			}
   			ajaxLogout.send(null);
      		return false;
      	}
      
      	$(document).ready(btnHome());
      	function btnHome()
	   	{
      		
      		console.log("btnHome()");
      		var urlCurrentLogin="<%=request.getContextPath() %>/home/users/liveMe";
   			var httpMethod="GET";
   			var ajaxProfileDetails=new XMLHttpRequest();
   			ajaxProfileDetails.open(httpMethod,urlCurrentLogin,true);
   			ajaxProfileDetails.onreadystatechange=function(){
   				if(ajaxProfileDetails.readyState==4){
   					if(ajaxProfileDetails.status==200){
   						var response=JSON.parse(ajaxProfileDetails.responseText);
   						var user=response.response;
   						console.log(user);
   						var profileDetailsContainer='<div class="alert alert-success" role="alert">';
   						profileDetailsContainer=profileDetailsContainer+'<div><strong>Name : </strong>'+user.name+'</div>';
   						profileDetailsContainer=profileDetailsContainer+'<div><strong>User name : </strong>'+user.username+'</div>';
   						profileDetailsContainer=profileDetailsContainer+'<div><strong>City : </strong>'+user.city+'</div>';
   						profileDetailsContainer=profileDetailsContainer+'<div><strong>State : </strong>'+user.state+'</div>';
   						profileDetailsContainer=profileDetailsContainer+'<div><strong>Country : </strong>'+user.country+'</div>';
   						profileDetailsContainer=profileDetailsContainer+'</div>';
   						$('#profile-details').html(profileDetailsContainer);
   						var urlProfilePhoto='<%=request.getContextPath() %>/home/profilePhoto/'+user.id;
   						//console.log(urlProfilePhoto);
   						var profilePhotoContainer='<img src="'+urlProfilePhoto+'" alt="ssdf" height="140px" width="100px">';
   						console.log(profilePhotoContainer);
   						$('#profile-photo').html(profilePhotoContainer);
   					}else if(ajaxProfileDetails.status==401){
   						var error=JSON.parse(ajaxProfileDetails.responseText);
   						var yesOrNo=confirm("session has expired or you are not authorized to access this resource press ok to go to login page");
   						if(yesOrNo==true){
   							window.location="<%=request.getContextPath() %>"+"#login";
   						}
   					}
   				}
   			}
			
   			ajaxProfileDetails.send(null);
   			
	   		var data=commonPanelBeforeBody('Home Panel');
   			data=data+'<div class="col-md-3" id="profile-photo">';
   			//data=data+'<img src="<%=request.getContextPath() %>/resources/images/a.jpg" alt="sdf" height="140px" width="100px">';
   			data=data+'</div>';
   			data=data+'<div class="col-md-4"></div>';
   			data=data+'<div class="col-md-5" id="profile-details">';
   			data=data+'</div>';
   			data=data+commonPanelAfterBody();
	   		$("#middle-content").html(data);
 			cleanSelections();
 			$("#idHome").addClass("active");
	   	    return false;
	   	}
      	
      	function btnCompose()
	   	{
	   		console.log("btnCompose()");
	   		var data=commonPanelBeforeBody('Compose Panel');
	   		data=data+'<div id="users-list"></div>';
			data=data+'<div><textarea class="form-control" id="message-box" rows="5"></textarea></div>';
			data=data+'<div><button class="btn btn-primary" type="submit">Send</button>';
			data=data+'<button class="btn btn-danger" type="submit">Clear</button></div>';
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			$("#idCompose").addClass("active");
 			loadComposeData();
 			//myVar=setInterval(loadComposeData,3000);
 			checkLive();
	   	    return false;
	   	}
      	
      	function loadComposeData(){
      		var urlCurrentLogin="<%=request.getContextPath() %>/home/users/liveMe";
   			var httpMethod="GET";
   			var ajaxProfileDetails=new XMLHttpRequest();
   			ajaxProfileDetails.open(httpMethod,urlCurrentLogin,true);
   			ajaxProfileDetails.onreadystatechange=function(){
   				if(ajaxProfileDetails.readyState==4){
   					if(ajaxProfileDetails.status==200){
   						var me=JSON.parse(ajaxProfileDetails.responseText);
   						console.log(me);
   						var urlLiveAndNotLive="<%=request.getContextPath() %>/home/users/liveAndNotLive";
   						var ajaxLiveAndNotLive=new XMLHttpRequest();
   						ajaxLiveAndNotLive.open(httpMethod,urlLiveAndNotLive,true);
   						ajaxLiveAndNotLive.onreadystatechange=function(){
   							if(ajaxLiveAndNotLive.readyState==4){
   								if(ajaxProfileDetails.status==200){
   									var usersLiveAndNotLive=JSON.parse(ajaxLiveAndNotLive.responseText);
   									console.log(usersLiveAndNotLive);
   									var liveUsersContainer='<div style="overflow: auto; max-height: 150px; font-size: 24px;" id="all-users">';
   									for(i=0;i<usersLiveAndNotLive.length;i++){
   										var status="";
   										if(usersLiveAndNotLive[i].active==true){
   											status='<span id="online-status">&bull;</span>';
   										}
   										var urlProfilePhoto='<%=request.getContextPath() %>/home/profilePhoto/'+usersLiveAndNotLive[i].id;
   				   						//console.log(urlProfilePhoto);
   				   						liveUsersContainer=liveUsersContainer+'<div><div class="col-xs-5"><img src="'+urlProfilePhoto+'" alt="sdf" height="50px" width="50px"><label><input type="checkbox" value="'+usersLiveAndNotLive[i].id+'">'+usersLiveAndNotLive[i].name+'</label></div><div class="col-xs-1">'+status+'</div></div>';
   										status="";
   										console.log(liveUsersContainer);
   									}
   									liveUsersContainer=liveUsersContainer+'</div>';
   									$('#users-list').html(liveUsersContainer);
   									$('#online-status').css("color","#32cd32");
   								}
   							}
   						}
   						ajaxLiveAndNotLive.send(null);
   					}else if(ajaxProfileDetails.status==200){
   						var error=JSON.parse(ajaxProfileDetails.responseText);
   						var yesOrNo=confirm("session has expired or you are not authorized to access this resource press ok to go to login page");
   						if(yesOrNo==true){
   							window.location="<%=request.getContextPath() %>"+"#login";
   						}
   					}
   				}
   			}
   			ajaxProfileDetails.send(null);
      	}
      	function checkLive(){
      		console.log("all users list\n"+$('#users-list').get+" end");
      	}
      	function btnSendMessage(){
      		console.log("btnSendMessage");
      		var message=$('#message-box').val();
      		
      	}
      	
      	function btnInbox()
	   	{
	   		console.log("btnInbox()");
	   		var data=commonPanelBeforeBody('Inbox Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			$("#idInbox").addClass("active");
	   	    return false;
	   	}
      	function btnOutbox()
	   	{
	   		console.log("btnOutbox()");
	   		var data=commonPanelBeforeBody('Outbox Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			$("#idOutbox").addClass("active");
	   	    return false;
	   	}
      	function btnTrash()
	   	{
	   		console.log("btnTrash()");
	   		var data=commonPanelBeforeBody('Trash Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			$("#idTrash").addClass("active");
	   	    return false;
	   	}
      	function btnPhoto()
	   	{
	   		console.log("btnPhoto()");
	   		var data=commonPanelBeforeBody('Photo Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			$("#idPhoto").addClass("active");
	   	    return false;
	   	}
	   	function btnAudio()
	   	{
	   		console.log("btnAudio()");
	   		var data=commonPanelBeforeBody('Audio Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			$("#idAudio").addClass("active");
	   	    return false;
	   	}
	   	
	   	function btnVideo()
	   	{
	   		console.log("btnVideo()");
	   		var data=commonPanelBeforeBody('Video Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			$("#idVideo").addClass("active");
	   	    return false;
	   	}
	   	
	   	function btnFile()
	   	{
	   		console.log("btnFile()");
	   		var data=commonPanelBeforeBody('File Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			$("#idFile").addClass("active");
	   	    return false;
	   	}
	   	
	   	function btnUpload()
	   	{
	   		console.log("btnUpload()");
	   		var data=commonPanelBeforeBody('Upload Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			$("#idUpload").addClass("active");
	   	    return false;
	   	}
	   	
	   	function cleanSelections(){
	   		$("#idHome").removeClass("active");
	   		$("#idCompose").removeClass("active");
	   		$("#idInbox").removeClass("active");
	   		$("#idOutbox").removeClass("active");
	   		$("#idTrash").removeClass("active");
	   		$("#idPhoto").removeClass("active");
	   		$("#idAudio").removeClass("active");
	   		$("#idVideo").removeClass("active");
	   		$("#idFile").removeClass("active");
	   		$("#idUpload").removeClass("active");
	   	}
	   	
   	</script>
   	
	</body>
</html>