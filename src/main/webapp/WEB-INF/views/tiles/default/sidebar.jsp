<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/common.jsp"%>

<div id="sidebar" class="active">
	<div class="sidebar-wrapper active">
		<div class="sidebar-header">
			<div class="d-flex justify-content-between">
				<div class="logo">
					<a href="/"><img src="/resources/dist/assets/images/logo/logo.png" alt="Logo" srcset=""></a>
				</div>
				<div class="toggler">
					<a href="#" class="sidebar-hide d-xl-none d-block"><i class="bi bi-x bi-middle"></i></a>
				</div>
			</div>
		</div>
		<div class="sidebar-menu">
			<ul class="menu">
				<li class="sidebar-title">Menu</li>
				<tiles:importAttribute name="menuList"/>
				<c:forEach var="menu" items="${menuList}">
					<c:choose>
						<c:when test="${menu.url ne '#'}">
							<li class="sidebar-item"><a href="${menu.url}" class="sidebar-link"><i class="${menu.icon}"></i><span>${menu.menu}</span></a></li>
						</c:when>
						<c:when test="${menu.url eq '#'}">
							<li class="sidebar-item has-sub"><a href="${menu.url}" class="sidebar-link"><i class="${menu.icon}"></i><span>${menu.menu}</span></a>
								<ul class="submenu">
									<c:forEach var="subMenu" items="${menu.subMenuList}">
										<li class="submenu-item"><a href="${subMenu.url}">${subMenu.menu}</a></li>
									</c:forEach>
								</ul>
							</li>
						</c:when>
					</c:choose>
				</c:forEach>
				
				<sec:authorize access="isAuthenticated()"> <!-- 권한이 있는 경우 보여짐 -->
					<li class="sidebar-item">
						<form id="formLogout" action="/logout" method="post">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<a onclick="$('#formLogout').submit();" class="sidebar-link"><i class="fa-solid fa-right-from-bracket"></i><span>Logout</span></a>
						</form>
					</li>
				</sec:authorize>
				
			</ul>
			
			<script type="text/javascript">
				if(location.pathname != "/"){
					$("a[href='" + location.pathname + "']").parent().attr("class", "active");
					$("title").html($("a[href='" + location.pathname + "']").html()+ " - " +$("title").html());
				}
			</script>
		</div>
		<button class="sidebar-toggler btn x"><i data-feather="x"></i></button>
	</div>
</div>
