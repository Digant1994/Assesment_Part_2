package utils;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Description: this class is contains common core methods of Selenium
 *
 */
public class CoreUtilities
{

	static Logger log = CoreUtilities.getLogger(CoreUtilities.class);
	public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	public static String line = "=================================================================";
	public static String s3Videourl = null;
	public static String s3ScreenShoturl = null;
	static ArrayList<String> tabs;

	/**
	 * 
	 * @param mainClass
	 * @return Description: Get logger for specific class
	 */
	public static Logger getLogger(Class<?> mainClass)
	{
		Logger log = Logger.getLogger(mainClass);
		DOMConfigurator.configure("log4j.xml");
		return log;
	}

	/**
	 * 
	 * @param browser Description: To open browser
	 */
	public static void loadBrowser(String browser)
	{
		if (browser.equalsIgnoreCase("Chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
		} else if (browser.equalsIgnoreCase("Firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
		} else if (browser.equalsIgnoreCase("Edge"))
		{
			WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver());
		}
	}

	/**
	 * 
	 * @return Description: Get browser
	 */
	public static WebDriver getDriver()
	{
		WebDriver Driver1;
		Driver1 = driver.get();
		return Driver1;
	}

	/**
	 * 
	 * @param element Click on specific element
	 */
	public static void findWebElementAndClick(WebElement element)
	{
		CoreUtilities.wait(2);
		waitForElementToBeClickable(element);
		getHighlightElement(element);
		element.click();
	}

	public static void OpenNewTab()
	{
		((JavascriptExecutor) getDriver()).executeScript("window.open()");

	}

	/**
	 * 
	 * @param element
	 * @param text    Description: Clear text from text field and enter text
	 */
	public static void clearTextAndSendKeys(WebElement element, String text)
	{
		if (StringUtilities.isStringNotEmpty(text))
		{
			waitForElementToBeClickable(element);
			getHighlightElement(element);
			element.clear();
			element.sendKeys(text);
		}
	}

	public static void SendKeys(WebElement element, String text, Keys key)
	{
		waitForElementToBeDisplayed(element);
		element.sendKeys(text);
		element.sendKeys(key);

	}

	public static boolean elementIsDisplayed(WebElement ele, int time)
	{
		try
		{
			WebDriverWait wait1 = new WebDriverWait(getDriver(), time);
			wait1.until(ExpectedConditions.visibilityOf(ele));
			return true;
		} catch (Exception e)
		{
			return false;
		}

	}

	public static void scrollToElement(WebElement element)
	{
		try
		{
			((JavascriptExecutor) CoreUtilities.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception e)
		{
			Assert.fail("Element not available");
		}
	}

	/**
	 * 
	 * 
	 * /**
	 * 
	 * 
	 * @param element
	 * @return Mar 25, 2020 4:03:29 PM Description: Get text for specific element
	 */
	public static String getText(WebElement element)
	{

		return element.getText();
	}

	/**
	 * 
	 * @param i Mar 25, 2020 4:03:49 PM Description: Wait for specific time
	 */
	public static void wait(int i)
	{
		long wait = System.currentTimeMillis() + (i * 1000);
		while (System.currentTimeMillis() < wait)
			;
	}

	/**
	 * 
	 * @param element
	 * @return Mar 25, 2020 4:04:10 PM Description: Check whether element is
	 *         displayed or not
	 */
	public static boolean checkElementDisplayed(WebElement element)
	{

		boolean visibility;
		try
		{
			waitForElementToBeClickable(element);
			getHighlightElement(element);
			visibility = element.isDisplayed();
			return visibility;
		} catch (Exception ex)
		{
			return false;
		}

	}

	public static void getHighlightElement(final WebElement element)
	{
		try
		{

			((JavascriptExecutor) getDriver()).executeScript("arguments[0].style.border='2px solid red'", element);
		} catch (Exception e)
		{
			log.info("Fail to highlight the Element");
		}
	}

	/**
	 * 
	 * @param element
	 * @return Mar 25, 2020 4:04:10 PM Description: Check whether element is
	 *         displayed or not
	 */
	public static boolean checkElementDisplayedWithOutClickable(WebElement element)
	{

		boolean visibility;
		try
		{

			visibility = element.isDisplayed();
			return visibility;
		} catch (Exception ex)
		{
			return false;
		}

	}

	/**
	 * 
	 * @return Mar 25, 2020 3:50:58 PM Description: To get page title
	 */
	public static String getTitle()
	{
		return getDriver().getTitle();
	}

	/**
	 *
	 * @param url Description: To open application URL
	 */
	public static void openURL(String url)
	{
		getDriver().get(url);
	}

	public static String windowSwitch()
	{
		String winHandleBefore = CoreUtilities.getDriver().getWindowHandle();

		for (String winHandle : CoreUtilities.getDriver().getWindowHandles())
		{
			CoreUtilities.getDriver().switchTo().window(winHandle);
		}
		return winHandleBefore;
	}

	public static void switchToParentWindow(String window)
	{
		CoreUtilities.getDriver().close();
		CoreUtilities.getDriver().switchTo().window(window);

	}

	public static void switchToDefaultTab()
	{
		tabs = new ArrayList<String>(getDriver().getWindowHandles());
		getDriver().switchTo().window(tabs.get(0));
	}

	public static void switchToNewTab()
	{
		tabs = new ArrayList<String>(getDriver().getWindowHandles());
		getDriver().switchTo().window(tabs.get(1));
	}

	/**
	 * 
	 * @param element Mar 25, 2020 3:52:31 PM Description: Wait for specific element
	 *                to be displayed
	 */
	public static void waitForElementToBeDisplayed(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(getDriver(), 15);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 *
	 * @param element Mar 26, 2020 4:12:43 PM Description: Wait for element to be
	 *                clickable
	 */
	public static void waitForElementToBeClickable(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(getDriver(), 15);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void moveToElementAndEnterText(WebElement element, String taskname)
	{
		Actions builder = new Actions(getDriver());
		builder.moveToElement(element).click().sendKeys(taskname).sendKeys(Keys.ENTER).perform();
	}

	public static void moveToElementAndClearText(WebElement element)
	{
		Actions builder = new Actions(getDriver());
		builder.moveToElement(element).click().keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).build().perform();

	}

	public static void waitForElementToBeInvisible(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(getDriver(), 15);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public static void scenarioHeading(String heading)
	{

		log.info("###########################  Start Scenario " + "   #####################");
		log.info("================================ Sceanario : " + "================================");
		Reporter.log("Scenario " + ": " + heading);
		Reporter.log(line);
		Reporter.log("Steps : ");
		Reporter.log("");

	}

	/**
	 * @param heading
	 */
	public static void scenarioRunning(String heading)
	{

		Reporter.log("Scenario " + ": " + heading);
		System.out.println("Scenario Started " + ": " + heading);
	}
}
