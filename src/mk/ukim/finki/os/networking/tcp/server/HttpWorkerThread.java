package mk.ukim.finki.os.networking.tcp.server;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HttpWorkerThread extends Thread{
    private Socket socket;

    public HttpWorkerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            System.out.println("Connecting to " + socket.getInetAddress());

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String line = null;
            StringBuilder builder = new StringBuilder();

            while(!(line = reader.readLine()).isEmpty()){
                builder.append(line +"\n");
                System.out.println(line);
            }
            RequestProcessor request = RequestProcessor.of(builder.toString());
            writer.write("HTTP/1.1 200 OK\n\n");
            if(request.getCommand().equals("GET") && request.getUri().equals("/time")){
                writer.printf("<html><body><h1>%s</h1></body></html>", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }else{
                writer.printf("<html><body><h1>Hello World</h1></body></html>");
            }
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally{
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
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
