package info.devcase.board.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import info.devcase.board.service.BoardService;
import info.devcase.common.dao.CommonDAO;

@Service("BoardService")
public class BoardServiceImpl implements BoardService{

	@Autowired
	private CommonDAO commonDAO;
	
	private static String namespace = "BoardMapper";
	
	
	@Override
	public ModelAndView selectBoard(HashMap<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("dsList", commonDAO.selectList(namespace + ".selectBoard", param));
		
		return mv;
	}
	
	@Override
	public ModelAndView insertBoard(HashMap<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		commonDAO.insert(namespace + ".insertBoard", param);
		mv.addObject("result", "success");
		
		return mv;
	}
	
	@Override
	public ModelAndView updateBoard(HashMap<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		commonDAO.insert(namespace + ".updateBoard", param);
		mv.addObject("result", "success");
		
		return mv;
	}
	
	@Override
	public ModelAndView deleteBoard(HashMap<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		commonDAO.insert(namespace + ".deleteBoard", param);
		mv.addObject("result", "success");
		
		return mv;
	}
	
}
