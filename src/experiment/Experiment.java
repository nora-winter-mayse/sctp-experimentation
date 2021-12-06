package experiment;

import client.Client;
import common.SecretManager;
import server.Server;

import java.util.Scanner;

public class Experiment {

    public static void main(String[] args) {
        SecretManager secretManager = new SecretManager();
        Server server = new Server(secretManager);
        Client client = new Client("ClientA", secretManager);

        Thread serverThread = new Thread(server);
        serverThread.start();

        Thread clientThread = new Thread(client);
        clientThread.start();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press any key to finish");
        scanner.nextLine();
        serverThread.interrupt();
        clientThread.interrupt();
    }
}
