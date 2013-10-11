import java.net.*;
import java.util.Vector;
import java.io.*;

import examples.*;
import rmi.M_Registry;
import utils.*;

public class Client {

    static M_Registry registry = null;
    static String r_ip;
    static int r_port;
    static String url = "examples.Test";

    public static void main(String args[])
    {

	if (args.length < 2)
	    {
		System.out.println("Wrong arguments, Format: java Client <ip of S_Registry> <port of S_Registry>");
		return;
	    }
	r_ip = args[0];
	try
	    {
		r_port = Integer.parseInt(args[1]);
	    }
	catch(NumberFormatException e)
	    {
		System.out.println("Please input valid port");
		return;
	    }
	registry = new M_Registry(r_ip, r_port);
	
	try {
	    RemoteObjectRef ror = M_Registry.lookup(url);
	    //System.out.println("In Client, server ip:"+ror.getIP_adr());
	    TestI t = (TestI) ror.localise();
	    String s = t.test("Yanan Jian");
	    System.out.println(s);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}