package fr.mrcoq;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {

    enum LogType {
        DEBUG(),
        CHAT(),
        EVENT(),
        OTHER();
    }

    private boolean stop = false;
    private PrintWriter writer;
    private Socket socket;

    private FileConfiguration config = Plugin.getInstance().getConfig();
    private Logger logger = Plugin.getInstance().getLogger();

    private String ip;
    private int port;

    public Client() {
        this.ip = this.config.getString("server.ip");

        this.port = this.ip.contains(":")
                ? Integer.parseInt(this.ip.split(":")[1])
                : this.config.getInt("server.port");
    }

    public void start() {

        try {
            socket = new Socket(this.ip, this.port);

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            writer = new PrintWriter(outputStream);

            Scanner reader = new Scanner(inputStream, StandardCharsets.UTF_8);

            while(!stop) {
                if(!reader.hasNext())
                    continue;

                String line = reader.nextLine();
                logger.fine("Client receive : " + line);
            }

        } catch(IOException e) {
            e.printStackTrace();
            this.stop();
        }

    }

    public void stop() {
        writer.println("client-disconnect");
        writer.flush();

        try {
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.stop = true;
    }

    public boolean sendMessage(String message) {
        return sendMessage(LogType.DEBUG, message);
    }

    public boolean sendMessage(LogType logType, String message) {
        boolean success = true;
        try {
            writer.printf("[%s] %s%n", logType.name(), message);
            writer.flush();
        } catch(Exception e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return ip + ":" + port;
    }


}
