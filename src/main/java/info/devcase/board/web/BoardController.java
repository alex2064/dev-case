package info.devcase.board.web;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import info.devcase.board.service.BoardService;

@Controller	
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	
	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public void pageBoardList(){
		
	}
	
	@RequestMapping(value = "/board/list", method = RequestMethod.POST)
	public ModelAndView insertBoard(@RequestBody HashMap<String, Object> param) throws Exception{
		
		ModelAndView mv = boardService.insertBoard(param);
		mv.setViewName("jsonView");
		
		return mv;
	}
	
	@RequestMapping(value = "/board/list", method = RequestMethod.PUT)
	public ModelAndView updateBoard(@RequestBody HashMap<String, Object> param) throws Exception{
		
		ModelAndView mv = boardService.updateBoard(param);
		mv.setViewName("jsonView");
		
		return mv;
	}
	
	@RequestMapping(value = "/board/list", method = RequestMethod.DELETE)
	public ModelAndView deleteBoard(@RequestBody HashMap<String, Object> param) throws Exception{
		
		ModelAndView mv = boardService.deleteBoard(param);
		mv.setViewName("jsonView");
		
		return mv;
	}
	
	
	
}
