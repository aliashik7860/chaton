$("#inputFirstName").on("input",function(e){
	var firstName=$("#inputFirstName").val();
	if(firstName.match("^[a-zA-Z]{3,12}$")){
		$("#idFirstName").attr("class","form-group has-success");
		$("#firstNameValidationMessage").html("");
	}else{
		$("#idFirstName").attr("class","form-group has-error");
		$("#firstNameValidationMessage").html("First name is not valid");
	}
});

$("#inputLastName").on("input",function(e){
	var lastName=$("#inputLastName").val();
	if(lastName.match("^[a-zA-Z]{3,12}$")){
		$("#idLastName").attr("class","form-group has-success");
		$("#lastNameValidationMessage").html("");
	}else{
		$("#idLastName").attr("class","form-group has-error");
		$("#lastNameValidationMessage").html("Last name is not valid");
	}
});

$("#inputEmailID").on("input",function(e){
	var emailID=$("#inputEmailID").val();
	var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	if(emailID.match(regex)){
		$("#idEmailID").attr("class","form-group has-success");
		$("#emailIDValidationMessage").html("");
	}else{
		$("#idEmailID").attr("class","form-group has-error");
		$("#emailIDValidationMessage").html("Email ID is not valid");
	}
});

$("#inputPassword").on("input",function(e){
	var password=$("#inputPassword").val();
	var regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";
	if(password.match(regex)){
		$("#idPassword").attr("class","form-group has-success");
		$("#passwordValidationMessage").html("");
	}else{
		$("#idPassword").attr("class","form-group has-error");
		$("#passwordValidationMessage").html("Password should contain at least 4 char and maximum 8 char with at least 1 upper case,one digit ");
	}
});