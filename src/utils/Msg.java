package utils;

import utils.Constants.MESSAGE_TYPE;



public class Msg implements java.io.Serializable{
	// required: type of message (identifies desired action by recipient)
    private MESSAGE_TYPE msg_type = null;
    private String func_name;
    private String obj_name;
    private String params[];
    private RemoteObjectRef remote_ref;
    
    public void set_msg_tp(MESSAGE_TYPE tp)
    {
    	this.msg_type = tp;
    }
    
    public MESSAGE_TYPE get_msg_tp()
    {
    	return msg_type;
    }

	public RemoteObjectRef getRemote_ref() {
		return remote_ref;
	}

	public void setRemote_ref(RemoteObjectRef remote_ref) {
		this.remote_ref = remote_ref;
	}

	public String getObj_name() {
		return obj_name;
	}

	public void setObj_name(String obj_name) {
		this.obj_name = obj_name;
	}
}
