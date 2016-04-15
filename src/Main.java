/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author ford.terrell
 */
public class Main {

    private static final int PORT = 9001;
    private static Server server;
    public static final ArrayList<Client> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket listener = new ServerSocket(PORT);
            server = new Server();
            appendServer("The chat server is running.\nLAN address at --> " + InetAddress.getLocalHost().getHostAddress());
            ClientData.init();
            while (true) {
                addClient(new Client(listener.accept()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeClient(Client c) {
        clients.remove(c);
        server.updateClients();
        append(c.getName() + " DISCONNECTED");
        appendServer(c.getName() + ", ID : " + c.getId() + ", DISCONNECTED");
    }

    public static void addClient(Client c) {
        c.start();
        clients.add(c);
        server.updateClients();
        appendAll(c.getName() + " CONNECTED");
    }

    public static void parseInput(String input) {
        if(input.startsWith("/")) {
            parseCommand(input.substring(1).split(" "));
        } else {
            appendAll(input);
        }
    }
    
    private static void parseCommand(String[] cmd) {
        switch(cmd[0]) {
            case "kick":
                getClient(cmd[1]).close();
                break;
            case "msg":
                getClient(cmd[1]).append(Arrays.toString(cmd));
                break;
        }
    }
    
    public static void appendAll(String message) {
        appendServer(message);
        append(message);
    }

    public static void append(String message) {
        for (Client c : clients) {
            c.append(message);
        }
    }
    
    public static void appendServer(String message) {
        server.append(message);
    }
    
    public static Client getClient(String name) {
        for (Client c : clients) {
            if (c.equals(name)) {
                return c;
            }
        }
        return null;
    }
}
