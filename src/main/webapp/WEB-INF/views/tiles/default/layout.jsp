<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp"%>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title><tiles:insertAttribute name="title"/></title>

	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="/resources/dist/assets/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/dist/assets/vendors/perfect-scrollbar/perfect-scrollbar.css">
	<link rel="stylesheet" href="/resources/dist/assets/vendors/bootstrap-icons/bootstrap-icons.css">
	<link rel="stylesheet" href="/resources/dist/assets/css/app.css">
</head>

<body>
	<div id="app">
		<tiles:insertAttribute name="sidebar"/>
		<div id="main">
			<header class="mb-3">
				<a href="#" class="burger-btn d-block d-xl-none">
					<i class="bi bi-justify fs-3"></i>
				</a>
			</header>

			<div class="page-heading">
				<h3>Head</h3>
			</div>
			<div class="page-content">
				<tiles:insertAttribute name="content"/>
			</div>
			
			<tiles:insertAttribute name="footer"/>
		</div>
	</div>
    
	<script src="/resources/dist/assets/vendors/perfect-scrollbar/perfect-scrollbar.min.js"></script>
	<script src="/resources/dist/assets/js/bootstrap.bundle.min.js"></script>
	<script src="/resources/dist/assets/js/main.js"></script>
</body>

</html>