$("#inputEmailID").on('input', function(e){
	var emailID=$("#inputEmailID").val();
	var txtEmail={
			'emailID':emailID
	}
	e.preventDefault();
	$.ajax({ 
		url: '/chat/validate_email_existence',
		data:txtEmail,
		contentType: 'application/json',
		type: 'GET',
		success: function(message) {
			if(message==="true"){
				$("#idUserName").attr("class","form-group has-success");
				$("#emailIDExistenceMessage").html("");
			}else{
				$("#idUserName").attr("class","form-group has-error");
				$("#emailIDExistenceMessage").html("email id does not exist");
			}	   
		},
		error: function(e) {
			console.log("error"+e);
		}
	})
});