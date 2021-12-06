package server;

public interface SecretProvider {

    byte[] generateSecret(String clientID);

    int getSecretSize();
}
