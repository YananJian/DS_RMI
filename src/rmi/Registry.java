package rmi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Vector;
import java.io.*;
import utils.*;
import utils.Constants.*;

public class Registry {

    private HashMap<String, RemoteObjectRef> obj_map = new HashMap<String, RemoteObjectRef>(); // obj name -> obj
    private HashMap<String, String> server_map = new HashMap<String, String>(); // obj name -> serverIP_port
    private int port = utils.Constants.PORT_REGISTER;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ServerSocket listener = null;

    public Vector<String> list()
    {
	Vector<String> list = new Vector<String>();
	for (String key : this.obj_map.keySet()) {
	    list.add(key);
	}
	return list;
    }

    public void bind(String serverIP_port, String url, RemoteObjectRef r)
    {
	this.server_map.put(url, serverIP_port);
	this.obj_map.put(url, r);
    }

    public void rebind(String serverIP_port, String url, RemoteObjectRef r) // currently identical to bind
    {
	this.bind(serverIP_port, url, r);
    }

    public void unbind(String serverIP_port, String url)
    {
	this.server_map.remove(url);
	this.obj_map.remove(url);
    }

    public String get_address(Socket sock)
    {
	InetAddress IP = sock.getInetAddress();
	String IP_str = IP.getHostAddress();
	int Port = sock.getPort();
	String Port_str = Integer.toString(Port);
	return IP_str + "_" + Port_str;
    }

    public utils.Msg process(utils.Msg msg, Socket sock) throws IOException
    {
	System.out.println(" > processing message");
	utils.Msg reply = new utils.Msg();
	utils.Constants.MESSAGE_TYPE msg_type = msg.get_msg_tp();

	if (msg_type == MESSAGE_TYPE.LIST) { 
	    reply.set_list(this.list());
	    System.out.println(" > replying with RET_LIST message");
	    reply.set_msg_tp(MESSAGE_TYPE.RET_LIST);
	}
	if (msg_type == MESSAGE_TYPE.LOOKUP) {
	    String url = msg.get_url();
	    if (this.obj_map.containsKey(url)) { reply.setRemote_ref(obj_map.get(url)); }
	    reply.set_msg_tp(MESSAGE_TYPE.RET_LOOKUP);
	}
	if (msg_type == MESSAGE_TYPE.BIND) {
	    String serverIP_port = get_address(sock);
	    String url = msg.get_url();
	    RemoteObjectRef r = msg.getRemote_ref();

	    this.bind(serverIP_port, url, r);

	    System.out.println(" > replying with RET_BIND message");
	    reply.set_msg_tp(MESSAGE_TYPE.RET_BIND);
	}
	if (msg_type == MESSAGE_TYPE.REBIND) {
	    String serverIP_port = get_address(sock);
	    String url = msg.get_url();
	    RemoteObjectRef r = msg.getRemote_ref();

	    this.rebind(serverIP_port, url, r);

	    System.out.println(" > replying with RET_REBIND message");
	    reply.set_msg_tp(MESSAGE_TYPE.RET_REBIND);
	}
	if (msg_type == MESSAGE_TYPE.UNBIND) {
	    String serverIP_port = get_address(sock);
	    String url = msg.get_url();

	    this.unbind(serverIP_port, url);

	    System.out.println(" > replying with RET_UNBIND message");
	    reply.set_msg_tp(MESSAGE_TYPE.RET_UNBIND);
	}
	else { 
	    System.out.println(" > replying with DEFAULT message");
	    reply.set_msg_tp(MESSAGE_TYPE.DEFAULT);
	}
	return reply;
    }

    public void listen() throws IOException, ClassNotFoundException
    {
	while (true) {
	    System.out.println("Listening for messages...");
	    Socket sock = listener.accept();
	    try {
		ois = new ObjectInputStream(sock.getInputStream());
		oos = new ObjectOutputStream(sock.getOutputStream());
		utils.Msg msg = (utils.Msg) ois.readObject();
		System.out.println(" > got a message!");
		utils.Msg reply = this.process(msg, sock);  
		oos.writeObject(reply);
		oos.flush();
	    } catch (ClassNotFoundException e) {
		e.printStackTrace();
	    }
	}
    }

    public void connect() throws InterruptedException, ClassNotFoundException
    {
	try {
	    listener = new ServerSocket(this.port);
	    this.listen();
	} catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException 
    {						  
	Registry registry = new Registry();
	registry.connect();
    }

}
