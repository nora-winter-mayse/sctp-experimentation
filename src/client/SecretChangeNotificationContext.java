package client;

import java.util.Objects;

public class SecretChangeNotificationContext {

    private final String clientID;

    public SecretChangeNotificationContext(String clientID) {
        this.clientID = clientID;
    }

    public String getClientID() {
        return clientID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecretChangeNotificationContext that = (SecretChangeNotificationContext) o;
        return Objects.equals(clientID, that.clientID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientID);
    }

    @Override
    public String toString() {
        return "SecretChangeNotificationContext{" +
                "clientID='" + clientID + '\'' +
                '}';
    }
}
