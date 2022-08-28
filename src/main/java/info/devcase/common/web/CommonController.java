package info.devcase.common.web;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import info.devcase.common.domain.CustomUser;
import info.devcase.common.domain.UserVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class CommonController {
	
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(Authentication auth, @RequestParam HashMap<String, String> param) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		UserVO user = ((CustomUser)auth.getPrincipal()).getUser();
		
		mv.setViewName("/index");
		
		return mv;
	}
	
	
	
}
