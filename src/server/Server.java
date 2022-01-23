package server;

import directive.MethodManager_Impl;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Server implements Runnable{
    public Socket socket;
    public Server(Socket socket) {
        this.socket = socket;
    }

    public void readRequest() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while(!(line = br.readLine()).isBlank()) {
            requestBuilder.append(line).append("\r\n");
        }

        //handle request
        String request = requestBuilder.toString();
        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String method = requestLine[0];
        String path = requestLine[1];
        String version = requestLine[2];
        String host = requestsLines[1].split(" ")[1];

        //read header
        String username = "";
        List<String> headers = new ArrayList<>(Arrays.asList(requestsLines).subList(2, requestsLines.length));
        if (headers.toString().toLowerCase().contains("authorization")) {
            //substract username
            username = headers.toString().split("Basic ")[1].split("-")[0];
        }

        //get body length
        String body = "";
        if(headers.toString().contains("Content-Length")) {
            String bodyLength = headers.toString().split("Content-Length: ")[1].split("]")[0];
            if(Integer.parseInt(bodyLength) != 0) {
                //read body only if not null
                int contentLength = headers.size();
                StringBuilder bodyBuilder = new StringBuilder(10000);
                char[] buf = new char[1024];
                int totalLen = 0;
                int len;
                while ((len = br.read(buf)) != -1) {
                    bodyBuilder.append(buf, 0, len);
                    totalLen += len;
                    if( totalLen >= contentLength )
                        break;
                }
                body = bodyBuilder.toString();
            }
        }






        //convert body to JSON
        JsonNode node = null;
        ObjectMapper mapper = new ObjectMapper();
        if(!body.equals("")) {
            node = mapper.readTree(body);
        }

        MethodManager_Impl manager = new MethodManager_Impl();
        StringBuilder message = new StringBuilder();
        //call directive handler
        String reply = manager.handleDirective(method, path, node, username);

        //write out response
        message.append(version).append(" ").append("200").append(" ").append("baaaaast").append("\r\n");
        message.append("Host: ").append(host).append("\r\n");
        message.append("Content-type: ").append("application/json").append("\r\n");
        message.append("Content-length: ").append(reply.length()).append("\r\n");
        message.append("\r\n").append(reply).append("\r\n");
        writer.write(message.toString());
        writer.close();



        System.out.println("----------------------\nClient "+ socket.toString() +"\nmethod "+ method +"\npath "+ path +"\nversion "+ version+ "\nhost "+ host +"\nheaders "+ headers.toString() +"\ndata: " + body);
        socket.close();
    }

    @Override
    public void run() {
        try {
            readRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


