package MiniProject;

import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Practoo {
	static WebDriver driver;
	

	public static void main(String args[]) throws Exception
		{
		System.setProperty("webdriver.chrome.driver","E:\\\\Selinum\\\\Chrome driver\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.get("https://www.practo.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		String strUrl = driver.getCurrentUrl();    //used to print current url
	    System.out.println("Current Url is:"+ strUrl);
	    clicklogin();
		  excelread();
		   searchhospital();
	       filter();
	       searchresult();
    
}
		
    public static void clicklogin() throws InterruptedException, FileNotFoundException, IOException
	{
		
		
		File file = new File("E:\\Java Demo\\HelloWorld\\src\\Outputfiles\\Readfile");//textfile to access login data
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		Properties prop = new Properties();
		prop.load(new FileInputStream(file));
		String user = prop.getProperty("username");
		String pass = prop.getProperty("password");
		while ((st = br.readLine()) != null)
			System.out.println(st);
		
		driver.findElement(By.xpath("//a[@name='Practo login']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(user);
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(pass);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@id='login']")).click();
		Thread.sleep(2000);
	}
	

public static void excelread()
{
try {
	  	    
	    File file = new File("E:\\Java Demo\\HelloWorld\\src\\Outputfiles\\cities.xlsx"); // creating a new file instance
		FileInputStream fis = new FileInputStream(file); // obtaining bytes from the file
         //creating Workbook instance that refers to .xlsx file
		XSSFWorkbook wb= new XSSFWorkbook(fis); //creating a workbook
		XSSFSheet Sheet1= wb.getSheetAt(0); //creating sheet object 
		String input0= Sheet1.getRow(1).getCell(0).getStringCellValue(); //input object created in term of city 
		String input1= Sheet1.getRow(2).getCell(0).getStringCellValue();
		String input2= Sheet1.getRow(3).getCell(0).getStringCellValue();
	    String input3= Sheet1.getRow(4).getCell(0).getStringCellValue();
		
	    System.out.println("***************************************");
		System.out.println(input0);
		System.out.println(input1);
		System.out.println(input2);
		System.out.println(input3);
		System.out.println("***************************************");
		Search(input2);
		 wb.close();
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}

		}
	
	 static void Search(String input2) throws InterruptedException 
	 {
		 
		    driver.findElement(By.xpath("(//input[@class='c-omni-searchbox c-omni-searchbox--small'])[1]")).clear();   //for clearing the result on search bar
		    driver.findElement(By.xpath("//input[@data-qa-id='omni-searchbox-locality']")).sendKeys(input2);
			Thread.sleep(2000);
			
						
			driver.findElement(By.xpath("(//div[@data-qa-id='omni-suggestion-main'])[1]")).click();
			//driver.findElement(By.xpath("(//span[@class='c-omni-suggestion-item__content'])[2]")).click();
			Thread.sleep(2000);
		
     }
	 
	public static void searchhospital() throws InterruptedException
	{
		Actions act=new Actions(driver);
		WebElement searchhospital= driver.findElement(By.xpath("(//input[@class='c-omni-searchbox c-omni-searchbox--small'])[2]"));
	    act.moveToElement(searchhospital).click().sendKeys("Hospital").build().perform();
	    
	    Thread.sleep(3000);
	     driver.findElement(By.xpath("(//div[@class='c-omni-suggestion-item__media__item'])")).click();
	}
	public static void filter() throws InterruptedException
	{
			
		driver.findElement(By.xpath("//div[@data-qa-id='Accredited_checkbox']")).click();
		Thread.sleep(1000);
		
		driver.findElement(By.xpath("//span[contains(text(), 'All Filters')]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@data-qa-id='24x7_Pharmacy_checkbox']")).click();
		Thread.sleep(2000);
		
	}
	public static void searchresult() throws Exception
	{
		try
		{
		String FileExtension=".txt";
		String lines[];
		FileWriter writer = new FileWriter("E:\\testout.txt");  
	    BufferedWriter buffer = new BufferedWriter(writer);  
       List<WebElement> searchresult=driver.findElements(By.xpath("//div[@class='c-card']"));
		int size = searchresult.size();
		int i=0;
		for(i=0; i<5; i++)
		{			
			String details= searchresult.get(i).getText();  //getting text of each object
			lines=details.split("[\\r\\n]+");
			System.out.println(Arrays.toString(lines));
			//split the line by using new line character	
			buffer.write("Hospital-- "+lines[0]);
			buffer.newLine();
			buffer.write("Rating-- "+lines[3]);
			buffer.newLine();
			 			
			if(isNumeric(lines[3]))
			{
				if(Double.parseDouble(lines[3])>=3.5)
				System.out.println("Hospital rating > 3.5 "+lines[0]);
			}
			
		}
		buffer.close();
		}
        catch(Exception e) {
            e.getStackTrace();
        
        }        
	}

	private static boolean isNumeric(String string) {
		// TODO Auto-generated method stub
		return false;

	}
	
}	
