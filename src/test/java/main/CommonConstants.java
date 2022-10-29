package main;

import utils.FileUtils;

/**
 * Description: this class contain common constants
 */
public class CommonConstants
{
	// ============================================PropertyFilesValues============================================================================================

	public static String CSV_FILE_PATH = FileUtils.getPropertiesfileValue("CSV_FILE_PATH");
	public static String CSV_FILE_NAME = FileUtils.getPropertiesfileValue("CSV_FILE_NAME");
}
