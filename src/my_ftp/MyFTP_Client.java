package my_ftp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class MyFTP_Client extends Thread implements ActionListener {
	//Server information
	String IpAddress = "192.168.205.1";
	int controlPort = 21;
	
	private JFrame jf;
	// Path information
	private String root;
    private String currDirectory;
    private String fileSeparator = "/";
	private boolean checklogin = false;
	//CONTROL connection
    private Socket controlConnection;
    private PrintWriter controlOutWriter;
    
    //DATA connection
    private ServerSocket dataSocket;
	private Socket dataConnection;
    private PrintWriter dataOutWriter;
    private DataInputStream din;
    private DataOutputStream dout;
    
    private static JLabel lbUsername, lbPassword, lbPort, lbServer, lbClient, lbFile;
	private static JTextField username, port;
	private static JPasswordField password;
	private static JPanel pnHeader, pnLeft, pnServerReplay, pnTable, pnFL, pnFR;
	private static JButton login, btnFile;
	
    private enum transferType {
        ASCII, BINARY
    }
    private transferType transferMode = transferType.ASCII;
	public static void main(String[] args) {
		new MyFTP_Client();
	}
	
	public MyFTP_Client() {
		jf = new JFrame();
		
		jf.setTitle("FTP");
		jf.setSize(780, 620);
		jf.setLayout(null);
		
		pnHeader = new JPanel();
		pnHeader.setBounds(50, 0, 715, 50);
		pnHeader.setBackground(Color.WHITE);
		pnHeader.setLayout(null);
		jf.add(pnHeader);
		
		pnLeft = new JPanel();
		pnLeft.setLayout(null);
		pnLeft.setBounds(0, 0, 50, 600);
		pnLeft.setBackground(new Color(0,18,50));
		jf.add(pnLeft);
		
		pnServerReplay = new JPanel();
		pnServerReplay.setLayout(null);
		pnServerReplay.setBounds(50, 50, 715, 200);
		pnServerReplay.setBackground(new Color(204,204,204));
		jf.add(pnServerReplay);
		
		pnTable = new JPanel();
		pnTable.setLayout(null);
		pnTable.setBounds(50, 250, 715, 350);
		pnTable.setBackground(new Color(32, 47, 90));
		jf.add(pnTable);
		
		lbUsername =  new JLabel("USER: ");
		lbUsername.setBounds(175, 15, 35, 25);
		lbUsername.setFont(new java.awt.Font("Century Gothic", 0, 12));
		lbUsername.setForeground(new Color(47,79,79));
		
		username = new JTextField("tmq");
		username.setBounds(210,15, 120, 25);
		username.setFont(new java.awt.Font("Century Gothic", 0, 12));
		username.setForeground(new Color(47,79,79));
		username.setBackground(Color.WHITE);
		
		lbPassword =  new JLabel("PASS: ");
		lbPassword.setBounds(350, 15, 40, 25);
		lbPassword.setFont(new java.awt.Font("Century Gothic", 0, 12));
		lbPassword.setForeground(new Color(47,79,79));

		password = new JPasswordField("301199");
		password.setBounds(390,15, 120, 25);
		password.setFont(new java.awt.Font("Century Gothic", 0, 12));
		password.setForeground(new Color(47,79,79));
		password.setBackground(Color.WHITE);

		lbPort =  new JLabel("PORT: ");
		lbPort.setBounds(530, 15, 40, 25);
		lbPort.setFont(new java.awt.Font("Century Gothic", 0, 12));
		lbPort.setForeground(new Color(47,79,79));
		
		port = new JTextField("21");
		port.setBounds(580,15, 50, 25);
		port.setForeground(new Color(47,79,79));
		port.setFont(new java.awt.Font("Century Gothic", 0, 12));
		port.setBackground(Color.WHITE);

		
		pnHeader.add(lbUsername);
		pnHeader.add(username);
		pnHeader.add(lbPassword);
		pnHeader.add(password);
		pnHeader.add(lbPort);
		pnHeader.add(port);
		
		login = new JButton();
		login.setText("LOG IN");
		login.setBounds(660, 3, 45, 45);
		login.setFont(new java.awt.Font("Century Gothic", 0, 12));
		
		pnFL = new JPanel();
		pnFL.setLayout(null);
		pnFL.setBounds(0, 0, 715/2, 600);
		pnFL.setBackground(Color.GREEN);
		
		pnFR = new JPanel();
		pnFR.setLayout(null);
		pnFR.setBounds(715/2, 0, 715/2, 600);
		pnFR.setBackground(Color.PINK);
		
		lbServer =  new JLabel("Server");
		lbServer.setBounds(10, 0, 55, 25);
		lbServer.setFont(new Font("Century Gothic", 0, 15));
		lbServer.setForeground(new Color(47,79,79));
		
		lbClient =  new JLabel("Client");
		lbClient.setBounds(10, 0, 55, 25);
		lbClient.setFont(new Font("Century Gothic", 0, 15));
		lbClient.setForeground(new Color(47,79,79));
		
		btnFile = new JButton(new ImageIcon("./image/file.png"));
		btnFile.setBounds(10, 30, 23, 25);
		
//		String[] server = {"Volvo", "BMW", "Ford", "Mazda"};
		
		 File fileOrDirServer = new File("D:\\FTP\\Ftp_server");
		 String[] server = readFile(fileOrDirServer);
        for(int i=0; i<server.length; i++) {
        	System.out.println(server[i]);
        }
        
        File fileOrDirClient = new File("D:\\FTP\\Ftp_client");
		 String[] client = readFile(fileOrDirClient);
       for(int i=0; i<client.length; i++) {
       	System.out.println(client[i]);
       }
		
		for(int i =0; i<server.length; i++) {
			lbFile =  new JLabel(server[i]);
			lbFile.setBounds(40, 30 + i*30, 200, 25);
			lbFile.setFont(new Font("Century Gothic", 0, 15));
			lbFile.setForeground(new Color(47,79,79));
			
			pnFL.add(lbFile);
		}
		
		for(int i =0; i<client.length; i++) {
			lbFile =  new JLabel(client[i]);
			lbFile.setBounds(40, 30 + i*30, 200, 25);
			lbFile.setFont(new Font("Century Gothic", 0, 15));
			lbFile.setForeground(new Color(47,79,79));
			
			pnFR.add(lbFile);
		}
		
		pnHeader.add(login);
		login.addActionListener(this);
		jf.setVisible(true);
		this.currDirectory = System.getProperty("user.dir") + "/Ftp_client";
        this.root = System.getProperty("user.dir");        
        try {
	        InetAddress myHost = InetAddress.getLocalHost();
			debugOutput(myHost.getHostAddress());
        }catch(Exception e) {
        	debugOutput("Cannot get IP Address from InetAddress");
        }
        
        /*openControlConnection();
		this.start();
		try {
			while(true) {			
				Scanner sc = new Scanner(System.in);
				String command = sc.nextLine();
							
				// Output to client, automatically flushed after each print
	            controlOutWriter = new PrintWriter(controlConnection.getOutputStream(), true);
	            controlOutWriter.println(command);
	            System.out.println("Create Control ****************");
	            
	            executeCommand(command);

				//System.out.println("Send to server");
			}		
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Cannot send to server");
		}
		finally {
			closeControlConnection();
		}*/
	}
	public void run() {
		try {
			while(true) {
				BufferedReader controlIn = new BufferedReader(new InputStreamReader(controlConnection.getInputStream()));		
				String result = controlIn.readLine();
				if (result == null) {
					System.out.println("---------Disconnection---------");
					closeControlConnection();
					System.exit(0);
				}
				System.out.println("Getting from server: "+result);	
				checkPassiveConnect(result);
			}
		}catch(Exception e) {
			System.out.println(e);
			System.out.println("Cannot read from server");
		}
	}
	public void openControlConnection() {
		try {
			controlConnection = new Socket(IpAddress,controlPort);
			debugOutput("Open control connection");
		}catch(Exception e) {
			debugOutput("Cannot open Control Connection");
		}
	}
	public void openDataConnectionPassive(int dataPort) {
		try {
			dataConnection = new Socket(IpAddress,dataPort);
			dataOutWriter = new PrintWriter(dataConnection.getOutputStream(), true);
			debugOutput("Open data connection");
		}catch(Exception e) {
			debugOutput("Cannot create data connection");
		}
	}
	public void openDataConnectionActive(int dataPort) {
		try{
			dataSocket = new ServerSocket(dataPort);
			System.out.println("Create Datasocket*******");
            dataConnection = dataSocket.accept();
            System.out.println("Accept Datasocket*******");
            dataOutWriter = new PrintWriter(dataConnection.getOutputStream(), true);
            debugOutput("Open data connection");
        }catch(IOException e){
            debugOutput("Could not create data connection.");
            e.printStackTrace();
        }
	}
	public void closeDataConnection() {
		try {
			dataOutWriter.close();
			dataConnection.close();
		} catch (IOException e) {
			System.out.println("Cannot close data connection");
			e.printStackTrace();
		}
	}
	public void closeControlConnection() {
		try {
			controlConnection.close();
			controlOutWriter.close();
		} catch (IOException e) {
			System.out.println("Cannot close control connection");
			e.printStackTrace();
		}
	}
	private void executeCommand(String c){
    	debugOutput("Excute Command "+c);
        // split command and arguments
        int index = c.indexOf(' ');
        String command = ((index == -1)? c.toUpperCase() : (c.substring(0, index)).toUpperCase());
        String args = ((index == -1)? null : c.substring(index+1, c.length()));

        debugOutput("Command: " + command + " Args: " + args);
        
        // dispatcher mechanism for different commands
        switch(command)  {   
            case "STOR":
            	if (hdlSendFile(args)) {
	            	debugOutput("Send file successfully");
	            }
            	else {
            		debugOutput("Cannot send file");
            	}
                break;
            case "TYPE":
            	handleType(args);
            	break;
            case "PORT":
            	checkActiveConnect(args);
            	break;
            case "RETR":
            	hdlReceiveFile(args);
            	break;
            default:
                debugOutput("501 Unknown command");
                break;
            
        }
    }
	public void checkPassiveConnect(String result) {
		if (result.contains("227 Entering Passive Mode")) {
			//227 Entering Passive Mode (10,10,42,222,19,137)
			String[] stringSplit = result.split("\\(");										
			stringSplit[1] = stringSplit[1].substring(0,stringSplit[1].length()-1);
			String[] numSplit = stringSplit[1].split("\\,");

			int num1 = Integer.parseInt(numSplit[4]);
			int num2 = Integer.parseInt(numSplit[5]);
			
			int dataPort = num1*256+num2;
			openDataConnectionPassive(dataPort);
		}
	}
	private void checkActiveConnect(String result) {
		String[] numSplit = result.split("\\,");

		int num1 = Integer.parseInt(numSplit[4]);
		int num2 = Integer.parseInt(numSplit[5]);
		
		int dataPort = num1*256+num2;
		System.out.println("Create Checkactive*******");
		openDataConnectionActive(dataPort);
		
	}
	private void handleType(String mode){
        if(mode.toUpperCase().equals("A")){
            transferMode = transferType.ASCII;
            debugOutput("Change type ASCII successfully");
        }
        else if(mode.toUpperCase().equals("I")){
            transferMode = transferType.BINARY;
            debugOutput("Change type BINARY successfully");
        }
        else
        	debugOutput("Change type unsuccessfully");
    }
	
	public boolean hdlSendFile(String file) {
		File f =  new File(currDirectory + fileSeparator + file);
        if(!f.exists()){
        	debugOutput("550 File does not exist");
            return false;
        }
        // Binary mode
        if (transferMode == transferType.BINARY){
            BufferedOutputStream fout = null;
            BufferedInputStream fin = null;
            
            debugOutput("150 Opening binary mode data connection for requested file " + f.getName());
            
            try{
                //create streams
                fout = new BufferedOutputStream(dataConnection.getOutputStream());
                fin = new BufferedInputStream(new FileInputStream(f));
            }
            catch (Exception e){
                debugOutput("Could not create file streams");
                return false;
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
                return false;
            }
            
            //close streams
            try{
                fin.close();
                fout.close();
            }catch (IOException e){
                debugOutput("Could not close file streams");
                e.printStackTrace();
                return false;
            }
                           
            debugOutput("Completed file transmission of " + f.getName());
        }
        
        // ASCII mode
        else{
        	debugOutput("Opening ASCII mode data connection for requested file " + f.getName());

            BufferedReader rin = null;
            PrintWriter rout = null;
            
            try{
                rin = new BufferedReader(new FileReader(f));
                rout = new PrintWriter(dataConnection.getOutputStream(),true);           
            }catch (IOException e){
                debugOutput("Could not create file streams");
                return false;
            }
            
            String s;
            
            try{
                while((s = rin.readLine()) != null){
                    rout.println(s);
                }
            }catch (IOException e){
                debugOutput("Could not read from or write to file streams");
                e.printStackTrace();
                return false;
            }           
            try{
                rout.close();
                rin.close();
            }catch (IOException e){
                debugOutput("Could not close file streams");
                e.printStackTrace();
                return false;
            }
            debugOutput("File transfer ASCII successful. Closing data connection.");         
        }
        closeDataConnection();  
        return true;
    }
	public void hdlReceiveFile(String file){
        if (file == null){
            debugOutput("No filename given");
        }
        else{
            File f =  new File(currDirectory + fileSeparator + file);
            if(f.exists()){
            	debugOutput("File already exists");
            }
            else{
                // Binary mode
                if (transferMode == transferType.BINARY){                
                	BufferedOutputStream fout = null;
                    BufferedInputStream fin = null;
                    debugOutput("Opening binary mode data connection for requested file " + f.getName());
                    try{
                        // create streams
                        fout = new BufferedOutputStream(new FileOutputStream(f));
                        fin = new BufferedInputStream(dataConnection.getInputStream());
                    }
                    catch (Exception e){
                        debugOutput("Could not create file streams");
                    }

                    debugOutput("Start receiving file " + f.getName());

                    // write file with buffer
                    byte[] buf = new byte[1024];
                    int l = 0;
                    try{
                        while ((l = fin.read(buf,0,1024)) != -1){
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
                    debugOutput("File transfer successful. Closing data connection.");
                }
                // ASCII mode
                else{
                    debugOutput("Opening ASCII mode data connection for requested file " + f.getName());

                    BufferedReader rin = null;
                    PrintWriter rout = null;
                    try{
                        rin = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
                        rout = new PrintWriter(new FileOutputStream(f),true);
                    }
                    catch (IOException e){
                        debugOutput("Could not create file streams");
                    }
                    String s;
                    try{
                    	
                        while((s = rin.readLine()) != null){
                        	//debugOutput("Read line from file: "+s);
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
                    debugOutput("File transfer successful. Closing data connection.");
                }
            }
            closeDataConnection();
        }
	}
	public void debugOutput(String name) {
    	System.out.println(name);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==login) {
			String user = username.getText();
			String pass = password.getText();
			String command1 = "user "+ user;
			String command2 = "pass " + pass;
			String command = command1 + "/" + command2;
			openControlConnection();
			pnTable.add(pnFL);
			pnTable.add(pnFR);
			this.start();
			try {
				while(true) {			
					//Scanner sc = new Scanner(System.in);
					//String command = sc.nextLine();
					// Output to client, automatically flushed after each print
					if (command=="dung") {
					}
					else {
						checklogin = true;
						username.setText("aaa");
						controlOutWriter = new PrintWriter(controlConnection.getOutputStream(), true);
						controlOutWriter.println(command);
						System.out.println("Create Control ****************");
		            
						executeCommand(command);
					
						//System.out.println("Send to server");
					}
					command = "dung";
					
				}		
			}catch(Exception e1) {
				e1.printStackTrace();
				System.out.println("Cannot send to server");
			}
			finally {
				closeControlConnection();
			}
		}
	}
	public String[] readFile(final File fileOrDir) {
        // check xem fileOrDir la file hay foder
        if (fileOrDir.isDirectory()) {
            // in ten folder ra man hinh
            System.out.println(fileOrDir.getAbsolutePath());
             
            final File[] children = fileOrDir.listFiles();
//            if (children == null) {
//                return;
//            }
//            // sap xep file theo thu tu tang dan
//            Arrays.sort(children, new Comparator<File>() {
//                public int compare(final File o1, final File o2) {
//                    return o1.getName().compareTo(o2.getName());
//                }
//            });
//            for (final File each : children) {
//                // goi lai ham traverseDepthFiles()
//                traverseDepthFiles(each);
//            }
//            System.out.println(children.length);
            String[] file = new String[children.length];
            for(int i=0; i<children.length; i++) {
            	file[i] = children[i].getName();
//            	System.out.println(file[i]);
            }
            
            return file;
        } 
        return null;
//        else {
//            // in ten file ra man hinh
//            System.out.println(fileOrDir.getAbsolutePath());
//        }
    }
}
