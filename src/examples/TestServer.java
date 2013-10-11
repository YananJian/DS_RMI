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
	private static int server_port;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sr_ip;
		int sr_port;
		if (args.length < 3)
		{
			System.out.println("Wrong arguments, Format: java examples/TestServer <ip of S_Registry> <port of S_Registry> <port of this Server>");
			return;
		}
		sr_ip = args[0];
		try
		{
			sr_port = Integer.parseInt(args[1]);
			server_port = Integer.parseInt(args[2]);
		}
		catch(NumberFormatException e)
		{
			System.out.println("Please input valid port");
			return;
		}
			
		String ip_server = args[0];
		System.out.println("test ipserver:"+ip_server);
		M_Registry register = new M_Registry(sr_ip, sr_port);
		Test test = new Test();
		
		/*Class<?> interfaces[] = test.getClass().getInterfaces();
    	String remote_name = test.getClass().toString();
    	String[] interface_names=new String[interfaces.length];*/
    	

		RMIServer rms = rmi.RMIServer.getInstance(server_port);
		RemoteObjectRef ror = rms.create_ror(url, test);
		System.out.println("in TestServer, created ror, serverip:"+ror.getIP_adr());
		try {

		    //bind
		    register.bind(url, ror);

		    //list
		    Vector<String> s = register.list();
		    if (s == null)
			{
				System.out.println("TestServer can not connect to Registry");
				rms.stop();
				return;
			}
		    Iterator<String> itr = s.iterator();
		    System.out.println(" > Listing ...");
		    while (itr.hasNext())
			{
			    String insts = itr.next();
			    System.out.println(" > "+insts);
			}
		    
		    //unbind
		    register.unbind(url);

		    //list
		    s = register.list();
		    itr = s.iterator();
		    System.out.println(" > Listing ...");
		    while (itr.hasNext())
			{
			    String insts = itr.next();
			    System.out.println(" > "+insts);
			}

		    //rebind
		    register.rebind(url, ror);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can not connect to S_Registry, please start S_Registry first and use the proper ip and port of S_Registry");
		}
	}

}
