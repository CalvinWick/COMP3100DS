import java.io.*;
import java.net.*;

public class dsClient {

	public static void main(String[] args) {
		try{
			Socket s = new Socket("localhost", 50000);
			BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			
			out.write(("HELO" + "\n").getBytes());
			
			String str=in.readLine();
			System.out.println(str);
			
			String username = System.getProperty("user.name");
			out.write(("AUTH " + username + "\n").getBytes());
			
			str=in.readLine();
			System.out.println(str);
			
			out.write(("REDY\n").getBytes());
			
			str=in.readLine();
			System.out.println(str);
			
// rec: JOBN XX YY ZZ we need to store the Job ID form here
			System.out.println("SENT: REDY);
			dout.write(("REDY\n").getBytes());
			dout.flush();
			str = din.readLine();
			System.out.println("RCVD: " + str);
			String[] jobInfo = str.split(" ");
			jobID = Integer.parseInt(jobInfo[2]);
			
			System.out.println("SENT: GETS ALL");
			dout.write(("GETS ALL\n").getBytes());
			dout.flush();
			str = din.readLine();
			
// send: GETS ALL
	

// rec: DATA X YYY this x is the following line number

// send: OK

// loop X times, each time a line of records

// send: OK

// rec: . (the received thing here is a DOT)

// find a way to locate the largest server type and server ID

//do the SCHD

			out.write(("QUIT\n").getBytes());
			
			str=in.readLine();
			
			in.close();
			out.close();
			s.close();
		
		} catch (IOException e) { e.printStackTrace(); System.exit(1); }
	}
}
