package com.abderrahmane.core;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private String address;
    private int port;
    private ConnectionPool pool;
    private final int THREADS_NUMBER = 4;

    public Server (String address, int port) {
        this.address = address;
        this.port = port;
        this.pool = new ConnectionPool();
    }

    public void start () {
        // Create server socket
        ServerSocket serverSocket = this.createServerSocket();
        int connectionCount = 0;

        if (serverSocket == null) System.exit(1);

        initThreads(THREADS_NUMBER);
        
        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();

                pool.add(new Connection(clientSocket, connectionCount % THREADS_NUMBER));
                connectionCount++;
            } catch (IOException ex) {
                System.err.println("[ERROR] " + ex.getMessage());
            }
        }
    }

    private ServerSocket createServerSocket () {
        try {
            ServerSocket serverSocket = new ServerSocket();
            InetSocketAddress socketAddress = new InetSocketAddress(this.address, this.port);

            serverSocket.bind(socketAddress);

            return serverSocket;
        } catch (IOException ex) {
            System.err.println("[ERROR] " + ex.getMessage());
        }

        return null;
    }

    private void initThreads (int count) {
        for (int i = 0; i < count; i++) {
            ThreadHandler handler = new ThreadHandler(this.pool, i);
            handler.start();
        }
    }
}
