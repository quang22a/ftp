package my_ftp;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class test2 extends JFrame {
	private JTextField username, port, hostName ;
	private static JTextField path;
	private JPasswordField password;
	private JButton btnConnect,btnDisconnect,btnDownload,btnUpload, btnDeleteFile, btnAddFolde,btnRefresh,btnRename,btnBack, btnFile;
	private static JTable tableShow;
	private JScrollPane jsc,jSc;
	private JLabel lbPath,lbHostName,lbUsername,lbPassword,lbPort, lbServer, lbClient, lbFile;
	private static JLabel lbThoiGian;
	private JSeparator jse;
	private JPopupMenu menu;
	private JPanel pnHeader,pnLeft,pnServerReplay,pnTable, pnFL,pnFR;
	private static JTextArea statusConnect=new JTextArea();
	private JFrame f;
	static boolean checkConnect;
    private String link = null;
    private String getSizeFile = "";
    public boolean kiemTraFolder = false;
	
	public test2() {
		createAndShow();
	}
	
	public static void main(String[] args) {
		new test2();
	}
	
	public void createAndShow() {
//		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//	    } catch (Exception evt) {
//	    	evt.printStackTrace();
//	    }
		
		
		
		
		
		f = new JFrame();
		f.setLayout(null);
		f.setTitle("FTP - [LE VIET KHANH 17TCLC2]");
		//f.setContentPane(new JLabel(new ImageIcon("./image/4291.jpg")));
		pnHeader = new JPanel();
		pnHeader.setBounds(50, 0, 715, 50);
		pnHeader.setBackground(Color.WHITE);
		pnHeader.setLayout(null);
		
		pnLeft = new JPanel();
		pnLeft.setLayout(null);
		pnLeft.setBounds(0, 0, 50, 600);
		pnLeft.setBackground(new Color(0,18,50));
		
//		pnServerReplay = new JPanel();
//		pnServerReplay.setLayout(null);
//		pnServerReplay.setBounds(50, 50, 715, 200);
//		pnServerReplay.setBackground(new Color(204,204,204));
		
		pnTable = new JPanel();
		pnTable.setLayout(null);
		pnTable.setBounds(50, 250, 715, 350);
		pnTable.setBackground(new Color(32, 47, 90));
		
		lbHostName =  new JLabel("FTP: ");
		lbHostName.setBounds(10, 15, 35, 25);
		lbHostName.setFont(new Font("Century Gothic", 0, 12));
		lbHostName.setForeground(new Color(47,79,79));
		
		hostName = new JTextField("ftp.dlptest.com");
		hostName.setForeground(new Color(47,79,79));
		hostName.setFont(new java.awt.Font("Century Gothic", 0, 12));
		hostName.setBounds(35,15, 120, 25);
		hostName.setBackground(Color.WHITE);
		
		lbUsername =  new JLabel("USER: ");
		lbUsername.setBounds(175, 15, 35, 25);
		lbUsername.setFont(new java.awt.Font("Century Gothic", 0, 12));
		lbUsername.setForeground(new Color(47,79,79));
		
		username = new JTextField("dlpuser@dlptest.com");
		username.setBounds(210,15, 120, 25);
		username.setFont(new java.awt.Font("Century Gothic", 0, 12));
		username.setForeground(new Color(47,79,79));
		username.setBackground(Color.WHITE);
		
		lbPassword =  new JLabel("PASS: ");
		lbPassword.setBounds(350, 15, 40, 25);
		lbPassword.setFont(new java.awt.Font("Century Gothic", 0, 12));
		lbPassword.setForeground(new Color(47,79,79));
		
		password = new JPasswordField("fLDScD4Ynth0p4OJ6bW6qCxjh");
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
		
		jse = new JSeparator();
		jse.setOrientation(javax.swing.SwingConstants.VERTICAL);
		jse.setBounds(650, 0, 5, 50);
		
		/*--------------------------------Button Connection------------------------------------*/
		btnConnect = new JButton(new ImageIcon("./image/network.png"));
		btnConnect.setBounds(660,3, 45, 45);
		
		/*--------------------------------Button Disconnect------------------------------------*/
//		btnDisconnect = new JButton(new ImageIcon("./image/gtk-disconnect.png"));
//		btnDisconnect.setBounds(660, 3, 45, 45);
		
		/*--------------------------------Button Download------------------------------------*/
//		btnDownload = new JButton(new ImageIcon("./image/download02.png"));
//		btnDownload.setPressedIcon(new ImageIcon("./image/download01.png"));
//		btnDownload.setRolloverIcon(new ImageIcon("./image/download01.png"));
//		btnDownload.setBounds(0, 160, 50, 50);
//		btnDownload.setBorderPainted(false); 
//		btnDownload.setContentAreaFilled(false);
		/*--------------------------------Button Upload------------------------------------*/
//		btnUpload = new JButton(new ImageIcon("./image/upload02.png"));
//		btnUpload.setPressedIcon(new ImageIcon("./image/upload01.png"));
//		btnUpload.setRolloverIcon(new ImageIcon("./image/upload01.png"));
//		btnUpload.setBounds(0, 210, 50, 50);
//		btnUpload.setBorderPainted(false); 
//		btnUpload.setContentAreaFilled(false);
		
		/*--------------------------------Button AddFolder------------------------------------*/
//		btnAddFolde = new JButton(new ImageIcon("./image/add02.png"));
//		btnAddFolde.setPressedIcon(new ImageIcon("./image/add01.png"));
//		btnAddFolde.setRolloverIcon(new ImageIcon("./image/add01.png"));
//		btnAddFolde.setBounds(0, 60, 50, 50);
//		btnAddFolde.setBorderPainted(false); 
//		btnAddFolde.setContentAreaFilled(false);
		
		/*--------------------------------Button Delete------------------------------------*/
//		btnDeleteFile = new JButton(new ImageIcon("./image/delete02.png"));
//		btnDeleteFile.setPressedIcon(new ImageIcon("./image/detele01.png"));
//		btnDeleteFile.setRolloverIcon(new ImageIcon("./image/detele01.png"));
//		btnDeleteFile.setBounds(0, 110, 50, 50);
//		btnDeleteFile.setBorderPainted(false); 
//		btnDeleteFile.setContentAreaFilled(false);
		
		/*--------------------------------Button Refresh------------------------------------*/
//		btnRename = new JButton(new ImageIcon("./image/remane02.png"));
//		btnRename.setPressedIcon(new ImageIcon("./image/remane01.png"));
//		btnRename.setRolloverIcon(new ImageIcon("./image/remane01.png"));
//		btnRename.setBounds(0, 260, 50, 50);
//		btnRename.setBorderPainted(false);
//		btnRename.setContentAreaFilled(false);
		
		/*--------------------------------Button Refresh------------------------------------*/
//		btnRefresh = new JButton(new ImageIcon("./image/refresh01.png"));
//		btnRefresh.setPressedIcon(new ImageIcon("./image/refresh02.png"));
//		btnRefresh.setRolloverIcon(new ImageIcon("./image/refresh02.png"));
//		btnRefresh.setBounds(0, 310, 50, 50);
//		btnRefresh.setBorderPainted(false);
//		btnRefresh.setContentAreaFilled(false);
		
		/*--------------------------------Button Back------------------------------------*/
//		btnBack = new JButton(new ImageIcon("./image/left-arrow01.png"));
//		btnBack.setPressedIcon(new ImageIcon("./image/left-arrow.png"));
//		btnBack.setRolloverIcon(new ImageIcon("./image/left-arrow.png"));
//		btnBack.setBounds(5, 5, 16, 16);
//		btnBack.setBorderPainted(false);
//		btnBack.setContentAreaFilled(false);
		
		
		
		/*--------------------------------Panel add Item-----------------------------------*/
		pnHeader.add(lbHostName);
		pnHeader.add(hostName);
		pnHeader.add(lbUsername);
		pnHeader.add(username);
		pnHeader.add(lbPassword);
		pnHeader.add(password);
		pnHeader.add(lbPort);
		pnHeader.add(port);
		pnHeader.add(jse);
		pnHeader.add(btnConnect);
		
//		pnHeader.add(btnDisconnect);
//		pnLeft.add(btnRename);
//		pnLeft.add(btnRefresh);
//		pnLeft.add(btnDownload);
//		pnLeft.add(btnAddFolde);
//		pnLeft.add(btnUpload);
//		pnLeft.add(btnDeleteFile);
		
		/*--------------------------------Status------------------------------------*/
		jSc =  new JScrollPane();
		jSc.setBounds(5, 60, 705, 135);
		jSc.setBackground(Color.WHITE);
		jSc.setBorder(javax.swing.BorderFactory.createTitledBorder("SERVER REPLY"));
		jSc.setViewportView(statusConnect);
		statusConnect.setEditable(false);
		statusConnect.setBackground(new Color(36,47,65));
		statusConnect.setForeground(new Color(204, 204, 204));
		statusConnect.setFont(new java.awt.Font("Consolas", 0, 12));
			
		/*--------------------------------Path------------------------------------*/
        lbPath = new JLabel("PATH: ");
        lbPath.setBounds(5, 300, 50, 20);
        lbPath.setFont(new java.awt.Font("Century Gothic", 0, 12));
        lbPath.setForeground(new java.awt.Color(204, 204, 204));
        
        path = new JTextField();
        path.setBounds(50,300,260,20);
        path.setFont(new java.awt.Font("Consolar", 0, 12));
        path.setEditable(false);
        path.setForeground(new Color(204, 204, 204));
        path.setBackground(new Color(32, 47, 90));
		
		/*--------------------------------Table------------------------------------*/
//		tableShow = new JTable(){
//			// Set icon cho table
//			 public Class getColumnClass(int column){
//	                return getValueAt(0, column).getClass();
//	            }
//		};
//		tableShow.setRowHeight(35);
//		tableShow.setFont(new Font("Consoles", 0, 12));
//		tableShow.setShowVerticalLines(false);
//		tableShow.setModel(new DefaultTableModel( new Object [][] {},new String [] {"Icon","TÃªn file","SLTM","KÃ­ch thÆ°á»›c (byte)","NgÃ y"}){
//			@Override
//		    public boolean isCellEditable(int row, int column) {
//		       //all cells false
//		       return false;
//		    }
//		});
//		
//		tableShow.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		tableShow.getColumnModel().getColumn(0).setPreferredWidth(50);
//		tableShow.getColumnModel().getColumn(1).setPreferredWidth(350);
//		tableShow.getColumnModel().getColumn(2).setPreferredWidth(80);
//		tableShow.getColumnModel().getColumn(3).setPreferredWidth(100);
//		tableShow.getColumnModel().getColumn(4).setPreferredWidth(120);
//	    jsc =  new JScrollPane();
//	    jsc.setBackground(Color.WHITE);
//	    jsc.setBorder(javax.swing.BorderFactory.createTitledBorder("FILE HOST"));
//	    jsc.setForeground(new Color(204, 204, 204));
//		jsc.setBounds(5, 25, 705, 270);
//		jsc.setViewportView(tableShow);
		
		
		/*----------------------------------------------------------------------------------------------*/
		
//		JMenuItem add = new JMenuItem("ThÃªm thÆ° má»¥c");
//		add.setIcon(new ImageIcon("./image/add.png"));
//		JMenuItem delete = new JMenuItem("Delete");
//		delete.setIcon(new ImageIcon("./image/dustbin.png"));
//		JMenuItem download = new JMenuItem("Táº£i xuá»‘ng");
//		download.setIcon(new ImageIcon("./image/download.png"));
//		JMenuItem upload = new JMenuItem("Táº£i lÃªn");
//		upload.setIcon(new ImageIcon("./image/upload.png"));
//		JMenuItem refresh = new JMenuItem("Refresh");
//		refresh.setIcon(new ImageIcon("./image/loading.png"));
//		JMenuItem rename = new JMenuItem("Rename");
//		rename.setIcon(new ImageIcon("./image/rename.png"));
//		menu = new JPopupMenu();
//		menu.add(upload);
//		menu.add(download);
//		menu.add(new JSeparator());
//		menu.add(refresh);
//		menu.add(add);
//		menu.add(delete);
//		menu.add(rename);
		
		/*-----------------------------------------------------------------------------------------------*/
		JLabel lbTieuDe = new JLabel("Ä�á»’ Ã�N CÆ  Sá»ž NGÃ€NH Máº NG");
		lbTieuDe.setFont(new Font("Segoe UI Light", 0, 22));
		lbTieuDe.setForeground(new Color(25,25,112));
		lbTieuDe.setBounds(230, 5,350, 24);
		JLabel lbDeTai = new JLabel("CHÆ¯Æ NG TRÃŒNH  FTP");
		lbDeTai.setFont(new Font("Segoe UI Light", 0, 18));
		lbDeTai.setForeground(new Color(25,25,112));
		lbDeTai.setBounds(290, 30,350, 24);
		/*-----------------------------------------------------------------------------------------------*/
		lbThoiGian = new JLabel();
		lbThoiGian.setBounds(580, 300, 200, 20);
		lbThoiGian.setFont(new java.awt.Font("Century Gothic", 0, 13));
		lbThoiGian.setForeground(new Color(204, 204, 204));
		/*-----------------------------------------------------------------------------------------------*/
		JPanel pnRight = new JPanel();
		pnRight.setBounds(765, 0, 5, 600);
		pnRight.setBackground(new Color(0,18,50));
		
		JLabel lbLogo = new JLabel(new ImageIcon("./image/cnttbk.png"));
		lbLogo.setBounds(0, 0, 50, 50);
		JLabel lbHinhNen = new JLabel(new ImageIcon("./image/world.png"));
		lbHinhNen.setBounds(0, 0, 715,350);
		JLabel lbHinhNen1 = new JLabel(new ImageIcon("./image/connect2.png"));
		lbHinhNen1.setBounds(0, 0, 715,200);
		
		///
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
//		lbFile =  new JLabel("Ten 1");
//		lbFile.setBounds(40, 30, 55, 25);
//		lbFile.setFont(new Font("Century Gothic", 0, 15));
//		lbFile.setForeground(new Color(47,79,79));
		
		///
			
		pnLeft.add(lbLogo);
		pnTable.add(lbThoiGian);
//		pnServerReplay.add(lbDeTai);
//		pnServerReplay.add(lbTieuDe);
//		pnServerReplay.add(jSc);
//		pnServerReplay.add(lbHinhNen1);
//		pnTable.add(btnBack);
//		pnTable.add(jsc);
		pnTable.add(pnFL);
		pnTable.add(pnFR);
		pnFL.add(lbServer);
		pnFL.add(btnFile);
		
		pnFR.add(lbClient);
//		pnTable.add(path);
//		pnTable.add(lbPath);
		pnTable.add(lbHinhNen);
		
		f.add(pnRight);
		f.add(pnLeft);
		f.add(pnHeader);
		f.add(pnTable);
//		f.add(pnServerReplay);
		
		f.setSize(780,620);
		f.setVisible(true);
		
//		btnConnect.addActionListener(this);
//		btnDisconnect.addActionListener(this);
//		tableShow.addMouseListener(this);
//		btnDownload.addActionListener(this);
//		btnAddFolde.addActionListener(this);
//		btnUpload.addActionListener(this);
//		btnDeleteFile.addActionListener(this);
//		btnRefresh.addActionListener(this);
//		btnRename.addActionListener(this);
//		btnBack.addActionListener(this);
		
//		add.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				newFolder.giaoDien();
//				// Táº¡o má»›i folder thÃ¬ truyá»�n path
//				newFolder.setPathNewFolder(path.getText());
//				if(kiemTraFolder){
//					newFolder.setPathNewFolder(link);
//				}
//			}
//		});
//		delete.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				int n= JOptionPane.showConfirmDialog(delete,"Báº¡n cÃ³ cháº¯c cnáº¯n muá»‘n xÃ³a file nÃ y ?","XÃ³a file",JOptionPane.YES_NO_OPTION);
//				deleteFileOrFolder(n);
//			}
//		});
//		refresh.addActionListener(new ActionListener() {	
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				deleteAllRow();
//				showDataWithTable(path.getText());
//			}
//		});
//		download.addActionListener(new ActionListener() {	
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				dow.giaoDien();
//			}
//		});
//		// Chua cap nhap lai bang
//		upload.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				up.giaoDien();
//			}
//		});
//		rename.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				renameFile.giaoDien();
//				renameFile.setPathRenameFile(link);
//			}
//		});
//		setVisableDisconnection();
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
