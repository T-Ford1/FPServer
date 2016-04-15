
import java.io.*;
import java.net.Socket;

/**
 *
 * @author ford.terrell
 */
public class Client extends Thread {

    private final ClientData cs;
    private final PrintWriter out;
    private final BufferedReader in;

    public Client(Socket s) throws IOException {
        out = new PrintWriter(s.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        cs = init();
        setName(cs.getName());
    }

    private ClientData init() {
        String name = "";
        int id = 0;
        try {
            //setting up the client info
            id = Integer.parseInt(in.readLine());
            name = in.readLine();
            if (id == -1) {
                id = ClientData.getNewID();
                out.println(id);
            }
        } catch (Exception ex) {
            Main.appendServer("error setting up client data\nconnection terminated");
            close();
        }
        return new ClientData(id, name);
    }

    public PrintWriter getWriter() {
        return out;
    }

    public final void output(String message) {
        out.println(message);
    }

    public final void append(String message) {
        output("0 " + message);
    }

    public void run() {
        try {
            while (true) {
                String input = in.readLine();
                if (input != null) {
                    Main.parseInput(input);
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            out.println("You were disconnected.");
            close();
        }
    }

    public void close() {
        try {
            Main.removeClient(this);
            out.close();
            in.close();
            cs.close();
        } catch (Exception e) {
        }
    }

    public String toString() {
        return cs.toString();
    }
}
