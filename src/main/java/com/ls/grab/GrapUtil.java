package com.ls.grab;
//package com.ls.grap;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriverService;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;
//
//public class GrapUtil {
//
//	public static ChromeDriverService service;
//
//	public static void main(String[] args) {
//
//		System.setProperty("webdriver.chrome.driver", "D:/Tools/ChromeDriverServer/chromedriver.exe");
//
//		service =
//			new ChromeDriverService.Builder().usingChromeDriverExecutable(new File(System.getProperty("webdriver.chrome.driver"))).usingAnyFreePort()
//				.build();
//		try {
//			service.start();
//
//		} catch (IOException e) {
//
//		}
//		WebDriver webDriver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
//
//		webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//
//		webDriver.get("http://sh.58.com/meirongshi/pn2/");
//
//		//String xpathExpression = "//a[@class='f1']/@target";
//		List<WebElement> shops = webDriver.findElements(By.className("f1"));
//		
//		for (WebElement webElement : shops) {
//			System.out.println(webElement.getText());
//		}
//
//		webDriver.quit();
//
//		service.stop();
//
//	}
//
//}
