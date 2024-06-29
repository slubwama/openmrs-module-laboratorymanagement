package org.openmrs.module.labmanagement.api.utils;

import org.openmrs.module.labmanagement.api.dto.SortField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {

	public static String MASKED_STRING ="**********";

	static Pattern emailPattern = Pattern
	        .compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

	public static boolean isValidEmail(String email) {
		if (email == null)
			return false;
		if (email.endsWith("."))
			return false;
		return emailPattern.matcher(email).matches();
	}

	public static List<SortField> parseSortOrder(String sortCriteria){
		List<String> sortValues = Arrays.stream(sortCriteria.split(","))
				.filter(org.apache.commons.lang.StringUtils::isNotBlank)
				.collect(Collectors.toList());
		if (sortValues.isEmpty()) return null;

		List<SortField> sortFields = new ArrayList<>();
		for (String sortValue : sortValues)
		{
			boolean ascending = true;
			String sortField = null;
			if (sortValue.startsWith("+") || sortValue.startsWith(" "))
			{
				if (sortValue.length() > 1)
				{
					sortField = sortValue.substring(1);
				}else{
					continue;
				}
			}
			else if (sortValue.startsWith("-"))
			{
				if (sortValue.length() > 1)
				{
					ascending = false;
					sortField = sortValue.substring(1);
				}
				else
				{
					continue;
				}
			}
			else if (sortValue.endsWith("_asc"))
			{
				if (sortValue.length() > 4)
				{
					sortField = sortValue.substring(0, sortValue.length() - 4);
				}
				else
				{
					continue;
				}
			}
			else if (sortValue.endsWith("_desc"))
			{
				if (sortValue.length() > 5)
				{
					ascending = false;
					sortField = sortValue.substring(0, sortValue.length() - 5);
				}
				else
				{
					continue;
				}
			}
			else
			{
				sortField = sortValue;
			}
			sortFields.add(new SortField(sortField, ascending));
		}
		return sortFields;
	}
}
