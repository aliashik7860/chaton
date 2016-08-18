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
	
	<style type="text/css">
		.online-status {
      		width: 10px;
      		height: 10px;
      		-webkit-border-radius: 25px;
      		-moz-border-radius: 25px;
      		border-radius: 25px;
      		background: green;
    	}
    	.offline-status {
      		width: 10px;
      		height: 10px;
      		-webkit-border-radius: 25px;
      		-moz-border-radius: 25px;
      		border-radius: 25px;
      		background: red;
    	}
	</style>
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
 		
      	function btnProfile(){
	   		console.log("btnProfile()");
	   		var data=commonPanelBeforeBody('profile-panel','Profile Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			return false;
	   	}
      	
      	function btnSetting(){
	   		console.log("btnSetting()");
	   		var data=commonPanelBeforeBody('setting-panel','Setting Panel');
	   		data=data+commonPanelAfterBody();
	   		console.log(data);
			$("#middle-content").html(data);
 			cleanSelections();
 			return false;
	   	}
      	
      	function commonPanelBeforeBody(id,title){
      		var htmlContent='\
      		<div id='+id+' class="panel panel-default">\
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
      	function btnAccountSetting(){
	   		console.log("btnAccountSetting()");	   		
	   		var data=commonPanelBeforeBody('change-password-panel','Change Password Panel');
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
   			var logoutResponse;
   			getServerResponse(urlLogout,function(response){
   				logoutResponse=response;
   			});
   			console.log("logout Response : "+logoutResponse);
   			window.location="<%=request.getContextPath() %>"+"#login";
      		return false;
      	}
      
      	$(document).ready(btnHome());
      	function btnHome(){
      		console.log("btnHome()");
      		/* Home Tab Data For UI Start*/
      		var homePanel=commonPanelBeforeBody('home-panel','Home Panel');
      		homePanel=homePanel+'<div class="col-md-3" id="profile-photo">';
   			//homePanel=homePanel+'<img src="<%=request.getContextPath() %>/resources/images/a.jpg" alt="sdf" height="140px" width="100px">';
   			homePanel=homePanel+'</div>';
   			homePanel=homePanel+'<div class="col-md-4"></div>';
   			homePanel=homePanel+'<div class="col-md-5" id="profile-details">';
   			homePanel=homePanel+'</div>';
   			homePanel=homePanel+commonPanelAfterBody();
	   		$("#middle-content").html(homePanel);
	   		cleanSelections();
 			$("#idHome").addClass("active");
	   	    
      		var me;
 			var urlLiveUserDetails='<%=request.getContextPath() %>/home/users/liveMe';
 			getServerResponse(urlLiveUserDetails,function(response){
      			me=response;
          	});
 			console.log("after response me : "+me);
			var profileDetailsContainer='<div class="alert alert-success" role="alert">';
			var profileDetailsContainer=profileDetailsContainer+'<div><strong>Name : </strong>'+me.name.firstName+" "+me.name.middleName+" "+me.name.lastName+'</div>';
			profileDetailsContainer=profileDetailsContainer+'<div><strong>User name : </strong>'+me.username+'</div>';
			profileDetailsContainer=profileDetailsContainer+'<div><strong>City : </strong>'+me.address.city+'</div>';
			profileDetailsContainer=profileDetailsContainer+'<div><strong>State : </strong>'+me.address.state+'</div>';
			profileDetailsContainer=profileDetailsContainer+'<div><strong>Country : </strong>'+me.address.country+'</div>';
			profileDetailsContainer=profileDetailsContainer+'</div>';
			console.log("profile details container : "+profileDetailsContainer);
			$('#profile-details').html(profileDetailsContainer);
			var urlProfilePhoto='<%=request.getContextPath() %>/home/profilePhoto/'+me.id;
			//console.log(urlProfilePhoto);
			var profilePhotoContainer='<img src="'+urlProfilePhoto+'" alt="ssdf" height="140px" width="100px">';
			console.log(profilePhotoContainer);
			$('#profile-photo').html(profilePhotoContainer);
			
			/* Home Tab Data For UI End*/
			
			
			/* Compose Tab Data For UI Start */
			
			var composePanel=commonPanelBeforeBody('compose-panel','Compose Panel');
			composePanel=composePanel+'<div id="users-list"></div>';
			composePanel=composePanel+'<div><textarea class="form-control" id="message-box" rows="5"></textarea></div>';
			composePanel=composePanel+'<div><button class="btn btn-primary" onclick="btnSendMessage()">Send</button>';
			composePanel=composePanel+'<button class="btn btn-danger" type="submit">Clear</button></div>';
			composePanel=composePanel+commonPanelAfterBody();
			$("#middle-content").append(composePanel);
			console.log("loading Compose Intial (All Connected Friends) Data");
			
			liveAndNotLiveUserUi();
			/* Compose Tab Data For UI End */
			
			/* Inbox Tab Data For UI Start */
			
			console.log("btnInbox()");
	   		var inboxPanel=commonPanelBeforeBody('inbox-panel','Inbox Panel');
	   		inboxPanel=inboxPanel+'<div id="in-messages-list"></div>';
	   		inboxPanel=inboxPanel+commonPanelAfterBody();
	   		//console.log(inboxPanel);
			$("#middle-content").append(inboxPanel);
 			console.log("loading Inbox Intial");
      		var inMessagesContainer='<div style="overflow: auto; max-height: 450px; font-size: 24px;" id="all-messages" >';
 			var inMessages=[];
 			var urlInMessagesByReceiverId='<%=request.getContextPath() %>/home/message/in/receiverId/'+me.id;
 			getServerResponse(urlInMessagesByReceiverId,function(response){
      			inMessages=response;
          	});
 			console.log("inMessages response : "+inMessages);
 			for(var i=0;i<inMessages.length;i++){
      			var from;
      			var urlGetUserById='<%=request.getContextPath() %>/home/users/id/'+inMessages[i].senderId;
          		getServerResponse(urlGetUserById,function(response){
          			from=response;
          		});
   				console.log("user : "+from);
   				var name=from.username;
   				console.log("sender name : "+name);
				inMessagesContainer=inMessagesContainer+'<div class="alert alert-success alert-dismissible" role="alert">'
		  		+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
		  		+'<strong> From:' +name+'          '+'</strong><br>'+inMessages[i].message+ '</div>';
   			}
      		inMessagesContainer=inMessagesContainer+'</div>';
   			$('#in-messages-list').append(inMessagesContainer);
			
			/* Inbox Tab Data For UI End */
			
			/* Outbox Tab Data For UI Start */
			var outboxPanel=commonPanelBeforeBody('outbox-panel','Outbox Panel');
			outboxPanel=outboxPanel+'<div id="out-messages-list"></div>';
			outboxPanel=outboxPanel+commonPanelAfterBody();
	   		//console.log(data);
			$("#middle-content").append(outboxPanel);
   			console.log("loading Outbox Intial");
      		var inMessagesContainer='<div style="overflow: auto; max-height: 450px; font-size: 24px;" id="all-messages" >';
 			var outMessages=[];
 			var urlOutMessagesByReceiverId='<%=request.getContextPath() %>/home/message/out/senderId/'+me.id;
 			getServerResponse(urlOutMessagesByReceiverId,function(response){
      			outMessages=response;
          	});
 			console.log("outMessages response : "+outMessages);
 			for(var i=0;i<outMessages.length;i++){
      			var to;
      			var urlGetUserById='<%=request.getContextPath() %>/home/users/id/'+outMessages[i].receiverId;
          		getServerResponse(urlGetUserById,function(response){
          			to=response;
          		});
   				console.log("user : "+to);
   				var name=to.username;
   				console.log("receiver name : "+name);
				inMessagesContainer=inMessagesContainer+'<div class="alert alert-success alert-dismissible" role="alert">'
		  		+'<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
		  		+'<strong> To:' +name+'          '+'</strong><br>'+outMessages[i].message+ '</div>';
   			}
      		inMessagesContainer=inMessagesContainer+'</div>';
   			$('#out-messages-list').append(inMessagesContainer);
			
			/* Outbox Tab Data For UI End */
			
			/* Trash Tab Data For UI Start */
			
			var trashPanel=commonPanelBeforeBody('trash-panel','Trash Panel');
			trashPanel=trashPanel+commonPanelAfterBody();
	   		//console.log(data);
			$("#middle-content").append(trashPanel);
			
			/* Trash Tab Data For UI End */
			
			/* Photo Tab Data For UI Start */
			
			var photoPanel=commonPanelBeforeBody('photo-panel','Photo Panel');
			photoPanel=photoPanel+commonPanelAfterBody();
	   		//console.log(photoPanel);
			$("#middle-content").append(photoPanel);
			
			/* Photo Tab Data For UI End */
			
			/* Audio Tab Data For UI Start */
			
			var audioPanel=commonPanelBeforeBody('audio-panel','Audio Panel');
			audioPanel=audioPanel+commonPanelAfterBody();
	   		//console.log(audioPanel);
			$("#middle-content").append(audioPanel);
			
			/* Audio Tab Data For UI End */
			
			/* Video Tab Data For UI Start */
			
			var videoPanel=commonPanelBeforeBody('video-panel','Video Panel');
			videoPanel=videoPanel+commonPanelAfterBody();
	   		//console.log(videoPanel);
			$("#middle-content").append(videoPanel);
			
			/* Video Tab Data For UI End */
			
			/* File Tab Data For UI Start */
			
			var filePanel=commonPanelBeforeBody('file-panel','File Panel');
			filePanel=filePanel+commonPanelAfterBody();
	   		//console.log(filePanel);
			$("#middle-content").append(filePanel);
			
			/* File Tab Data For UI End */
			
			/* Upload Tab Data For UI Start */
			
			var uploadPanel=commonPanelBeforeBody('upload-panel','Upload Panel');
	   		uploadPanel=uploadPanel+commonPanelAfterBody();
	   		//console.log(uploadPanel);
			$("#middle-content").append(uploadPanel);
			
			hidePanels();
			$("#home-panel").show();
			/* Upload Tab Data For UI End */
			
	   		return false;
	   	}
      	
      	function btnCompose(){
      		console.log("btnCompose()");
      		cleanSelections();
 			$("#idCompose").addClass("active");
 			hidePanels();
 			$("#compose-panel").show();
 			//myVar=setInterval(setOnlineAndOffline,10000);
 			//setOnlineAndOffline();
	   	    return false;
	   	}
      	function setOnlineAndOffline(){
      		var usersLiveAndNotLive=getLiveAndNotLiveUsers();
      		for(i=0;i<usersLiveAndNotLive.length;i++){
      			if(usersLiveAndNotLive[i].live==true){
      				//console.log("user : "+usersLiveAndNotLive[i].id+" live : "+usersLiveAndNotLive[i].live);
      				$('#status_'+usersLiveAndNotLive[i].id).removeClass('offline-status');
      				$('#status_'+usersLiveAndNotLive[i].id).addClass('online-status');
      			}else{
      				$('#status_'+usersLiveAndNotLive[i].id).removeClass('online-status');
      				$('#status_'+usersLiveAndNotLive[i].id).addClass('offline-status');
      			}
      		}
      	}
      	function getLiveAndNotLiveUsers(){
      		var usersLiveAndNotLive;
 			var urlLiveAndNotLive="<%=request.getContextPath() %>/home/users/liveAndNotLive";
 			getServerResponse(urlLiveAndNotLive,function(response){
 				usersLiveAndNotLive=response;
          	});
 			//console.log("live and notLive Users : "+usersLiveAndNotLive);
 			return usersLiveAndNotLive;
      	}
      	function liveAndNotLiveUserUi(){
      		var usersLiveAndNotLive=getLiveAndNotLiveUsers();
			var liveUsersContainer='<div style="overflow: auto; max-height: 150px; font-size: 24px;" id="all-users" >';
			for(i=0;i<usersLiveAndNotLive.length;i++){
				var status="";
				if(usersLiveAndNotLive[i].live==true){
					status='<div id=status_'+usersLiveAndNotLive[i].id+' class="online-status" ></div>';
					//console.log(usersLiveAndNotLive[i].username +" : is Live ==true")
					//status='<span id="online-status">&bullsdf</span>';
					//status='<div class='online-status'></div>';
					//$('.online-status').css('background-color','green');
				}else{
					status='<div id=status_'+usersLiveAndNotLive[i].id+' class="offline-status" ></div>';
					//console.log(usersLiveAndNotLive[i].username +" : is Live ==false")
				}
				var urlProfilePhoto='<%=request.getContextPath() %>/home/profilePhoto/'+usersLiveAndNotLive[i].id;
				var name=usersLiveAndNotLive[i].name;
				//console.log("name : "+name.firstName);
				liveUsersContainer=liveUsersContainer+'<div><div class="col-xs-7"><img src="'+urlProfilePhoto+'" alt="sdf" height="50px" width="50px"><label><input type="checkbox" name="friend-names" value="'+usersLiveAndNotLive[i].id+'">'+name.firstName+" "+name.middleName+""+name.lastName+'</label></div><div class="col-xs-1">'+status+'</div></div>';
				status="";
			}
			liveUsersContainer=liveUsersContainer+'</div>';
			//console.log(liveUsersContainer);
			$('#users-list').append(liveUsersContainer);
      	}
      	function btnSendMessage(){
      		console.log("btnSendMessage");
      		var me;
 			var urlLiveUserDetails='<%=request.getContextPath() %>/home/users/liveMe';
 			getServerResponse(urlLiveUserDetails,function(response){
      			me=response;
          	});
 			console.log("after response me : "+me);
			var message=$('#message-box').val();
			var receiverIds=[];
			$("input[name='friend-names']").each(function() {    
      		    if($(this).is(':checked')){
      		   		receiverIds.push($(this).val());
      		    }
      		});
      		var createMessageRequest={
      			"senderId":me.id,
      			"receiverIds":receiverIds,
      			"message":message
      		};
      		console.log("createMessageRequest : "+JSON.stringify(createMessageRequest));
      		var createMessages;
      		var urlCreateMessage="<%=request.getContextPath() %>/home/message/create";
   			sendDataToServer(urlCreateMessage,createMessageRequest,function(response){
   				createMessages=response;
   			});
   			console.log("createMessage response : "+createMessages);
      	}
      	function sendDataToServer(urlString,jsonRequest,callback_function){
       		$.ajax({
       		   url: urlString,
       		   data:JSON.stringify(jsonRequest),
       		   dataType:'json',
       			//contentType: 'application/json',
       		   error: function(xhr,response) {
       			 	console.log("error : "+response);
       			   	if(xhr.status==401){
 						var yesOrNo=confirm("session has expired or you are not authorized to access this resource press ok to go to login page");
 						if(yesOrNo==true){
 							window.location="<%=request.getContextPath() %>"+"#login";
 						}
       			   }else if(xhr.status==500){
       				   alert("oops server error")
       			   }
       		   },
       		   async: false,
       		   success: function(data) {
       		      console.log("response"+JSON.stringify(data));
       		      callback_function(data.response);
       		   },
       		   type: 'POST'
       		});
       	}
      	function btnInbox(){
      		console.log("btnInbox()");
      		cleanSelections();
 			$("#idInbox").addClass("active");
 			hidePanels();
 			$("#inbox-panel").show();
	   	    return false;
	   	}
      	
      	function getServerResponse(urlString,callback_function){
      		$.ajax({
      		   url: urlString,
      		   data: {
      		      format: 'json'
      		   } ,
      		   error: function(xhr,response) {
      			 	console.log("error : "+response);
      			   	if(xhr.status==401){
						var yesOrNo=confirm("session has expired or you are not authorized to access this resource press ok to go to login page");
						if(yesOrNo==true){
							window.location="<%=request.getContextPath() %>"+"#login";
						}
      			   }else if(xhr.status==500){
      				   alert("oops server error")
      			   }
      		   },
      		   async: false,
      		   success: function(data) {
      		      console.log("response"+JSON.stringify(data));
      		      callback_function(data.response);
      		   },
      		   type: 'GET'
      		});
      	}
      	
      	function btnOutbox(){
	   		console.log("btnOutbox()");
	   		cleanSelections();
 			$("#idOutbox").addClass("active");
 			hidePanels();
 			$("#outbox-panel").show();
	   	    return false;
	   	}
      	function btnTrash(){
	   		console.log("btnTrash()");
	   		cleanSelections();
 			$("#idTrash").addClass("active");
 			hidePanels();
 			$("#trash-panel").show();
	   	    return false;
	   	}
      	function btnPhoto(){
	   		console.log("btnPhoto()");
 			cleanSelections();
 			$("#idPhoto").addClass("active");
 			hidePanels();
 			$("#photo-panel").show();
	   	    return false;
	   	}
	   	function btnAudio(){
	   		console.log("btnAudio()");
 			cleanSelections();
 			$("#idAudio").addClass("active");
 			hidePanels();
 			$("#audio-panel").show();
	   	    return false;
	   	}
	   	
	   	function btnVideo(){
	   		console.log("btnVideo()");
 			cleanSelections();
 			$("#idVideo").addClass("active");
 			hidePanels();
 			$("#video-panel").show();
	   	    return false;
	   	}
	   	
	   	function btnFile(){
	   		console.log("btnFile()");
 			cleanSelections();
 			$("#idFile").addClass("active");
 			hidePanels();
 			$("#file-panel").show();
	   	    return false;
	   	}
	   	
	   	function btnUpload(){
	   		console.log("btnUpload()");
 			cleanSelections();
 			$("#idUpload").addClass("active");
 			hidePanels();
 			$("#upload-panel").show();
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
	   	
	   	function hidePanels(){
	   		$("#home-panel").hide();
	   		$("#compose-panel").hide();
	   		$("#inbox-panel").hide();
	   		$("#outbox-panel").hide();
	   		$("#trash-panel").hide();
	   		$("#photo-panel").hide();
	   		$("#audio-panel").hide();
	   		$("#video-panel").hide();
	   		$("#file-panel").hide();
	   		$("#upload-panel").hide();
	   	}
	   	
   	</script>
   	
	</body>
</html>