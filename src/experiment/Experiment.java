package experiment;

import client.Client;
import server.SecretProviderImpl;
import server.Server;

public class Experiment {

    public static void main(String[] args) {
        Server server = new Server(new SecretProviderImpl());
        Client client = new Client();

        Thread serverThread = new Thread(server);
        serverThread.start();

        Thread clientThread = new Thread(client);
        clientThread.start();
    }
}
