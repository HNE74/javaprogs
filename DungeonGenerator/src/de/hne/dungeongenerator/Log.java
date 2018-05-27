package de.hne.dungeongenerator;

public class Log {
	
	public static void info(String msg) {
		System.out.println(msg);
	}
	
	public static void error(String msg) {
		System.err.println(msg);
	}
	
	public static void error(String msg, Throwable th) {
		System.err.println(msg);
		StackTraceElement element[] = th.getStackTrace();
		for(int i=0; i<element.length; i++) {
			System.err.println(element.toString());
		}
	}
	

}
