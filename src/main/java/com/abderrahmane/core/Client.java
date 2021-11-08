package com.abderrahmane.core;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private String address;
    private int port;
    private PrintStream output;
    private Scanner input;

    public Client (String address, int port) {
        this.address = address;
        this.port = port;   
    }

    public void start () {
        Scanner input = new Scanner(System.in);
        Socket socket = this.connect();

        if (socket == null) System.exit(1);

        try {
            this.output = new PrintStream(socket.getOutputStream());
            this.input = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("[ERROR] " + ex.getMessage());
            System.exit(1);
        }
        
        while (true) {
            int answer;
            String serverResponse;

            System.out.print("Entrer : ");
            answer = input.nextInt();

            this.output.println(answer);

            serverResponse = this.input.nextLine();

            if (serverResponse.compareTo(ServerResponse.RESPONSE_CORRECT.toString()) == 0) {
                System.out.println("Correct !");
                break;
            }

            if (serverResponse.compareTo(ServerResponse.RESPONSE_LOWER.toString()) == 0) {
                System.out.println("Moins !");
            } else {
                System.out.println("Plus !");
            }
        }
    }

    private Socket connect () {
        try {
            Socket socket = new Socket();
            InetSocketAddress socketAddress = new InetSocketAddress(this.address, this.port);

            socket.connect(socketAddress);

            return socket;
        } catch (IOException ex) {
            System.err.println("[ERROR] " + ex.getMessage());
        }

        return null;
    }
}
