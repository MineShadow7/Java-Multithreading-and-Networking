package org.jvmlthread.javamultithreading;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerMain {
    Socket socket;
    ServerMain()
    {
        try {
            socket = new Socket("ya.ru", 80);
            socket.setSoTimeout(2000);
            StringBuilder command = new StringBuilder("GET /index.html HTTP/1.1");
            command.append(System.lineSeparator());
            command.append("Host: ya.ru").append(System.lineSeparator());
            command.append("Connection close").append(System.lineSeparator());
            command.append(System.lineSeparator());

            OutputStream os = socket.getOutputStream();
            os.write(command.toString().getBytes());

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "cp1251"));
            String line = br.readLine();
            while(line != null){
                System.out.println(line);
                try{
                    line = br.readLine();
                }catch (SocketTimeoutException ex){
                    ex.printStackTrace(System.out);
                    break;
                }
            }
            os.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
