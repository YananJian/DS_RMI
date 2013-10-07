package examples;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import examples.TestI;
import utils.*;

public class Test_stub implements TestI{
		
    private String url = "examples.Test";
    
    public String test(String t)
    {	
	String []params = {t};
	String ret_val = (String) RemoteObjectRef.invoke(url, "test", params);
	return ret_val;
	//System.out.println("This is a test!");
    }
}
