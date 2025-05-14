package mk.ukim.finki.os.networking.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {

    private int port;

    public TCPServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("TCP is starting...");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("TCP server could not be started");
            return;
        }
        System.out.println("TCP server started on port " + port);
        System.out.println("Waiting for connection...");
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                new HttpWorkerThread(socket).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        TCPServer server = new TCPServer(9000);
        server.start();
    }
}
