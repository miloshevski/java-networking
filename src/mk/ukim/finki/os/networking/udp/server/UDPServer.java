package mk.ukim.finki.os.networking.udp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer extends Thread{
    private DatagramSocket socket;

    private byte[] buf = new byte[256];

    public UDPServer(int port){
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e){
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        while(true){
            try{
                socket.receive(packet);
                String recieving = new String(packet.getData(),0,packet.getLength());
                System.out.printf("RECIEVED:" + recieving);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                packet = new DatagramPacket(buf, buf.length, address,port);
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        UDPServer server = new UDPServer(12345);
        server.start();
    }
}
