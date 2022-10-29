package utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import main.CommonConstants;

public class ResultListener implements ITestListener
{
	Logger log = CoreUtilities.getLogger(ResultListener.class);
	public static List<ITestNGMethod> totalruntests = new ArrayList<ITestNGMethod>();
	public static List<ITestNGMethod> passedtests = new ArrayList<ITestNGMethod>();
	public static List<ITestNGMethod> failedtests = new ArrayList<ITestNGMethod>();
	public static String failureMessage;
	public static String methodName;

	@Override
	public void onTestFailure(ITestResult result)
	{
		try
		{

		
		}
		catch (Exception e)
		{

			e.printStackTrace();
		}

	}

	@Override
	public void onFinish(ITestContext context)
	{

		
	}

	public void onTestStart(ITestResult result)
	{
		totalruntests.add(result.getMethod());
		
	}

	public void onTestSuccess(ITestResult result)
	{
		passedtests.add(result.getMethod());
	
		CoreUtilities.scenarioRunning(result.getName().toString());
	

}

	public void onTestSkipped(ITestResult result)
	{

	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result)
	{
	}

	public void onStart(ITestContext context)
	{
	}
}
