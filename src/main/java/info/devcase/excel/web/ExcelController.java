package info.devcase.excel.web;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import info.devcase.common.domain.CustomUser;
import info.devcase.common.domain.UserVO;
import info.devcase.excel.service.ExcelService;

@Controller
public class ExcelController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);
	
	@Autowired
	private ExcelService excelService;
	
	@RequestMapping(value = "/excel/upload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ModelAndView uploadExcelFile(MultipartHttpServletRequest multipartRequest
									, HttpServletRequest request
									, Authentication auth) throws Exception{
		
		ModelAndView mv = null;
		
		HashMap<String, Object> param = new HashMap<String, Object>();
		
		// User 정보
		UserVO user = ((CustomUser)auth.getPrincipal()).getUser();
		param.put("id", user.getId());
		
		// 파라미터
		Map<String, String[]> map = multipartRequest.getParameterMap();
		for(String key : map.keySet()) {
			String[] value = map.get(key);
			param.put(key, value[0]);
		}
		
		// Excel File
		MultipartFile excelFile  = multipartRequest.getFile("excelFile");
		
		// Excel -> DB
		int errorCnt = excelService.uploadExcel(param, excelFile);
		
		return mv;
	}
	
	@RequestMapping(value = "/excel/download", method = RequestMethod.GET)
	public ModelAndView downloadExcelFile(String fileName) throws Exception{
		
		ModelAndView mv = new ModelAndView("downloadView");
		
		String path = getClass().getResource("/").getFile();
		path = new File(path).getParent();
		path = new File(path).getParent();	// webapp 경로
		path = path.replaceAll("%20", " ");
		
		File file = new File(path + "/resources/excel-format/" + fileName);
		
		mv.addObject("downloadFile", file);
		mv.addObject("downFileName", fileName);
		return mv;
	}

}
