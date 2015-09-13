package LCB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class LaunchCloseBrowser {
	public static WebDriver driver;
	
	public static String URL;
	static Properties proData=new Properties();
	static Properties proLoc=new Properties();
	
	@BeforeMethod
	public void launchBrowser() throws Exception{
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		//Read data
		String DataPath="D:\\workspace\\Gmail\\Data.properties";
		String LocPath="D:\\workspace\\Gmail\\Loc.properties";
		
		File data=new File(DataPath);
		File Location=new File(LocPath);
		
		FileInputStream fledata=new FileInputStream(data);
		FileInputStream Locdata=new FileInputStream(Location);
		
		proData.load(fledata);
		proLoc.load(Locdata);
	}
	
	@AfterMethod
	public void CloseBrowser(ITestResult results) throws IOException{
		
		
		//If Test Fails then Takes Screenshot : TestNG Listner
		if(results.isSuccess()){
			File source= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			
			//specify the screenshot file name i.e. methoddd-MM-yyyy_hh-mm-ss.jpg
			//atTest1_11-04-2015_05-51-03.jpg
			String Filename=results.getMethod().getMethodName()+ new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss").format(new GregorianCalendar().getTime())+".jpg";
			String Location="D://screenshot//";
			File Destination=new File(Location + Filename);
			FileUtils.moveFile(source, Destination);
			driver.close();
		
		}
	}
	public static String getValueOfData(String key){
		return proData.getProperty(key);
	}
	
	public static String getvalueOfLoc(String key){
		return proLoc.getProperty(key);
	}
	
}
