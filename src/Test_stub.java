import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import examples.TestI;
import utils.*;

public class Test_stub implements TestI{
	
	public void test()
	{
	    String s[] = {};
	    RemoteObjectRef.invoke("test", s);
	    //System.out.println("This is a test!");
	}
}
