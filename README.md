# ToDo-Server

## How to test your server
1. Let the ServerRunner run
2. Open up Powershell (type Poweshell in the Windows Searchbar)
3. Type **ipconfig** in Powershell
4. At the bottom of the result, it returns **IPv4 Address**. This is your IP-address - copy it
5. Type **test-netconnection IPADDRESS -port 9999**. Change IPADDRESS to your IP-address that you copied from the last step.
6. The console from the ServerRunner should give you an output. test-netconnection sends a TCP-packet to your server.

## Messaging protocol format
- Client -> Server: ClientMessageType|Token|Data
- Server -> Client: ServerMessageType|Token|Data