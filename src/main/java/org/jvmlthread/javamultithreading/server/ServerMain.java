package org.jvmlthread.javamultithreading.server;

public class ServerMain {
    public static void main(String[] args) {
        ServerClass server = new ServerClass();
        server.startServer(4000);
    }
}