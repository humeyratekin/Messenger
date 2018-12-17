import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import java.util.Vector;

public  class ChatServer {
	
	static Vector ClientSockets;
	static Vector LoginNames;
	
	ChatServer() throws IOException{
		
		ServerSocket server=new ServerSocket(5217);
		ClientSockets=new Vector();
		LoginNames=new Vector();
		
		while(true){
			Socket client=server.accept();
			AcceptClient acceptClient=new AcceptClient(client);
			
			
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		ChatServer server=new ChatServer();
		
	}

	
	class AcceptClient extends Thread{
		Socket ClientSocket;
		DataInputStream dataIn;
		DataOutputStream dataOut;
		
		AcceptClient(Socket client) throws IOException{
			ClientSocket=client;
			dataIn=new DataInputStream(ClientSocket.getInputStream());
			dataOut=new DataOutputStream(ClientSocket.getOutputStream());
			
			String LoginName=dataIn.readUTF();
			
			LoginNames.add(LoginName);
			ClientSockets.add(ClientSocket);
			
			start();
			
		}
		
		public void run() {
			while(true) {
				
				try {
					String msgFromClient=dataIn.readUTF();
					StringTokenizer sToken=new StringTokenizer(msgFromClient);
					String LoginName=sToken.nextToken();
					String MsgType=sToken.nextToken();
					String msg="";
					int lo=-1;
					while(sToken.hasMoreTokens()) {
						msg=msg+" "+sToken.nextToken();
					}
					
					if(MsgType.equals("LOGIN")) {
						for(int i=0; i<LoginNames.size(); i++) {
							Socket pSocket=(Socket)ClientSockets.elementAt(i);
							DataOutputStream pOut=new DataOutputStream(pSocket.getOutputStream());
							pOut.writeUTF(LoginName+" has logged in");
						}
						
					}else if(MsgType.equals("LOGOUT")) {
						for(int i=0; i<LoginNames.size(); i++) {
							if(LoginName.equals(LoginNames.elementAt(i)));
								lo=i;
							Socket pSocket=(Socket)ClientSockets.elementAt(i);
							DataOutputStream pOut=new DataOutputStream(pSocket.getOutputStream());
							pOut.writeUTF(LoginName+" has logged out");
						}
						if(lo>=0) {
							LoginNames.removeElementAt(lo);
							ClientSockets.removeElementAt(lo);
						}
					}else {
						for(int i=0; i<LoginNames.size(); i++) {
							Socket pSocket=(Socket)ClientSockets.elementAt(i);
							DataOutputStream pOut=new DataOutputStream(pSocket.getOutputStream());
							pOut.writeUTF(LoginName+": "+msg);
						}
					}
					
					if(MsgType.equals("LOGOUT")) {
						break;
					}
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		
	}

}
