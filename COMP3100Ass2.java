import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class COMP3100Ass2{
    private static int sID = 0;
    private static int highestCoreNum = 0;
    private static boolean isTrue = true;
    private static boolean serverStatus = true;
    private static String highestCoreServer = null;
    private static String serverReply = null;
    private static List<String> highestServerCoresList = new ArrayList<>();
    

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 50000); // Creates new Socket Object to establish connection
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream()); // Writes data out 
            BufferedReader dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Reads input from server

            sendReply("HELO\n", dataOut); // Begin Socket Connection
            readReply(dataIn);
            sendReply("AUTH calvin\n", dataOut); // Authorise as me 
            readReply(dataIn);
            sendReply("REDY\n", dataOut); // Tells Server ready for data 
            
            String response = readReply(dataIn); // Server sends Job info 
            serverReply = response.substring(0, 4); // Extracts JOB info and stores "JOBN submitTime jobID estRuntime cores"

           
            while (!serverReply.equals("NONE")) { // While server is still sending messages
                String getJobID[] = response.split(" "); // Splits string to store in array
                int jobID = Integer.parseInt(getJobID[2]); // Stores jobID

                sendReply("GETS All\n", dataOut); // Gets serverType, Capable core memory disks and Available core memory disks
                String getReply = readReply(dataIn); // Sends DATA 

                while (serverStatus) { // Only allows it to run once 
                    serverStatus = false;
                }

                String getServerCount[] = getReply.split(" "); // Splits DATA to store as String Array
                int serverCount = Integer.parseInt(getServerCount[1]); // Stores number of jobs 
                //System.out.println(serverCount);

                sendReply("OK\n", dataOut);

                // Creates list of total number of servers and their info
                List<String> totalServerList = new ArrayList<>();
                for (int i = 0; i < serverCount; i++) {
                    totalServerList.add(readReply(dataIn)); 
                }

                sendReply("OK\n", dataOut);
                readReply(dataIn);

                // Find highest core loop
                for (int i = 0; i < totalServerList.size(); i++) {
                    String highestCoresArray[] = totalServerList.get(i).split(" "); // Gets the JOBN info, and stores in array
                    int coreCount = Integer.parseInt(highestCoresArray[4]); // Stores number of Cores
                    if (coreCount > highestCoreNum) { // checks to see if core is bigger than 0
                        highestCoreNum = coreCount; // Stores core as highest core number 
                        highestCoreServer = highestCoresArray[0]; // Stores the highest core server from the above core  
                    }
                }

                while (isTrue) { // Iterates over the totalServerList to find and add the servers with the 
                		          // highest number of cores and matching server type to the highestServerCoresList
                    for (int i = 0; i < totalServerList.size(); i++) {
                        int serverCores = Integer.parseInt(totalServerList.get(i).split(" ")[4]); // Gets number of cores from the list 
                        //System.out.println("Server Cores: " + serverCores); 
                        String serverType = totalServerList.get(i).split(" ")[0]; // Gets server type 
                       // System.out.println("serverType: " + serverType);
                        if (serverCores == highestCoreNum && serverType.equals(highestCoreServer)) { // Checks to see if server cores matches 		 
                        									                                          // highest core number and matches the server
                        									                                           // type to the highest core server 
                            highestServerCoresList.add(totalServerList.get(i)); // Adds to highestServerList
                        }
                    }
                    isTrue = false; // Stops it from iterating again 
                }

                if (serverReply.equals("JOBN")) { // Check if server is sending JOBN
                    sendReply("SCHD " + jobID + " " + highestCoreServer + " " + sID + "\n", dataOut); // Schedule job 
                    readReply(dataIn);   // Read server response 
                    //System.out.println(dataIn);
                    sID++; // Increment sID
                }

                if (sID >= highestServerCoresList.size()) { // If this reaches the end of the list 
                    sID = 0;                                // then it resets count back to 0
                }

                sendReply("REDY\n", dataOut); // Ready for next job 
                response = readReply(dataIn); // Reads server response 
                serverReply = response.substring(0, 4); // Stores the response here 
            }
            sendReply("QUIT\n", dataOut); // Quits the connection 
            readReply(dataIn); // Server acknowledges 

            dataOut.close(); // Closes stream 
            socket.close(); // Closes socket connection 
        
        } catch (Exception e) { // Error catching 
            System.out.println("An Exception has occurred:" + e);
        }
    }

    // Send to server
public static void sendReply(String reply, DataOutputStream dataOut) throws IOException{
        dataOut.write(reply.getBytes()); // Send reply to server 
        dataOut.flush(); // Flushes output so nothing left 
	}

// Read from Server
public static String readReply(BufferedReader dataIn) throws IOException{
        String resp = (String)dataIn.readLine(); // Reads from server and storing as String 
        return resp;
	}
}
