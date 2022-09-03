package info.devcase.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request
							, HttpServletResponse response
							, Object handler) throws Exception {
		logger.info("[" + request.getRequestURI() + "][" + request.getMethod() + "]");
		return true;
	}
}
