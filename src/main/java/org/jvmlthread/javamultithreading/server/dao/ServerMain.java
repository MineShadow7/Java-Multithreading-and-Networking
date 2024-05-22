package org.jvmlthread.javamultithreading.server.dao;

public class ServerMain {
    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(4000);
    }
}
