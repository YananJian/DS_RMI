package utils;

import utils.Constants.MESSAGE_TYPE;

import java.util.Set;
import java.util.Vector;

public class Msg implements java.io.Serializable{
    
    private MESSAGE_TYPE msg_type = null; // required: type of message (identifies desired action by recipient)
    private String func_name; 
    private String obj_name;
    private Object params[];
    private Vector<String> list; // for LIST requests only
    private String url; // for BIND, REBIND, UNBIND
    private RemoteObjectRef remote_ref;
    private String ip;
    private int port;
    private Object obj;
    private Object rets;
    
    public void set_msg_tp(MESSAGE_TYPE tp)
    {
    	this.msg_type = tp;
    }
    
    public MESSAGE_TYPE get_msg_tp()
    {
    	return this.msg_type;
    }

    public void set_list(Vector<String> set)
    {
	this.list = set;
    }

    public Vector<String> get_list() 
    {
	return this.list;
    }

    public void set_url(String url) 
    {
	this.url = url;
    }

    public String get_url() 
    {
	return this.url;
    }

    public RemoteObjectRef getRemote_ref() 
    {
	return this.remote_ref;
    }
    
    public void setRemote_ref(RemoteObjectRef remote_ref) 
    {
	this.remote_ref = remote_ref;
    }
    
    public String getObj_name() 
    {
	return this.obj_name;
    }
    
    public void setObj_name(String obj_name) 
    {
	this.obj_name = obj_name;
    }

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object params[]) {
		this.params = params;
	}

	public String getFunc_name() {
		return func_name;
	}

	public void setFunc_name(String func_name) {
		this.func_name = func_name;
	}

	public Object getRets() {
		return rets;
	}

	public void setRets(Object ret_val) {
		this.rets = ret_val;
	}
    
}
