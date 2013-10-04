package examples;

import java.io.IOException;

import utils.*;
import rmi.*;

public class TestServer implements Remote{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		M_Registry register = new M_Registry("0.0.0.0", 12345);
		Test test = new Test();
		
		Class<?> interfaces[] = test.getClass().getInterfaces();
    	String remote_name = test.getClass().toString();
    	String[] interface_names=new String[interfaces.length];
    	System.out.println(interface_names[0]);
		RemoteObjectRef ror = rmi.RMIServer.getInstance().create_ror(test);
		try {
			register.rebind("test", ror);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
