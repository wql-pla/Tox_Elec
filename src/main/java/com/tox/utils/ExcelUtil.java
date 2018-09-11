package com.tox.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
    private static final String EXCEL_XLS = "xls";  
    private static final String EXCEL_XLSX = "xlsx";  
  
	
	    /** 
	     * 判断文件是否是excel 
	     * @throws Exception  
	     */  
	   public static void checkExcelVaild(File file) throws Exception{  
	        if(!file.exists()){  
	            throw new Exception("文件不存在");  
	        }  
	        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){  
	            throw new Exception("文件不是Excel");  
	        }  
	    }  
	
	    /** 
	     * 判断Excel的版本,获取Workbook 
	     * @param in 
	     * @param filename 
	     * @return 
	     * @throws IOException 
	     */  
	    public static Workbook getWorkbok(InputStream in,File file) throws IOException{  
	        Workbook wb = null;  
	        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003  
	            wb = new HSSFWorkbook(in);  
	        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010  
	            wb = new XSSFWorkbook(in);  
	        }  
	        return wb;  
	    }  
	  /**
	   *  读取 
	   * @param filePath
	   * @return
	   */
	public static Map<String,Object> readExcel(String filePath){

    	Map<String,Object> map = new HashMap<String,Object>();
    	List<Object> NUM = new ArrayList<Object>();
    	
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        //BufferedWriter bw = new BufferedWriter(new FileWriter(new File("E:/xxx/InsertSql.txt")));  
        try { 
            // 同时支持Excel 2003、2007  
            File excelFile = new File(filePath); // 创建文件对象  
            FileInputStream is = new FileInputStream(excelFile); // 文件流  
            checkExcelVaild(excelFile);  
            Workbook workbook = getWorkbok(is,excelFile);  
            //Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel2003/2007/2010都是可以处理的  
  
            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量  
            /** 
             * 设置当前excel中sheet的下标：0开始 
             */  
            Sheet sheet = workbook.getSheetAt(0);   // 遍历第一个Sheet  
  
            // 为跳过第一行目录设置count  
            int count = 0;  
  
            for (Row row : sheet) {  
                // 跳过第一行的目录  
                if(count == 0){  
                    count++;  
                    continue;  
                }  
                // 如果当前行没有数据，跳出循环  
                if(row.getCell(0).toString().equals("")){  
                    map.put("result", "100");
                	map.put("data", NUM);
                	map.put("msg", "读取成功!");
                	return map;
                }  
                List<String> rowValue = new ArrayList<String>();
                for (Cell cell : row) {  
                    if(cell.toString() == null){  
                        continue;  
                    }  
                    int cellType = cell.getCellType();  
                    String cellValue = "";  
                    switch (cellType) {  
                        case Cell.CELL_TYPE_STRING:     // 文本  
                            cellValue = cell.getRichStringCellValue().getString();  
                            break;  
                        case Cell.CELL_TYPE_NUMERIC:    // 数字、日期  
                            if (DateUtil.isCellDateFormatted(cell)) {  
                                cellValue = fmt.format(cell.getDateCellValue());  
                            } else {  
                                cell.setCellType(Cell.CELL_TYPE_STRING);  
                                cellValue = String.valueOf(cell.getRichStringCellValue().getString());  
                            }  
                            break;  
                        case Cell.CELL_TYPE_BOOLEAN:    // 布尔型  
                            cellValue = String.valueOf(cell.getBooleanCellValue()) ;  
                            break;  
                        case Cell.CELL_TYPE_BLANK: // 空白  
                            cellValue = cell.getStringCellValue();  
                            break;  
                        case Cell.CELL_TYPE_ERROR: // 错误  
                            cellValue = "错误#";  
                            break;  
                        case Cell.CELL_TYPE_FORMULA:    // 公式  
                            // 得到对应单元格的公式  
                            //cellValue = cell.getCellFormula() + "#";  
                            // 得到对应单元格的字符串  
                            cell.setCellType(Cell.CELL_TYPE_STRING);  
                            cellValue = String.valueOf(cell.getRichStringCellValue().getString());  
                            break;  
                        default:  
                            cellValue = "#";  
                    }  
                    //System.out.print(cellValue);  
                    rowValue.add(cellValue) ;  
                }  
               // System.out.println(rowValue);  
                NUM.add(rowValue);
                //System.out.println();  
            }  
          //  bw.flush();  
        } catch (Exception e) {  
        	map.put("result", "-100");
        	map.put("data", "读取失败!");
        	map.put("msg", "读取失败!");
            e.printStackTrace();  
            return map;
        } finally{  
           // bw.close();  
        }  
    
        map.put("result", "100");
    	map.put("data", NUM);
    	map.put("msg", "读取成功!");
    	return map;
    }
    
}
