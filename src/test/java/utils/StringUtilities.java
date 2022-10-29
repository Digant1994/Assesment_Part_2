package utils;

/**
 * 
 *  This class contains methods to be performed on a String
 *
 */
public class StringUtilities {

	/**
	 * 
	 * 
	 * @param text
	 * @return
	 * @since Jul 30, 2021 11:31:03 AM Description: To remove leading zeros from
	 *        string
	 */
	public static String removeLeadingZerosFromString(String text) {
		int numberOfChars = 0;
		while (numberOfChars < text.length()
				&& text.charAt(numberOfChars) == '0')
			numberOfChars++;
		StringBuffer bufferedString = new StringBuffer(text);
		bufferedString.replace(0, numberOfChars, "");
		return bufferedString.toString();
	}

	/**
	 * 
	 * @param stringText
	 * @return Mar 25, 2020 4:05:30 PM Description: Check whether string is
	 *         empty or not
	 */
	public static boolean isStringNotEmpty(String stringText) {
		return !(stringText.isEmpty() || stringText == null);
	}

}
