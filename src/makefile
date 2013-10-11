.SUFFIXES: .java .class
.java.class:
	javac $<

CLASSES = Client.class rmi/RMIServer.class rmi/M_Registry.class rmi/S_Registry.class examples/Test.class examples/TestServer.class examples/TestI.class examples/Test_stub.class utils/Constants.class utils/Msg.class utils/Remote.class utils/RemoteObjectRef.class

all: $(CLASSES)


Client: Client.class


RMIServer: RMIServer.class

M_Registry: M_Registry.class

S_Registry: S_Registry.class 


Test: Test.class

TestServer: TestServer.class

TestI: TestI.class

Test_stub: Test_stub.class


Constants: Constants.class

Msg: Msg.class

Remote: Remote.class

RemoteObjectRef: RemoteObjectRef.class


clean:
	rm $(CLASSES)