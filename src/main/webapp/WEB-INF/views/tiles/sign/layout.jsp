<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp"%>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title><tiles:insertAttribute name="title"/></title>

	<link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="/resources/dist/assets/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/dist/assets/vendors/bootstrap-icons/bootstrap-icons.css">
	<link rel="stylesheet" href="/resources/dist/assets/css/app.css">
	<link rel="stylesheet" href="/resources/dist/assets/css/pages/auth.css">
	<link rel="stylesheet" href="/resources/dist/assets/css/style.css">
	
	<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
</head>

<body>
	<div id="auth">
		<div class="row h-100">
			<div class="col-lg-5 col-12">
				<div id="auth-left">
					<div class="auth-logo">
						<a href="/"><img src="/resources/dist/assets/images/logo/logo.png" alt="Logo"></a>
					</div>
					<tiles:insertAttribute name="content"/>
				</div>
			</div>
			<div class="col-lg-7 d-none d-lg-block">
				<div id="auth-right">
				</div>
			</div>
		</div>
	</div>
	
	<div id="loadingBar" style="display:none;">
		<img id="loadingImg" src="/resources/dist/assets/images/samples/loadingImg.gif" alt="Loading...">
	</div>
	<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</body>

</html>