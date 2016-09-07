package com.selenium.futureAdvisor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class FutureAdvisor {
	
	WebDriver driver;
	@BeforeTest
	public void runtestcase() throws IOException, InterruptedException{
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.google.com/");
		}
	
	@Test
	public void loadfutureadvisor() throws InterruptedException{
		driver.findElement(By.id("lst-ib")).sendKeys("futureadvisor.com");
		driver.findElement(By.xpath(".//*[@id='sblsbb']/button")).click();
		Thread.sleep(2000);
		SoftAssert a  = new SoftAssert();
		a.assertEquals(driver.findElement(By.xpath(".//*[@id='rso']/div[1]/div/div/div/div/div/cite/b")).getText(), "futureadvisor");
		a.assertEquals(driver.findElement(By.xpath(".//*[@id='rso']/div[1]/div/div/h3/a")).getText(), "FutureAdvisor: Online Financial Advisor & Investing Advice");
		a.assertTrue(driver.findElement(By.id("logocont")).isDisplayed());
		a.assertTrue(driver.findElement(By.id("gb_70")).isDisplayed());
		driver.findElement(By.xpath(".//*[@id='rso']/div[1]/div/div/h3/a")).click();
		Thread.sleep(3000);
		String url = driver.getCurrentUrl();
		a.assertEquals(url, "https://www.futureadvisor.com/");
		Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath(".//*[@id='header']/div/nav/ul/li[1]/a"))).build().perform();
        Thread.sleep(1000);
        driver.findElement(By.linkText("Investment Philosophy")).click();
        Thread.sleep(5000);
        driver.findElement(By.linkText("or log in to your account")).click();
	}
	
	@Test(dependsOnMethods="loadfutureadvisor")
	public  void validateLogin(){
        driver.findElement(By.id("user_session_user_name")).sendKeys("thankyuo@gemail.com");
        driver.findElement(By.id("user_session_password")).sendKeys("youmustbekidding");
        driver.findElement(By.id("user_session_submit")).click(); 
        SoftAssert s = new SoftAssert();
        String errorMsg = driver.findElement(By.cssSelector(".alert.alert-danger")).getText();
        s.assertEquals(errorMsg, "Either your email or password were incorrect.");
        
	}
	
	@AfterTest
	public void	closeBrowser(){
		driver.close();
	}
	
}
