package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.opencsv.CSVWriter;

import main.Basic;
import main.CommonConstants;
import utils.CoreUtilities;

public class Assesment_1_Test extends Basic
{

	@Test()
	public void acceptWikiLink() throws Exception
	{
		String wikiPagelink = "https://en.wikipedia.org/wiki/Selenium_(software)";
		int n = 10;
		List<String> allScrapedWiki = new ArrayList<String>();

		// Accepts a Wikipedia link - return/throw an error if the link is not a valid
		// wiki link
		checkValidWikiLink(wikiPagelink);

		CoreUtilities.getDriver().get(wikiPagelink);

		allScrapedWiki.add(wikiPagelink);

		List<WebElement> wikiLinks = CoreUtilities.getDriver().findElements(By.xpath("//a[contains(@href,'wiki/')]"));

		for (int i = 1;i <= n;i++)
		{
			if (wikiLinks.get(i).getAttribute("href") != wikiLinks.get(i - 1).getAttribute("href"))
			{
				String scrapewiki = wikiLinks.get(i).getAttribute("href");

				allScrapedWiki.add(scrapewiki);
			}

		}
		writeDataLineByLine(CommonConstants.CSV_FILE_PATH + File.separator + CommonConstants.CSV_FILE_NAME, allScrapedWiki);
		System.out.println(allScrapedWiki);
	}

	public boolean checkValidWikiLink(String requestUrl) throws Exception
	{
		try (CloseableHttpClient httpClient = HttpClients.createDefault();)
		{
			HttpGet httpGet = new HttpGet(requestUrl);

			CloseableHttpResponse response = httpClient.execute(httpGet);

			if (response.getStatusLine().getStatusCode() == 200)
			{

				System.out.println("WIKI Link: " + requestUrl + " is valid");

			} else
			{
				System.out.println("WIKI: " + requestUrl + " is not valid");
				throw new Exception("Invalid wiki link");

			}

		}
		return false;
	}

	public static void writeDataLineByLine(String filePath, List<String> data)
	{

		File file = new File(filePath);
		try
		{

			FileWriter outputfile = new FileWriter(file);

			CSVWriter writer = new CSVWriter(outputfile);

			String[] header = { "All found links", "Total count", "Unique count" };
			writer.writeNext(header);

			String count = String.valueOf((data.size()));

			for (int i = 0;i < data.size();i++)
			{
				String data1[] = { data.get(i), count, count };
				writer.writeNext(data1);
			}

			writer.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
