package info.devcase.sign.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller	
public class JavascriptController {
	
	private static final Logger logger = LoggerFactory.getLogger(JavascriptController.class);
	
	@RequestMapping(value = "/javascript/basic", method = RequestMethod.GET)
	public void pageBasic(HttpServletRequest request){
		
	}
	
}
