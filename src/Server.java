/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author ford.terrell
 */
public class Server extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JTextArea clientList;
    
    private final JTextField textField;
    private final JTextArea messageArea;

    public Server() {
        super("Server");
        textField = new JTextField(40);
        messageArea = new JTextArea(8, 40);
        textField.addActionListener(new InputListener());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                ClientData.exit();
                System.exit(0);
            }
        });
        //
        clientList = new JTextArea(8, 10);
        DefaultCaret b = (DefaultCaret) clientList.getCaret();
        b.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        getContentPane().add(new JScrollPane(clientList), "West");
        //
        DefaultCaret a = (DefaultCaret) messageArea.getCaret();
        a.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        messageArea.setEditable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        getContentPane().add(textField, "North");
        getContentPane().add(new JScrollPane(messageArea), "Center");
        setVisible(true);
        setLocationRelativeTo(null);
        pack();
    }
    
    public void append(String message) {
        messageArea.append(message + "\n");
    }

    private class InputListener implements ActionListener {
        
        public void actionPerformed(ActionEvent ae) {
            Main.parseInput(textField.getText());
            textField.setText("");
        }
    }
    
    public void updateClients() {
        clientList.setText("");
        for (Client name : Main.clients) {
            clientList.append(name.getName() + "\n");
        }
    }
}
