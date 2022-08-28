package info.devcase.excel.service;

import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
	
	public int uploadExcel(HashMap<String, Object> param, MultipartFile excelFile) throws Exception;
}
