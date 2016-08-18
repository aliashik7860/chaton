$("#btnCaptcha").on('click', function(e){
	e.preventDefault();
	$.ajax({ 
		url: '/chat/getCaptcha', 
		type: 'GET',
		success: function(rawImageData) {
   			$("#inputCaptchaImage").attr("src","data:application/octet-stream;base64,"+rawImageData);	   
		},
		error: function(e) {
		}
	})
});
	  	
$(document).ready(function(){	
	  $("#btnCaptcha").click();
});