package com.bps.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommonUtilityTest {
	
	@Test
	public void testIsValidJson() {
		String valid1 = "{\"name\": \"Bhanu\"}";
		String valid2 = "bhanu";
		String invalid1 = "";
		String invalid2 = null;
		String invalid3 = "name:bhanu";
		String invalid5 = "name=bhanu";
		assertTrue(CommonUtility.isValidJson(valid1));
		assertTrue(CommonUtility.isValidJson(valid2));
		assertFalse(CommonUtility.isValidJson(invalid1));
		assertFalse(CommonUtility.isValidJson(invalid2));
		assertFalse(CommonUtility.isValidJson(invalid3));
		assertFalse(CommonUtility.isValidJson(invalid5));
	}

}
