package info.devcase.sign.service;

import java.util.HashMap;

import org.springframework.web.servlet.ModelAndView;

public interface SignService {
	public ModelAndView insertUser(HashMap<String, Object> param) throws Exception;
	
	public ModelAndView updateRecoverPwd(HashMap<String, Object> param) throws Exception;
	
	public ModelAndView updateUserPwd(HashMap<String, Object> param) throws Exception;
}
