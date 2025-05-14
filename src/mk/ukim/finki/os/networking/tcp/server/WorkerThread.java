package mk.ukim.finki.os.networking.tcp.server;

import java.io.*;
import java.net.Socket;

public class WorkerThread extends Thread {

    private Socket socket = null;

    public WorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            System.out.printf("Connected:%s:%d\n", socket.getInetAddress(), socket.getPort());

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String line = null;

            while (!(line=reader.readLine()).isEmpty()){
                System.out.println(line);
                writer.write(line);
                writer.flush();
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally{
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (writer != null){
                writer.close();
            }
        }
    }
}
