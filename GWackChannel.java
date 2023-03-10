import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;

public class GWackChannel extends GWackClientGUI{
    //make an ancton listener for the connect button adn have all of the stuff print to terminal
     //call the sign in page frame
    public void connectServer(String addIP, String addName, String addPort, String label){
        if(label.equals("Connect")){
            System.out.println(addName);
            System.out.println(addIP);
            System.out.println(addPort);
        }
    }
    

}
