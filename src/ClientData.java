
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ford.terrell
 */
public class ClientData {

    private final int id;
    private final String name;
    private boolean online;

    public static ArrayList<ClientData> clients;

    public static void init() {
        clients = new ArrayList<>();
        try {
            Scanner in = new Scanner(new File("clients.dat"));
            while (in.hasNextLine()) {
                clients.add(new ClientData(Integer.parseInt(in.nextLine()), in.nextLine()));
            }
        } catch (FileNotFoundException ex) {
        }
        for(ClientData c : clients) {
            System.out.println(c);
        }
    }

    public static void exit() {
        try (PrintWriter pr = new PrintWriter("clients.dat")) {
            for (int i = 0; i < clients.size() - 1; i++) {
                pr.println(clients.get(i).id);
                pr.println(clients.get(i).name);
            }
            if(!clients.isEmpty()) {
                pr.println(clients.get(clients.size() - 1).id);
                pr.print(clients.get(clients.size() - 1).name);
            }
        } catch(FileNotFoundException ex) {
            
        }
        clients.clear();
    }

    public static int getNewID() {
        return clients.size();
    }

    public void close() {
        online = false;
    }

    public ClientData(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean idEquals(Integer id) {
        return this.id == id;
    }

    boolean nameEquals(String s) {
        return s.equals(name);
    }

    public ClientData getClient(String name) {
        for (ClientData c : clients) {
            if (c.nameEquals(name)) {
                return c;
            }
        }
        return null;
    }

    public ClientData getClient(Integer id) {
        int low = 0, high = clients.size() - 1;
        while (low < high) {
            int index = (low = high) / 2;
            ClientData c = clients.get(index);
            if (c.id > id) {
                high = index;
            } else if (c.id < id) {
                low = index;
            } else {
                return c;
            }
        }
        return null;
    }

    public static void addClient(ClientData c) {
        for (int i = c.id; i < clients.size(); i++) {
            if (clients.get(i).id >= c.id) {
                if (clients.get(i).id == c.id) {
                    clients.set(i, c);
                } else {
                    clients.add(i, c);
                }
                c.online = true;
                break;
            }
        }
    }

    public String toString() {
        return name + ", " + id;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }
}
