package ru.stoliarenko.kafka.chat;

import ru.stoliarenko.kafka.chat.server.Server;

public class StartServer {
    public static void main(String[] args) {
        final Server server = new Server();
        server.start();
    }
}
