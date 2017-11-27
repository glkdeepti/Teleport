package com.org.bytecubed.teleport.common;

/**
 * @description Common class for String manupulations
 * @author Deepti
 *
 */
public class StringUtils {

	/**
	 * Suppress default constructor for noninstantiability     
	 */
	private StringUtils() {         
		// This constructor will never be invoked 
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	
	
	public static String translate(boolean trueOrFalse) {
	    return trueOrFalse ? "yes" : "no";
	}

}
