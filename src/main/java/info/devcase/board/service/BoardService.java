package info.devcase.board.service;

import java.util.HashMap;

import org.springframework.web.servlet.ModelAndView;

public interface BoardService {
	public ModelAndView selectBoard(HashMap<String, Object> param) throws Exception;
	
	public ModelAndView insertBoard(HashMap<String, Object> param) throws Exception;
	
	public ModelAndView updateBoard(HashMap<String, Object> param) throws Exception;
	
	public ModelAndView deleteBoard(HashMap<String, Object> param) throws Exception;
	
	
}
