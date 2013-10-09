package examples;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import utils.*;
import rmi.*;

public class TestServer implements Remote{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String url = "examples.Test";
	private static int server_port = Constants.PORT_SERVER;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		M_Registry register = new M_Registry(Constants.IP_REGISTER, Constants.PORT_REGISTER);
		Test test = new Test();
		
		/*Class<?> interfaces[] = test.getClass().getInterfaces();
    	String remote_name = test.getClass().toString();
    	String[] interface_names=new String[interfaces.length];*/
    	
		RMIServer rms = rmi.RMIServer.getInstance(server_port);
		RemoteObjectRef ror = rms.create_ror(url, test);
		System.out.println("in TestServer, created ror, serverip:"+ror.getIP_adr());
		try {
			register.bind(url, ror);
			Vector<String> s = register.list();
			Iterator<String> itr = s.iterator();
			System.out.println(" > Listing ...");
			while (itr.hasNext())
			{
				String insts = itr.next();
				System.out.println(" > "+insts);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
