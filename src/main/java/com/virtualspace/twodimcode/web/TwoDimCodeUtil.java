package com.virtualspace.twodimcode.web;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

public class TwoDimCodeUtil 
{
	public static final String DEFAULT_ENCODE = "utf-8";
	public static int getParamInt(String key,HttpServletRequest req)
	{
		String number = req.getParameter(key);
		int result = 0;
		if(number != null)
		{
			try
			{
				result = Integer.parseInt(number);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		return result;
	}
	public static String getParamString(String key,HttpServletRequest req)
	{
		return getParamString(key,req,DEFAULT_ENCODE);
	}
	public static String getParamString(String key,HttpServletRequest req,String encodeType)
	{
		String text = req.getParameter(key);
		try {
			if(text != null && !text.toLowerCase().trim().equals(""))
			{
				text = URLDecoder.decode(text,encodeType);
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		return text;
	}
}
