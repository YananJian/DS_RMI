package examples;
import utils.*;

public class Test implements TestI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String test(String t)
	{
		String s = "start test *********"+t;
		String c_s = "Return from Server, client should print: " + s;
		System.out.println(s);
		return c_s;
	}
}
