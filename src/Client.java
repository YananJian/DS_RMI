import java.net.*;
import java.io.*;

public class Client {

	static M_Registry registry = null;
	static String r_ip = "0.0.0.0";
	static int r_port = 12345;
	
	
	public static void main(String args[])
	{		
    	registry = new M_Registry(r_ip, r_port);
    	try {
			registry.lookup("test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
