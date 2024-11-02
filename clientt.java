package stream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class clientt {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int port = 8080;
        
        try (Socket socket = new Socket(host, port)) {

            Thread receiveThread = new Thread(() -> {
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String msg;
                    while (true) {
                        msg = input.readLine();
                        if (msg.equalsIgnoreCase("dc")) {

                            System.out.println("Server disconnected.");

                            break;
                        }
                        System.out.println("Server: " + msg);
                        System.out.println("You (Client): ");
                    }
                } catch (IOException e) {
                    System.out.println("Error: " + e);
                }
            });

            Thread sendThread = new Thread(() -> {
                try (PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))){
                    String msg;

                    while (true) { 
                        System.out.println("You (Client): ");

                        msg = consoleInput.readLine();
                        output.println(msg);

                        if (msg.equalsIgnoreCase("dc")) {
                            System.out.println("You disconnected.");

                            break;
                        }
                    }

                    

                } catch (IOException e) {
                    System.out.println("Error: " + e);
                } 
            
            });

            receiveThread.start();
            sendThread.start();

            receiveThread.join();
            sendThread.join();

        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }
}
