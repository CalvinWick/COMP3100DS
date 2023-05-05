import java.io.*;
import java.net.*;

public class dsClient {
	public static void main(String[] args) {
		try{
			Socket sock = new Socket("localhost", 50000);
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream())); // in from server
			DataOutputStream out = new DataOutputStream(sock.getOutputStream()); // out from client
			
			//Begin of Handshake
			out.write(("HELO\n").getBytes());
			System.out.println("Client sent HELO");
			
			String str=in.readLine();
			System.out.println("Server reply: " + str);
			
			String username = System.getProperty("user.name");
			out.write(("AUTH " + username + "\n").getBytes());
			System.out.println("Client sent Auth: " + username);
			
			str=in.readLine();
			System.out.println("Server reply: " + str);
			
			out.write(("REDY\n").getBytes());
			System.out.println("Client sent REDY");
			// End of Handshake
			
			//Sends JOBN
			str=in.readLine();
			System.out.println("Server reply: " + str);
			
			String jobIDString[] = str.split(" ");		//Getting jobID
			int jobID = Integer.parseInt(jobIDString[1]); 
			System.out.println("JobID: " + jobID);

			out.write(("GETS All\n").getBytes());
			System.out.println("Client sent GETS");
			
			str=in.readLine(); 				//Receives DATA
			System.out.println("Server reply: " + str);
			
			out.write(("OK\n").getBytes());
			System.out.println("Client sent OK");
			
			str=in.readLine(); //Receives SERVERS
			System.out.println("Server LIST: " + str);
			
			
			// LOOP TO ITERATE THROUGH SERVERS 
			/*
			List<String> myList = new ArrayList<String>();
			Iterator<String> iter = myList.iterator();
			String line;
            		while ((line = in.readLine()) != "\n" ) {
               		 myList.add(line);
               		// System.out.println("LINE: " + line);
               		// str=in.readLine();
               		 // System.out.println("SIZE :" + myList.size());
           		 } 
           		 */
           		
           		
			String serverIDString[] = str.split(" ");
			int serverID = Integer.parseInt(serverIDString[1]);
			System.out.println("serverID: " + serverID);
			
			String serverType = serverIDString[0];
			System.out.println("severType: " + serverType);

			
			out.write(("OK\n").getBytes());
			System.out.println("Client sent OK");
			
			System.out.println("Client sent SCHD");
			out.write(("SCHD " + jobID +" "+ serverType + " " + serverID + "\n").getBytes());	//Sending SCHD info
			
			str=in.readLine();	// Calls next JOBN
			System.out.println("Server reply: " + str);
			
			//Terminating Session
			out.write(("QUIT\n").getBytes());
			System.out.println("Client sent QUIT");
			str=in.readLine();
			System.out.println("Server sent QUIT");
			
			in.close();
			out.close();
			sock.close();
			System.out.println("Session Terminated");
		
		} catch (IOException e) { e.printStackTrace(); System.exit(1); }
	}
}
