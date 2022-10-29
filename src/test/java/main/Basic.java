package main;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.automation.remarks.testng.UniversalVideoListener;

import utils.CoreUtilities;

/**
 * @author Digant Joshi
 *
 */

/**
 * This class contains initialization of Driver and browser and before and after
 * test configuration test will start from here
 *
 */
@Listeners(UniversalVideoListener.class)
public class Basic
{

	/**
	 * 
	 * @param browser Mar 25, 2020 3:52:57 PM
	 * @throws Exception
	 */
	@BeforeTest
	@Parameters("browser")
	public static void openBrowser(@Optional String browser) throws Exception
	{

		CoreUtilities.loadBrowser("chrome");
		CoreUtilities.getDriver().manage().window().maximize();
	}

	@AfterTest
	public void closebrowser() throws Exception
	{

		if (CoreUtilities.getDriver() != null)
		{
			CoreUtilities.getDriver().quit();
		}
	}

	@AfterSuite
	public void sendNotification()
	{

	}

}
