package server;

public interface SecretProvider {

    byte[] generateSecret();

    int getSecretSize();
}
