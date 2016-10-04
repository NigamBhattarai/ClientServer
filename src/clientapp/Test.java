/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author anmol
 */
public class Test extends JFrame implements ActionListener{
    private JTextArea displayArea;
    private JTextField enterField;
    private JButton send;
    JPanel pan;
    private JLabel name;
    private DatagramSocket socket;
    private JTextField addField;
    String names;
    private JButton addButton;
    JFrame addingFrame;
    private String ip;
    public Test()
    {
        super("Chatting");
        pan = new JPanel();
        addField = new JTextField();
        addButton = new JButton("Set IP");
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        enterField = new JTextField();
        addButton.addActionListener(this);
        enterField.setPreferredSize(new Dimension(400,40));
        addField.setPreferredSize(new Dimension(300,30));
        pan.add(addField);
        pan.add(addButton);
        this.add(pan,BorderLayout.NORTH);
        this.add(new JScrollPane(displayArea),BorderLayout.CENTER);
        this.add(enterField,BorderLayout.SOUTH);
        addButton.addActionListener(this);
        enterField.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String message=e.getActionCommand();
                    displayArea.append("Me: "+message+"\n");
                    enterField.setText("");
                    byte[] data=message.getBytes();
                    System.out.println(ip);
                    DatagramPacket sendPacket=new DatagramPacket(data,data.length,InetAddress.getByName(ip),5000);
                    socket.send(sendPacket);
                            
                            } catch (IOException ex) {
                                displayArea.append(ex+"\n");
                                ex.printStackTrace();
                             }
               
            }
        });
        this.setSize(400, 300);
        this.setLocation(new Point(700,450));
        this.setVisible(true);
        try {
            socket=new DatagramSocket(5000);
        } catch (SocketException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        
    }
public void waitForPackets()
{
    while(true)
    {
        try {
            byte[] data=new byte[100];
            DatagramPacket recievePacket=new DatagramPacket(data,data.length);
            socket.receive(recievePacket);
            displayMessage("\n"+names+": "+new String(recievePacket.getData())+"\n");
        } catch (IOException ex) {
            displayArea.append(ex +"\n");
            ex.printStackTrace();
        }
    }
}
private void displayMessage(final String messageToDisplay)
{
    SwingUtilities.invokeLater(
            new Runnable()
            {
                public void run()
                {
                    displayArea.append(messageToDisplay);
                }
            }
    );
}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addButton)
        {
            ip = addField.getText();
            JFrame addingFrame = new JFrame("Add");
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(1,1));
            JLabel label = new JLabel("Enter Name:");
            JTextField field = new JTextField();
            panel.add(label);
            panel.add(field);
            addingFrame.add(panel);
            addingFrame.setSize(300,100);
            addingFrame.setResizable(false);
            addingFrame.setLocation(new Point(750,550));
            addingFrame.setVisible(true);
            field.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    names=field.getText();
                    addingFrame.dispose();
                }                
            });
        }
    }
}