package org.jvmlthread.javamultithreading;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MyClient {
    int port = 3124;
    InetAddress ip = null;
    public void ClientStart() {
        ServerSocket ss;
        Socket cs;
        InputStream is;
        OutputStream os;
        DataInputStream dis;
        DataOutputStream dos;
        try {
            ip = InetAddress.getLocalHost();
            cs = new Socket(ip, port);
            System.out.append("Client start\n");
        }catch (IOException ex) {
            System.out.println("Error");

        }
    }

    public static void main(String[] args) {
        new MyClient().ClientStart();
    }
}
