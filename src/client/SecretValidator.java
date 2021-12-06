package client;

public interface SecretValidator {

    boolean isSecretCorrect(String clientID, byte[] secret);
}
