package mk.ukim.finki.os.network.file_calc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private final int port;
    private final String aggregationFile;
    private final String counterFile;

    public Server(int port, String aggregationFile, String counterFile) {
        this.port = port;
        this.aggregationFile = aggregationFile;
        this.counterFile = counterFile;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection from " + socket.getRemoteSocketAddress());
                new Worker(socket, aggregationFile, counterFile).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        Server server = new Server(5005, "aggregation.txt", "counter.bin");
        server.start();
    }
}