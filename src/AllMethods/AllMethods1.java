package AllMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;

import com.google.common.base.Function;

import LCB.LaunchCloseBrowser;
import Login.Login;

public class AllMethods1 extends LaunchCloseBrowser{
		
	
	public static void Gmail_Login1(){
		URL="https://www.gmail.com";
		driver.get(URL);
		driver.findElement(By.id(getvalueOfLoc("GmailUserNme"))).sendKeys(getValueOfData("UserName"));
		if(driver.findElement(By.id(getvalueOfLoc("GmailNext"))).isDisplayed()){
			driver.findElement(By.id(getvalueOfLoc("GmailNext"))).click();
			driver.findElement(By.id(getvalueOfLoc("GmailPassword"))).sendKeys(getValueOfData("Password"));
		}
		else
		{
		driver.findElement(By.id(getvalueOfLoc("GmailPassword"))).sendKeys(getValueOfData("Password"));
		}
		driver.findElement(By.id(getvalueOfLoc("GmailLogin"))).click();
		System.out.println("Gmail login is successfull");
	}
	
	public static void Gmail_Compose() throws Exception{
		Gmail_Login1();
		
		Thread.sleep(10000);
		
		driver.findElement(By.xpath(getvalueOfLoc("GmailCompose"))).click();
		/*new FluentWait<WebElement>(compose)
		.withTimeout(45, TimeUnit.SECONDS)
		.pollingEvery(5, TimeUnit.SECONDS)
		.until(
				new Function<WebElement,Boolean>(){////Remember import of Function
					public Boolean apply(WebElement compose){
						compose.click();
						return null;
					}
				}
				);*/
		
String filepath="D:\\workspace\\Gmail\\Gmail_Compose_Data.xlsx";
		
		File myfile=new File(filepath);
		FileInputStream FIS=new FileInputStream(myfile);
		
		XSSFWorkbook hssfbook=new XSSFWorkbook(FIS);
		XSSFSheet mysheet=hssfbook.getSheetAt(0);
		
		int xrow= mysheet.getLastRowNum()+1;
		int xcolumn=mysheet.getRow(0).getLastCellNum();
		
		System.out.println("number of row in xls sheet:" + xrow);
		System.out.println("number of column in xls sheet:" + xcolumn);
		
		//String[][] xdata=new String[xrow][xcolumn];
		
		for (int i=0;i<xrow;i++){
			//XSSFRow row= mysheet.getRow(i);
					
			Thread.sleep(5000);
			
				
				String to= mysheet.getRow(i).getCell(0).getStringCellValue();
				driver.findElement(By.xpath(getvalueOfLoc("GmailTo"))).sendKeys(to);
				
				String subject= mysheet.getRow(i).getCell(1).getStringCellValue(); 
				driver.findElement(By.xpath(getvalueOfLoc("GmailSubject"))).sendKeys(subject);
				
				String body= mysheet.getRow(i).getCell(2).getStringCellValue(); 
				driver.findElement(By.xpath(getvalueOfLoc("GmailBody"))).sendKeys(body);
				
				String attachment= mysheet.getRow(i).getCell(3).getStringCellValue(); 
				if(attachment.equalsIgnoreCase("Yes"))
				{
					System.out.println("attach the file");
				}
				else if(attachment.equalsIgnoreCase("No")){
					System.out.println("you have no attached file");
				}
				
				FIS.close();
				driver.findElement(By.xpath(getvalueOfLoc("GmailSend"))).click();
				Thread.sleep(1000);
				String st=driver.findElement(By.xpath("//div[starts-with(text(),'Your message has been sent.')]")).getText();
				FileOutputStream FOS=new FileOutputStream(myfile);
				mysheet.createRow(i).createCell(5).setCellValue(st);
				//mysheet.getRow(i).getCell(5).setCellValue(st);
				
				hssfbook.write(FOS);
				FOS.close();
				
			
		}
		
		
		
	
	
	}
	
	public void Gmail_Compose_data() throws Exception{
		
		String filepath="D:\\workspace\\Gmail\\Gmail_Compose_Data.xls";
		
		File myfile=new File(filepath);
		FileInputStream FIS=new FileInputStream(myfile);
		
		HSSFWorkbook hssfbook=new HSSFWorkbook(FIS);
		HSSFSheet mysheet=hssfbook.getSheetAt(0);
		
		int xrow= mysheet.getLastRowNum()+1;
		int xcolumn=mysheet.getRow(0).getLastCellNum();
		
		System.out.println("number of row in xls sheet:" + xrow);
		System.out.println("number of column in xls sheet:" + xcolumn);
		
		String[][] xdata=new String[xrow][xcolumn];
		
		for (int i=0;i<xrow;i++){
			HSSFRow row= mysheet.getRow(i);
			for (int j=0;j<xcolumn;j++){
				HSSFCell cell= row.getCell(j);
				
				String value = celltostring(cell);
				xdata[i][j]= value;
				
				
				String a = xdata[0][1];
				 
			}
		}
		
		
		
		
	}

	public String celltostring(HSSFCell cell) {
		
		int type=cell.getCellType();
		Object result;
		switch(type)
		{
		case HSSFCell.CELL_TYPE_BOOLEAN:
			result=cell.getBooleanCellValue();
			System.out.println("type of cell is: "+type);
			break;
			
		case HSSFCell.CELL_TYPE_NUMERIC:
			result=cell.getNumericCellValue();
			System.out.println("type of cell is:" +type);
			break;
		case HSSFCell.CELL_TYPE_STRING:
			result=cell.getStringCellValue();
			System.out.println("type of cell is:" +type);
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			result="";
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			System.out.println("can't handled the formula");
			throw new RuntimeException("can't handle java formula");	
		case HSSFCell.CELL_TYPE_ERROR:
			System.out.println("Can not eveulate formila in JAVA");
			throw new RuntimeException("This cell has an error");
			
		default:
			throw new RuntimeException("We dont support this cell type: "+type);
			
		}
		
		return result.toString();
	}
	
	public static void Gmail_send() throws Exception
	{
		/*Gmail_Login1();
		Gmail_Compose();
		Thread.sleep(5000);
		driver.findElement(By.xpath(getvalueOfLoc("GmailSentMail"))).click();
		*/
		
	}
	
}
