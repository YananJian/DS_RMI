package utils;

import utils.Constants.MESSAGE_TYPE;


public class Msg implements java.io.Serializable{
    
// required: type of message (identifies desired action by recipient)
    private MESSAGE_TYPE msg_type = null;
    private String func_name;
    private String obj_name;
    private Object params[];
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
    	return msg_type;
    }

    public RemoteObjectRef getRemote_ref() 
    {
	return remote_ref;
    }
    
    public void setRemote_ref(RemoteObjectRef remote_ref) 
    {
	this.remote_ref = remote_ref;
    }
    
    public String getObj_name() 
    {
	return obj_name;
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
