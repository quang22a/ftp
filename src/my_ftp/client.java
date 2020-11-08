package my_ftp;



import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;



import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;



public class client extends JFrame {
   private static final long serialVersionUID = 1L;
   private JPanel contentPane;
   private JLabel lbInputString;
   private JTextField inputString;
   private JButton btnGui;
   private JLabel lbRes;
   private JTextArea res;



   public static void main(String[] args) {
       client frame = new client();
       frame.setLocationRelativeTo(null);
       frame.setVisible(true);
   }



   public client() {
       initComponents();
   }



   private void initComponents() {
       setTitle("Client");
       setResizable(false);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setBounds(100, 100, 822, 500);
       contentPane = new JPanel();
       setContentPane(contentPane);
       contentPane.setLayout(null);
       contentPane.add(getLbInputString());
       contentPane.add(getInputString());
       contentPane.add(getBtnGui());
       contentPane.add(getLbRes());
       contentPane.add(getTextAreaRes());
   }



   public JLabel getLbInputString() {
       if (lbInputString == null) {
           lbInputString = new JLabel("Nhập chuỗi:");
           lbInputString.setFont(new Font("Tahoma", Font.BOLD, 21));
           lbInputString.setBounds(23, 13, 131, 51);
       }
       return lbInputString;
   }



   public JTextField getInputString() {
       if (inputString == null) {
           inputString = new JTextField();
           inputString.setBounds(155, 26, 631, 31);
           inputString.setFont(new Font("Tahoma", Font.PLAIN, 18));
       }
       return inputString;
   }



   public JLabel getLbRes() {
       if (lbRes == null) {
           lbRes = new JLabel("Kết quả trả về từ Server: ");
           lbRes.setFont(new Font("Tahoma", Font.BOLD, 20));
           lbRes.setBounds(22, 60, 300, 51);
       }
       return lbRes;
   }



   public JTextArea getTextAreaRes() {
       if (res == null) {
           res = new JTextArea();
           res.setFont(new Font("Tahoma", Font.PLAIN, 18));
           res.setBounds(22, 110, 770, 270);
           res.setBackground(Color.CYAN);
           res.setLineWrap(true);
       }
       return res;
   }



   public JButton getBtnGui() {
       if (btnGui == null) {
           btnGui = new JButton("Gửi dữ liệu lên Server");
           btnGui.addActionListener(new ActionListener() {



               @SuppressWarnings("resource")
               @Override
               public void actionPerformed(ActionEvent arg0) {
                   String input = inputString.getText();
                   final String serverHost = "localhost";
                   res.setText("");
                   Socket socketOfClient = null;
                   BufferedReader is = null;
                   BufferedWriter os = null;



                   try {
                       socketOfClient = new Socket("10.10.30.210", 6969);
                       os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
                       is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));
                   } catch (UnknownHostException e) {
                       System.out.println("Don't know about host " + serverHost);
                       return;
                   } catch (IOException e) {
                       System.out.println("Couldn't get I/O for the connection to " + serverHost);
                       return;
                   }



                   try {
                       os.write(input);
                       os.newLine();
                       os.flush();
                       os.write("End");
                       os.newLine();
                       os.flush();
                       inputString.setText("");



                       String responseString;
                       while ((responseString = is.readLine()) != null) {
                           if (responseString.indexOf("End") != -1) {
                               break;
                           }
                           res.append(responseString + "\n");
                       }
                       os.close();
                       is.close();
                       
                   } catch (UnknownHostException e) {
                       System.out.println("Trying to connect to unknown host: " + e);
                       return;
                   } catch (IOException e) {
                       System.out.println("IOException:  " + e);
                       return;
                   }



               }
           });
           btnGui.setBackground(Color.BLUE);
           btnGui.setForeground(Color.WHITE);
           btnGui.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
           btnGui.setFont(new Font("Tahoma", Font.BOLD, 20));
           btnGui.setBounds(310, 400, 246, 51);
           btnGui.setFocusPainted(false);
       }
       return btnGui;
   }



}
