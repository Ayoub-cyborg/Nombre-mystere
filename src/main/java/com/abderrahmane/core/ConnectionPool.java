package com.abderrahmane.core;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private ArrayList<Connection> connections;
    private ReentrantLock mutext = new ReentrantLock();

    public ConnectionPool () {
        connections = new ArrayList<Connection>();
    }

    public void add (Connection connection) {
        try {
            mutext.lock();
            connections.add(connection);
        } finally {
            mutext.unlock();
        }
    }

    public ArrayList<Connection> filterByThreadId (int threadId) {
        ArrayList<Connection> filtred = new ArrayList<Connection>();

        for (Connection connection : connections) {
            if (connection.threadId == threadId) filtred.add(connection);
        }

        return filtred;
    }
}
