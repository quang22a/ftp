package my_ftp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class MyFTP_Server_Worker extends Thread {
	private boolean debugMode = true;
	private enum transferType {
        ASCII, BINARY
    }
	private enum userStatus {
        NOTLOGGEDIN, ENTEREDUSERNAME, LOGGEDIN
    }
	// Path information
	private String root;
	private String currDirectory;
	private String fileSeparator = "/";
	// control connection
	private Socket controlSocket;
	private PrintWriter controlOutWriter;
    private BufferedReader controlIn;
    // data Connection
    private ServerSocket dataSocket;
    private Socket dataConnection;
    private PrintWriter dataOutWriter;
    
    private int dataPort;
    private transferType transferMode = transferType.ASCII;
    // user properly logged in?
    private userStatus currentUserStatus = userStatus.NOTLOGGEDIN;
    private String validUser = "tmq";
    private String validPassword = "301199";
    
    private boolean quitCommandLoop = false;
    
    /**
     * Create new worker with given client socket
     * @param client the socket for the current client
     * @param dataPort the port for the data connection
     */
	public MyFTP_Server_Worker(Socket client, int dataPort){
        super();
        this.controlSocket = client;
        this.dataPort = dataPort;
        // Get the home directory
        this.currDirectory = System.getProperty("user.dir") + "/Ftp_server";
        this.root = System.getProperty("user.dir");
        debugOutput("Construct successfully");
    }
	/**
     * Run method required by Java thread model
     */

    public void run(){
    	try{
            // Input from client
            controlIn = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
            
            // Output to client, automatically flushed after each print
            controlOutWriter = new PrintWriter(controlSocket.getOutputStream(), true);
            
            // Greeting
            sendMsgToClient("220 Welcome to the FTP-Server-VH");
            
            // Get new command from client
            while (!quitCommandLoop){
            	String command = controlIn.readLine();
            	System.out.println("Command: "+command);
            	if (command == "dung") {
            		
            	}
            	else {
                executeCommand(command);
            	}
            }
                        
        }catch (Exception e){
            e.printStackTrace();
        }finally{ 
            // Clean up 
            try{                    
                controlIn.close(); 
                controlOutWriter.close(); 
                controlSocket.close(); 
                debugOutput("Sockets closed and worker stopped"); 
            }catch(IOException e){ 
                e.printStackTrace();
                debugOutput("Could not close sockets");
            } 
        }     
    }
    
    /**
     * Main command dispatcher method.
     * Separates the command from the arguments and dispatches it to single handler functions.
     * @param c the raw input from the socket consisting of command and arguments
     */
    private void executeCommand(String c){
    	
    	debugOutput("Excute Command "+c);
        // split command and arguments
        int index = c.indexOf('/');
        String user = ((index == -1)? c : (c.substring(0, index)));
        String pass = ((index == -1)? null : c.substring(index+1, c.length()));
        int index1 = c.indexOf(' ');
        String command1 = ((index1 == -1)? user.toUpperCase() : (user.substring(0, index1)).toUpperCase());
        String args1 = ((index1 == -1)? null : user.substring(index1+1, user.length()));
        String command2 = ((index1 == -1)? pass.toUpperCase() : (pass.substring(0, index1)).toUpperCase());
        String args2 = ((index1 == -1)? null : pass.substring(index1+1, pass.length()));
        debugOutput("Command1: " + command1 + " Args1: " + args1 + "\nCommand2: " + command2 + " Args2: " + args2);
        debugOutput(user + " " +pass);
        // dispatcher mechanism for different commands
        switch(command1) {
            case "USER":                
                handleUser(args1);
                break;
        }
        switch(command2) {
            case "PASS":
                handlePass(args2);
                break;
        }
        //##########################
        /*if (currentUserStatus != userStatus.LOGGEDIN) {
    		sendMsgToClient("You must loggin first");
    		return;
    	}*/
        /*switch(command)  {   
            case "CWD":
                handleCwd(args);
                break;
                
            case "LIST":
                handleNlst(args);
                break;
                
            case "NLST":
                handleNlst(args);
                break;
                
            case "PWD":
                handlePwd();
                break;
                
            case "QUIT":
                handleQuit();
                break;
                
            case "PASV":
                handlePasv();
                break;
                
            case "SYST":
                handleSyst();
                break;
                
            case "PORT":
                handlePort(args);
                break;
            
            case "RETR":
                handleRetr(args);
                break;
                
            case "MKD":
                handleMkd(args);
                break;
                
            case "RMD":
                handleRmd(args);
                break;
            
            case "TYPE":
                handleType(args);
                break;
                
            case "STOR":
                handleStor(args);
                break;
                
            default:
                sendMsgToClient("501 Unknown command");
                break;
            
        }*/

    }
    
    /**
     * Sends a message to the connected client over the control connection.
     * Flushing is automatically performed by the stream.
     * @param msg The message that will be sent
     */
	private void sendMsgToClient(String msg) {
		controlOutWriter.println(msg);
		
	}
	
	/**
     * Send a message to the connected client over the data connection.
     * @param msg Message to be sent
     */
    private void sendDataMsgToClient(String msg){
        if (dataConnection == null || dataConnection.isClosed()){
        	sendMsgToClient("425 No data connection was established");
            debugOutput("Cannot send message, because no data connection is established");
        }
        else{
            dataOutWriter.print(msg + '\r' + '\n');
        }   
    }
    
    /**
     * Open a new data connection socket and wait for new incoming connection from client.
     * Used for passive mode.
     * @param port Port on which to listen for new incoming connection
     */
    private void openDataConnectionPassive(int port){
        try{
            dataSocket = new ServerSocket(port);
            dataConnection = dataSocket.accept();
            dataOutWriter = new PrintWriter(dataConnection.getOutputStream(), true);
            debugOutput("Data connection - Passive Mode - established");
        }catch(IOException e){
            debugOutput("Could not create data connection.");
            e.printStackTrace();
        }

    }
    
    /**
     * Connect to client socket for data connection.
     * Used for active mode.
     * @param ipAddress Client IP address to connect to
     * @param port Client port to connect to
     */
    private void openDataConnectionActive(String ipAddress, int port){
        try{
            dataConnection = new Socket(ipAddress, port);
            dataOutWriter = new PrintWriter(dataConnection.getOutputStream(), true);
            debugOutput("Data connection - Active Mode - established");
        }catch (IOException e){
            debugOutput("Could not connect to client data socket");
            e.printStackTrace();
        }

    }
    
    /**
     * Close previously established data connection sockets and streams
     */
    private void closeDataConnection(){
        try{
            dataOutWriter.close();
            dataConnection.close();
            if (dataSocket != null){
                dataSocket.close();
            }
            debugOutput("Data connection was closed");
        }catch (IOException e){
            debugOutput("Could not close data connection");
            e.printStackTrace();
        }
        dataOutWriter = null;
        dataConnection = null;
        dataSocket = null;          
    }
    
    /**
     * Debug output to the console. Also includes the Thread ID for better readability.
     * @param msg Debug message
     */
	private void debugOutput(String msg) {
		if (debugMode){
            System.out.println("Thread " + this.getId() + ": " + msg);
        }
		
	}
	
	/**
     * ##################################################
     * 			HANDLER FUNCTION of FTP command 
     * ##################################################
     */
    
    /**
     * Handler for USER command.
     * User identifies the client.
     * @param username Username entered by the user
     */
    private void handleUser(String username){
    	if (username == null) {
    		sendMsgToClient("USER command must have an argument");
    		return;
    	}
        if (username.toLowerCase().equals(validUser)){
            sendMsgToClient("331 User name okay, need password");
            currentUserStatus = userStatus.ENTEREDUSERNAME;
        }
        else if (currentUserStatus == userStatus.LOGGEDIN){
            sendMsgToClient("530 User already logged in");
        }
        else{
            sendMsgToClient("530 Not logged in");
        }
    }
    
    /**
     * Handler for PASS command.
     * PASS receives the user password and checks if it's valid.
     * @param password Password entered by the user
     */
    private void handlePass(String password){
        // User has entered a valid username and password is correct
        if (currentUserStatus == userStatus.ENTEREDUSERNAME && password.equals(validPassword)){
            currentUserStatus = userStatus.LOGGEDIN;
            sendMsgToClient("230-Welcome ");
            sendMsgToClient("230 User logged in successfully");  
        }       
        // User is already logged in
        else if (currentUserStatus == userStatus.LOGGEDIN){
            sendMsgToClient("530 User already logged in");
        }    
        // Wrong password
        else{
            sendMsgToClient("530 Not logged in");
        }
    }

    /**
     * Handler for CWD (change working directory) command.
     * @param args New directory to be created
     */
    private void handleCwd(String args){
        String filename = currDirectory;
 
        // go one level up (cd ..)
        if (args.equals("..")){
            int ind = filename.lastIndexOf(fileSeparator);
            if (ind > 0){
                filename = filename.substring(0, ind);
            }
        }
        // if argument is anything else (cd . does nothing)
        else if ((args != null) && (!args.equals("."))){
            filename = filename + fileSeparator + args;
        }
    
        // check if file exists, is directory and is not above root directory
        File f = new File(filename);
    
        if (f.exists() && f.isDirectory() && (filename.length() >= root.length())){
            currDirectory = filename;
            sendMsgToClient("250 The current directory has been changed to " + currDirectory);
        }
        else{
            sendMsgToClient("550 Requested action not taken. File unavailable.");
        }
    }

    /**
     * Handler for NLST (Named List) command.
     * Lists the directory content in a short format (names only)
     * @param args The directory to be listed
     */
    private void handleNlst(String args){
        if (dataConnection == null || dataConnection.isClosed()){
            sendMsgToClient("425 No data connection was established");
        }
        else{
            String[] dirContent = nlstHelper(args);            
            if (dirContent == null){
                sendMsgToClient("550 File does not exist.");
            }
            else{
                sendMsgToClient("125 Opening ASCII mode data connection for file list.");
                for (int i = 0; i < dirContent.length; i++){
                    sendDataMsgToClient(dirContent[i]);
                }
                sendMsgToClient("226 Transfer complete.");
                closeDataConnection();        
            }    
        }
    }
    
    /**
     * A helper for the NLST command. The directory name is obtained by 
     * appending "args" to the current directory
     * @param args The directory to list
     * @return an array containing names of files in a directory. If the given
     * name is that of a file, then return an array containing only one element
     * (this name). If the file or directory does not exist, return null.
     */
    private String[] nlstHelper(String args){
        // Construct the name of the directory to list.
        String filename = currDirectory;
        if (args != null){
            filename = filename + fileSeparator + args;
        }
    
        // Now get a File object, and see if the name we got exists and is a directory.
        File f = new File(filename);
            
        if (f.exists() && f.isDirectory()){
            return f.list();
        }
        else if (f.exists() && f.isFile()){
            String[] allFiles = new String[1];
            allFiles[0] = f.getName();
            return allFiles;
        }
        else{
            return null;
        }
    }

    /**
     * Handler for the PORT command.
     * The client issues a PORT command to the server in active mode, so the
     * server can open a data connection to the client through the given address
     * and port number.
     * @param args The first four segments (separated by comma) are the IP address.
     *        The last two segments encode the port number (port = seg1*256 + seg2)
     */
    private void handlePort(String args){
        // Extract IP address and port number from arguments
        String[] stringSplit = args.split(",");
        String hostName = stringSplit[0] + "." + stringSplit[1] + "." + 
                stringSplit[2] + "." + stringSplit[3];
    
        int p = Integer.parseInt(stringSplit[4])*256 + Integer.parseInt(stringSplit[5]);
        
        // Initiate data connection to client
        openDataConnectionActive(hostName, p);
        sendMsgToClient("200 Command OK");
        
    }

    /**
     * Handler for PWD (Print working directory) command.
     * Returns the path of the current directory back to the client.
     */
    private void handlePwd(){
        sendMsgToClient("257 \"" + currDirectory + "\"");
    }
    
    /**
     * Handler for PASV command which initiates the passive mode.
     * In passive mode the client initiates the data connection to the server.
     * In active mode the server initiates the data connection to the client.
     */
    private void handlePasv(){
        // Using fixed IP for connections on the same machine
        // For usage on separate hosts, we'd need to get the local IP address from somewhere
        // Java sockets did not offer a good method for this
    	InetAddress myHost;
		try {
			myHost = InetAddress.getLocalHost();		
	        //String myIp = "127.0.0.1";
			String myIp = String.valueOf(myHost.getHostAddress());
	        String myIpSplit[] = myIp.split("\\.");
	        
	        int p1 = dataPort/256;
	        int p2 = dataPort%256;
	        
	        sendMsgToClient("227 Entering Passive Mode ("+ myIpSplit[0] +"," + myIpSplit[1] + "," + myIpSplit[2] + "," + myIpSplit[3] + "," + p1 + "," + p2 +")");
	        
	        openDataConnectionPassive(dataPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}		
    }

    /**
     * Handler for the QUIT command.
     */
    private void handleQuit(){
        sendMsgToClient("221 Closing connection");
        quitCommandLoop = true;
    }
    
    /**
     * Identifies the operating system at the server.
     */
    private void handleSyst(){
        sendMsgToClient("215 FTP Server Homebrew");
    }
    
    /**
     * Handler for the MKD (make directory) command.
     * Creates a new directory on the server.
     * @param args Directory name
     */
    private void handleMkd(String args){
        // Allow only alphanumeric characters
        if (args != null && args.matches("^[a-zA-Z0-9]+$")){
            File dir = new File(currDirectory + fileSeparator + args);
            if(!dir.mkdir()){
                sendMsgToClient("550 Failed to create new directory");
                debugOutput("Failed to create new directory");
            }
            else{
                sendMsgToClient("250 Directory successfully created");
            }
        }
        else{
            sendMsgToClient("550 Invalid name");
        }     
    }

    /**
     * Handler for RMD (remove directory) command.
     * Removes a directory.
     * @param dir directory to be deleted.
     */
    private void handleRmd(String dir){
        String filename = currDirectory;
        
        // only alphanumeric folder names are allowed
        if (dir != null && dir.matches("^[a-zA-Z0-9]+$")){
            filename = filename + fileSeparator + dir;
            
            // check if file exists, is directory
            File d = new File(filename);       
            if (d.exists() && d.isDirectory()){
                d.delete();
                sendMsgToClient("250 Directory was successfully removed");
            }
            else{
                sendMsgToClient("550 Requested action not taken. File unavailable.");
            }
        }
        else{
            sendMsgToClient("550 Invalid file name.");
        }          
    }

    /**
     * Handler for the TYPE command.
     * The type command sets the transfer mode to either binary or ascii mode
     * @param mode Transfer mode: "a" for Ascii. "i" for image/binary.
     */
    private void handleType(String mode){
        if(mode.toUpperCase().equals("A")){
            transferMode = transferType.ASCII;
            sendMsgToClient("200 OK");
        }
        else if(mode.toUpperCase().equals("I")){
            transferMode = transferType.BINARY;
            sendMsgToClient("200 OK");
        }
        else
            sendMsgToClient("504 Not OK");
            
    }

    /**
     * Handler for the RETR (retrieve) command.
     * Retrieve transfers a file from the ftp server to the client.
     * @param file The file to transfer to the user
     */
    private void handleRetr(String file){
        File f =  new File(currDirectory + fileSeparator + file);
        if(!f.exists()){
            sendMsgToClient("550 File does not exist");
            return;
        }
        // Binary mode
        if (transferMode == transferType.BINARY){
            BufferedOutputStream fout = null;
            BufferedInputStream fin = null;
            
            sendMsgToClient("150 Opening binary mode data connection for requested file " + f.getName());
            
            try{
                //create streams
                fout = new BufferedOutputStream(dataConnection.getOutputStream());
                fin = new BufferedInputStream(new FileInputStream(f));
            }
            catch (Exception e){
                debugOutput("Could not create file streams");
            }
                
            debugOutput("Starting file transmission of " + f.getName());
            
            // write file with buffer
            byte[] buf = new byte[1024];
            int l = 0;
            try{
                while ((l = fin.read(buf,0,1024)) != -1){
                    fout.write(buf,0,l);
                }
            }catch (IOException e){
                debugOutput("Could not read from or write to file streams");
                e.printStackTrace();
            }
            
            //close streams
            try{
                fin.close();
                fout.close();
            }catch (IOException e){
                debugOutput("Could not close file streams");
                e.printStackTrace();
            }
                           
            debugOutput("Completed file transmission of " + f.getName());

            sendMsgToClient("226 File transfer successful. Closing data connection.");
        }
        
        // ASCII mode
        else{
            sendMsgToClient("150 Opening ASCII mode data connection for requested file " + f.getName());

            BufferedReader rin = null;
            PrintWriter rout = null;
            
            try{
                rin = new BufferedReader(new FileReader(f));
                rout = new PrintWriter(dataConnection.getOutputStream(),true);           
            }catch (IOException e){
                debugOutput("Could not create file streams");
            }
            
            String s;
            
            try{
                while((s = rin.readLine()) != null){
                	debugOutput("Read line rout: "+s);
                    rout.println(s);
                }
            }catch (IOException e){
                debugOutput("Could not read from or write to file streams");
                e.printStackTrace();
            }           
            try{
                rout.close();
                rin.close();
            }catch (IOException e){
                debugOutput("Could not close file streams");
                e.printStackTrace();
            }
            sendMsgToClient("226 File transfer successful. Closing data connection.");
        }
    closeDataConnection();   
    }

    /**
     * Handler for STOR (Store) command.
     * Store receives a file from the client and saves it to the ftp server.
     * @param file The file that the user wants to store on the server
     */
    private void handleStor(String file){
        if (file == null){
            sendMsgToClient("501 No filename given");
        }
        else{
            File f =  new File(currDirectory + fileSeparator + file);
            if(f.exists()){
                sendMsgToClient("550 File already exists");
            }
            else{
                // Binary mode
                if (transferMode == transferType.BINARY){
                    
                	BufferedOutputStream fout = null;
                    BufferedInputStream fin = null;
                    sendMsgToClient("150 Opening binary mode data connection for requested file " + f.getName());

                    try{
                        // create streams
                        fout = new BufferedOutputStream(new FileOutputStream(f));
                        fin = new BufferedInputStream(dataConnection.getInputStream());
                    }
                    catch (Exception e){
                        debugOutput("Could not create file streams");
                        e.printStackTrace();
                    }

                    debugOutput("Start receiving file " + f.getName());

                    // write file with buffer
                    byte[] buf = new byte[1024];
                    int l = 0;
                    try{
                        while ((l = fin.read(buf,0,1024)) != -1){
                        	debugOutput("Writing file by Binary mode");
                            fout.write(buf,0,l);
                        }
                    }
                    catch (IOException e){
                        debugOutput("Could not read from or write to file streams");
                        e.printStackTrace();
                    }

                    //close streams
                    try{
                        fin.close();
                        fout.close();
                    }catch (IOException e){
                        debugOutput("Could not close file streams");
                        e.printStackTrace();
                    }

                    debugOutput("Completed receiving file " + f.getName());
                    sendMsgToClient("226 File transfer successful. Closing data connection.");
                }
                // ASCII mode
                else{
                    sendMsgToClient("150 Opening ASCII mode data connection for requested file " + f.getName());

                    BufferedReader rin = null;
                    PrintWriter rout = null;
                    try{
                        rin = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
                        rout = new PrintWriter(new FileOutputStream(f),true);
                    }
                    catch (IOException e){
                        debugOutput("Could not create file streams");
                    }
                 
                    debugOutput("OK file ...");
                    try{
                    	String s;
                        while((s = rin.readLine()) != null){
                        	debugOutput("OK file lala ...");
                            rout.println(s);
                            debugOutput("Writing file ...");
                        }
                    }catch (IOException e){
                        debugOutput("Could not read from or write to file streams");
                        e.printStackTrace();
                    }
                    try{
                        rout.close();
                        rin.close();
                    }catch (IOException e){
                        debugOutput("Could not close file streams");
                        e.printStackTrace();
                    }
                    debugOutput("File transfer successful");
                    sendMsgToClient("226 File transfer successful. Closing data connection.");
                }
            }
            closeDataConnection();
        }

    }
}
