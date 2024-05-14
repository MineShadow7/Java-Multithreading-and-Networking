package org.jvmlthread.javamultithreading;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServer {
    int port = 3124;
    InetAddress ip = null;

    public void serverStart() {
        ServerSocket ss;
        Socket cs;
        InputStream is;
        OutputStream os;
        DataInputStream dis;
        DataOutputStream dos;
        try {
            ip = InetAddress.getLocalHost();
            ss = new ServerSocket(port, 0, ip);
            System.out.println("Server started\n");
            cs = ss.accept();
            System.out.println("Client connect (" + cs.getPort() + ")\n");

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void main(String[] args) {
        new MyServer().serverStart();
    }
}
