import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class COMP3100Ass2{
    private static int SID = 0;
    private static int highestCoreNum = 0;
    
   
    private static String highestCoreServer = null;
    private static String serverReply = null;
    
    

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 50000); // Creates new Socket Object to establish connection
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream()); // Writes data out 
            BufferedReader dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Reads input from server

            sendReply("HELO\n", dataOut); // Begin Socket Connection
            readResp(dataIn);
            sendReply("AUTH calvin\n", dataOut); // Authorise as me 
            readResp(dataIn);
            sendReply("REDY\n", dataOut); // Tells Server ready for data 
            
            String redyResp = readResp(dataIn); // Server sends Job info 
            serverReply = redyResp.substring(0, 4); // Extracts JOB info and stores "JOBN submitTime jobID estRuntime cores"

// LOOP GOES HERE 
 		

                String getJobID[] = redyResp.split(" "); // Splits string to pass to stroe in array
                int jobID = Integer.parseInt(getJobID[2]); // Stores jobID

                sendReply("GETS All\n", dataOut); // Gets serverType, Capable core memory disks and Available core memory disks
                String getReply = readResp(dataIn); // Sends DATA 
                
                String getServerCount[] = getReply.split(" "); // Splits DATA to store as String Array
                int serverCount = Integer.parseInt(getServerCount[1]); // Stores number of jobs 
                //System.out.println(serverCount);

                sendReply("OK\n", dataOut);
             
            	sendReply("QUIT\n", dataOut);
            	readResp(dataIn);


		
                }
            dataOut.close();

            socket.close();
        } catch (Exception e) {
            System.out.println("An Exception has occurred:" + e);
        }
    }

    // Send to server
    public static void sendReply(String reply, DataOutputStream dataOut) throws IOException{
        dataOut.write(reply.getBytes()); // Send reply to server 
        dataOut.flush(); // Flushes output so nothing left 
}

// Read from Server
public static String readResp(BufferedReader dataIn) throws IOException{
        String resp = (String)dataIn.readLine(); // Reads from server and storing as String 
        return resp;
}

