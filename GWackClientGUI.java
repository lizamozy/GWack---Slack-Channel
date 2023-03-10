//import javax.swing.GridLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class GWackClientGUI{

    private static Socket s;
    private static PrintWriter out;
    public static void main(String args[]){

        JFrame gwackMainPageFrame = new JFrame();
        gwackMainPageFrame.setLayout(new BorderLayout(15, 15));
        gwackMainPageFrame.setBackground(new Color(210, 216, 250));
        gwackMainPageFrame.setTitle("GWack - Disconnected");
        gwackMainPageFrame.setSize(3000, 1000);
        gwackMainPageFrame.setLocation(50, 50);
                  
        //upper layout for the  Home page GUI
        JPanel mainNorth = new JPanel();

        JTextField  addName = new JTextField("", 10);
        mainNorth.add(new JLabel("User Name:"));
            //need to make actual box where name appears and user can input
        mainNorth.add(addName);

         //make a place for IP Adress
         JTextArea  addIP = new JTextArea(1, 10);
         mainNorth.add(new JLabel("IP Adress:"));
         //addIP.setPreferredSize(new Dimension(10, 5));
         //need to make actual box where IP Adress appears
         mainNorth.add(addIP);
 
         //make a place for the user to input port
         JTextArea  addPort = new JTextArea(1, 10);
         mainNorth.add(new JLabel("Port:"));
         mainNorth.add(addPort);
 
         JButton bConnect = new JButton("Connect");
         mainNorth.add(bConnect);
    
        //need to make an instance class of when the button is clicked, the button name chnages to disconnect  and takes user back to sign in pa
        JPanel mainPwestPanel = new JPanel(new BorderLayout());
        mainPwestPanel.setPreferredSize(new Dimension(150,100));
        mainPwestPanel.setBackground(new Color(210, 216, 250));
        //west side of the main
   
        JPanel mainPeastPanel = new JPanel(new BorderLayout());

        //code to make the message box appear
        JTextArea mainMessages = new JTextArea();
        mainMessages.setPreferredSize(new Dimension(80, 600));
        mainPeastPanel.add(new JLabel("Messages:"), BorderLayout.NORTH);
        mainPeastPanel.add(mainMessages, BorderLayout.CENTER);
        mainPeastPanel.setBackground(new Color(210, 216, 250));

        //code to make the members in server appear
        JTextArea members = new JTextArea();
        
        mainPwestPanel.add(new JLabel("Connected Users:"), BorderLayout.NORTH);
        JScrollPane  showMembers = new JScrollPane(members);
        showMembers.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        showMembers.setPreferredSize(new Dimension(5, 680));

        mainPwestPanel.add(showMembers, BorderLayout.SOUTH);

        bConnect.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bConnect.getText().equals("Connect")){
                    try{
                        //need too get name
                        String name = addName.getText();
                        String IP = addIP.getText();
                        Integer port  = Integer.parseInt(addPort.getText());
                        s = new Socket (IP, port);
                        out = new PrintWriter(s.getOutputStream());
                        //
                        out.println("SECRET");
                        out.println("3c3c4ac618656ae32b7f3431e75f7b26b1a14a87");
                        out.println("NAME");
                        out.println(name);
                        out.flush();

                        gwackMainPageFrame.setTitle("GWack - Connected");
                        ClientHandler myClients = new ClientHandler(s, mainMessages, members);
                        myClients.start();
                        //drt everything to be non editable 
                        addName.setEditable(false);
                        addPort.setEditable(false);
                        addIP.setEditable(false);
                        members.setEditable(false);
                        mainMessages.setEditable(false);
                        bConnect.setText("Disconnect");
                    }catch(UnknownHostException e1){
                        //drt the error errroe 
                            //warning messages
                            JOptionPane.showMessageDialog(new JFrame(), "invalid host", "connection error", JOptionPane.WARNING_MESSAGE);
                            System.exit(1);
                    }catch(Exception e2){
                            JOptionPane.showMessageDialog(new JFrame(), "Cannot Connect", "connection error", JOptionPane.WARNING_MESSAGE);

                    }
                }
                else{
                    //change disconnect to connect 
                    bConnect.setText("Connect");
                    //change the title to be GWack -- diconnected
                    gwackMainPageFrame.setTitle("GWack - Disconnected");
                    //want to clear everything
                    mainMessages.setText("");

                    addName.setEditable(true);
                    addPort.setEditable(true);
                    addIP.setEditable(true);
                    members.setEditable(true);
                    addName.setText("");
                    addIP.setText("");
                    addPort.setText("");
                    members.setText("");

                    //closing the socket
                    out.close();
                }
        }
    
    });
   
        JPanel mainPsouthPanel = new JPanel(new BorderLayout());
        //makes the compose box appear 
        JTextArea composeBox = new JTextArea();
        composeBox.setPreferredSize(new Dimension(100, 60));
        mainPsouthPanel.add(new JLabel("Compose:"), BorderLayout.NORTH);
        mainPsouthPanel.add(composeBox);
        // makes the send button apear
        JButton bSend = new JButton("Send");
        mainPsouthPanel.add(bSend, BorderLayout.EAST);
        mainPsouthPanel.setBackground(new Color(162, 174, 245));

        //action listerner for the send buton 
        bSend.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                out.write(composeBox.getText() + "\n");
                out.flush();
                composeBox.setText("");
            }

        });
        
        //action listerner for the enter key
        composeBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyPressed(KeyEvent e){}
            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyChar() == '\n')
                {
                    out.write(composeBox.getText());
                    out.flush();
                    composeBox.setText(""); 
                }
            }

        });

        gwackMainPageFrame.add(mainPwestPanel, BorderLayout.WEST);
        gwackMainPageFrame.add(mainPeastPanel, BorderLayout.CENTER);
        gwackMainPageFrame.add(mainPsouthPanel, BorderLayout.SOUTH);
        gwackMainPageFrame.add(mainNorth, BorderLayout.NORTH);

        gwackMainPageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gwackMainPageFrame.setVisible(true);

        }
       
         
}
