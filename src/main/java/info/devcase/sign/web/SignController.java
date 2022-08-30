package info.devcase.sign.web;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import info.devcase.common.domain.CustomUser;
import info.devcase.common.domain.UserVO;
import info.devcase.sign.service.SignService;

@Controller	
public class SignController {
	
	private static final Logger logger = LoggerFactory.getLogger(SignController.class);
	
	@Autowired
	private SignService signService;
	
	
	@RequestMapping(value = "/sign/login", method = RequestMethod.GET)
	public void pageLogin(HttpServletRequest request){
		// 로그인 실패 후 다시 로그인 하면 요청 페이지가 로그인 페이지로 지정되니 인터셉터 전의 페이지 저장 
		String uri = request.getHeader("Referer");
		if(!uri.contains("/sign/login")) {
			request.getSession().setAttribute("prevPage", request.getHeader("Referer"));
		}
	}
	
	
	@RequestMapping(value = "/sign/signup", method = RequestMethod.GET)
	public void pageSignUp(){
		
	}
	
	@RequestMapping(value = "/sign/signup", method = RequestMethod.POST)
	public ModelAndView insertUser(@RequestBody HashMap<String, Object> param) throws Exception{
		
		ModelAndView mv = signService.insertUser(param);
		mv.setViewName("jsonView");
		
		return mv;
	}
	
	
	@RequestMapping(value = "/sign/forgotpwd", method = RequestMethod.GET)
	public void pageForgotPwd(){
		
	}
	
	@RequestMapping(value = "/sign/forgotpwd", method = RequestMethod.PUT)
	public ModelAndView updateRecoverPwd(@RequestBody HashMap<String, Object> param) throws Exception{
		
		ModelAndView mv = signService.updateRecoverPwd(param);
		mv.setViewName("jsonView");
		
		return mv;
	}
	
	
	@RequestMapping(value = "/user/pwd", method = RequestMethod.PUT)
	public ModelAndView updateUserPwd(@RequestBody HashMap<String, Object> param, Authentication auth) throws Exception{
		
		UserVO user = ((CustomUser)auth.getPrincipal()).getUser();
		param.put("id", user.getId());
		
		ModelAndView mv = signService.updateUserPwd(param);
		mv.setViewName("jsonView");
		
		return mv;
	}
}
