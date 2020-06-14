package ru.stoliarenko.kafka.chat;

import ru.stoliarenko.kafka.chat.client.Client;

public class StartClient {
    public static void main(String[] args) {
        final Client client = new Client();
        client.start();
    }
}
