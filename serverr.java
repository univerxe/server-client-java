package stream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
  

public class serverr {
    public static void main(String[] args) throws IOException {
        int port = 8080; // listen on port 8080
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                
                // try {Thread.sleep(100);} catch (InterruptedException e) {
                //     System.out.println("Error: " + e);
                // }

                Thread reconnect = new Thread(() -> {
                    
                    handleClient(clientSocket);

                });
                reconnect.start();
                
            }
        
            // serversocket.accept() waits for a client to connect, then returns a socket object
            // BufferedReader (buffer (BufferedReader) <- character stream (InputStreamReader) <- byte stream)

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
         
    }

    private static void handleClient(Socket clientSocket) {
        try {Thread.sleep(100);} catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }

        Thread receiveThread = new Thread(() -> {
            try(BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String msg;
                while (true) { 
                    msg = input.readLine();
                    if (msg.equalsIgnoreCase("dc")) {
                        System.out.println("Client: " + clientSocket.getInetAddress() + " disconnected.");
                        break;

                        
                    }
                    System.out.println("Client: " + msg);
                    System.out.println("You (Server): ");
            }
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        });

        Thread sendThread = new Thread(() -> {
            try (PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
                String msg;

                while (true) {
                    System.out.println("You (Server): ");
                    
                    msg = consoleInput.readLine();

                    if (msg.equalsIgnoreCase("dc")) {
                        System.out.println("You disconnected.");
                        break;
                    }

                    output.println(msg);

                }

                
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        });

        receiveThread.start();
        sendThread.start(); 
        
        try {
            receiveThread.join();
            sendThread.join();
        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }
}