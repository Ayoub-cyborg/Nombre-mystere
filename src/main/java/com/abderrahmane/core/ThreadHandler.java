package com.abderrahmane.core;

import java.util.ArrayList;

public class ThreadHandler extends Thread {
    private ConnectionPool pool;
    private int threadId;

    public ThreadHandler (ConnectionPool pool, int threadId) {
        this.pool = pool;
        this.threadId = threadId;
    }

    public void run () {
        while (true) {
            ArrayList<Connection> connections = this.pool.filterByThreadId(this.threadId);

            for (Connection connection: connections) {
                if (!connection.inputHasNext()) continue;

                int answer = connection.getInputNextInt();

                if (answer == connection.mysteryNumber()) {
                    connection.sendResponse(ServerResponse.RESPONSE_CORRECT);
                } else if (answer < connection.mysteryNumber()) {
                    connection.sendResponse(ServerResponse.RESPONSE_UPPER);
                } else if (answer > connection.mysteryNumber()) {
                    connection.sendResponse(ServerResponse.RESPONSE_LOWER);
                }
            }

            this.threadSleep(100);
        }
    }

    private void threadSleep (long miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException ex) {}
    }
}
