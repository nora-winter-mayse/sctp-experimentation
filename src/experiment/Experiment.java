package experiment;

import client.Client;
import server.SecretProviderImpl;
import server.Server;

import java.util.Scanner;

public class Experiment {

    public static void main(String[] args) {
        Server server = new Server(new SecretProviderImpl());
        Client client = new Client();

        Thread serverThread = new Thread(server);
        serverThread.start();

        Thread clientThread = new Thread(client);
        clientThread.start();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press any key to finish");
        scanner.nextLine();
    }
}
