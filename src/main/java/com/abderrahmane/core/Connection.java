package com.abderrahmane.core;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Connection {
    public int threadId;
    private Socket socket;
    private PrintStream output = null;
    private Scanner input = null;
    private int mysteryNumber;

    public Connection (Socket socket, int threadId) {
        this.socket = socket;
        this.threadId = threadId;

        try {
            this.output = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.err.println("[ERROR] " + ex.getMessage());
        }

        try {
            this.input = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            System.err.println("[ERROR] " + ex.getMessage());
        }

        this.mysteryNumber = (int)(100 * Math.random());
    }

    public boolean inputHasNext () {
        return this.input.hasNext();
    }
    
    public int getInputNextInt () {
        return input.nextInt();
    }

    public void sendResponse (ServerResponse sr) {
        this.output.println(sr);
    }

    public int mysteryNumber () {
        return this.mysteryNumber;
    }
}
