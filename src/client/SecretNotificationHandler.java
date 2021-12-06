package client;

import com.sun.nio.sctp.*;

public class SecretNotificationHandler extends AbstractNotificationHandler<SecretChangeNotificationContext> {

    @Override
    public HandlerResult handleNotification(AssociationChangeNotification notification, SecretChangeNotificationContext attachment) {
        System.out.println("An association change notification has been received by " + attachment.getClientID());
        return HandlerResult.CONTINUE;
    }

    @Override
    public HandlerResult handleNotification(PeerAddressChangeNotification notification, SecretChangeNotificationContext attachment) {
        System.out.println("A peer address change notification has been received by " + attachment.getClientID());
        return isPeerAddressChangeRecoverable(notification) ? HandlerResult.CONTINUE : HandlerResult.RETURN;
    }

    @Override
    public HandlerResult handleNotification(SendFailedNotification notification, SecretChangeNotificationContext attachment) {
        System.out.println("An send failed notification has been received by " + attachment.getClientID());
        return HandlerResult.RETURN;
    }

    @Override
    public HandlerResult handleNotification(ShutdownNotification notification, SecretChangeNotificationContext attachment) {
        System.out.println("A shutdown notification has been received by " + attachment.getClientID());
        return HandlerResult.RETURN;
    }

    private boolean isPeerAddressChangeRecoverable(PeerAddressChangeNotification notification) {
        return notification.event() == PeerAddressChangeNotification.AddressChangeEvent.ADDR_AVAILABLE
                || notification.event() == PeerAddressChangeNotification.AddressChangeEvent.ADDR_MADE_PRIMARY
                || notification.event() == PeerAddressChangeNotification.AddressChangeEvent.ADDR_ADDED
                || notification.event() == PeerAddressChangeNotification.AddressChangeEvent.ADDR_CONFIRMED;
    }
}
