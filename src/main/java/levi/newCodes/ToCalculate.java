package levi.newCodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class ToCalculate {
	public static void main(String[] args) throws IOException {
		
		//Create the include files
		ToCalculate.getToCalculates(1,"common", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Duplo", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Kinder_Bueno", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Kinder_Cereali", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Kinder_Cioccolato", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Giotto", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Ferrero_Kuesschen", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Nutella", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Kinder_Fetta_al_latte", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Kinder_Pingui", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Kinder_Chocofresh", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Kinder_Maxi_King", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Tic_Tac", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Kinder_Sorpresa", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Kinder_Schoko_Bons", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Ferrero_Rocher", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Mon_Cheri", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Raffaello_Ferrero", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Ferrero_Prestige", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Hanuta", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Kinder_Riegel", args[0]);
		ToCalculate.getToCalculates(2,"Q12_Yogurette", args[0]);	
	}

	public static void getToCalculates(int shee, String brand, String codeFrame) throws IOException {
		List<ItemToTabulate> items = new ArrayList<ItemToTabulate>();
		File out = new File(brand + ".inc");
		FileWriter fw = new FileWriter(out);
		String cbCode, engTrans;
		int levelInExcel;
		StringBuilder sb = new StringBuilder();
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(codeFrame));
			HSSFSheet sheet = wb.getSheetAt(shee);
			
			//The list that has to be included for all brands, have a question id "Produktwissen" in the xls
			if (brand.equals("common")) {
				brand = "Produktwissen";
			}
			
			//Go through every row in the xls
			for (Row row : sheet) {
				//if column A contains the particular brand, or "Produktwissen" in case of common codes
		        if (row.getCell(0) != null && row.getCell(0).getStringCellValue().equals(brand)) {
					levelInExcel = (int) row.getCell(3).getNumericCellValue();
					engTrans = row.getCell(4).getStringCellValue();
					
					if (row.getCell(1) == null) {
						cbCode = null;
					} else {
						cbCode = "CB_" + String.valueOf((int) row.getCell(1).getNumericCellValue());
					}
					
					//creates a new object with column B as CB_Code, column D as the net level and column E as the english translation
					items.add(new ItemToTabulate(cbCode, levelInExcel, engTrans));
		        }
		    }
			
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ItemToTabulate[] itemsArray = items.toArray(new ItemToTabulate[items.size()]);
		
		//goes through every object in the array
		for (int i = 0; i < itemsArray.length; i++) {		
			//if the cbcode value is not empty (it's not a net), then set to tabulation codes to the actual cbcode
			if (itemsArray[i].getCbCode() != null) {
				itemsArray[i].setCodesToTab(itemsArray[i].getCbCode());
			//if it's a net
			} else {
				sb.setLength(0);
				
				//go through the rest of the array from the next element
				for (int j = i + 1; j < itemsArray.length; j++) {
					//if the objects level is higher than the current's level 
					if (itemsArray[j].getLevelInExcel() > itemsArray[i].getLevelInExcel()) {
						//if it's not a net, then add the cbcode to the tabulation codes
						if (itemsArray[j].getCbCode() != null) {
							sb.append(itemsArray[j].getCbCode() + ",");
						//if it's a net then skip to the next row
						} else {
							continue;
						}
					//if the object's level is not hight than the current's level, then continue with the next element in the array
					} else {
						break;
					}
				}
				
				if (sb.length() != 0) {
					sb.setLength(sb.length() - 1);
				}
				//sets the tabulation codes
				itemsArray[i].setCodesToTab(sb.toString());
			}
			
			fw.write(itemsArray[i].getEngTrans() + ";" + itemsArray[i].getCodesToTab() + "\n");
		}
		
		fw.close();
		
		return;
	}
}
