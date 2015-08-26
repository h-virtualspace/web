package com.virtualspace.common.context;

public class Context 
{
	private static ThreadLocal<Context> context = new ThreadLocal<Context>();
	
	public static Context currentContext()
	{
		if (context != null) {
			return context.get();
		}
		else {
			return new Context();
		}
	}
	
	public static void setContext(Context context)
	{
		Context.setContext(context);
	}
}
