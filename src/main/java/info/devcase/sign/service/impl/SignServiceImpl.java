package info.devcase.sign.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import info.devcase.common.dao.CommonDAO;
import info.devcase.common.domain.UserVO;
import info.devcase.mail.MailService;
import info.devcase.sign.service.SignService;

@Service("SignService")
public class SignServiceImpl implements SignService{

	@Autowired
	private CommonDAO commonDAO;
	
	@Autowired
	private BCryptPasswordEncoder pwEncoder;
	
	@Autowired
	private MailService mailService;
	
	private static String namespace = "SignMapper";
	
	
	@Override
	public ModelAndView insertUser(HashMap<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		UserVO vo = commonDAO.selectOne(namespace + ".selectUser", param);
		if(vo == null) {
			param.put("pwd", pwEncoder.encode(param.get("pwd").toString()));
			commonDAO.insert(namespace + ".insertUser", param);
			commonDAO.insert(namespace + ".insertUserAuth", param);
			
			mv.addObject("result", "success");
			
		}else {
			mv.addObject("result", "fail");
		}
		
		return mv;
	}
	
	@Override
	public ModelAndView updateRecoverPwd(HashMap<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		// id와 email 맞는지 체크
		UserVO vo = commonDAO.selectOne(namespace + ".selectUser", param);
		if(vo != null && vo.getEmail().equals(param.get("email").toString())){
			String pwd = String.valueOf(Math.random()).substring(2,8);
			param.put("pwd", pwEncoder.encode(pwd));
			
			// 메일 발송
			ArrayList<String> receivers = new ArrayList<String>();
			receivers.add(vo.getEmail());
			
			String sender = "kimkimgod3@gmail.com";
			String subject = "Password Recover";
			String text = "Password : " + pwd;
			String result = mailService.sendMail(sender, receivers, subject, text);
			
			if(result.equals("success")) {
				commonDAO.update(namespace + ".updateUserPwd", param);
				mv.addObject("result", "success");
			}else {
				mv.addObject("result", "fail");
			}
		}else {
			mv.addObject("result", "fail");
		}
 
		return mv;
	}
	
	@Override
	public ModelAndView updateUserPwd(HashMap<String, Object> param) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		param.put("pwd", pwEncoder.encode(param.get("pwd").toString()));
		commonDAO.update(namespace + ".updateUserPwd", param);
		mv.addObject("result", "success");
 
		return mv;
	}
	
}
