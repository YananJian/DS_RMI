package examples;

import java.io.IOException;

import utils.*;
import rmi.*;

public class TestServer implements Remote{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String url = "examples.Test";
	private static int server_port = 12346;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		M_Registry register = new M_Registry(Constants.IP_REGISTER, Constants.PORT_REGISTER);
		Test test = new Test();
		
		/*Class<?> interfaces[] = test.getClass().getInterfaces();
    	String remote_name = test.getClass().toString();
    	String[] interface_names=new String[interfaces.length];*/
    	
		RMIServer rms = rmi.RMIServer.getInstance(server_port);
		RemoteObjectRef ror = rms.create_ror(url, test);
		
		try {
			register.rebind(url, ror);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
