package utils;

public class Constants {

    // TODO: remove following 2 lines (and all refs to them)
    public static final int PORT_MASTER = 12345;
    public static final String IP_MASTER = "0.0.0.0";

    public static final String S_IP = "0.0.0.0";
    public static final int S_Port = 12345;
    public enum MESSAGE_TYPE {
	LIST, BIND, REBIND, UNBIND, LOOKUP,
	    RET_LIST, RET_BIND, RET_REBIND, RET_UNBIND, RET_LOOKUP,
	    DEFAULT, INVOKE};

}
