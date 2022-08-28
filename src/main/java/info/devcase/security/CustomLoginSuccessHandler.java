package info.devcase.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request
										, HttpServletResponse response
										, Authentication auth) throws IOException, ServletException{
		logger.warn("Login Success");		
		
		// Security가 요청을 가로챈 경우 사용자가 원래 요청했던 URI 정보 찾기
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		//SavedRequest savedRequest = (SavedRequest)request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		
		// 요청 페이지 -> 로그인 페이지 -> (실패) -> 로그인 페이지 인 경우
		String prevPage = (String)request.getSession().getAttribute("prevPage");
		if(prevPage != null) {
			request.getSession().removeAttribute("prevPage");
		}
		
		String uri = "/";
		if(savedRequest != null){
			uri = savedRequest.getRedirectUrl();
			requestCache.removeRequest(request, response);	// 세션에 저장된 객체 사용한뒤 지우기
		}else if(prevPage != null && !prevPage.equals("")) {
			uri = prevPage;
		}
		
		response.sendRedirect(uri);		
	}
}
