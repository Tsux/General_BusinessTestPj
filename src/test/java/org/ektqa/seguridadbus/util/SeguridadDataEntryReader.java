package org.ektqa.seguridadbus.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.ektqa.commons.SystemConstants;

public class SeguridadDataEntryReader {
SystemConstants sysConst = new SystemConstants();
	
	public enum EntryField {
		IDUS("idUsuario"), NOMBRE("nombre"), APPAT ("apPaterno"), APMAT("apMaterno"), CONTRA("contrasenia");
		
		String field;
		EntryField(String fld){
			this.field = fld;
		}
		
		public String getCode(){
			return this.field;
		}
	}
	
	private List<Map<String,String>> entryData = new ArrayList<Map<String, String>>();
	private Map<String, String> mappedValues;
	
	public void ReadFromExcel() throws IOException{
		FileInputStream file = new FileInputStream(new File(sysConst.DATAENTRY_FILE_PATH+sysConst.SWITCH_SEGPAR_XLS_NAME));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		Iterator<Row> rowIterator = sheet.iterator();
		Row currentRow;
		Cell currentCell;
		
		while(rowIterator.hasNext()){
			currentRow = rowIterator.next();
			Iterator<Cell> cellIterator = currentRow.cellIterator();
			mappedValues = new HashMap<String, String>();
			while(cellIterator.hasNext()){
				currentCell = cellIterator.next();
				if(currentCell.getCellType() == currentCell.CELL_TYPE_STRING
						&& currentCell.getRowIndex() != 0){
					switch(currentCell.getColumnIndex()){						
						case 1:
							mappedValues.put(EntryField.IDUS.getCode(), currentCell.getStringCellValue());
						case 2:
							mappedValues.put(EntryField.NOMBRE.getCode(), currentCell.getStringCellValue());
						case 3:
							mappedValues.put(EntryField.APPAT.getCode(), currentCell.getStringCellValue());
						case 4:
							mappedValues.put(EntryField.APMAT.getCode(), currentCell.getStringCellValue());
						case 5:
							mappedValues.put(EntryField.CONTRA.getCode(), currentCell.getStringCellValue());
					}
				}
			}
			getEntryData().add(mappedValues);
		}
		file.close();
	}

	public List<Map<String,String>> getEntryData() {
		return entryData;
	}

	public void setEntryData(List<Map<String,String>> entryData) {
		this.entryData = entryData;
	}
}
