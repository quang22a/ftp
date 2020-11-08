package my_ftp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class login extends JFrame implements ActionListener, MouseListener{
	public static void main(String[] args) {
		new login();
	}
	String IpAddress = "192.168.31.151";
	int controlPort = 21;
	private Socket sk ;
	private static JLabel lbUsername, lbPassword, lbPort;
	private static JTextField username, port;
	private static JPasswordField password;
	private static JPanel pnHeader, pnLeft, pnServerReplay, pnTable;
	private static JButton login;
	public login() {
		
		
		this.setTitle("FTP");
		this.setSize(780, 620);
		this.setLayout(null);
		
		pnHeader = new JPanel();
		pnHeader.setBounds(50, 0, 715, 50);
		pnHeader.setBackground(Color.WHITE);
		pnHeader.setLayout(null);
		this.add(pnHeader);
		
		pnLeft = new JPanel();
		pnLeft.setLayout(null);
		pnLeft.setBounds(0, 0, 50, 600);
		pnLeft.setBackground(new Color(0,18,50));
		this.add(pnLeft);
		
		pnServerReplay = new JPanel();
		pnServerReplay.setLayout(null);
		pnServerReplay.setBounds(50, 50, 715, 200);
		pnServerReplay.setBackground(new Color(204,204,204));
		this.add(pnServerReplay);
		
		pnTable = new JPanel();
		pnTable.setLayout(null);
		pnTable.setBounds(50, 250, 715, 350);
		pnTable.setBackground(new Color(32, 47, 90));
		this.add(pnTable);
		
		lbUsername =  new JLabel("USER: ");
		lbUsername.setBounds(175, 15, 35, 25);
		lbUsername.setFont(new java.awt.Font("Century Gothic", 0, 12));
		lbUsername.setForeground(new Color(47,79,79));
		this.add(lbUsername);
		
		username = new JTextField("dlpuser@dlptest.com");
		username.setBounds(210,15, 120, 25);
		username.setFont(new java.awt.Font("Century Gothic", 0, 12));
		username.setForeground(new Color(47,79,79));
		username.setBackground(Color.WHITE);
		this.add(username);
		
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
		
		pnHeader.add(login);
		login.addActionListener(this);
		this.setVisible(true);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource()==login) {
			
		}
	}
}
