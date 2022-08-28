package info.devcase.excel.service.impl;

import java.io.InputStream;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import info.devcase.excel.service.ExcelService;

@Service("ExcelService")
public class ExcelServiceImpl implements ExcelService {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int uploadExcel(HashMap<String, Object> param, MultipartFile excelFile) throws Exception{
		int errorCnt = 0;
		
		String seq = "123";
		String sqlOrigin = "INSERT INTO SYS_EXCEL(CREATED_DATE, CREATED_BY, SEQ, SHEET_NUM, SHEET_NAME, LINE, C01, C02, C03, C04, C05, C06, C07, C08, C09, C10, C11, C12, C13, C14, C15, C16, C17, C18, C19, C20, C21, C22, C23, C24, C25, C26, C27, C28, C29, C30 )"; 
        sqlOrigin += " VALUES(NOW(),'"+param.get("id")+"','"+seq+"'";
        sqlOrigin += ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        		
		// Excel 파싱 시작
		Connection conn = sqlSession.getConnection();
        OPCPackage opc = OPCPackage.open(excelFile.getInputStream());
		XSSFReader xssfReader = new XSSFReader(opc);
		SharedStringsTable sst = xssfReader.getSharedStringsTable();
		int sheetNum = 0;

		// Sheet 시작
		Iterator<InputStream> sheets = xssfReader.getSheetsData();
		while(sheets.hasNext()) {
			XMLInputFactory xmlif = XMLInputFactory.newInstance();
			XMLStreamReader xmlReader = xmlif.createXMLStreamReader(sheets.next());
			PreparedStatement sqlStatement = conn.prepareStatement(sqlOrigin);

			String cellType = "";
			String cellName = "";
			int row = 0, col = 0;
			Calendar cal = new GregorianCalendar();
			String preLocalName = "";

			sheetNum++;
			
			// 파라미터 기본 빈값 셋팅
			for(int i=1; i<=33; i++){
				sqlStatement.setString(i, "");
			}
			
			// Sheet -> data 시작
			while(xmlReader.hasNext()){
				xmlReader.next();
						
				if(xmlReader.isStartElement()){
					//System.out.println("xmlReader.getLocalName() = " + xmlReader.getLocalName());
					if(xmlReader.getLocalName().equals("c")){							// c => cell
						cellName = xmlReader.getAttributeValue(null, "r");				// ex. A1
						cellType = xmlReader.getAttributeValue(null, "t");				// ex. s, null
						row = Integer.parseInt(cellName.replaceAll("[^0-9]", ""));		// ex. 1
						col = cellName.replaceAll("[0-9]", "").charAt(0) - 64;			// ex. 'A' => 1
						
					}else if(xmlReader.getLocalName().equals("v") && row > 1){ 			// v => contents of a cell, 1번 row(헤더) 무시
						String temp = "";
						//System.out.print("\tv(");
							
						if(cellType != null && cellType.equals("s")){
							int index = Integer.parseInt(xmlReader.getElementText());
							temp = new XSSFRichTextString(sst.getEntryAt(index)).toString();
						
						}else{
							temp = xmlReader.getElementText();
							if(temp.contains(".")) {
								// 기본적으로 6자리에서 반올림(XML로 읽을 시 부동소수점 문제가 있음)
								temp = String.valueOf(Math.round(Double.parseDouble(temp)*1000000)/1000000.0).replaceAll("\\.0*$", "");
							}
							
							// xmlReader로 읽으면 날짜타입은 2018-07-09 => 43290 이렇게 읽혀서 변환해주어야함
							if(param.get("dateType").toString().matches("\\["+String.valueOf(col)+"\\]")){	// [1][2][4]
								// excel에서는 60 => 1900-02-29  JAVA에서는 60 => 1900-03-01이여서 현재를 계산할때 1899-12-30에서 날짜 계산하는게 맞음
								cal.set(Calendar.YEAR, 1899);
								cal.set(Calendar.MONTH, 11);
								cal.set(Calendar.DATE, 30);
								cal.add(Calendar.DATE, Integer.parseInt(temp));
								temp = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
							}
						}
						sqlStatement.setString(col+3, temp);
						//System.out.print(cellType + " / "+ cellName + " / row : " + row + " / col : " + col + ") : " + temp);
						//System.out.print(temp + "     ");
					
					}else if(!xmlReader.getLocalName().equals("c") 
							&& !xmlReader.getLocalName().equals("v") 
							&& !xmlReader.getLocalName().equals("f") //formula
							&& (preLocalName.equals("c") || preLocalName.equals("v")) 
							&& row > 1){
							
						sqlStatement.setInt(1, sheetNum);
						sqlStatement.setString(2, "Sheet" + sheetNum);
						sqlStatement.setInt(3, row);
						
						sqlStatement.addBatch();
						for(int i=1; i<=33; i++){
							sqlStatement.setString(i, "");
						}
						//System.out.println("\n--END");
					}
					
					preLocalName = xmlReader.getLocalName();
				}
			}
			
			// SQL 실행
			try{
				int[] resultRow = sqlStatement.executeBatch();
				/*
					0 ~ n : 성공한 row count 수
					-2 : 성공은 하였으나 row count 수를 알 수 없음
					-3 : 실패
				*/
				for(int resultValue : resultRow){
					if(resultValue == -3){
						errorCnt++;
					}
				}

			}catch(BatchUpdateException e){
				errorCnt++;
			}finally{
				if(sqlStatement != null) sqlStatement.close();
				if(xmlReader != null) xmlReader.close();
			}
		}
		
		if(sst != null) sst.close();
		if(opc != null) opc.close();
		
		return errorCnt;
	}

}
