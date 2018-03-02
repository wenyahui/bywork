package com.fixture.utils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class Excel2007Reader extends DefaultHandler {
	// 共享字符串表
	private SharedStringsTable sst;
	// 上一次的内容
	private String lastContents;
	private boolean nextIsString;
	private boolean isFirstCell=true;//是否是第已列
	private int sheetIndex = -1;
	private List<String> rowlist = new ArrayList<String>();
	// 当前行
	private int curRow = 0;
	private boolean  cellNull; 
	 //定义前一个元素和当前元素的位置，用来计算其中空的单元格数量，如A6和A8等  
    private String preRef = null, ref = null;  
    //定义该文档一行最大的单元格数，用来补全一行最后可能缺失的单元格  
    private String maxRef = null; 
	// 当前列
	private int curCol = 0;
	// 日期标志
	private boolean dateFlag;
	// 数字标志
	private boolean numberFlag;

	private boolean isTElement;

	private IRowReader rowReader;

	public void setRowReader(IRowReader rowReader) {
		this.rowReader = rowReader;
	}

	public Excel2007Reader() {
	}

	public Excel2007Reader(IRowReader rowReader) {
		this.rowReader = rowReader;
	}

	/**
	 * 只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3
	 * 
	 * @param filename
	 * @param sheetId
	 * @throws Exception
	 */
	public void processOneSheet(String filename, int sheetId) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);

		// 根据 rId# 或 rSheet# 查找sheet
		InputStream sheet2 = r.getSheet("rId" + sheetId);
		sheetIndex++;
		InputSource sheetSource = new InputSource(sheet2);
		parser.parse(sheetSource);
		sheet2.close();
	}

	/**
	 * 遍历工作簿中所有的电子表格
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public void process(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			curRow = 0;
			sheetIndex++;
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
		}
	}

	/**
	 * 遍历工作簿中所有的电子表格
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public void process(InputStream stream) throws Exception {
		OPCPackage pkg = OPCPackage.open(stream);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			curRow = 0;
			sheetIndex++;
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
		}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException, ParserConfigurationException {
		SAXParserFactory m_parserFactory = null;
		m_parserFactory = SAXParserFactory.newInstance(); 
		m_parserFactory.setNamespaceAware(true);
		XMLReader parser = m_parserFactory.newSAXParser().getXMLReader();
		this.sst = sst;
		parser.setContentHandler(this);
		return parser;
	}

	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		// c => 单元格
		/*if ("c".equals(name)) {
			// 如果下一个元素是 SST 的索引，则将nextIsString标记为true
			String cellType = attributes.getValue("t");
			if ("s".equals(cellType)) {
				nextIsString = true;
			} else {
				nextIsString = false;
			}
			// 日期格式
			String cellDateType = attributes.getValue("s");
			System.out.println(cellDateType+"       --cellDateTypecellDateType");
			if ("1".equals(cellDateType)) {
				dateFlag = true;
			} else {
				dateFlag = false;
			}
			String cellNumberType = attributes.getValue("s");
			if ("2".equals(cellNumberType)) {
				numberFlag = true;
			} else {
				numberFlag = false;
			}
		}
		// 当元素为t时
		if ("t".equals(name)) {
			isTElement = true;
		} else {
			isTElement = false;
		}*/
		 if ("c".equals(name)) { 
				 //前一个单元格的位置  
	             if(preRef == null){  
	                 preRef = attributes.getValue("r");  
	             }else{  
	                 preRef = ref;  
	             }  
	             //当前单元格的位置  
	             ref = attributes.getValue("r"); 
	            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true  
	            String cellType = attributes.getValue("t");  
	            if ("s".equals(cellType)) {  
	                nextIsString = true;  
	                cellNull = false;  
	            }   
	            else {  
	                nextIsString = false;  
	                cellNull = true;  
	            }  
	        }
		// 置空
		lastContents = "";
	}

	public void endElement(String uri, String localName, String name) throws SAXException {
		// 根据SST的索引值的到单元格的真正要存储的字符串
		// 这时characters()方法可能会被调用多次
		/*if (nextIsString) {
			try {
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
			} catch (Exception e) {

			}
		}
		// t元素也包含字符串
		if (isTElement) {
			String value = lastContents.trim();
			rowlist.add(curCol, value);
			curCol++;
			isTElement = false;
			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
			// 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
		} else if ("v".equals(name)) {
			String value = lastContents.trim();
			System.out.println(value);
			value = value.equals("") ? " " : value;
			// 日期格式处理
			if (dateFlag) {
				Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value));
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				value = dateFormat.format(date);
			}
			// 数字类型处理
			if (numberFlag) {
				BigDecimal bd = new BigDecimal(value);
				value = bd.setScale(3, BigDecimal.ROUND_UP).toString();
			}
			rowlist.add(curCol, value);
			curCol++;
		} else {
			// 如果标签名称为 row ，这说明已到行尾
			if (name.equals("row")) {
				if (rowReader != null) { // 每行结束时， 调用getRows() 方法
					rowReader.getRows(sheetIndex, curRow, rowlist);
				}
				rowlist.clear();
				curRow++;
				curCol = 0;
			}
		}*/
		// 根据SST的索引值的到单元格的真正要存储的字符串  
        // 这时characters()方法可能会被调用多次  
        if (nextIsString) {  
            try {  
                int idx = Integer.parseInt(lastContents);  
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();  
            } catch (Exception e) {  
  
            }  
        }  
       
          
        if ("v".equals(name) || "t".equals(name)) {  
            String value = lastContents.trim();  
            value = value.equals("") ? " " : value;  
          //补全单元格之间的空单元格  
            if(ref.equals(preRef)){
            	if(!ref.startsWith("A")){
            		int len = countNullCell(ref, "A"+(curRow+1))+1;
            		 for(int i=0;i<len;i++){  
                         rowlist.add(curCol, "");  
                         curCol++;  
                     } 
            	}
            }else if(!ref.equals(preRef)){ 
                int len = countNullCell(ref, preRef);  
                for(int i=0;i<len;i++){  
                    rowlist.add(curCol, "");  
                    curCol++;  
                }  
            } 
            rowlist.add(curCol, value);  
            curCol++;  
            cellNull = false;  
        }else if("c".equals(name) && cellNull == true){  
            rowlist.add(curCol, "");  
            curCol++;  
            cellNull = false;  
        }  
        else {  
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法  
            if (name.equals("row")) {  
            	
                //默认第一行为表头，以该行单元格数目为最大数目  
                if(curRow == 0){  
                    maxRef = ref;  
                }  
                //补全一行尾部可能缺失的单元格  
                if(maxRef != null){ 
                    int len = countNullCell(maxRef, ref);
                    for(int i=0;i<=len;i++){  
                        rowlist.add(curCol, "");  
                        curCol++;  
                    }  
                }  
                rowReader.getRows(sheetIndex, curRow, rowlist);  
                rowlist.clear();  
                curRow++;  
                curCol = 0;  
                preRef = null;  
                ref = null;
            }  
        }  
	}

	 /** 
     * 计算两个单元格之间的单元格数目(同一行) 
     * @param ref 
     * @param preRef 
     * @return 
     */  
    public int countNullCell(String ref, String preRef){  
        //excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD  
        String xfd = ref.replaceAll("\\d+", "");  
        String xfd_1 = preRef.replaceAll("\\d+", "");  
          
        xfd = fillChar(xfd, 3, '@', true);  
        xfd_1 = fillChar(xfd_1, 3, '@', true);  
          
        char[] letter = xfd.toCharArray();  
        char[] letter_1 = xfd_1.toCharArray();  
        int res = (letter[0]-letter_1[0])*26*26 + (letter[1]-letter_1[1])*26 + (letter[2]-letter_1[2]);  
        return res-1;  
    } 
    /** 
     * 字符串的填充 
     * @param str 
     * @param len 
     * @param let 
     * @param isPre 
     * @return 
     */  
    String fillChar(String str, int len, char let, boolean isPre){  
        int len_1 = str.length();  
        if(len_1 <len){  
            if(isPre){  
                for(int i=0;i<(len-len_1);i++){  
                    str = let+str;  
                }  
            }else{  
                for(int i=0;i<(len-len_1);i++){  
                    str = str+let;  
                }  
            }  
        }  
        return str;  
    }  
	public void characters(char[] ch, int start, int length) throws SAXException {
		// 得到单元格内容的值
		lastContents += new String(ch, start, length);
	}
	public static void main(String[] args) {
	}
}
