// make dummy version that starts up
// sends bind / rebind / unbind messages to registry; listens for ack...
//      server should start up, bind SOMETHING in main()
import utils.*;

import java.net.*;

public class Server
{
    static String host;
    static int port;

    // It will use a hash table, which contains ROR together with
    // reference to the remote object.
    // As you can see, the exception handling is not done at all.
    public static void main(String args[])    
	throws Exception
    {
	String InitialClassName = args[0];
	String registryHost = args[1];
	int registryPort = Integer.parseInt(args[2]);	
	String serviceName = args[3];

	// it should have its own port. assume you hardwire it.
	host = (InetAddress.getLocalHost()).getHostName();
	port = 12346;

	// it now has two classes from MainClassName: 
	// (1) the class itself (say ZipCpdeServerImpl) and
	// (2) its skeleton.
	Class initialclass = Class.forName(InitialClassName);
	Class initialskeleton = Class.forName(InitialClassName+"_skel");
	
	// you should also create a remote object table here.
	// it is a table of a ROR and a skeleton.
	// as a hint, I give such a table's interface as RORtbl.java. 
	
	M_Registry m_r = new M_Registry("0.0.0.0", 12345);
	Object o = initialclass.newInstance();
	m_r.rebind("test", (RemoteObjectRef) o);
	// after that, you create one remote object of initialclass.
	
	
	// then register it into the table.
	
	// create a socket.
	ServerSocket serverSoc = new ServerSocket(port);

	// Now we go into a loop.
	// Look at rmiregistry.java for a simple server programming.
	// The code is far from optimal but in any way you can get basics.
	// Actually you should use multiple threads, or this easily
	// deadlocks. But for your implementation I do not ask it.
	// For design, consider well.
	while (true)
	    {
		// (1) receives an invocation request.
		// (2) creates a socket and input/output streams.
		// (3) gets the invocation, in martiallled form.
		// (4) gets the real object reference from tbl.
		// (5) Either:
		//      -- using the interface name, asks the skeleton,
		//         together with the object reference, to unmartial
		//         and invoke the real object.
		//      -- or do unmarshalling directly and involkes that
		//         object directly.
		// (6) receives the return value, which (if not marshalled
		//     you should marshal it here) and send it out to the 
		//     the source of the invoker.
		// (7) closes the socket.
	    }
    }
}