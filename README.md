DS_RMI
======

To run this project:

Firstly, compile everything.
Then,
1, start the register
   > java rmi/S\_Registry \<port of S_Registry\>

2, get register's ip address
   > /sbin/ifconfig $1 | grep "inet addr" | awk -F: '{print $2}' | awk '{print $1}'

2, start the server
   > java examples/TestServer \<ip of S_Registry\> \<port of S_Registry\> \<port of this Server\>
   
3, start the client
   > java Client \<ip of S_Registry\> \<port of S_Registry\>
  
Now you should see 'start test *********' promped on the server side.
Means that function 'test' in Test class been executed remotely on the server.
