package examples;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import examples.TestI;
import utils.*;

public class Test_stub implements TestI{
	
	
	private String url = "examples.Test";
	private static String server_ip;
	private static int server_port;

	public Test_stub(){}
	public Test_stub(String ip, int port)
	{
		Test_stub.server_ip = ip;
		Test_stub.server_port = port;
	}
	
	public String test(String t)
	{	
		String []params = {t};
	    String ret_val = (String) RemoteObjectRef.invoke(url, "test", params, server_ip, server_port);
	    return ret_val;
	    //System.out.println("This is a test!");
	}
}
