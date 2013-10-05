import java.net.*;
import java.util.Vector;
import java.io.*;

import examples.*;
import rmi.M_Registry;
import rmi.Registry;
import utils.*;

public class Client {

	static M_Registry registry = null;
	static String r_ip = "0.0.0.0";
	static int r_port = 12345;
	static String url = "examples.Test";
	
	public static void main(String args[])
	{		
    	registry = new M_Registry(r_ip, r_port);
    	
    	
    	try {
			RemoteObjectRef ror = M_Registry.lookup(url);
			
			TestI t = (TestI) ror.localise();
			t.test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
