package mk.ukim.finki.os.network.logging_file;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private int port;
    private String filePath;

    public Server(int port, String filePath) {
        this.port = port;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listening on port " + port);

            while (true) {
                Socket socket = null;

                try{
                    socket = serverSocket.accept();
                    new Worker(socket, filePath).start();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Integer serverPort = 8080;
        Server server = new Server(serverPort, "/logfile.txt");
        server.start();
    }
}
