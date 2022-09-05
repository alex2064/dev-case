package info.devcase.algorithm.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import info.devcase.algorithm.service.SortService;

@Controller	
public class SortController {
	
	private static final Logger logger = LoggerFactory.getLogger(SortController.class);
	
	@Autowired
	private SortService sortService;
	
	
	@RequestMapping(value = "/algorithm/sort", method = RequestMethod.GET)
	public void pageAlgorithmSort(){
		
	}
}
