<div class="panel panel-default">
	<div class="panel-heading login-heading">
		<h3 class="panel-title">Change Password Panel</h3>
	</div>
	<div class="panel-body">
		<form class="form-horizontal">
		 	<div class="form-group" id="idUserName">
			    <label for="inputOldPassword" class="col-sm-3 control-label">Old Password</label>
			    <div class="col-sm-6">
			      <input name="oldPassword" type="password" class="form-control" id="inputOldPassword" placeholder="Old Password"/><span id="oldPassword-error-message" class="text-danger"></span>
			    </div>
		  	</div>
			<div class="form-group">
			    <label for="inputNewPassword" class="col-sm-3 control-label">New Password</label>
			    <div class="col-sm-6">
			     	<input name="newPassword" type="password" class="form-control" id="inputNewPassword" placeholder="New Password" /><span id="newPassword-error-message" class="text-danger"></span>
			    </div>
			</div>
			<div class="form-group">
			    <label for="inputReNewPassword" class="col-sm-3 control-label">Re New Password</label>
			    <div class="col-sm-6">
			     	<input name="reNewPassword" type="password" class="form-control" id="inputReNewPassword" placeholder="Re New Password" /><span id="reNewPassword-error-message" class="text-danger"></span>
			    </div>
			</div> 
		  	<div class="form-group">
				<div class="col-sm-offset-3 col-sm-6">
			      	<button id="btnChangePassword" class="btn btn-default">Change Password</button>
			      	<button id="btnReset" class="btn btn-default">Reset</button>
			    </div>
			</div>
		</form>
	</div>
	<div class="panel-footer">
		Panel Footer
	</div>
</div>