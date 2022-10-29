package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.testng.Reporter;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * Description: this class is contains methods related to properties file and
 * report directory
 *
 */
public class FileUtils
{
	protected final static String PROPERTIES_FILE = System.getProperty("user.dir") + File.separator + "Assesment1.properties";

	static Logger log = CoreUtilities.getLogger(FileUtils.class);
	static List<String> fileList = new ArrayList<String>();
	static File file = new File("");

	public static void zipIt(String zipFile) throws InterruptedException
	{
		byte[] buffer = new byte[1024];
		try
		{

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			log.info("input to Zip : " + fileList);
			log.info("Output to Zip : " + zipFile);

			for (String file : fileList)
			{

				log.info("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream("");

				int len;
				while ((len = in.read(buffer)) > 0)
				{
					zos.write(buffer, 0, len);
				}
				in.close();
			}

			zos.closeEntry();
			// remember close it
			zos.close();

			log.info("Done");
		} catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public static void generateFileList(File node)
	{

		if (node.isFile())
		{
			fileList.add(node.toString());
		}

		if (node.isDirectory())
		{
			String[] subNote = node.list();

			for (String filename : subNote)
			{
				if (!(filename.contains(".js")))
				{
					fileList.add(filename);
				}

			}

		}
	}

	public static String getPropertiesfileValue(String value)
	{
		File file = new File(PROPERTIES_FILE);
		String propertyValue = null;
		FileInputStream fileInput = null;
		try
		{
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		Properties prop = new Properties();

		// load properties file
		try
		{
			prop.load(fileInput);
			propertyValue = (String) prop.get(value);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return propertyValue;
	}

	public static String uploadFileOnS3Bucket(File file)
	{
		try
		{
			AWSCredentials credentials = new BasicAWSCredentials("", "");
			AmazonS3 s3client = new AmazonS3Client(credentials);
			String bucketName = "";
			s3client.createBucket(bucketName);
			String fileName = file.getName();
			s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
			System.out.println("file uploaded sucessfully to " + bucketName);
			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName).withMethod(HttpMethod.GET).withExpiration(new DateTime(new Date()).plusDays(7).toDate());
			URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);

			Reporter.log(" Screenshot Url: " + url.toString());
			return url.toString();
		} catch (Exception e)
		{
			e.printStackTrace();

			return null;
		}

	}

	public static String uploadvideo(File file) throws FileNotFoundException
	{
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("", "");
		AmazonS3 s3Client = new AmazonS3Client(awsCreds);
		String bucketName = "";
		s3Client.createBucket(bucketName);
		String fileName = file.getName();
		s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
		System.out.println("file uploaded sucessfully to " + bucketName);
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName).withMethod(HttpMethod.GET).withExpiration(new DateTime(new Date()).plusDays(7).toDate());
		URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		Reporter.log(" Screenshot Url: " + url.toString());
		return url.toString();
	}

	public static void deleteDir(File file)
	{
		File[] contents = file.listFiles();
		if (contents != null)
		{
			for (File f : contents)
			{
				if (!Files.isSymbolicLink(f.toPath()))
				{
					deleteDir(f);
				}
			}
		}
		file.delete();
	}

	public static String readTextFile(String filePath)
	{
		try
		{
			String text = new String(Files.readAllBytes(Paths.get(filePath)));
			return text;
		} catch (Exception e)
		{
			return null;
		}

	}

	/**
	 * 
	 * 
	 * 
	 * @param filePath
	 * @param text
	 * @since Sep 22, 2021 12:26:14 PM Description: Write to text file
	 */
	public static void writeToTextFile(String filePath, String text)
	{
		try
		{
			FileWriter fw = new FileWriter(filePath);
			fw.write(text);
			fw.close();
		} catch (Exception e)
		{
			System.out.println(e);
		}
		log.error("Couldn't write to file");
	}

}
