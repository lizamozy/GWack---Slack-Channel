import java.net.*;
import javax.swing.JTextArea;
import java.io.*;
import javax.swing.*;
    
    public class ClientHandler extends Thread{

        Socket sock;
        JTextArea messages;
        JTextArea users;

        public ClientHandler(Socket sock, JTextArea messages, JTextArea users){
            this.sock=sock;
            this.messages = messages;
            this.users = users;
        }

        public void run(){
            PrintWriter out=null;
            BufferedReader in=null;
            try{
                out = new PrintWriter(sock.getOutputStream());
                in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                //read and echo back forever!
                while(!sock.isClosed()){
                    String msg = in.readLine();
                    if(msg == null) break; //read null, remote closed
                    if(msg.equals("START_CLIENT_LIST")){
                        String clientList = "";
                        msg = in.readLine();

                        while(!msg.equals("END_CLIENT_LIST")){
                            clientList += msg +"\n";
                            msg = in.readLine();
                        }
                        users.setText(clientList);

                    }else{
                        messages.append(msg + "\n");
                    }
                    
                }
                //close the connections
                out.close();
                in.close();
                sock.close();
                
            }catch(Exception e){}

            //note the loss of the connection
            JOptionPane.showMessageDialog(new JFrame(), "Lost Connection", "connection error", JOptionPane.WARNING_MESSAGE);
        }

    }


