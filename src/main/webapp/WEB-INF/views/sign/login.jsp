<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp"%>

<h1 class="auth-title">Log in.</h1>
<p class="auth-subtitle mb-5">Log in with your data that you entered during registration.</p>

<form action="/login" method="post">
	<div class="form-group position-relative has-icon-left mb-4">
		<input type="text" class="form-control form-control-xl" name="username" placeholder="Username" required>
		<div class="form-control-icon">
			<i class="bi bi-person"></i>
		</div>
	</div>
	<div class="form-group position-relative has-icon-left mb-4">
		<input type="password" class="form-control form-control-xl" name="password" placeholder="Password" required>
		<div class="form-control-icon">
			<i class="bi bi-shield-lock"></i>
		</div>
	</div>
	<div class="form-check form-check-lg d-flex align-items-end">
		<input class="form-check-input me-2" type="checkbox" id="chkRememberMe" name="remember-me">
		<label class="form-check-label text-gray-600" for="chkRememberMe">Keep me logged in</label>
	</div>
	<button class="btn btn-primary btn-block btn-lg shadow-lg mt-5">Log in</button>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<div class="text-center mt-5 text-lg fs-4">
	<p class="text-gray-600">Don't have an account? <a href="/sign/signup" class="font-bold">Sign up</a>.</p>
	<p><a class="font-bold" href="/sign/forgotpwd">Forgot password?</a>.</p>
</div>


<script type="text/javascript">
	$(document).ready(function(){
		if("${requestScope.loginFailMsg}" != ""){
			Swal.fire("${requestScope.loginFailMsg}", "", "error");
		}
	});
</script>	



