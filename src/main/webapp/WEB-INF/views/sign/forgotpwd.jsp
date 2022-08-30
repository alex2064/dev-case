<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp"%>

<h1 class="auth-title">Forgot Password</h1>
<p class="auth-subtitle mb-5">Input your email and we will send you reset password link.</p>

<form id="formSave" onsubmit="return false;">
	<div class="form-group position-relative has-icon-left mb-4">
		<input type="email" id="txiEmail" class="form-control form-control-xl" placeholder="Email" required>
		<div class="form-control-icon">
			<i class="bi bi-envelope"></i>
		</div>
	</div>
	<div class="form-group position-relative has-icon-left mb-4">
		<input type="text" id="txiId" class="form-control form-control-xl" placeholder="Username" required>
		<div class="form-control-icon">
			<i class="bi bi-person"></i>
		</div>
	</div>
	<button class="btn btn-primary btn-block btn-lg shadow-lg mt-5">Send</button>
</form>
<div class="text-center mt-5 text-lg fs-4">
	<p class='text-gray-600'>Remember your account? <a href="/sign/login" class="font-bold">Log in</a>.</p>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		
		// 저장
		$("#formSave").submit(function(event){
			var param = new Object();
			param.id = $("#txiId").val();
			param.email = $("#txiEmail").val();
			
			$.ajax({
				url: "/sign/forgotpwd"
				, type: "put"
				, data: JSON.stringify(param)
				, dataType:"json"
				, headers : { "Accept":"application/json" ,"Content-Type":"application/json" }
				, beforeSend : function(xhr){xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");  $("#loadingBar").show();}
				, success: function(data){
					if(data.result == "success"){
						$(location).attr("href", "/sign/login");
					}else{
						Swal.fire("Error", data.result, "error");
					}
					$("#loadingBar").hide();
				}
				, error: function(e){
					Swal.fire("Exception", e, "error");
					$("#loadingBar").hide(); 
				}
			});
		});
	});
</script>	