package examples;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import examples.TestI;
import utils.*;

public class Test_stub implements TestI{
	
	
	private String url = "examples.Test";
	
	public void test()
	{
	    String s[] = {};
	    RemoteObjectRef.invoke(url, "test", s);
	    //System.out.println("This is a test!");
	}
}
