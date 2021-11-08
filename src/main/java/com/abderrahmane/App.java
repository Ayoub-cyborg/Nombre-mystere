package com.abderrahmane;

import com.abderrahmane.core.Client;
import com.abderrahmane.core.Server;

public class App 
{
    public static void main( String[] args ) {
        if (args.length > 0 && args[0].compareTo("--server") == 0) {
            new Server("127.0.0.1", 6660).start();
            return;
        }

        new Client("127.0.0.1", 6660).start();  
    }
}
