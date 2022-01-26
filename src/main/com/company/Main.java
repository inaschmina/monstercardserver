package com.company;

import HttpRequest.RequestHandlerImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(10001)) {

            while (true) {
                Socket client = serverSocket.accept();
                RequestHandlerImpl server = new RequestHandlerImpl(client);
                Thread thread = new Thread(server);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}


